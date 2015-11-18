package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.SkillBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DataBaseHelper.DBHelper;
import com.iec.dwx.timer.Utils.DataBaseHelper.DataBaseSkillHelper;

import java.util.List;

/**
 * Created by Flying SnowBean on 2015/10/26.
 */
public class ManageSkillAdapter extends RecyclerView.Adapter<ManageSkillAdapter.ManageSkillViewHolder> {
    private final String TAG = ManageSkillAdapter.class.getSimpleName();
    private Context mCtx;
    private List<SkillBean> mData;

    public ManageSkillAdapter(Context context) {
        mCtx = context;
    }

    public void obtainData(List<SkillBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ManageSkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.manage_skill_item, parent, false);
        return new ManageSkillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ManageSkillViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position).getmCotent());
        holder.mImageView.setOnClickListener(v -> {
            DataBaseSkillHelper.getInstance(mCtx).deleteOneBean(mData.get(position).getmId());
            mData.remove(holder.getLayoutPosition());
            notifyItemRemoved(holder.getLayoutPosition());
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ManageSkillViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;

        public ManageSkillViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_skill_item);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_delete_skill);
        }
    }
}
