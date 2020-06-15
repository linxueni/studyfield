package com.lxn.test;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Hold extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    private ListView listView;
    private String TAG = "first";
    ArrayAdapter adapter;
    public String[] tag1;
    public String[] tag2;
    public String[] content;
    Button btn;
    Button btnr1;
    Button btnr2;
    int count;
    private ArrayList<HashMap<String, Object>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    Collect da;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_hold);
        listView = findViewById(R.id.listview);
        btn = findViewById(R.id.button3);
        btnr1=findViewById(R.id.return1);
        btnr1.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View arg0) {
                                         ComponentName componentname = new ComponentName(Hold.this, Data.class);
                                         Intent intent = new Intent();
                                         intent.setComponent(componentname);
                                         startActivity(intent);
                                     }
                                 });
        btnr2=findViewById(R.id.return2);
        btnr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ComponentName componentname = new ComponentName(Hold.this, User.class);
                Intent intent = new Intent();
                intent.setComponent(componentname);
                startActivity(intent);
            }
        });
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        da= new Collect();
        BmobQuery<Collect> bmobQuery = new BmobQuery<Collect>();
        bmobQuery.findObjects(new FindListener<Collect>() {
            @Override
            public void done(List<Collect> object, BmobException e) {
                if (e == null) {
                    count = object.size();
                    tag1 = new String[count];
                    tag2 = new String[count];
                    content = new String[count];
                    for (int i = 0; i < count; i++) {
                        Log.i(TAG, object.get(i).getObjectId());
                        tag1[i] = object.get(i).getTag1();
                        tag2[i] = object.get(i).getTag2();
                        content[i] = object.get(i).getContent();
                        Log.i(TAG,"得到的content数组："+ content[i]);//详细描述
                    }
                    init();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        listView.setAdapter(listItemAdapter);
    }

    private void init() {

         MyCollectAdapter   listItemAdapter = new MyCollectAdapter(this, getData(),//listTtems数据源
                    R.layout.list_collect,//ListItem的XML布局实现
                    new String[]{"ItemTag1", "ItemTag2", "ItemContent", "button"},
                    new int[]{R.id.itemTag1, R.id.itemTag2, R.id.itemContent, R.id.button3}
            );
            listView.setAdapter(listItemAdapter);
            listView.setOnItemClickListener(this);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
    private ArrayList<HashMap<String,Object>> getData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
        Log.i(TAG,"3456"+String.valueOf(count));//详细描述
        for(int i=count-1;i>=0;i--){
            map = new HashMap<String,Object>();
            map.put("ItemTag1",  tag1[i]);//TAG1
            map.put("ItemTag2",  tag2[i]);//TAG2
            map.put("ItemContent",  content[i]);
            map.put("button", "删除");
            Log.i(TAG,content[i]);//详细描述
            data.add(map);
        }
        return data;
    }
}
