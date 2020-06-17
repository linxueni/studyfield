package com.lxn.test;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class User2 extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView listView;
    private String TAG="first";
    Handler handler;
    ArrayAdapter adapter;
    public String[] tag1;
    public String[] tag2;
    public String[] content;
    public String[] price;
    int count;
    TextView app;
    private RecyclerView recyclerView;
    boolean isRunning=false;
    Button btn1;
    int[] image_expense;
    private List<User_data> data = new ArrayList<>();//创建集合保存资料信息
    private SimpleAdapter listItemAdapter; // 适配器
    private int msgWhat = 7;
    List<String> items;
    SwipeRefreshLayout refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        recyclerView = findViewById(R.id.listview);
        app=findViewById(R.id.app);
        refresh=findViewById(R.id.refresh);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        String bql = "select * from User_data ORDER BY updatedAt DESC";
        new BmobQuery<User_data>().doSQLQuery(
                bql, new SQLQueryListener<User_data>() {
                    @Override
                    public void done(BmobQueryResult<User_data> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            data = bmobQueryResult.getResults();
                            recyclerView = findViewById(R.id.listview);  //获得子布局
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                            final MyuseAdapter  useAdapter = new MyuseAdapter(data,User2.this);

                            //设置动画：采用默认动画效果
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            //设置适配器
                            recyclerView.setAdapter(useAdapter);
                            //通过mRecyclerView.addItemDecoration(RecyclerView.ItemDecoration itemDecoration)方法添加分割线
                            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                @Override
                                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                                    super.getItemOffsets(outRect, view, parent, state);
                                    //getItemOffsets中的参数Rect的left、top、right、bottom的值即为每个item距离左上右下的距离。
                                    outRect.set(0, 0, 0, 0);
                                }
                            });


//
                            //设置列表监听
                            useAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //获取点击的对象
                                    User_data use_data= data.get(position);
                                    String objectId = use_data.getObjectId();
                                    Log.i(TAG, "onItemClickDF: get了id为"+objectId);
                                    //进入动态详情
                                    Intent intent = new Intent(User2.this,User_detail.class);
                                    intent.putExtra("objectId", objectId);
                                    startActivity(intent);
                                }
                            });
                            useAdapter.setOnItemLikeListener(new MyuseAdapter.onItemLikeListener() {
                                @Override
                                public void onLikeClick(View view, int i) {
                                    //获取点击的对象
                                    User_data use_data = data.get(i);
                                    String objectId = use_data.getObjectId();
                                    Log.i(TAG, "onItemClickDF: button get了id为" + objectId);
                                    Collect da = new Collect();
                                    User_data db = new User_data();
                                    BmobQuery<User_data> bmobQuery = new BmobQuery<User_data>();
                                    bmobQuery.getObject(objectId, new QueryListener<User_data>() {
                                        @Override
                                        public void done(User_data object, BmobException e) {
                                            if (e == null) {
                                                Log.i(TAG, "done: " + object.getTag1());
                                                da.setTag1(object.getTag1());
                                                da.setTag2(object.getTag2());
                                                da.setContent(object.getContent());
                                                da.setPicture(object.getPicture());
                                                da.save(new SaveListener<String>() {
                                                    @Override
                                                    public void done(String objectId, BmobException e) {
                                                        if (e == null) {
                                                            Toast.makeText(User2.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.i("worong", "wrong:" + e.getMessage());
                                                            Toast.makeText(User2.this, "收藏失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                            }
                                            Intent intent = new Intent(User2.this, Hold.class);
                                            User2.this.startActivity(intent);
                                        }

                                    });
                    }
                });
    }
                        else{
                            Log.i(TAG, "done: "+e.getMessage());
                        }
}
});
        refresh.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.white});
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String bdl = "select  * from User_data order by -updatedAt";
                new BmobQuery<User_data>().doSQLQuery(
                        bdl, new SQLQueryListener<User_data>() {
                            @Override
                            public void done(BmobQueryResult<User_data> bmobQueryResult, BmobException e) {
                                if (e == null) {
                                    data = bmobQueryResult.getResults();
                                    Log.i(TAG, "doneDF: dataList2=" + data);
                                }else {
                                    Log.e(TAG, " errorDF: e2="+e);
                                }
                            }
                        });


            }
        });
        btn1 = (Button) findViewById(R.id.button);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname = new ComponentName(User2.this, Data.class);
                Intent intent = new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
        btn1.setOnClickListener(this::onClick);
        }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
    Intent i=new Intent(this,Use_data_1.class);
    startActivity(i);
