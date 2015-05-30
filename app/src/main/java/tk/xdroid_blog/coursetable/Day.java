package tk.xdroid_blog.coursetable;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Owen on 2015/5/28.
 */
public class Day {
    private List<Course> list;
    private Calendar calendar;

    public Calendar getCalendar() {
        return calendar;
    }

    public List<Course> getList() {
        return list;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }

    public Day(Calendar calendar, List<Course> list) {
        this.calendar = calendar;
        this.list = list;
    }

    public String getDate(){
        return "" + calendar.get(Calendar.MONTH)
                + calendar.get(Calendar.DAY_OF_MONTH)
                + calendar.get(Calendar.YEAR);
    }

    public String getSummary(){
        int i = this.validCourseNumber();
        return "There "
                + ((i == 1) ? "is " : "are ")
                + i
                + " course"
                + ((i == 1) ? " " : "s ")
                + "today.";
    }

    public int validCourseNumber(){
        return 1;
    }
}
