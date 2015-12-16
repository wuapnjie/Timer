package com.iec.dwx.timer.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.iec.dwx.timer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Flying SnowBean on 2015/11/18.
 */
public class ShareDialog {
    private final String TAG = ShareDialog.class.getSimpleName();
    private AlertDialog mAlertDialog;
    private GridView mGridView;
    private SimpleAdapter mAdapter;
    private Button mBtnCancel;
    private int[] mImageIds = new int[]{R.drawable.ic_sina_weibo_big,R.drawable.ic_wechat_big,R.drawable.ic_qq_big};
    private String[] mNames = {"新浪微博","微信好友","QQ"};

    public ShareDialog(Context context) {
        mAlertDialog = new AlertDialog.Builder(context).create();
        mAlertDialog.show();
        Window window = mAlertDialog.getWindow();
        window.setContentView(R.layout.share_dialog);
        mGridView = (GridView) window.findViewById(R.id.share_grid_items);
        mBtnCancel = (Button) window.findViewById(R.id.btn_share_cancel);
        List<HashMap<String,Object>> shareData = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("Image",mImageIds[i]);
            map.put("Text",mNames[i]);
            shareData.add(map);
        }

        mAdapter = new SimpleAdapter(context,shareData,R.layout.share_item,new String[]{"Image","Text"},new int[]{R.id.iv_share_image,R.id.tv_shar_text});

        mGridView.setAdapter(mAdapter);
    }

    public void setCancelButtonOnClickListener(View.OnClickListener Listener){
        mBtnCancel.setOnClickListener(Listener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        mGridView.setOnItemClickListener(listener);
    }

    public void dismiss() {
        mAlertDialog.dismiss();
    }
}
