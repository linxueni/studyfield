package com.lxn.test;

import cn.bmob.v3.BmobObject;

public class Collect extends BmobObject {
    private String tag1;
    private String tag2;
    private String content;
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
}
