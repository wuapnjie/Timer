package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.R;

import java.util.List;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class MyWishesAdapter extends RecyclerView.Adapter<MyWishesAdapter.WishesViewHolder> {
    private final String TAG = MyWishesAdapter.class.getSimpleName();

    private onRecylerViewItemLongClickListener onRecylerViewItemLongClickListener=null;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<CommonBean> mList = null;
    private boolean mIsItemSelected = false;
    private Context context;

    public MyWishesAdapter(List list,Context context) {
        mList = list;
        this.context=context;
    }

    public void obtainData(List<CommonBean> data) {
        mList = data;
        notifyDataSetChanged();
    }

    public List<CommonBean> getData() {
        return mList;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(onRecylerViewItemLongClickListener listener){
        onRecylerViewItemLongClickListener=listener;
    }

    public void deleteItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public WishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.my_wishes_item, parent, false);
        return new WishesViewHolder(itemview);
    }


    @Override
    public void onBindViewHolder(WishesViewHolder holder, int position) {
        holder.mTime.setText(mList.get(position).getTime());
        holder.mContent.setText(mList.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onRecylerViewItemLongClickListener!=null){
                    onRecylerViewItemLongClickListener.onRecylerViewItemLongClickListener(v,holder.getLayoutPosition());
                    holder.mShader.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //记得修改
        return mList.size();
    }

    public static class WishesViewHolder extends RecyclerView.ViewHolder {
        TextView mContent = null;
        TextView mTime = null;
        View mShader;

        public WishesViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.my_wishes_text);
            mTime = (TextView) itemView.findViewById(R.id.my_wishes_time);
            mShader = itemView.findViewById(R.id.wishes_shader);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onRecylerViewItemLongClickListener{
        void onRecylerViewItemLongClickListener(View v,int position);
    }
}

