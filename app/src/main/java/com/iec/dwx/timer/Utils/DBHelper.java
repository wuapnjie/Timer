package com.iec.dwx.timer.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iec.dwx.timer.Beans.CommonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个单例的数据库操作类，一共有三张表，分别为wish，achievement，skill，包含增删改查，DBHelper.getInstance(context)即可获得对象
 * 增：见下
 * 删：见下
 * 改：见下
 * 查：见下
 * Created by Flying SnowBean on 2015/10/10.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper mInstance;
    //the name of database
    public static final String DB_NAME = "Time";

    //the name of tables
    public static final String DB_TABLE_ACHIEVEMENT = "Achievement";
    public static final String DB_TABLE_WISH = "Wish";
    public static final String DB_TABLE_SKILL = "Skill";

    //the name of columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_PICTURE = "picture";


    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context, DB_NAME, null, Utils.getAppVersion(context));
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WISH_TABLE = "CREATE TABLE if not exists " + DB_TABLE_WISH + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TIME + " VARCHAR(50)," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_PICTURE + " TEXT" + " )";
        String CREATE_ACHIEVEMENT_TABLE = "CREATE TABLE if not exists " + DB_TABLE_ACHIEVEMENT + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TIME + " VARCHAR(50)," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_PICTURE + " TEXT" + " )";
        String CREATE_SKILL_TABLE = "CREATE TABLE if not exists " + DB_TABLE_SKILL + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TIME + " VARCHAR(50)," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_PICTURE + " TEXT" + " )";

        db.execSQL(CREATE_WISH_TABLE);
        db.execSQL(CREATE_ACHIEVEMENT_TABLE);
        db.execSQL(CREATE_SKILL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) return;

        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_WISH);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ACHIEVEMENT);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SKILL);
        onCreate(db);
    }

    /**
     * 根据时间获得一个通用Bean对象
     *
     * @param tableName 表名
     * @param time      Bean的time属性
     * @return 表中一个符合time属性的bean
     */
    public CommonBean getOneBean(String tableName, String time) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, new String[]{
                COLUMN_ID,
                COLUMN_TIME,
                COLUMN_CONTENT,
                COLUMN_PICTURE
        }, COLUMN_TIME + "=?", new String[]{
                time, null, null
        }, null, null, null);
        if (cursor.moveToFirst()) {
            CommonBean bean = new CommonBean();
            bean.setID(Integer.valueOf(cursor.getString(0)));
            bean.setTime(cursor.getString(1));
            bean.setContent(cursor.getString(2));
            bean.setPicture(cursor.getString(3));
            cursor.close();
            return bean;
        } else {
            cursor.close();
            return null;
        }
    }

    /**
     * 根据id获得一个通用Bean对象
     *
     * @param tableName 表名
     * @param id        Bean的id属性
     * @return 表中一个符合id属性的bean
     */
    public CommonBean getOneBean(String tableName, int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + COLUMN_ID + "=" + "'" + id + "'", null);
        if (cursor.moveToFirst()) {
            CommonBean bean = new CommonBean();
            bean.setID(Integer.valueOf(cursor.getString(0)));
            bean.setTime(cursor.getString(1));
            bean.setContent(cursor.getString(2));
            bean.setPicture(cursor.getString(3));
            cursor.close();
            return bean;
        } else {
            cursor.close();
            return null;
        }
    }

    /**
     * 获取一张表中的所有通用Bean对象
     *
     * @param tableName 表名
     * @return 通用Bean的集合
     */
    public List<CommonBean> getAllBeans(String tableName) {
        List<CommonBean> list = new ArrayList<>();

        String querySQL = "SELECT * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(querySQL, null);
        if (cursor.moveToFirst()) {
            do {
                CommonBean bean = new CommonBean();
                bean.setID(Integer.valueOf(cursor.getString(0)));
                bean.setTime(cursor.getString(1));
                bean.setContent(cursor.getString(2));
                bean.setPicture(cursor.getString(3));
                list.add(bean);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    /**
     * 添加一条数据到表中
     *
     * @param tableName 表名
     * @param bean      要添加的数据
     * @return 添加数据的id值
     */
    public int addBeanToDatabase(String tableName, CommonBean bean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TIME, bean.getTime());
        contentValues.put(COLUMN_CONTENT, bean.getContent());
        contentValues.put(COLUMN_PICTURE, bean.getPicture());

        SQLiteDatabase db = this.getWritableDatabase();
        int id = (int) db.insert(tableName, null, contentValues);

        return id;
    }

    /**
     * 删除表中时间和内容与deleteBean一致的属性
     *
     * @param tableName  表名
     * @param deleteBean 要删除的数据
     */
    public void deleteBean(String tableName, CommonBean deleteBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName + " WHERE " + COLUMN_TIME + "=" + "'" + deleteBean.getTime() + "'" + " AND " + COLUMN_CONTENT + "='" + deleteBean.getContent() + "'");
    }

    /**
     * 删除表中对应id的数据
     *
     * @param tableName 表名
     * @param id        id
     */
    public void deleteBean(String tableName, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * 更新一条与oldBean的时间一致的数据
     *
     * @param tableName 表名
     * @param oldBean   旧数据
     * @param newBean   新数据
     * @return 数据的id
     */
    public int updateBean(String tableName, CommonBean oldBean, CommonBean newBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TIME, newBean.getTime());
        contentValues.put(COLUMN_CONTENT, newBean.getContent());
        contentValues.put(COLUMN_PICTURE, newBean.getPicture());

        int newID = db.update(tableName, contentValues, COLUMN_TIME + "=?", new String[]{oldBean.getTime()});
        return newID;
    }

    /**
     * 更新对应id的数据
     *
     * @param tableName 表名
     * @param id        id
     * @param newBean   新数据
     * @return 数据的id
     */
    public int updateBean(String tableName, int id, CommonBean newBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TIME, newBean.getTime());
        contentValues.put(COLUMN_CONTENT, newBean.getContent());
        contentValues.put(COLUMN_PICTURE, newBean.getPicture());

        int newID = db.update(tableName, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return newID;
    }
}
