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
import android.widget.TextView;

import com.iec.dwx.timer.Animate.DividerItemDecoration;
import com.iec.dwx.timer.R;


public class MyWishesFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private MyWishesAdapter mAdapter;
    private RecyclerView.LayoutManager mManager;

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
        View rootView=inflater.inflate(R.layout.fragment_my_wishes, container, false);

            mAdapter=new MyWishesAdapter();
            mManager=new LinearLayoutManager(getActivity());
            ((RecyclerView) rootView.findViewById(R.id.rv_my_wishes)).setLayoutManager(mManager);
            ((RecyclerView) rootView.findViewById(R.id.rv_my_wishes)).setAdapter(mAdapter);
//            ((RecyclerView) rootview.findViewById(R.id.rv_my_wishes)).addItemDecoration(new DividerItemDecoration(getContext(),
//                    DividerItemDecoration.VERTICAL_LIST));
        return rootView;
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
            case R.id.menu_my_wishes_share:
                System.out.println("yunduan");
                break;
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
//            holder.mTime.setText("2013");
            holder.mContent.setText("myWishes");
        }

        @Override
        public int getItemCount() {
            //记得修改
            return 30;
        }
    }

    public static class WishesViewHolder extends RecyclerView.ViewHolder{
        TextView mContent;
//        TextView mTime;
        public WishesViewHolder(View itemView) {
            super(itemView);
            mContent= (TextView) itemView.findViewById(R.id.my_wishes_text);
//            mTime= (TextView) itemView.findViewById(R.id.my_wishes_time);
        }
    }
}