//    onDestroy();
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.listview);
        app = findViewById(R.id.app);
        refresh = findViewById(R.id.refresh);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        Log.i(TAG, "onResume: 进入重新加载");
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(200000);//休眠3秒
                    String bql = "select * from User_data ORDER BY updatedAt DESC";
                    new BmobQuery<User_data>().doSQLQuery(
                            bql, new SQLQueryListener<User_data>() {
                                @Override
                                public void done(BmobQueryResult<User_data> bmobQueryResult, BmobException e) {
                                    if (e == null) {
                                        data = bmobQueryResult.getResults();
                                        Log.i(TAG, "done: onRestart获取的数据"+data.get(0).getContent());
                                        recyclerView = findViewById(R.id.listview);  //获得子布局
                                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                                        final MyuseAdapter  useAdapter = new MyuseAdapter(data,User2.this);

                                        //设置动画：采用默认动画效果
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        //设置适配器
                                        recyclerView.setAdapter(useAdapter);
                                        //通过mRecyclerView.addItemDecoration(RecyclerView.ItemDecoration itemDecoration)方法添加分割线
                                        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                            @Override
                                            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                                                super.getItemOffsets(outRect, view, parent, state);
                                                //getItemOffsets中的参数Rect的left、top、right、bottom的值即为每个item距离左上右下的距离。
                                                outRect.set(0, 0, 0, 0);
                                            }
                                        });


//
                                        //设置列表监听
                                        useAdapter.setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                //获取点击的对象
                                                User_data use_data= data.get(position);
                                                String objectId = use_data.getObjectId();
                                                Log.i(TAG, "onItemClickDF: get了id为"+objectId);
                                                //进入动态详情
                                                Intent intent = new Intent(User2.this,User_detail.class);
                                                intent.putExtra("objectId", objectId);
                                                startActivity(intent);
                                            }
                                        });
                                        useAdapter.setOnItemLikeListener(new MyuseAdapter.onItemLikeListener() {
                                            @Override
                                            public void onLikeClick(View view, int i) {
                                                //获取点击的对象
                                                User_data use_data = data.get(i);
                                                String objectId = use_data.getObjectId();
                                                Log.i(TAG, "onItemClickDF: button get了id为" + objectId);
                                                Collect da = new Collect();
                                                User_data db = new User_data();
                                                BmobQuery<User_data> bmobQuery = new BmobQuery<User_data>();
                                                bmobQuery.getObject(objectId, new QueryListener<User_data>() {
                                                    @Override
                                                    public void done(User_data object, BmobException e) {
                                                        if (e == null) {
                                                            Log.i(TAG, "done: " + object.getTag1());
                                                            da.setTag1(object.getTag1());
                                                            da.setTag2(object.getTag2());
                                                            da.setContent(object.getContent());
                                                            da.setPicture(object.getPicture());
                                                            da.save(new SaveListener<String>() {
                                                                @Override
                                                                public void done(String objectId, BmobException e) {
                                                                    if (e == null) {
                                                                        Toast.makeText(User2.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        Log.i("worong", "wrong:" + e.getMessage());
                                                                        Toast.makeText(User2.this, "收藏失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                                        }
                                                        Intent intent = new Intent(User2.this, Hold.class);
                                                        User2.this.startActivity(intent);
                                                    }

                                                });
                                            }
                                        });
                                    }
                                    else{
                                        Log.i(TAG, "done: "+e.getMessage());
                                    }
                                }
                            });
                    refresh.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.white});
                    refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            String bdl = "select  * from User_data order by -updatedAt";
                            new BmobQuery<User_data>().doSQLQuery(
                                    bdl, new SQLQueryListener<User_data>() {
                                        @Override
                                        public void done(BmobQueryResult<User_data> bmobQueryResult, BmobException e) {
                                            if (e == null) {
                                                data = bmobQueryResult.getResults();
                                                Log.i(TAG, "doneDF: dataList2=" + data);
//                                                                useAdapter.notifyDataSetChanged();
//                                                                recyclerView.setAdapter(useAdapter);
                                            }else {
                                                Log.e(TAG, " errorDF: e2="+e);
                                            }
                                        }
                                    });
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */

            }
        }.start();
        btn1 = (Button) findViewById(R.id.button);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname = new ComponentName(User2.this, Data.class);
                Intent intent = new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
        btn1.setOnClickListener(this::onClick);
    }
}
