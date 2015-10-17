package com.iec.dwx.timer.Views;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.iec.dwx.timer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义的单选控件，提供外部String[]的数据接口
 * Created by Flying SnowBean on 2015/10/6.
 */
public class PickView extends FrameLayout {
    private final String TAG = PickView.class.getSimpleName();

    public static String VALUE_EMPTY = "empty";

    private PickAdapter mAdapter;

    private RecyclerView.LayoutManager mManager;

    private String[] mValues;

    private int mCheckPosition = -1;

    private OnConfirmBtnClickListener mOnConfirmBtnClickListener;     //确认按钮监听

    public PickView(Context context) {
        super(context);
    }

    public PickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        View mRootView = LayoutInflater.from(context).inflate(R.layout.pick_view, this, false);
        addView(mRootView);

        RecyclerView mList = (RecyclerView) mRootView.findViewById(R.id.list_pick_values);
        Button mBtnPickYes = (Button) mRootView.findViewById(R.id.btn_pick_yes);
        mBtnPickYes.setOnClickListener(v -> dismiss(v));

        mAdapter = new PickAdapter();
        mManager = new LinearLayoutManager(context);

        mList.setAdapter(mAdapter);
        mList.setLayoutManager(mManager);
    }

    private void dismiss(View view) {
        if (mOnConfirmBtnClickListener != null) {
            mOnConfirmBtnClickListener.onConfirmBtnClick(view);
        }
    }


    public void setOnConfirmBtnClickListener(OnConfirmBtnClickListener onConfirmBtnClickListener) {
        mOnConfirmBtnClickListener = onConfirmBtnClickListener;
    }

    public String getCheckedValue() {
        if (mCheckPosition != -1)
            return mValues[mCheckPosition];
        else return VALUE_EMPTY;
    }

    public int getCheckPosition() {
        return mCheckPosition;
    }

    public String[] getValues() {
        return mValues;
    }

    /**
     * 设置数据源并更新数据
     *
     * @param values 数据源
     */
    public void setValues(String[] values) {
        mValues = values;
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    public class PickAdapter extends RecyclerView.Adapter<PickViewHolder> {

        @Override
        public PickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.pick_view_item, parent, false);
            return new PickViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PickViewHolder holder, int position) {
            holder.checkBox.setText(mValues[position]);
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> changeChecked(position, isChecked));
        }

        private void changeChecked(int checkPosition, boolean isChecked) {
            if (isChecked) {
                for (int i = 0; i < getItemCount(); i++) {
                    if (i != checkPosition)
                        ((AppCompatCheckBox) mManager.getChildAt(i)).setChecked(false);
                }
                mCheckPosition = checkPosition;
                return;
            }
            mCheckPosition = -1;
        }

        @Override
        public int getItemCount() {
            return mValues == null ? 0 : mValues.length;
        }
    }

    public static class PickViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chk_value)
        AppCompatCheckBox checkBox;

        public PickViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnConfirmBtnClickListener {
        void onConfirmBtnClick(View view);
    }
}
