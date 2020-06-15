package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class App_detail extends AppCompatActivity {
TextView Content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        Content=findViewById(R.id.textView);
        String content=getIntent().getStringExtra("content");
        Content.setText(content);
    }
}
