package com.iec.dwx.timer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iec.dwx.timer.Animate.OtherWishDividerItemDecoration;
import com.iec.dwx.timer.R;

public class OtherWishesFragment extends Fragment{
    private Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;

    //测试数据
    private String mString[]=new String[]{"aaaa","bbbbbbbbbbbbbb","ccccccccccccccccccccc","d"};

    public static OtherWishesFragment newInstance() {
        return new OtherWishesFragment();
    }

    public OtherWishesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_other_wishes, container, false);
        madapter=new Adapter();
        mlayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        ((RecyclerView) rootView.findViewById(R.id.rv_other_wishes)).setLayoutManager(mlayoutManager);
        ((RecyclerView) rootView.findViewById(R.id.rv_other_wishes)).setAdapter(madapter);
       // ((RecyclerView) rootView.findViewById(R.id.rv_other_wishes)).addItemDecoration(new OtherWishDividerItemDecoration(getContext()));

        return rootView;
    }

    private class Adapter extends RecyclerView.Adapter<OtherViewHolder>{

        @Override
        public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(getContext()).inflate(R.layout.other_wishes_item,parent,false);
            return new OtherViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(OtherViewHolder holder, int position) {
            holder.mTime.setText("1995");
            holder.mContent.setText(mString[position]);
        }

        @Override
        public int getItemCount() {
            return mString.length;
        }
    }

    private class OtherViewHolder extends RecyclerView.ViewHolder{
        TextView mContent;
        TextView mTime;
        public OtherViewHolder(View itemView) {
            super(itemView);
            mContent= (TextView) itemView.findViewById(R.id.other_wishes_text);
            mTime= (TextView) itemView.findViewById(R.id.other_wishes_time);
        }
    }
}
