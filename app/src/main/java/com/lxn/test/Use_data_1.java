package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Use_data_1 extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    EditText Price;
    User_data da;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_data_1);
        Tag1=findViewById(R.id.editText5);
        Tag2=findViewById(R.id.editText2);
        Content=findViewById(R.id.editText);
        Price=findViewById(R.id.editText4);
        btn1 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(this);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        da= new User_data();
    }  public void onClick(View v){
        if(!(Tag1.getText().toString()).equals("")||!(Tag2.getText().toString()).equals("")||!(Content.getText().toString()).equals("")||!(Price.getText().toString()).equals("")) {
            da.setTag1(Tag1.getText().toString());
            da.setTag2(Tag2.getText().toString());
            da.setContent(Content.getText().toString());
            da.setPrice(Price.getText().toString());
            da.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Toast.makeText(Use_data_1.this, "上传笔记成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("worong", "wrong:" + e.getMessage());
                        Toast.makeText(Use_data_1.this, "上传笔记失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            Intent i = new Intent(this,SecondFragment.class);
//            startActivity(i);
        }
        else{
            Toast.makeText(Use_data_1.this, "信息不完善，不能上传", Toast.LENGTH_SHORT).show();

        }
        Intent i = new Intent(this, User2.class);
        startActivity(i);

//    Intent i = new Intent(this, Data.class);
//    startActivity(i);
}



}
