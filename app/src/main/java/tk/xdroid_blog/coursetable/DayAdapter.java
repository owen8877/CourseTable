package tk.xdroid_blog.coursetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Owen on 2015/5/28.
 */
public class DayAdapter extends ArrayAdapter<Day> {
    private int resourceId;

    public DayAdapter(Context context, int textViewResourceId, List<Day> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Day day = getItem(position);
        View view;
        ViewHolder viewHolder;
        //TODO:A lot of things todo!!!
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.dateholder = (TextView) view.findViewById(R.id.day_date);
            viewHolder.summaryholder = (TextView) view.findViewById(R.id.day_coursesummary);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.dateholder.setText(day.getDate());
        viewHolder.summaryholder.setText(day.getSummary());
        return view;
    }

    class ViewHolder{
        TextView dateholder, summaryholder;
    }
}
