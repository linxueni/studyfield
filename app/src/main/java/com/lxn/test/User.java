package com.lxn.test;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class User extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
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
    Button btn1;
    int[] image_expense;
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    private int msgWhat = 7;
    List<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        listView = findViewById(R.id.listview);
        app=findViewById(R.id.app);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        User_data da = new User_data();
        btn1 = (Button) findViewById(R.id.button);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname=new ComponentName(User.this, Data.class);
                Intent intent=new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }

        });
        BmobQuery<User_data> bmobQuery = new BmobQuery<User_data>();
        bmobQuery.findObjects(new FindListener<User_data>() {
            @Override
            public void done(List<User_data> object, BmobException e) {
                if(e==null){
                    count=object.size();
                    tag1=new String[count];
                    tag2=new String[count];
                    content=new String[count];
                    price=new String[count];
                    for(int i=0;i<count;i++) {
                        Log.i(TAG,object.get(i).getObjectId() );
                        tag1[i]=object.get(i).getTag1();
                        tag2[i]=object.get(i).getTag2();
                        content[i]=object.get(i).getContent();
                        price[i]=object.get(i).getPrice();
                        Log.i(TAG,content[i]);//详细描述
                    }
                    init();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void init() {
//        Log.i(TAG,String.valueOf(count));
//        listItems=new ArrayList<HashMap<String, String>>();
//        for(int i=count-1;i>=0;i--){
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ItemTag1",  tag1[i]);//TAG1
//            map.put("ItemTag2",  tag2[i]);//TAG2
//            map.put("ItemContent",  content[i]);//详细描述
//            map.put("ItemPrice", "购买："+ price[i]+"元");//详细描述
//            Log.i(TAG,content[i]);//详细描述
//            listItems.add(map);
//        }
        //生成适配器的Item和动态数组对应的元素
//        listItemAdapter = new SimpleAdapter(this, listItems,//listTtems数据源
//                R.layout.list_item,//ListItem的XML布局实现
//                new String[]{"ItemTag1", "ItemTag2","ItemContent","ItemPrice"},
//                new int[]{R.id.itemTag1, R.id.itemTag2,R.id.itemContent,R.id.itemprice}
//        );
        MyuseAdapter adapter = new MyuseAdapter(this,getData(),R.layout.list_item,
                new String[]{"ItemTag2", "ItemTag1", "ItemContent", "ItemPrice","button"},
                new int[]{R.id.itemTag2, R.id.itemTag1, R.id.itemContent, R.id.itemprice,R.id.button3});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);//对listview的监听
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
        String priceStr =map.get("ItemPrice");
        Log.i(TAG,"onItemClick: Tag1Str="+Tag1Str);
        Log.i(TAG,"onItemClick: Tag2Str="+Tag2Str);
        TextView title=view.findViewById(R.id.itemTag1);
        String title2=String.valueOf(title.getText());
        Log.i(TAG,"onItemClick: title2="+title2);
        Intent i = new Intent(this, User_detail.class);
        Log.i(TAG,"onItemClick:content="+contentStr);
        Log.i(TAG,"onItemClick:price="+priceStr);
        i.putExtra("content",contentStr);
        i.putExtra("price",priceStr);
        startActivity(i);
    }
    private ArrayList<HashMap<String,Object>> getData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
        Log.i(TAG,"7   "+String.valueOf(count));//详细描述
        for(int i=count-1;i>=0;i--){
            map = new HashMap<String,Object>();
            map.put("ItemTag1",  tag1[i]);//TAG1
            map.put("ItemTag2",  tag2[i]);//TAG2
            map.put("ItemContent",  content[i]);
            map.put("ItemPrice", "购买："+ price[i]+"元");//详细描述
            map.put("button", "收藏");
            Log.i(TAG,content[i]);//详细描述
            data.add(map);
        }
        return data;
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, Use_data_1.class);
        startActivity(i);
        Log.i("first","button的事件监听");
    }

}

