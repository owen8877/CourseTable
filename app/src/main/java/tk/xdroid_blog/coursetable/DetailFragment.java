package tk.xdroid_blog.coursetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    private View view;
    private Activity mActivity;
    private CourseAdapter courseAdapter;

    // TODO: 实例化
    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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
        Button button_1 = (Button) view.findViewById(R.id.debug_button_1);
        button_1.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddCourseActivity.class);
                    startActivity(intent);
                }
            }
        );
        Intent intent = getActivity().getIntent();
        TextView textview = (TextView) view.findViewById(R.id.fragment_detail_datelayout_day);
        textview.setText(intent.getStringExtra("position"));
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
