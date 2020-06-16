package com.lxn.test;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class App_data extends BmobObject {
    private String tag1;
    private String tag2;
    private String content;
    private BmobFile picture;
    public String getTag1() {
        return tag1;
    }
    public void setTag1(String tag) {
        this.tag1 = tag;
    }
    public String getTag2() {
        return tag2;
    }
    public void setTag2(String tag) {
         this.tag2 = tag;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }
    public  BmobFile getPicture() {
        return picture;
    }
}
