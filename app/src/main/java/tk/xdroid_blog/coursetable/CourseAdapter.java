package tk.xdroid_blog.coursetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Owen on 2015/5/2.
 */
public class CourseAdapter extends ArrayAdapter<Course> {
    private int resourceId;

    public CourseAdapter(Context context, int textViewResourceId, List<Course> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position); // 获取当前项的Course实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.nameholder = (TextView) view.findViewById(R.id.name);
            viewHolder.placeholder = (TextView) view.findViewById(R.id.place);
            viewHolder.timeholder = (TextView) view.findViewById(R.id.time);
            viewHolder.durationholder = (TextView) view.findViewById(R.id.duration);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.nameholder.setText(course.getName());
        viewHolder.placeholder.setText(course.getPlace());
        viewHolder.timeholder.setText("From "
                + course.getStartTimeByInt()
                + " to "
                + course.getEndTimeByInt());
        viewHolder.durationholder.setText("From "
                + course.getStartDateByIntForDisplay()
                + " to "
                + course.getEndDateByIntForDisplay());
        return view;
    }

    class ViewHolder{
        TextView nameholder, placeholder, timeholder, durationholder;
    }
}
