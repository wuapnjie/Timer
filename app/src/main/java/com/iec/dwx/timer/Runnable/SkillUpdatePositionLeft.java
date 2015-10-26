package com.iec.dwx.timer.Runnable;

import android.content.Context;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Utils.DBHelper;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class SkillUpdatePositionLeft implements Runnable {
    private final String TAG = SkillUpdatePositionLeft.class.getSimpleName();
    private Context context;
    private int id;
    private int left;

    public SkillUpdatePositionLeft(Context context, int id, int left) {
        this.context = context;
        this.id = id;
        this.left = left;
    }

    @Override
    public void run() {
        CommonBean commonBean = DBHelper.getInstance(context).getOneBean(DBHelper.DB_TABLE_SKILL, id);
        if (commonBean == null) return;
        commonBean.setTime(left + "");
        DBHelper.getInstance(context).updateBean(DBHelper.DB_TABLE_SKILL, commonBean.getID(), commonBean);
    }
}
