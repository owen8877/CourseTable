package tk.xdroid_blog.coursetable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Owen on 2015/5/28.
 */
public class Day {
    private List<Course> list;
    private List<Course> visiblelist = new ArrayList<Course>();
    private Calendar calendar;

    public Calendar getCalendar() {
        return calendar;
    }
    public List<Course> getList() {
        return list;
    }
    public List<Course> getVisiblelist() {
        return visiblelist;
    }
    public int getWeekDay(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public void setList(List<Course> list) {
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
        return visiblelist.size();
    }

    public Day(Calendar calendar, List<Course> list) {
        this.calendar = calendar;
        this.list = list;
        refresh();
    }
    public Day(int i, List<Course> list) {
        this.calendar = Calendar.getInstance();
        calendar.set(i / 10000, (i / 100) % 100, i % 100, 0, 0, 0);
        this.list = list;
        refresh();
    }

    public boolean addCourse(Course course){
        if (list.add(course)){
            refresh();
            Collections.sort(list);
            return true;
        }
        return false;
    }
    public boolean deleteCourse(Course course){
        if (list.remove(course)){
            refresh();
            return true;
        }
        return false;
    }
    private boolean refresh(){
        visiblelist.clear();
        if (list == null) return true;
        for (Course c:list){
            if (c.isInThisDay(calendar)) visiblelist.add(c);
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Day{calendar=" + getDateByString() + ", list= ");
        for (Course c:list) stringBuilder.append(c.toString() + ";");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
