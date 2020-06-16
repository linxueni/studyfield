package com.lxn.test;
/*
* recycleview 的单击监听接口
* */

import android.view.View;

public interface OnItemDeleteListener {
    //Interface接口
    public void onItemDeleteClick(View view, int position);
}
