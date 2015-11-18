package com.iec.dwx.timer.Runnable;

import android.content.Context;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.SkillBean;
import com.iec.dwx.timer.Utils.DataBaseHelper.DBHelper;
import com.iec.dwx.timer.Utils.DataBaseHelper.DataBaseSkillHelper;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class SkillUpdatePositionTop implements Runnable {
    private final String TAG = SkillUpdatePositionTop.class.getSimpleName();
    private Context context;
    private int id;
    private int top;

    public SkillUpdatePositionTop(Context context, int id, int top) {
        this.context = context;
        this.id = id;
        this.top = top;
    }

    @Override
    public void run() {
        SkillBean skillBean= DataBaseSkillHelper.getInstance(context).getOneBean(id);
        if (skillBean == null) return;
        skillBean.setMarginTop(top);
        DataBaseSkillHelper.getInstance(context).updateOneBean(skillBean.getmId(),skillBean);
    }
}
