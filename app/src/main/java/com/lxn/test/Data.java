package com.lxn.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Data extends AppCompatActivity implements View.OnClickListener,Runnable,AdapterView.OnItemClickListener {
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
    int count;
    private ArrayList<HashMap<String,String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    private int msgWhat = 7;
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        app=findViewById(R.id.app);
        user=findViewById(R.id.user);
        btn1 = (Button) findViewById(R.id.button);
        listView = findViewById(R.id.listview);
        btn= findViewById(R.id.button3);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        App_data da = new App_data();
        BmobQuery<App_data> bmobQuery = new BmobQuery<App_data>();
        bmobQuery.findObjects(new FindListener<App_data>() {
            @Override
            public void done(List<App_data> object, BmobException e) {
                if(e==null){
                    count=object.size();
                    tag1=new String[count];
                    tag2=new String[count];
                    content=new String[count];
                    picture=new Object[count];
                    for(int i=0;i<count;i++) {
                        Log.i(TAG,object.get(i).getObjectId() );
                        tag1[i]=object.get(i).getTag1();
                        tag2[i]=object.get(i).getTag2();
                        content[i]=object.get(i).getContent();
//                        picture[i]=object.get(i).getPicture();
                        Log.i(TAG,content[i]);//详细描述
                    }
               init();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname=new ComponentName(Data.this, User.class);
                Intent intent=new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
        btn1.setOnClickListener(this::onClick);
        listView.setOnItemClickListener(this);//对listview的监听
    }
    public void onClick(View v){
        Intent i = new Intent(this, Use_data_1.class);
        startActivity(i);
        Log.i("first","button的事件监听");
    }

    private void init() {
        MyAdapter adapter = new MyAdapter(this,getData(),R.layout.list_item1,
                new String[]{"ItemTag2", "ItemTag1", "ItemContent", "button"},
                new int[]{R.id.itemTag2, R.id.itemTag1, R.id.itemContent, R.id.button3});
        listView.setAdapter(adapter);
    }
    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param listv   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     *
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> listv, View view, int position, long id) {
        Log.i(TAG,"onItemClick: listv="+listv);
        Log.i(TAG,"onItemClick: view="+view);
        Log.i(TAG,"onItemClick: position="+position);
        Log.i(TAG,"onItemClick: id="+id);
        HashMap<String,String> map=(HashMap<String,String>) listv.getItemAtPosition(position);
        String Tag1Str=map.get("ItemTag1");
        String Tag2Str =map.get("ItemTag2");
        String contentStr =map.get("ItemContent");
        Log.i(TAG,"onItemClick: Tag1Str="+Tag1Str);
        Log.i(TAG,"onItemClick: Tag2Str="+Tag2Str);
        TextView title=view.findViewById(R.id.itemTag1);
        String title2=String.valueOf(title.getText());
        Log.i(TAG,"onItemClick: title2="+title2);
        Intent detail = new Intent(this, App_detail.class);
        detail.putExtra("content",contentStr);
        startActivity(detail);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
    private ArrayList<HashMap<String,Object>> getData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
            Log.i(TAG,"3"+String.valueOf(count));//详细描述
        for(int i=count-1;i>=0;i--){
            map = new HashMap<String,Object>();
            map.put("ItemTag1",  tag1[i]);//TAG1
            map.put("ItemTag2",  tag2[i]);//TAG2
            map.put("ItemContent",  content[i]);
            map.put("button", "收藏");
            map.put("picture",R.mipmap.shujujiegou2);
            Log.i(TAG,content[i]);//详细描述
            data.add(map);
        }
        return data;
    }
}