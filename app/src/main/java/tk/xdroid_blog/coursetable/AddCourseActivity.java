package tk.xdroid_blog.coursetable;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddCourseActivity extends ActionBarActivity {

    private static int startYear, startMonth, startDay;
    private static int endYear, endMonth, endDay;
    private static int startHour, startMinute;
    private static int endHour, endMinute;
    private static int flag = 0;
    private static Calendar calendar = Calendar.getInstance();
    private static Course temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        EditText editTextName = (EditText) findViewById(R.id.activity_add_course_edittext_name);
        EditText editTextPlace = (EditText) findViewById(R.id.activity_add_course_edittext_place);
        if (getIntent().hasExtra("Course")){
            temp = getIntent().getParcelableExtra("Course");
            editTextName.setText(temp.getName());
            editTextPlace.setText(temp.getPlace());
            //TODO:init
            startYear = temp.getStartDateByIntForFile() / 10000;
            startMonth = (temp.getStartDateByIntForFile() / 100) % 100;
            startDay = temp.getStartDateByIntForFile() % 100;
            startHour = temp.getStartTimeByInt() / 100;
            startMinute = temp.getStartTimeByInt() % 100;
            endYear = temp.getEndDateByIntForFile() / 10000;
            endMonth = (temp.getEndDateByIntForFile() / 100) % 100;
            endDay = temp.getEndDateByIntForFile() % 100;
            endHour = temp.getEndTimeByInt() / 100;
            endMinute = temp.getEndTimeByInt() % 100;
            flag = flag | 16;
        }
        else {
            startYear = calendar.get(Calendar.YEAR);
            startMonth = calendar.get(Calendar.MONTH);
            startDay = calendar.get(Calendar.DAY_OF_MONTH);
            startHour = calendar.get(Calendar.HOUR_OF_DAY);
            startMinute = calendar.get(Calendar.MINUTE);
            endYear = calendar.get(Calendar.YEAR);
            endMonth = calendar.get(Calendar.MONTH);
            endDay = calendar.get(Calendar.DAY_OF_MONTH);
            endHour = calendar.get(Calendar.HOUR_OF_DAY);
            endMinute = calendar.get(Calendar.MINUTE);
        }

        Button button_submit, startdatepicker, enddatepicker;
        startdatepicker = (Button) findViewById(R.id.activity_add_course_startdatepicker);
        startdatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datepickerFragment = new DatePickerFragment();
                FragmentManager fm = getFragmentManager();
                datepickerFragment.show(fm, "startDatePicker");
            }
        });

        enddatepicker = (Button) findViewById(R.id.activity_add_course_enddatepicker);
        enddatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datepickerFragment = new DatePickerFragment();
                FragmentManager fm = getFragmentManager();
                datepickerFragment.show(fm, "endDatePicker");
            }
        });

        Button starttimepicker, endtimepicker;
        starttimepicker = (Button) findViewById(R.id.activity_add_course_starttimepicker);
        starttimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timepickerFragment = new TimePickerFragment();
                FragmentManager fm = getFragmentManager();
                timepickerFragment.show(fm, "startTimePicker");
            }
        });

        endtimepicker = (Button) findViewById(R.id.activity_add_course_endtimepicker);
        endtimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timepickerFragment = new TimePickerFragment();
                FragmentManager fm = getFragmentManager();
                timepickerFragment.show(fm, "endTimePicker");
            }
        });

        Spinner startdatepickers = (Spinner) findViewById(R.id.startDateSpinner);
        String[] startdatespinner = {String.valueOf(startYear)};
        startdatepickers.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startdatespinner));
        startdatepickers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatePickerFragment datepickerFragment = new DatePickerFragment();
                FragmentManager fm = getFragmentManager();
                datepickerFragment.show(fm, "startDatePicker");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner starttimepickers = (Spinner) findViewById(R.id.startTimeSpinner);
        String[] starttimespinner = {String.valueOf(startHour)};
        startdatepickers.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, starttimespinner));
        starttimepickers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TimePickerFragment timepickerFragment = new TimePickerFragment();
                FragmentManager fm = getFragmentManager();
                timepickerFragment.show(fm, "startTimePicker");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

                button_submit = (Button) findViewById(R.id.activity_add_course_button_submit);
        button_submit.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editTextName = (EditText) findViewById(R.id.activity_add_course_edittext_name);
                    EditText editTextPlace = (EditText) findViewById(R.id.activity_add_course_edittext_place);
                    if ((flag & 32) == 32) {
                        Database.modifyCourse(temp,
                                new Course(editTextName.getText().toString(),
                                        editTextPlace.getText().toString(),
                                        startHour * 100 + startMinute,
                                        endHour * 100 + endMinute,
                                        startYear * 10000 + startMonth * 100 + startDay,
                                        endYear * 10000 + endMonth * 100 + endDay));
                        finish();
                    }
                    if ((flag & 15) == 15) {
                        Database.addCourse(new Course(editTextName.getText().toString(),
                                editTextPlace.getText().toString(),
                                startHour * 100 + startMinute,
                                endHour * 100 + endMinute,
                                startYear * 10000 + startMonth * 100 + startDay,
                                endYear * 10000 + endMonth * 100 + endDay));
                        finish();
                    }
                    else{
                        Toast.makeText(AddCourseActivity.this, "Please pick dates and times!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (getTag().equals("startDatePicker")){
                return new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
            }
            else if (getTag().equals("endDatePicker")){
                return new DatePickerDialog(getActivity(), this, endYear, endMonth, endDay);
            }
            else{
                return new DatePickerDialog(getActivity(), this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
            }
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (getTag().equals("startDatePicker")){
                startYear = year;
                startMonth = monthOfYear;
                startDay = dayOfMonth;
                flag = flag | 1;
            }
            else if (getTag().equals("endDatePicker")){
                endYear = year;
                endMonth = monthOfYear;
                endDay = dayOfMonth;
                flag = flag | 2;
            }

        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (getTag().equals("startTimePicker")){
                return new TimePickerDialog(getActivity(), this, startHour, startMinute, true);
            }
            else if (getTag().equals("endTimePicker")){
                return new TimePickerDialog(getActivity(), this, endHour, endMinute, true);
            }
            else{
                return new TimePickerDialog(getActivity(), this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true);
            }
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (getTag().equals("startTimePicker")){
                startHour = hourOfDay;
                startMinute = minute;
                flag = flag | 4;
            }
            else if (getTag().equals("endTimePicker")){
                endHour = hourOfDay;
                endMinute = minute;
                flag = flag | 8;
            }
        }
    }
}
