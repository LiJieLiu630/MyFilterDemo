package com.liulije.myfilterdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.fbl_content)
    FlexboxLayout fblContent;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    @BindView(R.id.tv_sure_selected)
    TextView tvSureSelected;
    //可选选项集合
    private List<SelectedBean> mSelectedList = new ArrayList<>();

    //单选  false 多选
    private boolean singleSelected = false;

    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initSelected();
        addSelectedView();
        sb = new StringBuilder();
    }


    @OnClick(R.id.tv_sure_selected)
    public void onClick() {
        sb.setLength(0);
        for (SelectedBean selectedBean : mSelectedList) {
            if (selectedBean.isSelected()) {
                sb.append(selectedBean.getName()).append(",");
                Log.e(TAG, "onClick: selected" + selectedBean.getName());
            }

        }
        Intent intent = new Intent();
        if (sb != null && sb.length() > 0) {
            intent.putExtra("RESULT", sb.substring(0, sb.length() - 1).toString());
            setResult(RESULT_OK, intent);
        } else {
            sb.append("全部").append(",");
            intent.putExtra("RESULT", sb.substring(0, sb.length() - 1).toString());
            setResult(RESULT_OK, intent);
        }

        finish();

    }

    /**
     * 展示选项
     */
    private void addSelectedView() {
        fblContent.removeAllViews();
        for (int i = 0; i < mSelectedList.size(); i++) {
            String status = mSelectedList.get(i).getName();
            TextView textView = createBaseFlexItemTextView(mSelectedList.get(i));
            textView.setLayoutParams(createDefaultLayoutParams());
            textView.setText(status);
            textView.setTextSize(13);
            textView.setTag(i);
            fblContent.addView(textView);
        }
    }

    private TextView createBaseFlexItemTextView(SelectedBean selectedBean) {
        final TextView textView = new TextView(this);
        textView.setText(selectedBean.getName());
        textView.setPadding(30, 8, 30, 8);
        textView.setGravity(Gravity.CENTER);
        if (selectedBean.isSelected()) {
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.selected_textbg);
        } else {
            textView.setTextColor(Color.parseColor("#535353"));
            textView.setBackgroundResource(R.drawable.unselected_textbg);
        }
        if (singleSelected) {
            dealSingle(textView, selectedBean);
        } else {
            selectedMore(textView, selectedBean);
        }
        return textView;
    }

    /**
     * 多选
     *
     * @param mTextView
     * @param bean
     */
    private void selectedMore(final TextView mTextView, final SelectedBean bean) {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isSelected()) {
                    bean.setSelected(false);
                    mTextView.setTextColor(Color.parseColor("#535353"));
                    mTextView.setBackgroundResource(R.drawable.unselected_textbg);
                } else {
                    if (bean.getId() != 0) {//点击的不是全部
                        setOtherStatus(0,false);
                    } else {
                        for (int i = 0; i < mSelectedList.size(); i++) {
                            SelectedBean selectedBean = mSelectedList.get(i);
                            if (selectedBean.getId() != 0) {
                                setOtherStatus(i,false);
                            }
                        }
                    }
                    bean.setSelected(true);
                    mTextView.setTextColor(Color.WHITE);
                    mTextView.setBackgroundResource(R.drawable.selected_textbg);

                }
            }
        });
    }

    /**
     * 整理其它数据的选中状态
     *
     * @param i
     */
    private void setOtherStatus(int i,boolean selected) {
        mSelectedList.get(i).setSelected(selected);
        TextView textView = (TextView) fblContent.getChildAt(i);
        textView.setTextColor(Color.parseColor("#535353"));
        textView.setBackgroundResource(R.drawable.unselected_textbg);
    }

    /**
     * 单选处理数据
     *
     * @param mTextView
     */
    private void dealSingle(final TextView mTextView, final SelectedBean bean) {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setTextColor(Color.parseColor("#535353"));
                mTextView.setBackgroundResource(R.drawable.unselected_textbg);
                int tag = (int) mTextView.getTag();
                if (bean.isSelected()) {
                    bean.setSelected(false);
                } else {
                    bean.setSelected(true);
                    mTextView.setTextColor(Color.WHITE);
                    mTextView.setBackgroundResource(R.drawable.selected_textbg);
                    for (int j = 0; j < fblContent.getChildCount(); j++) {
                        TextView mAll = (TextView) fblContent.getChildAt(j);
                        if ((int) mAll.getTag() != tag) {
                            mSelectedList.get(j).setSelected(false);
                            mAll.setTextColor(Color.parseColor("#535353"));
                            mAll.setBackgroundResource(R.drawable.unselected_textbg);
                        }
                    }
                }
            }
        });
    }

    private ViewGroup.LayoutParams createDefaultLayoutParams() {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //它假设每个子项的 layout_flexGrow 属性的值设为 1，那么剩余空间将均匀分配到每个子项。默认为0
//        lp.flexGrow = 1;
        lp.setMargins(0, 40, 40, 0);
//        lp.setMargins(10, 5, 10, 5);
        return lp;
    }

    /**
     * 初始化选项
     */
    private void initSelected() {
        String[] StatusArray = getResources().getStringArray(R.array.selected_type);
        for (int i = 0; i < StatusArray.length; i++) {
            SelectedBean bean = new SelectedBean(StatusArray[i], false, i);
            if (i == 0) {//默认选中第一个
                bean.setSelected(true);
            } else {
                bean.setSelected(false);
            }
            mSelectedList.add(bean);
        }
    }

}
