package com.iec.dwx.timer.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.iec.dwx.timer.Activities.MyWishDetails;
import com.iec.dwx.timer.Activities.OtherWishes;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.WishBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.Utils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.Date;
import java.util.List;


public class MyWishesFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private MyWishesAdapter mAdapter = null;
    private RecyclerView.LayoutManager mManager = null;
    private LinearLayout add_view = null;
    private RecyclerView recyclerView = null;
    private InputMethodManager imm = null;
    private List<CommonBean> list = null;

    public static MyWishesFragment newInstance() {
        return new MyWishesFragment();
    }

    public MyWishesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_my_wishes, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.rv_my_wishes);
        add_view = (LinearLayout) rootview.findViewById(R.id.add_view);
        add_view.setVisibility(View.GONE);

        mManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mManager);

        //item divider
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.parseColor("#47646363"));
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        ((RecyclerView) rootview.findViewById(R.id.rv_my_wishes)).addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).inflateMenu(R.menu.menu_my_wishes);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).setOnMenuItemClickListener(this);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).setNavigationOnClickListener(v -> getActivity().onBackPressed());
        //新建确定按钮
        getView().findViewById(R.id.my_wishes_add_btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_view.setVisibility(View.GONE);
                recyclerView.setBackgroundColor(000);
                addDataToDataBase((EditText) getView().findViewById(R.id.my_wishes_add_editText));
            }
        });
        //新建取消按钮
        getView().findViewById(R.id.my_wishes_add_btn_cencel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_view.setVisibility(View.GONE);
                recyclerView.setBackgroundColor(000);
                ((EditText) getView().findViewById(R.id.my_wishes_add_editText)).setText("");
                Toast.makeText(getContext(), "已取消", Toast.LENGTH_SHORT).show();
                //隐藏键盘
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    //增加新数据的操作
    private void addDataToDataBase(EditText editText) {
        if(!editText.getText().equals("")){
        WishBean wishBean = new WishBean();
        wishBean.setContent(editText.getText().toString());
        editText.setText("");
        wishBean.setPicture(0 + "");
        wishBean.setTime(Utils.wishFormatTime(new Date(System.currentTimeMillis())));

        DBHelper.getInstance(getActivity()).addBeanToDatabase(DBHelper.DB_TABLE_WISH, wishBean);
        //隐藏键盘
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        list = DBHelper.getInstance(getContext()).getAllBeans(DBHelper.DB_TABLE_WISH);
        mAdapter = new MyWishesAdapter(list);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("itemClick" + position);
                Intent intent = new Intent(getActivity(), MyWishDetails.class);
                intent.putExtra("ClickPosition", position);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        Toast.makeText(getContext(), "已保存", Toast.LENGTH_SHORT).show();
        return;
        }
        Toast.makeText(getContext(), "保存内容不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        list = DBHelper.getInstance(getContext()).getAllBeans(DBHelper.DB_TABLE_WISH);
        mAdapter = new MyWishesAdapter(list);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("itemClick" + position);
                Intent intent = new Intent(getActivity(), MyWishDetails.class);
                intent.putExtra("ClickPosition", position);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        super.onResume();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_my_wishes_others:
                startActivity(new Intent(getActivity(), OtherWishes.class));
                break;
            case R.id.menu_my_wishes_add:
                add_view.setVisibility(View.VISIBLE);
                recyclerView.setBackgroundResource(R.color.black_overlay);
        }
        return false;
    }

    private class MyWishesAdapter extends RecyclerView.Adapter<WishesViewHolder> {
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;
        private List<CommonBean> mList = null;

        public MyWishesAdapter(List list) {
            mList = list;
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }


        @Override
        public WishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(getActivity()).inflate(R.layout.my_wishes_item, parent, false);
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
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            //记得修改
            return mList.size();
        }
    }

    public class WishesViewHolder extends RecyclerView.ViewHolder {
        TextView mContent = null;
        TextView mTime = null;

        public WishesViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.my_wishes_text);
            mTime = (TextView) itemView.findViewById(R.id.my_wishes_time);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
}
