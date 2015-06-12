package tk.xdroid_blog.coursetable;

import java.text.SimpleDateFormat;
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

    public void setList(List<Course> list) {
        this.list = list;
    }

    public Day(Calendar calendar, List<Course> list) {
        this.calendar = calendar;
        this.list = list;
    }

    public Day(int i, List<Course> list) {
        this.calendar = Calendar.getInstance();
        calendar.set(i / 10000, (i / 100) % 100, i % 100, 0, 0, 0);
        this.list = list;
    }

    public String getDateByString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M,d,yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public String getSummary(){
        int i = this.validCourseNumber();
        return "There "
                + ((i <= 1) ? "is " : "are ")
                + i
                + " course"
                + ((i <= 1) ? " " : "s ")
                + "today.";
    }

    public int validCourseNumber(){
        int i = 0;
        for (Course c:list){
            if (c.isInThisDay(calendar)) i++;
        }
        return i;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Day{calendar=" + getDateByString() + ", list= ");
        for (Course c:list) stringBuilder.append(c.toString() + ";");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
