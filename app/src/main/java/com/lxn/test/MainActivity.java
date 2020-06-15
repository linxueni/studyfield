package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        ListView listview = (ListView) findViewById(R.id.listview);;
        MyAdapter adapter = new MyAdapter(this,getData(),R.layout.list_item,
                new String[]{"image", "name", "size", "button"},
                new int[]{R.id.itemTag2, R.id.itemTag1, R.id.itemContent, R.id.button3});
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("first","我是item点击事件 i = " + i + "l = " + l);
            }
        });
    }
    private ArrayList<HashMap<String,Object>> getData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.jiantou);
        map.put("name", "王者农药");
        map.put("size", "30dp");
        map.put("button", "下载");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.jiantou);
        map.put("name", "吃鸡战场");
        map.put("size", "30dp");
        map.put("button", "下载");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.shujujiegou2);
        map.put("name", "氪金");
        map.put("size", "30dp");
        map.put("button", "下载");
        data.add(map);
        return data;

    }

}
