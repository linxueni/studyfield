package com.lxn.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyCollectAdapter extends SimpleAdapter {
    //上下文
    Context context;
    String TAG="first";
    String tag1;
    String tag2;
    String content;
    String id;
    public MyCollectAdapter(Context context,
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("first","开始");
                Log.i("first", "try"+String.valueOf(btn.getTag()));
                Bmob.initialize(context, "5b5e2b1c75af83d4316468eb9ae614c4");
                Collect p2 = new Collect();
                BmobQuery<Collect> bmobQuery = new BmobQuery<Collect>();
                bmobQuery.findObjects(new FindListener<Collect>() {

                    @Override
                    public void done(List<Collect> object, BmobException e) {
                        Log.i("first","开始1111");
                        if (e == null) {
                            int a=object.size();
                            Log.i(TAG,object.get(a-1-(Integer) btn.getTag()).getObjectId() );
                            id = object.get(a-1-(Integer) btn.getTag()).getObjectId();
                            p2.setObjectId(id);
                            p2.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context, Hold.class);
                                        context.startActivity(i);
                                    }
                                    else{
                                        Log.i("first",e.getMessage());
                                        Toast.makeText(context,"删除失败" ,Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }

                        else {
                            Log.i("bmob", "失败");
                        }
                    }
                });
//                p2.setObjectId(id);
//                p2.delete(new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if(e==null){
//                                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(context, Hold.class);
//                                context.startActivity(i);
//                            }
//                            else{
//                                Log.i("first",e.getMessage());
//                                Toast.makeText(context,"删除失败" ,Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                });
            }
        });
        return view;
    }
}
