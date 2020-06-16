package com.lxn.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyappAdapter extends RecyclerView.Adapter<MyappAdapter.MyViewHolder>{
    String TAG="first";
    private Context mContext;
    private List<App_data> mList;
    //定义监听接口
    private OnItemClickListener mOnItemClickListener;


    @NonNull
    @Override
    public MyappAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item1,parent,false);
        MyappAdapter.MyViewHolder myViewHolder = new MyappAdapter.MyViewHolder(itemView);
        return myViewHolder;
    }

    // 第一个参数是上下文环境，第二个参数是每一项的子布局
    public MyappAdapter(List<App_data> mList, Context context) {
        this.mContext = context;
        this.mList = mList;
    }
    @Override
    public void onBindViewHolder(@NonNull MyappAdapter.MyViewHolder holder, int position) {
        //获取数据
        App_data data = mList.get(position);
        Glide.with(mContext).load(data.getPicture().getUrl()).into(holder.image);
        holder.tag1.setText(data.getTag1());
//        Log.i(TAG, "onBindViewHolder: "+data.getTag1());
        holder.tag2.setText(data.getTag2());
        holder.content.setText(data.getContent());

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemLikeListener.onLikeClick(holder.itemView,position);
                Log.i(TAG, "onClick: 单击收藏"+position);
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
    //定义内部类ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        //定义对应的列表项
        private ImageView image;
        private TextView tag1;
        private TextView tag2;
        private TextView content;
        private Button likeBtn;
        private TextView comState;

        //构造方法
        public MyViewHolder(View itemView) {
            super(itemView);
            //获取对应的列表项
            image = itemView.findViewById(R.id.image);
            tag1= itemView.findViewById(R.id.itemTag1);
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
    public interface onItemLikeListener{
        void onLikeClick(View view,int i);
    }

    private onItemLikeListener onItemLikeListener;

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    public void setOnItemLikeListener(MyappAdapter.onItemLikeListener onItemLikeListener) {
        this.onItemLikeListener = onItemLikeListener;
    }
}
