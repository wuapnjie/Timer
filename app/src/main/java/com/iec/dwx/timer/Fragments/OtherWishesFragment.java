package com.iec.dwx.timer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iec.dwx.timer.Animate.DividerGridItemDecoration;
import com.iec.dwx.timer.Animate.DividerItemDecoration;
import com.iec.dwx.timer.R;

public class OtherWishesFragment extends Fragment{
    private OtherWishesAdapter mAdapter=null;
    private RecyclerView.LayoutManager mlayoutManager=null;

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
        View rootview=inflater.inflate(R.layout.fragment_other_wishes,container,false);

            mAdapter=new OtherWishesAdapter();
            System.out.println("mAdapter==null "+mAdapter==null);
            mlayoutManager=new LinearLayoutManager(getActivity());
            ((RecyclerView) rootview.findViewById(R.id.rv_others_wishes)).setLayoutManager(mlayoutManager);
            ((RecyclerView) rootview.findViewById(R.id.rv_others_wishes)).setAdapter(mAdapter);

        return inflater.inflate(R.layout.fragment_other_wishes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).inflateMenu(R.menu.menu_my_wishes);
    }

    private class OtherWishesAdapter extends RecyclerView.Adapter<OthersWishViewHolder>{

        @Override
        public OthersWishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(getActivity())
            .inflate(R.layout.others_wishes_item,parent,false);
            return new OthersWishViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(OthersWishViewHolder holder, int position) {
            holder.mContent.setText("test");
            holder.mTime.setText("2013");
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    public static class OthersWishViewHolder extends RecyclerView.ViewHolder{
        TextView mContent=null;
        TextView mTime=null;
        public OthersWishViewHolder(View itemView) {
            super(itemView);
            mTime= (TextView) itemView.findViewById(R.id.others_wishes_time);
            mContent= (TextView) itemView.findViewById(R.id.others_wishes_text);
        }
    }
}
