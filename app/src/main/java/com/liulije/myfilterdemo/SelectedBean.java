package com.liulije.myfilterdemo;

/**
 * @类名称: CLASS
 * @类描述:
 * @创建人：
 * @创建时间：2017/7/28 11:22
 * @备注：
 */
public class SelectedBean {
    private String name;
    private boolean selected;
    private int id;

    @Override
    public String toString() {
        return "SelectedBean{" +
                "name='" + name + '\'' +
                ", ifSelected=" + selected +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SelectedBean(String name, boolean ifSelected, int id) {

        this.name = name;
        this.selected = ifSelected;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean ifSelected) {
        this.selected = ifSelected;
    }

    public SelectedBean(String name, boolean Selected) {

        this.name = name;
        this.selected = Selected;
    }
}
