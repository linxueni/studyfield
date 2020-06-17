package com.lxn.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class Data extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private Button btn1;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    TextView app;
    TextView user;
    Handler handler;
    private  Button btn;
    SearchView mSearchView;
    private ListView listView;
    private String TAG="first";
    ArrayAdapter adapter;
    public String[] tag1;
    public String[] tag2;
    public String[] content;
    public Object[] picture;
    private RecyclerView recyclerView;
    private List<App_data> data = new ArrayList<>();//创建集合保存资料信息
    int count;
    boolean isRunning = false;
    private ArrayList<HashMap<String,String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    private int msgWhat = 7;
    List<String> items;
public String id;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        app=findViewById(R.id.app);
        user=findViewById(R.id.user);
        btn1 = (Button) findViewById(R.id.button);
        recyclerView = findViewById(R.id.listview);
        btn= findViewById(R.id.button3);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        String bql = "select * from App_data ORDER BY updatedAt DESC";
        new BmobQuery<App_data>().doSQLQuery(
                bql, new SQLQueryListener<App_data>() {
                    @Override
                    public void done(BmobQueryResult<App_data> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            data = bmobQueryResult.getResults();
                            //获取msg对象，用于返回主线程
//                            Message msg = handler.obtainMessage(7);
//                            msg.obj = data;
//                            handler.sendMessage(msg);
                            recyclerView = findViewById(R.id.listview);  //获得子布局
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                            final MyappAdapter  appAdapter = new MyappAdapter(data,Data.this);
                            //设置动画：采用默认动画效果
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            //设置适配器
                            recyclerView.setAdapter(appAdapter);
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

                            //设置列表监听
                            appAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //获取点击的对象
                                    App_data app_data= data.get(position);
                                    String objectId = app_data.getObjectId();
                                    Log.i(TAG, "onItemClickDF: get了id为"+objectId);
                                    //进入动态详情
                                    Intent intent = new Intent(Data.this,App_detail.class);
                                    intent.putExtra("objectId", objectId);
                                    startActivity(intent);
                                }
                            });
                            appAdapter.setOnItemLikeListener(new MyappAdapter.onItemLikeListener() {
                                @Override
                                public void onLikeClick(View view, int i) {
                                    //获取点击的对象
                                    App_data app_data= data.get(i);
                                    String objectId = app_data.getObjectId();
                                    Log.i(TAG, "onItemClickDF: button get了id为"+objectId);
                                    Collect da = new Collect();
                                    App_data  db = new App_data();
                                    BmobQuery<App_data> bmobQuery = new BmobQuery<App_data>();
                                    bmobQuery.getObject(objectId, new QueryListener<App_data>() {
                                        @Override
                                        public void done(App_data object,BmobException e) {
                                            if(e==null){
                                                Log.i(TAG, "done: "+object.getTag1());
                                                da.setTag1(object.getTag1());
                                                da.setTag2(object.getTag2());
                                                da.setContent(object.getContent());
                                                da.setPicture(object.getPicture());
                                                da.save(new SaveListener<String>() {
                                                    @Override
                                                    public void done(String objectId, BmobException e) {
                                                        if (e == null) {
                                                            Toast.makeText(Data.this, "收藏成功" , Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.i("worong", "wrong:" + e.getMessage());
                                                            Toast.makeText(Data.this, "收藏失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }

                                            else{
                                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                            }
                                            Intent intent = new Intent(Data.this, Hold.class);
                                            Data.this.startActivity(intent);
                                        }

                                    });
                                   }

                            });
                        }else {
                            Log.e(TAG, " errorCF: e2="+e);
                        }
                    }
                });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==7){
                    Log.i(TAG, "handleMessageCF:已返回"+data.size());


                }
            }
        };


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname=new ComponentName(Data.this, User2.class);
                Intent intent=new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
        btn1.setOnClickListener(this::onClick);
    }
    public void onClick(View v){
        Intent i = new Intent(this, Use_data_1.class);
        startActivity(i);
        Log.i("first","button的事件监听");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}