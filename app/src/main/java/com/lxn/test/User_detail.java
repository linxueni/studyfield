package com.lxn.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class User_detail extends AppCompatActivity {
    TextView Content;
    TextView Price;
    String TAG ="first";
    List<User_data> result;
    ImageView imageView;
    private Handler handler;
    private List<App_data> bookList = new ArrayList<>();//创建集合保存书信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        setContentView(R.layout.activity_user_detail);
        Content = findViewById(R.id.textView);
        Price = findViewById(R.id.down);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        String objectId = intent.getStringExtra("objectId");
        Log.i(TAG, "onCreate: 接收objectId=" + objectId);
        //根据objectid获取本条全部信息，填充布局
        String bdl = "select * from User_data where objectId='" + objectId + "'";
        new BmobQuery<User_data>().doSQLQuery(
                bdl, new SQLQueryListener<User_data>() {
                    @Override
                    public void done(BmobQueryResult<User_data> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            result = bmobQueryResult.getResults();
                            Content.setText(result.get(0).getContent());
                            Price.setText("购买：" + result.get(0).getPrice() + "元");
                            Glide.with(User_detail.this).load(result.get(0).getPicture().getUrl()).into(imageView);
                            Message msg = handler.obtainMessage(5);
                            msg.obj = bookList;
                            handler.sendMessage(msg);
                        } else {
                            Log.e(TAG, " error: e=" + e);
                        }
                    }
                });
    }


    }
