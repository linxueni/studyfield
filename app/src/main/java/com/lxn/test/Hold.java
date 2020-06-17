package com.lxn.test;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Hold extends AppCompatActivity {
    private ListView listView;
    private String TAG = "first";
    ArrayAdapter adapter;
    public String tag1;
    public String tag2;
    public String content;
    public String id;
    Button btn;
    Button btnr1;
    Button btnr2;
    int count;
    private List<Collect> collect = new ArrayList<>();//创建集合保存资料信息
    private SimpleAdapter listItemAdapter; // 适配器
    Collect da;
    private List<Like> likeList = new ArrayList<>();//创建集合保存资料信息
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_hold);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        String bql = "select * from Collect ORDER BY updatedAt DESC";
        new BmobQuery<Collect>().doSQLQuery(
                bql, new SQLQueryListener<Collect>() {
                    @Override
                    public void done(BmobQueryResult<Collect> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            collect = bmobQueryResult.getResults();
                            //获取msg对象，用于返回主线程
//                            Message msg = handler.obtainMessage(7);
//                            msg.obj = data;
//                            handler.sendMessage(msg);
                            recyclerView = findViewById(R.id.listview);  //获得子布局
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                            final MyCollectAdapter collectAdapter = new MyCollectAdapter(collect, Hold.this);
                            //设置动画：采用默认动画效果
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            //设置适配器
                            recyclerView.setAdapter(collectAdapter);
                            //通过mRecyclerView.addItemDecoration(RecyclerView.ItemDecoration itemDecoration)方法添加分割线
                            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                @Override
                                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                                    super.getItemOffsets(outRect, view, parent, state);
                                    //getItemOffsets中的参数Rect的left、top、right、bottom的值即为每个item距离左上右下的距离。
                                    outRect.set(0, 0, 0, 0);
                                }
                            });
                            //Toast.makeText(Data.this, "已刷新为该日期", Toast.LENGTH_SHORT).show();
                             collectAdapter.setOnItemDeleteClickListener(new MyCollectAdapter.onItemDeleteListener() {
                                 @Override
                                 public void onDeleteClick(View view, int i) {

                                         //获取点击的对象
                                         Collect co= collect.get(i);
                                         String objectId = co.getObjectId();
                                         Log.i(TAG, "onItemClickDF: get了id为"+objectId);
                                     co.setObjectId(objectId);
                                     co.delete(new UpdateListener() {

                                         @Override
                                         public void done(BmobException e) {
                                             if(e==null){
                                                 Toast.makeText(Hold.this, "删除成功" , Toast.LENGTH_SHORT).show();
                                             }else{
                                                 Toast.makeText(Hold.this, "删除失败" , Toast.LENGTH_SHORT).show();
                                             }
                                             Intent intent = new Intent(Hold.this, Hold.class);
                                             Hold.this.startActivity(intent);
                                         }

                                     });
                                 }

                             });
                            //设置列表监听
                            collectAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //获取点击的对象
                                    Collect co = collect.get(position);
                                    String objectId = co.getObjectId();
                                    Log.i(TAG, "onItemClickDF: get了id为" + objectId);
                                    //进入动态详情
                                    Intent intent = new Intent(Hold.this, App_detail.class);
                                    intent.putExtra("objectId", objectId);
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                });
        btnr1=findViewById(R.id.return1);
        btnr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname=new ComponentName(Hold.this, Data.class);
                Intent intent=new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
        btnr2=findViewById(R.id.return2);
        btnr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname=new ComponentName(Hold.this, User2.class);
                Intent intent=new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
    }
}