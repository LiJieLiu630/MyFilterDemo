package com.liulije.myfilterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.rl_reserch)
    RelativeLayout rlReserch;
    @BindView(R.id.tv_search_result)
    TextView tvSearchResult;
    private static final int SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_reserch)
    public void onClick() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("FLAG", "MainActivity");
        startActivityForResult(intent, SECOND_ACTIVITY);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SECOND_ACTIVITY == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                String SearchResult = data.getStringExtra("RESULT");
                Log.e(TAG, "onActivityResult:" + SearchResult);
                tvSearchResult.setText(SearchResult);
            }
        }
    }
}
