package com.iec.dwx.timer.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.iec.dwx.timer.Activities.MainActivity;
import com.iec.dwx.timer.Activities.MyWishDetails;
import com.iec.dwx.timer.Activities.OtherWishes;
import com.iec.dwx.timer.Adapters.MyWishesAdapter;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.WishBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.Utils;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MyWishesFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private MyWishesAdapter mAdapter = null;
    private RecyclerView.LayoutManager mManager = null;
    private FrameLayout add_view = null;
    private RecyclerView recyclerView = null;
    private PopupWindow mPopupWindow;
    private InputMethodManager imm = null;
    private List<CommonBean> list = null;
    public static final int SELECTED_NONE = -1;
    //被选中心愿的位置,
    private int mSelectedPosition = SELECTED_NONE;

    private EditText mEditText;

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
        add_view = (FrameLayout) rootview.findViewById(R.id.add_view);
        add_view.setVisibility(View.GONE);

        mManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mManager);

        list = DBHelper.getInstance(getContext()).getAllBeans(DBHelper.DB_TABLE_WISH);
        mAdapter = new MyWishesAdapter(list, getContext());
        mAdapter.setOnItemClickListener(new MyWishesAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("itemClick" + position);
                Intent intent = new Intent(getActivity(), MyWishDetails.class);
                intent.putExtra("ClickPosition", position);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        //item divider
//        Paint paint = new Paint();
//        paint.setStrokeWidth(10);
//        paint.setColor(Color.parseColor("#47646363"));
//        paint.setAntiAlias(true);
//        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
//        ((RecyclerView) rootview.findViewById(R.id.rv_my_wishes)).addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerListener();

        if (getView() != null)
            mEditText = (EditText) getView().findViewById(R.id.my_wishes_add_editText);
        //初始化PopupWindow
        Observable.just(R.layout.my_wishes_popup_view)
                .map(integer -> LayoutInflater.from(getActivity()).inflate(integer, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view1 -> initializePopupWindow(view1));
    }


    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    public void registerListener() {
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).inflateMenu(R.menu.menu_my_wishes);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).setOnMenuItemClickListener(this);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_wishes)).setNavigationOnClickListener(v -> getActivity().onBackPressed());

        EditText editText = (EditText) getView().findViewById(R.id.my_wishes_add_editText);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                        cancelSelectedShader(mSelectedPosition);
                        mSelectedPosition = SELECTED_NONE;
                    }
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new MyWishesAdapter.onRecylerViewItemLongClickListener() {
            @Override
            public void onRecylerViewItemLongClickListener(View v, int position) {
                mSelectedPosition = position;
                Log.d("LongClick", "after long click,the mSelectedPosition->" + mSelectedPosition);
                showPopupWindow();
            }
        });

        //新建确定按钮
        getView().findViewById(R.id.my_wishes_add_btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToDataBase(editText);
                //隐藏键盘
                imm.hideSoftInputFromInputMethod(editText.getWindowToken(), 0);
            }
        });
        //新建取消按钮
        getView().findViewById(R.id.my_wishes_add_btn_cencel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_view.setVisibility(View.GONE);
//                recyclerView.setBackgroundColor(000);
                (editText).setText("");
                Toast.makeText(getContext(), "已取消", Toast.LENGTH_SHORT).show();
                //隐藏键盘
                imm.hideSoftInputFromInputMethod(editText.getWindowToken(), 0);
            }
        });
    }


    /**
     * 初始化popupWindow
     *
     * @param view 视图
     */
    private void initializePopupWindow(View view) {
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnDelete = (Button) view.findViewById(R.id.btn_popup_delete);

        btnDelete.setOnClickListener(v -> {
            deleteWishes();
            Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
        });

    }

    /**
     * 长按事件
     * 显示PopupWindow
     */
    private void showPopupWindow() {
        mPopupWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 取消Item 的 选中状态
     */

    private void cancelSelectedShader(int position) {
        if (position != SELECTED_NONE && mManager.getChildAt(position) != null) {
            mManager.getChildAt(position).findViewById(R.id.wishes_shader).setVisibility(View.GONE);
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        Observable.just(DBHelper.DB_TABLE_WISH)
                .map(s -> DBHelper.getInstance(getActivity()).getAllBeans(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commonBeans -> {
                    if (commonBeans == null || commonBeans.size() == 0) {
                        WishBean bean = new WishBean();
                        bean.setContent("快来许下你第一个心愿\n----致亲爱的你");
                        bean.setTime(Utils.formatTime(new Date()));
                        bean.setPicture("null");
                        DBHelper.getInstance(getActivity()).addBeanToDatabase(DBHelper.DB_TABLE_WISH, bean);
                        if (commonBeans != null)
                            commonBeans.add(bean);
                    }
                    mAdapter.obtainData(commonBeans);
                });

    }

    /**
     * 删除Item
     */
    private void deleteWishes() {
        if (mSelectedPosition == SELECTED_NONE) return;
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();


        DBHelper.getInstance(getActivity()).deleteBean(DBHelper.DB_TABLE_WISH, mAdapter.getData().get(mSelectedPosition));

        mAdapter.deleteItem(mSelectedPosition);

        cancelSelectedShader(mSelectedPosition);

        refreshData();

        mSelectedPosition = SELECTED_NONE;
    }

    //增加新数据的操作
    private void addDataToDataBase(EditText editText) {
        if (!editText.getText().toString().equals("")) {
            WishBean wishBean = new WishBean();
            wishBean.setContent(editText.getText().toString());
            editText.setText("");
            wishBean.setPicture(0 + "");
            wishBean.setTime(Utils.wishFormatTime(new Date(System.currentTimeMillis())));

            DBHelper.getInstance(getActivity()).addBeanToDatabase(DBHelper.DB_TABLE_WISH, wishBean);

            add_view.setVisibility(View.GONE);
//            recyclerView.setBackgroundColor(000);
            //隐藏键盘
            imm.hideSoftInputFromInputMethod(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            list = DBHelper.getInstance(getContext()).getAllBeans(DBHelper.DB_TABLE_WISH);
            mAdapter = new MyWishesAdapter(list, getContext());
            mAdapter.setOnItemClickListener(new MyWishesAdapter.OnRecyclerViewItemClickListener() {
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
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_my_wishes_others:
                startActivity(new Intent(getActivity(), OtherWishes.class));
                break;
            case R.id.menu_my_wishes_add:
                add_view.setVisibility(View.VISIBLE);
                add_view.setClickable(true);
                if (mEditText != null && mEditText.requestFocus()) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);
                }
//                add_view.requestFocus();
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);
//                recyclerView.setBackgroundResource(R.color.black_overlay);
        }
        return false;
    }

}
