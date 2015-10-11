package com.iec.dwx.timer.Utils.EricViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2015/10/11 0011.
 */
public class FragmentMyWishesAdapter extends BaseAdapter {
    private final String TAG = FragmentMyWishesAdapter.class.getSimpleName();
    private Context mcontext=null;
    public FragmentMyWishesAdapter(Context context){
        this.mcontext=context;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
