package tk.xdroid_blog.coursetable;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailFragment extends Fragment {
    private View view;
    private Activity mActivity;
    private CourseAdapter courseAdapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Get Intent and the according day instance.
        Intent intent = getActivity().getIntent();
        int position = intent.getIntExtra("position", -1);
        Day day = Database.getdaylist(getActivity()).get(position);
        TextView datetext = (TextView) view.findViewById(R.id.fragment_detail_datelayout_day);
        datetext.setText("" + day.getCalendar().get(Calendar.DAY_OF_MONTH));
        TextView monthtext = (TextView) view.findViewById(R.id.fragment_detail_datelayout_month);
        SimpleDateFormat s = new SimpleDateFormat("日 MM月");
        monthtext.setText(s.format(day.getCalendar().getTime()));
        TextView weektext = (TextView) view.findViewById(R.id.fragment_detail_datelayout_weekday);
        s = new SimpleDateFormat("E");
        weektext.setText(s.format(day.getCalendar().getTime()));


        courseAdapter = new CourseAdapter(mActivity, R.layout.course_layout,
                Database.getlist(mActivity));
        ListView listView = (ListView) view.findViewById(R.id.fragment_detail_listview);
        listView.setAdapter(courseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AddCourseActivity.class);
                intent.putExtra("Course", Database.getlist(getActivity()).get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (courseAdapter != null) courseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
