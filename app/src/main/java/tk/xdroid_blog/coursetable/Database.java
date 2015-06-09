package tk.xdroid_blog.coursetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.nio.channels.NonWritableChannelException;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Owen on 2015/5/3.
 */
public class Database extends SQLiteOpenHelper {
    public static final String Create = "create table ";
    public static final String TableName = "Course";
    public static final String TEXT = "text";
    public static final String INTEGER = "integer";
    public static final String Name = "name";
    public static final String Place = "place";
    public static final String StartTime = "starttime";
    public static final String EndTime = "endtime";
    public static final String StartDate = "startdate";
    public static final String EndDate = "enddate";

    public static final String CREATE_COURSETABLE = Create + TableName + " ("
            + Name + " " + TEXT + " , "
            + Place + " " + TEXT + " , "
            + StartTime + " " + INTEGER + " primary key autoincrement, "
            + EndTime + " " + INTEGER + " , "
            + StartDate + " " + INTEGER + " , "
            + EndDate + " " + INTEGER + ")";

    private Context mContext;
    private static Database db;
    private static List<Course> list = new ArrayList<Course>();
    private static List<List<Course>> listofsublist;
    public static List<Day> daylist = new ArrayList<Day>();

    private Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public synchronized static List<Course> getlist(Context context){
        if (db == null) {
            db = new Database(context, "Course.db", null, 1);
            db.init();
        }
        return list;
    }

    public synchronized static List<Day> getdaylist(Context context){
        if (db == null) {
            getlist(context);
            listofsublist = new ArrayList<List<Course>>();
            for (int a = 0; a < 7; a++) listofsublist.add(new ArrayList<Course>());
            for (Course c : list) {
                listofsublist.get(c.getWeekDay() - 1).add(c);
            }
            Calendar now = Calendar.getInstance();
            int i = now.get(Calendar.YEAR) * 10000 + now.get(Calendar.MONTH) * 100 + now.get(Calendar.DAY_OF_MONTH);
            for (List<Course> sublist : listofsublist) {
                daylist.add(new Day(i++, sublist));
            }
        }
        return daylist;
    }

    public synchronized static boolean addCourse(Course course){
        if (list.add(course)){
            db.getWritableDatabase().insert(TableName, null, course.getContentValues());
            Collections.sort(list);
            return true;
        }
        return false;
    }

    public synchronized static boolean deleteCourse(Course course){
        return list.remove(course);
    }

    public synchronized static boolean modifyCourse(Course course_old, Course course_new){
        if (list.remove(course_old)||list.add(course_new)){
            db.getWritableDatabase().update(TableName, course_new.getContentValues(), StartTime + " = ?",
                    new String[]{String.valueOf(course_old.getStartTimeByInt())});
            Collections.sort(list);
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COURSETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void init(){
        SQLiteDatabase dbwriter = db.getWritableDatabase();

        SharedPreferences handle = mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = handle.edit();
        if(!handle.getBoolean("flaghasinit", false)){
            editor.putBoolean("flaghasinit", true);
            editor.apply();
            //TODO:Initialization
            Course c [];
            c = new Course[7];
            for (int i = 0;i < 7;i++){
                c[i] = new Course("C" + (i + 1), "Classroom" + (i + 1), 1030 + 100 * i, 1130 + 100 * i, 20150424 + i, 20150507 + i);
                dbwriter.insert(Database.TableName, null, c[i].getContentValues());
            }
        }

        Cursor cursor = dbwriter.query(Database.TableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Course temp = new Course(cursor.getString(cursor.getColumnIndex(Database.Name)),
                        cursor.getString(cursor.getColumnIndex(Database.Place)),
                        cursor.getInt(cursor.getColumnIndex(Database.StartTime)),
                        cursor.getInt(cursor.getColumnIndex(Database.EndTime)),
                        cursor.getInt(cursor.getColumnIndex(Database.StartDate)),
                        cursor.getInt(cursor.getColumnIndex(Database.EndDate)));
                Log.d("temp", temp.toString());
                list.add(temp);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
