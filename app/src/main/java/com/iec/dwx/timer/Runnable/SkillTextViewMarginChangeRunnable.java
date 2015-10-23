package com.iec.dwx.timer.Runnable;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public class SkillTextViewMarginChangeRunnable implements Runnable {
    private final String TAG = SkillTextViewMarginChangeRunnable.class.getSimpleName();
    private Context context=null;
    private List<TextView> viewList=null;

    public SkillTextViewMarginChangeRunnable(Context context,List<TextView> viewList){
        this.context=context;
        this.viewList=viewList;
    }
    @Override
    public void run() {
        CommonBean commonBean=null;
        for(TextView textView:viewList){
              commonBean=DBHelper.getInstance(context).getOneBean(DBHelper.DB_TABLE_SKILL,(int)textView.getTag(R.id.tag_zero));
              commonBean.setTime((int)textView.getTag(R.id.tag_first)+"");
              commonBean.setPicture((int)textView.getTag(R.id.tag_second)+"");
              DBHelper.getInstance(context).updateBean(DBHelper.DB_TABLE_SKILL, commonBean.getID(), commonBean);
        }

    }
}
