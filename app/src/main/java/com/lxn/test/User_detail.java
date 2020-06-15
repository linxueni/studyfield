package com.lxn.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class User_detail extends AppCompatActivity {
    TextView Content;
    TextView Price;
    String TAG ="first";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Content=findViewById(R.id.textView);
        String content=getIntent().getStringExtra("content");
        Log.i(TAG,"onItemClick: huoqu="+content);
        Content.setText(content);
        Price=findViewById(R.id.down);
        String price=getIntent().getStringExtra("price");
        Price.setText(price);
    }
}
