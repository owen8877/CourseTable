package tk.xdroid_blog.coursetable;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.Calendar;

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
    public int getStartDateByIntForFile() {
        return startdate.get(Calendar.YEAR) * 10000
                + startdate.get(Calendar.MONTH) * 100
                + startdate.get(Calendar.DAY_OF_MONTH);
    }
    public int getStartDateByIntForDisplay() {
        return startdate.get(Calendar.YEAR) * 10000
                + ( startdate.get(Calendar.MONTH) + 1 ) * 100
                + startdate.get(Calendar.DAY_OF_MONTH);
    }
    public int getEndDateByIntForFile() {
        return enddate.get(Calendar.YEAR) * 10000
                + enddate.get(Calendar.MONTH) * 100
                + enddate.get(Calendar.DAY_OF_MONTH);
    }
    public int getEndDateByIntForDisplay() {
        return enddate.get(Calendar.YEAR) * 10000
                + ( enddate.get(Calendar.MONTH) + 1 ) * 100
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

    /*
        Although this method can be used, however, it is strongly deprecated.
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
        endtime.set(Calendar.DAY_OF_WEEK, startdate.get(Calendar.DAY_OF_WEEK));
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
        contentValues.put(Database.StartDate, getStartDateByIntForFile());
        contentValues.put(Database.EndDate, getEndDateByIntForFile());
        return contentValues;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(place);
        dest.writeInt(this.getStartDateByIntForFile());
        dest.writeInt(this.getEndDateByIntForFile());
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
                ", starttime=" + getStartTimeByInt() +
                ", endtime=" + getEndTimeByInt() +
                ", startdate=" + getStartDateByIntForDisplay() +
                ", enddate=" + getEndDateByIntForDisplay() +
                '}';
    }
}

