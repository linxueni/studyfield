package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Use_data_1 extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    EditText Price;
    User_data da;
    private String photoPath;
    private String TAG="first";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_data_1);
        Tag1=findViewById(R.id.editText5);
        Tag2=findViewById(R.id.editText2);
        Content=findViewById(R.id.editText);
        Price=findViewById(R.id.editText4);
        btn1 = (Button) findViewById(R.id.button2);
        btn2 = (Button) findViewById(R.id.button5);
        btn2.setOnClickListener(this::onClick2) ;
        btn1.setOnClickListener(this::onClick);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        da= new User_data();
    }
    public void onClick2(View v) {
        if (ActivityCompat.checkSelfPermission(Use_data_1.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Use_data_1.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"),
                101);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (datas != null) {
                try {
                    Uri uri = datas.getData();
                    // 这里开始的第二部分，获取图片的路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = null;
                    if (Build.VERSION.SDK_INT < 11) {
                        cursor = managedQuery(uri, proj, null, null,
                                null);
                    } else {
                        CursorLoader cursorLoader = new CursorLoader(Use_data_1.this, uri, proj,
                                null, null, null);
                        cursor = cursorLoader.loadInBackground();
                    }
                    // 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.
                            Media.DATA);
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    photoPath = cursor.getString(column_index);
                    if (!photoPath.equals("") || photoPath != null) {
                        Log.i(TAG, "onActivityResult: "+photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length()-1));
                        Log.i(TAG, "-----str---->路径  " + photoPath);
                        Log.i(TAG, "----uri---->路径  " + Uri.parse(photoPath));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onClick(View v){
        if(!(Tag1.getText().toString()).equals("")||!(Tag2.getText().toString()).equals("")||!(Content.getText().toString()).equals("")||!(Price.getText().toString()).equals("")) {
            File file=new File(photoPath);
            BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        Log.i(TAG, "done: "+"上传了图片");
                        da.setTag1(Tag1.getText().toString());
                        da.setTag2(Tag2.getText().toString());
                        da.setContent(Content.getText().toString());
                        da.setPrice(Price.getText().toString());
                        da.setPicture(bmobFile);
                        da.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(Use_data_1.this, "上传笔记成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i(TAG, "wrong:" + e.getMessage());
                                    Toast.makeText(Use_data_1.this, "上传笔记失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Log.i(TAG, "done:上传图片失败： "+e.getMessage());
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }
        else{
            Toast.makeText(Use_data_1.this, "信息不完善，不能上传", Toast.LENGTH_SHORT).show();

        }
        Intent i = new Intent(this, User2.class);
        startActivity(i);
}



}
