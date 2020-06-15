package com.lxn.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MyAdapter extends SimpleAdapter {
    //上下文
    Context context;
String TAG="first";
String tag1;
    String tag2;
    String content;
    public MyAdapter(Context context,
                     List<? extends Map<String, ?>> data, int resource, String[] from,
                     int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = super.getView(i, convertView, viewGroup);
        final Button btn = (Button) view.findViewById(R.id.button3);
        btn.setTag(i);//设置标签

        btn.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i("first", "try"+String.valueOf(btn.getTag()));
                Bmob.initialize(context, "5b5e2b1c75af83d4316468eb9ae614c4");
                Collect da = new Collect();
                App_data db = new App_data();
                BmobQuery<App_data> bmobQuery = new BmobQuery<App_data>();
                bmobQuery.findObjects(new FindListener<App_data>() {
                    @Override
                    public void done(List<App_data> object, BmobException e) {
                        if(e==null){
                            int a=object.size();
                            Log.i(TAG,object.get(a-1-(Integer) btn.getTag()).getObjectId() );
                            tag1=object.get(a-1-(Integer) btn.getTag()).getTag1();
                            Log.i(TAG,"TAG!:"+tag1);
                            tag2=object.get(a-1-(Integer) btn.getTag()).getTag2();
                            content=object.get(a-1-(Integer) btn.getTag()).getContent();
                            da.setTag1(tag1);
                            da.setTag2(tag2);
                            da.setContent(content);
                            da.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(context, "收藏成功" + objectId, Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("worong", "wrong:" + e.getMessage());
                                    Toast.makeText(context, "收藏失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                            Intent i = new Intent(context, Hold.class);
                            context.startActivity(i);
                        }
                        else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });

            }
        });
        return view;
    }
}
