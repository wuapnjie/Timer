package com.iec.dwx.timer.Utils.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.iec.dwx.timer.Beans.SkillBeanNew;
import com.iec.dwx.timer.Utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class DataBaseSkillHelper extends SQLiteOpenHelper {
    private final String TAG = DataBaseSkillHelper.class.getSimpleName();
    private static DataBaseSkillHelper mInstance;

    //the name of database
    private static final String DB_NAME="Time";

    //the name of tables
    private static  final String DB_SKILL="skill";

    //the name of columns
    private static final String COLUMN_ID="id";
    private static final String COLUMN_CONTENT="content";
    private static final String COLUMN_MARGINLEFT="marginLeft";
    private static final String COLUMN_MARGINTOP ="marginRight";

    public DataBaseSkillHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public synchronized static DataBaseSkillHelper getInstance(Context context){
        if(mInstance==null){
            mInstance=new DataBaseSkillHelper(context,DB_NAME,null, Utils.getAppVersion(context));
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SKILL_TABLE="CREATE TABLE if not exists "+DB_SKILL+" ( " +
                COLUMN_ID+" INTEGER PRIMARY KEY,"+
                COLUMN_CONTENT+" VARCHAR,"+
                COLUMN_MARGINLEFT+" INTEGER,"+
                COLUMN_MARGINTOP +" INTEGER"+" )";
        db.execSQL(CREATE_SKILL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion>newVersion) return;
        db.execSQL("DROP TABLE IF EXISTS " + DB_SKILL);
        onCreate(db);
     }

    /**
     * 添加一条数据到表中,返回id值
     * @param skillBean
     * @return
     */
    public int addBean(SkillBeanNew skillBean){
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_CONTENT,skillBean.getmCotent());
        contentValues.put(COLUMN_MARGINLEFT,skillBean.getMarginLeft());
        contentValues.put(COLUMN_MARGINTOP, skillBean.getMarginTop());

        SQLiteDatabase db=this.getWritableDatabase();
        return (int)db.insert(DB_SKILL,null,contentValues);
    }

    /**
     * 根据id返回第一个相同id的skillbean
     * @param id
     * @return
     */
    public SkillBeanNew getOneBean(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM " + DB_SKILL + " WHERE " + "id" + "=" + "'" + id + "'", null);
        if(cursor.moveToFirst()){
            SkillBeanNew skillBeanNew=new SkillBeanNew();
            skillBeanNew.setmId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            skillBeanNew.setmCotent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
            skillBeanNew.setMarginLeft(cursor.getInt(cursor.getColumnIndex(COLUMN_MARGINLEFT)));
            skillBeanNew.setMarginTop(cursor.getInt(cursor.getColumnIndex(COLUMN_MARGINTOP)));;

            cursor.close();
            return skillBeanNew;
        }else{
            cursor.close();
            return null;
        }
    }

    /**
     * 根据content返回第一个相同skillbean
     * @param content
     * @return
     */
    public SkillBeanNew getOneBean(String content){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(DB_SKILL, new String[]{COLUMN_ID, COLUMN_CONTENT, COLUMN_MARGINLEFT, COLUMN_MARGINTOP},
                COLUMN_CONTENT + " =?", new String[]{content}, null, null, null);
        if(cursor.moveToFirst()){
            SkillBeanNew skillbean=new SkillBeanNew();
            skillbean.setmId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            skillbean.setmCotent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
            skillbean.setMarginLeft(cursor.getInt(cursor.getColumnIndex(COLUMN_MARGINLEFT)));
            skillbean.setMarginTop(cursor.getInt(cursor.getColumnIndex(COLUMN_MARGINTOP)));

            cursor.close();
            return skillbean;
        }else{
            cursor.close();
            return null;
        }
    }

    /**
     * 返回所有的skillbean
     * @return
     */
    public List<SkillBeanNew> getAllBeans(){
        List<SkillBeanNew> list=new ArrayList<SkillBeanNew>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + DB_SKILL, null);
        while (cursor.moveToNext()){
            SkillBeanNew skillbean=new SkillBeanNew();
            skillbean.setmId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            skillbean.setmCotent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
            skillbean.setMarginLeft(cursor.getInt(cursor.getColumnIndex(COLUMN_MARGINLEFT)));
            skillbean.setMarginTop(cursor.getInt(cursor.getColumnIndex(COLUMN_MARGINTOP)));

            list.add(skillbean);
        }
        if(list.isEmpty()){
            return null;
        }else {
            return list;
        }
    }

    /**
     * 删除相同id的所有skillbean
     * @param id
     * @return
     */
    public Boolean deleteOneBean(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        int count=db.delete(DB_SKILL, COLUMN_ID + "=?", new String[]{id + ""});
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除相同content的skillbean
     * @param content
     * @return
     */
    public Boolean deleteOneBean(String content){
        SQLiteDatabase db=this.getWritableDatabase();
        int count=db.delete(DB_SKILL,COLUMN_CONTENT+"=?",new String[]{content});
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新id对应的数据
     * @param id
     * @param newBean
     * @return
     */
    public Boolean updateOneBean(int id,SkillBeanNew newBean){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(COLUMN_ID,id);
        contentvalues.put(COLUMN_CONTENT,newBean.getmCotent());
        contentvalues.put(COLUMN_MARGINLEFT, newBean.getMarginLeft());
        contentvalues.put(COLUMN_MARGINTOP, newBean.getMarginTop());

        int count=db.update(DB_SKILL,contentvalues,COLUMN_ID+"=?",new String[]{id+""});
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新和oldBean里的content一样的数据
     * @param oldBean
     * @param newBean
     * @return
     */
    public Boolean updateOneBean(SkillBeanNew oldBean,SkillBeanNew newBean){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(COLUMN_CONTENT,newBean.getmCotent());
        contentvalues.put(COLUMN_MARGINLEFT, newBean.getMarginLeft());
        contentvalues.put(COLUMN_MARGINTOP, newBean.getMarginTop());

        int count=db.update(DB_SKILL,contentvalues,COLUMN_CONTENT+"=?",new String[]{oldBean.getmCotent()});
        if(count>0){
            return true;
        }else {
            return false;
        }
    }
}
