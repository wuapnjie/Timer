package com.iec.dwx.timer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iec.dwx.timer.Animate.MyWishDividerItemDecoration;
import com.iec.dwx.timer.R;

import org.w3c.dom.Text;


public class MyWishesFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private MyWishesAdapter mAdapter=null;
    private RecyclerView.LayoutManager mManager=null;

    public static MyWishesFragment newInstance() {
        return new MyWishesFragment();
    }

    public MyWishesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_my_wishes, container, false);

            mAdapter=new MyWishesAdapter();
            mManager=new LinearLayoutManager(getActivity());
            ((RecyclerView) rootview.findViewById(R.id.rv_my_wishes)).setLayoutManager(mManager);
            ((RecyclerView) rootview.findViewById(R.id.rv_my_wishes)).setAdapter(mAdapter);
        ((RecyclerView) rootview.findViewById(R.id.rv_my_wishes)).addItemDecoration(new MyWishDividerItemDecoration(
                getActivity(),MyWishDividerItemDecoration.VERTICAL_LIST
        ));
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).inflateMenu(R.menu.menu_my_wishes);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

        }
        return false;
    }

    private class MyWishesAdapter extends RecyclerView.Adapter<WishesViewHolder>{

        @Override
        public WishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemview=LayoutInflater.from(getActivity()).inflate(R.layout.my_wishes_item,parent,false);
            return new WishesViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(WishesViewHolder holder, int position) {
            holder.mTime.setText("2013");
            holder.mContent.setText("mywihes");
        }

        @Override
        public int getItemCount() {
            //记得修改
            return 9;
        }
    }

    public static class WishesViewHolder extends RecyclerView.ViewHolder{
        TextView mContent=null;
        TextView mTime=null;
        public WishesViewHolder(View itemView) {
            super(itemView);
            mContent= (TextView) itemView.findViewById(R.id.my_wishes_text);
            mTime= (TextView) itemView.findViewById(R.id.my_wishes_time);
        }
    }
}
