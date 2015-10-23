package com.iec.dwx.timer.Runnable;

import android.content.Context;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Utils.DBHelper;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class SkillUpdatePositionTop implements Runnable {
    private final String TAG = SkillUpdatePositionTop.class.getSimpleName();
    private Context context;
    private int id;
    private int top;

    public SkillUpdatePositionTop(Context context, int id, int top){
        this.context=context;
        this.id=id;
        this.top=top;
    }

    @Override
    public void run() {
        CommonBean commonBean=DBHelper.getInstance(context).getOneBean(DBHelper.DB_TABLE_SKILL,id);
        commonBean.setPicture(top+"");
        DBHelper.getInstance(context).updateBean(DBHelper.DB_TABLE_SKILL,commonBean.getID(),commonBean);
    }
}
