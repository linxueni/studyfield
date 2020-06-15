package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class testb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testb);
        Bmob.initialize(this, "abc00e8153d99c0e780197eebeb9fb1f");
        Person p2 = new Person();
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
//
//                    Toast.makeText(testb.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
//                }else{
//                    Log.i("worong","wrong:"+e.getMessage());
//                    Toast.makeText(testb.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });  //添加
//        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//        bmobQuery.getObject("06e85148f1", new QueryListener<Person>() {
//            @Override
//            public void done(Person object,BmobException e) {
//                if(e==null){
//                    Toast.makeText(testb.this,"查询成功",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(testb.this,"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });   //查询

//        p2.setAddress("北京朝阳");
//        p2.update("746d4b95bd", new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Toast.makeText(testb.this,"更新成功:"+p2.getUpdatedAt(),Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(testb.this,"更新失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//        }); //更新
        p2.setObjectId("06e85148f1");
        p2.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(testb.this,"删除成功:"+p2.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(testb.this,"删除失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
