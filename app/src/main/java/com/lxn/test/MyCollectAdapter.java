package com.lxn.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyCollectAdapter extends  RecyclerView.Adapter<MyCollectAdapter.MyViewHolder> {
    String TAG = "first";
    private Context mContext;
    private List<Collect> mList;
    //定义监听接口
    private OnItemClickListener mOnItemClickListener;
    @NonNull
    @Override
    public MyCollectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_collect, parent, false);
        MyCollectAdapter.MyViewHolder myViewHolder = new MyCollectAdapter.MyViewHolder(itemView);
        return myViewHolder;
    }
    // 第一个参数是上下文环境，第二个参数是每一项的子布局
    public MyCollectAdapter(List<Collect> mList, Context context) {
        this.mContext = context;
        this.mList = mList;
    }




    @Override
    public void onBindViewHolder(@NonNull MyCollectAdapter.MyViewHolder holder, int position) {
//获取数据
        Collect co = mList.get(position);
        Glide.with(mContext).load(co.getPicture().getUrl()).into(holder.image);
        Log.i(TAG, "onBindViewHolder: "+co.getTag1());
        holder.tag1.setText(co.getTag1());
        holder.tag2.setText(co.getTag2());
        holder.content.setText(co.getContent());
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(holder.itemView, position);
                Log.i(TAG, "onClick: 单击删除"+position);
            }
        });
        //设置点击事件
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);

                }
            });
        }


    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //定义对应的列表项
        private ImageView image;
        private TextView tag1;
        private TextView tag2;
        private TextView content;
        private Button likeBtn;

        //构造方法
        public MyViewHolder(View itemView) {
            super(itemView);
            //获取对应的列表项
            image = itemView.findViewById(R.id.image);
            tag1 = itemView.findViewById(R.id.itemTag1);
            tag2 = itemView.findViewById(R.id.itemTag2);
            content = itemView.findViewById(R.id.itemContent);
            likeBtn = itemView.findViewById(R.id.button3);
        }
    }
    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(View view, int i);
    }

    private MyCollectAdapter.onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(MyCollectAdapter.onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

}
