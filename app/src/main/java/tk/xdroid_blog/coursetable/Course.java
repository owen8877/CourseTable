package tk.xdroid_blog.coursetable;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Owen on 2015/5/2.
 */

public class Course implements Comparable<Course>, Parcelable{
    private String name;
    private String place;
    private Calendar starttime;
    private Calendar endtime;
    private Calendar startdate;
    private Calendar enddate;

    /*group functions*/
    public String getName(){
        return name;
    }
    public String getPlace(){
        return place;
    }
    public int getWeekDay(){
        return startdate.get(Calendar.DAY_OF_WEEK);
    }
    public int getStartDateByInt() {
        return startdate.get(Calendar.YEAR) * 10000
                + startdate.get(Calendar.MONTH) * 100
                + startdate.get(Calendar.DAY_OF_MONTH);
    }
    public int getEndDateByInt() {
        return enddate.get(Calendar.YEAR) * 10000
                + enddate.get(Calendar.MONTH) * 100
                + enddate.get(Calendar.DAY_OF_MONTH);
    }
    public int getStartTimeByInt(){
        return starttime.get(Calendar.DAY_OF_WEEK) * 10000
                + starttime.get(Calendar.HOUR_OF_DAY) * 100
                + starttime.get(Calendar.MINUTE);
    }
    public int getEndTimeByInt(){
        return endtime.get(Calendar.DAY_OF_WEEK) * 10000
                + endtime.get(Calendar.HOUR_OF_DAY) * 100
                + endtime.get(Calendar.MINUTE);
    }
    public String getStartDateByString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M,d,yyyy E");
        return simpleDateFormat.format(startdate.getTime());
    }
    public String getEndDateByString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M,d,yyyy E");
        return simpleDateFormat.format(enddate.getTime());
    }
    public String getStartTimeByString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(starttime.getTime());
    }
    public String getEndTimeByString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(endtime.getTime());
    }

    @NonNull
    public static String getString(int year, int month, int day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M,d,yyyy E");
        Date temp = new Date(year, month, day);
        return simpleDateFormat.format(temp);
    }

    @NonNull
    public static String getString(int hour, int minute){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date temp = new Date(0, 0, 0, hour, minute);
        return simpleDateFormat.format(temp);
    }

    public static int convertCalendarToIntFieldYMD(Calendar calendar){
        return calendar.get(Calendar.YEAR) * 10000
                + calendar.get(Calendar.MONTH) * 100
                + calendar.get(Calendar.DAY_OF_MONTH);
    }

    /*
        ConStruction Methods
     */
    public Course(String name, String place, int _starttime, int _endtime, int _start, int _end){
        this.name = name;
        this.place = place;
        starttime = Calendar.getInstance();
        endtime = Calendar.getInstance();
        startdate = Calendar.getInstance();
        enddate = Calendar.getInstance();
        startdate.set(_start / 10000, (_start / 100) % 100, _start % 100, 0, 0, 0);
        enddate.set(_end / 10000, (_end / 100) % 100, _end % 100, 0, 0, 0);
        starttime.set(0, 0, 0, (_starttime / 100) % 100, _starttime % 100, 0);
        endtime.set(0, 0, 0, (_endtime / 100) % 100, _endtime % 100, 0);
        starttime.set(Calendar.DAY_OF_WEEK, startdate.get(Calendar.DAY_OF_WEEK));
        //TODO:There may be bug!
        endtime.set(Calendar.DAY_OF_WEEK, enddate.get(Calendar.DAY_OF_WEEK));
    }

    public Course(Parcel sourse){
        name = sourse.readString();
        place = sourse.readString();
        starttime = Calendar.getInstance();
        endtime = Calendar.getInstance();
        startdate = Calendar.getInstance();
        enddate = Calendar.getInstance();
        int i = sourse.readInt();
        startdate.set(i / 10000, (i / 100) % 100, i % 100, 0, 0, 0);
        i = sourse.readInt();
        enddate.set(i / 10000, (i / 100) % 100, i % 100, 0, 0, 0);
        i = sourse.readInt();
        starttime.set(0, 0, 0, (i / 100) % 100, i % 100);
        i = sourse.readInt();
        endtime.set(0, 0, 0, (i / 100) % 100, i % 100);
        starttime.set(Calendar.DAY_OF_WEEK, startdate.get(Calendar.DAY_OF_WEEK));
        endtime.set(Calendar.DAY_OF_WEEK, enddate.get(Calendar.DAY_OF_WEEK));
    }

    public int compareTo(Course other){
        return this.starttime.compareTo(other.starttime);
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.Name, getName());
        contentValues.put(Database.Place, getPlace());
        contentValues.put(Database.StartTime, getStartTimeByInt());
        contentValues.put(Database.EndTime, getEndTimeByInt());
        contentValues.put(Database.StartDate, getStartDateByInt());
        contentValues.put(Database.EndDate, getEndDateByInt());
        return contentValues;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(place);
        dest.writeInt(this.getStartDateByInt());
        dest.writeInt(this.getEndDateByInt());
        dest.writeInt(this.getStartTimeByInt());
        dest.writeInt(this.getEndTimeByInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", starttime=" + getStartTimeByString() +
                ", endtime=" + getEndTimeByString() +
                ", startdate=" + getStartDateByString() +
                ", enddate=" + getEndDateByString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (!getName().equals(course.getName())) return false;
        if (!getPlace().equals(course.getPlace())) return false;
        Log.d("Course", "stime " + getStartTimeByInt() + course.getStartTimeByInt());
        Log.d("Course", "etime " + getEndTimeByInt() + course.getEndTimeByInt());
        Log.d("Course", "sdate " + getStartDateByInt() + course.getStartDateByInt());
        Log.d("Course", "edate " + getEndDateByInt() + course.getEndDateByInt());
        if (getStartTimeByInt() != course.getStartTimeByInt()) return false;
        if (getEndTimeByInt() != course.getEndTimeByInt()) return false;
        if (getStartDateByInt() != course.getStartDateByInt()) return false;
        return getEndDateByInt() == course.getEndDateByInt();

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPlace().hashCode();
        result = 31 * result + starttime.hashCode();
        result = 31 * result + endtime.hashCode();
        result = 31 * result + startdate.hashCode();
        result = 31 * result + enddate.hashCode();
        return result;
    }

    public boolean isInThisDay(Calendar calendar){
        return (Course.convertCalendarToIntFieldYMD(calendar) >= getStartDateByInt())
                && (Course.convertCalendarToIntFieldYMD(calendar) <= getEndDateByInt());
    }
}

