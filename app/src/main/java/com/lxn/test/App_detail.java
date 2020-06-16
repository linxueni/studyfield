package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class App_detail extends AppCompatActivity {
    String TAG ="App_detail";
    List<App_data> result;
    TextView content;
boolean isRunning = false;
ImageView imageView;
    private Handler handler;
    private List<App_data> bookList = new ArrayList<>();//创建集合保存书信息



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        setContentView(R.layout.activity_app_detail);
        content = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

////开启线程
//        new Thread() {
//            public void run() {
//                isRunning = true;
                Intent intent = getIntent();
                String objectId = intent.getStringExtra("objectId");
                Log.i(TAG, "onCreate: 接收objectId=" + objectId);
                //根据objectid获取本条全部信息，填充布局
//              String bdl = "select * from Competition where objectId='"+objectId+"'";
                String bdl = "select * from App_data where objectId='" + objectId + "'";
                new BmobQuery<App_data>().doSQLQuery(
                        bdl, new SQLQueryListener<App_data>() {
                            @Override
                            public void done(BmobQueryResult<App_data> bmobQueryResult, BmobException e) {
                                if (e == null) {
                                    result = bmobQueryResult.getResults();
                                    content.setText(result.get(0).getContent());
                                    Glide.with(App_detail.this).load(result.get(0).getPicture().getUrl()).into(imageView);
                                    Message msg = handler.obtainMessage(5);
                                    msg.obj = bookList;
                                    handler.sendMessage(msg);
                                } else {
                                    Log.e(TAG, " error: e=" + e);
                                }
                            }
                        });
//            }
//        }.start();

    }
}
