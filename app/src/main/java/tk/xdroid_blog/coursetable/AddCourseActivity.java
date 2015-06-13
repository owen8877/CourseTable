package tk.xdroid_blog.coursetable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;

import java.util.Calendar;


public class AddCourseActivity extends ActionBarActivity implements com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener, com.sleepbot.datetimepicker.time.TimePickerDialog.OnTimeSetListener{

    private static int startYear, startMonth, startDay;
    private static int endYear, endMonth, endDay;
    private static int startHour, startMinute;
    private static int endHour, endMinute;
    private static int flag = 0;
    private static Calendar calendar;
    private static Course temp;
    ButtonFlat button_submit, startdatepicker, enddatepicker, starttimepicker, endtimepicker;
    EditText editTextName, editTextPlace;

    @Override
    public void finish() {
        flag = 0;
        calendar = null;
        temp = null;
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //Toolbar part
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        calendar = Calendar.getInstance();
        editTextName = (EditText) findViewById(R.id.activity_add_course_edittext_name);
        editTextPlace = (EditText) findViewById(R.id.activity_add_course_edittext_place);

        startdatepicker = (ButtonFlat) findViewById(R.id.activity_add_course_startdatepicker);
        enddatepicker = (ButtonFlat) findViewById(R.id.activity_add_course_enddatepicker);
        starttimepicker = (ButtonFlat) findViewById(R.id.activity_add_course_starttimepicker);
        endtimepicker = (ButtonFlat) findViewById(R.id.activity_add_course_endtimepicker);

        if (getIntent().hasExtra("Course")){
            temp = getIntent().getParcelableExtra("Course");
            editTextName.setText(temp.getName());
            editTextPlace.setText(temp.getPlace());
            startYear = temp.getStartDateByInt() / 10000;
            startMonth = (temp.getStartDateByInt() / 100) % 100;
            startDay = temp.getStartDateByInt() % 100;
            startHour = (temp.getStartTimeByInt() / 100) % 100;
            startMinute = temp.getStartTimeByInt() % 100;
            endYear = temp.getEndDateByInt() / 10000;
            endMonth = (temp.getEndDateByInt() / 100) % 100;
            endDay = temp.getEndDateByInt() % 100;
            endHour = (temp.getEndTimeByInt() / 100) % 100;
            endMinute = temp.getEndTimeByInt() % 100;
            flag = flag | 16;
            UiRefresh();
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

        final com.fourmob.datetimepicker.date.DatePickerDialog startDatePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, startYear, startMonth, startDay);
        final com.sleepbot.datetimepicker.time.TimePickerDialog startTimePickerDialog = com.sleepbot.datetimepicker.time.TimePickerDialog.newInstance(this, startHour, startMinute, true);
        final com.fourmob.datetimepicker.date.DatePickerDialog endDatePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, endYear, endMonth, endDay);
        final com.sleepbot.datetimepicker.time.TimePickerDialog endTimePickerDialog = com.sleepbot.datetimepicker.time.TimePickerDialog.newInstance(this, endHour, endMinute, true);

        startdatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePickerDialog.setVibrate(false);
                startDatePickerDialog.setCloseOnSingleTapDay(true);
                startDatePickerDialog.show(getSupportFragmentManager(), "startDatePicker");
            }
        });

        enddatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePickerDialog.setVibrate(false);
                endDatePickerDialog.setCloseOnSingleTapDay(true);
                endDatePickerDialog.show(getSupportFragmentManager(), "endDatePicker");
            }
        });

        starttimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePickerDialog.setVibrate(false);
                startTimePickerDialog.setCloseOnSingleTapMinute(true);
                startTimePickerDialog.show(getSupportFragmentManager(), "startTimePicker");
            }
        });

        endtimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePickerDialog.setVibrate(false);
                endTimePickerDialog.setCloseOnSingleTapMinute(true);
                endTimePickerDialog.show(getSupportFragmentManager(), "endTimePicker");
            }
        });
    }

    @Override
    public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {
        if (datePickerDialog.getTag().equals("startDatePicker")){
            startYear = year;
            startMonth = month;
            startDay = day;
            flag = flag | 1;
            UiRefresh();
        }
        else if (datePickerDialog.getTag().equals("endDatePicker")){
            endYear = year;
            endMonth = month;
            endDay = day;
            flag = flag | 2;
            UiRefresh();
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        if (getSupportFragmentManager().findFragmentByTag("startTimePicker") != null){
            startHour = hourOfDay;
            startMinute = minute;
            flag = flag | 4;
            UiRefresh();
        }
        else if (getSupportFragmentManager().findFragmentByTag("endTimePicker") != null){
            endHour = hourOfDay;
            endMinute = minute;
            flag = flag | 8;
            UiRefresh();
        }
    }

    private void UiRefresh(){
        startdatepicker.setText(Course.getString(startYear, startMonth, startDay));
        starttimepicker.setText(Course.getString(startHour, startMinute));
        enddatepicker.setText(Course.getString(endYear, endMonth, endDay));
        endtimepicker.setText(Course.getString(endHour, endMinute));
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            EditText editTextName = (EditText) findViewById(R.id.activity_add_course_edittext_name);
            EditText editTextPlace = (EditText) findViewById(R.id.activity_add_course_edittext_place);
            switch (menuItem.getItemId()) {
                case R.id.action_add_course_apply:
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
                    break;
                case R.id.action_add_course_modify:
                    Database.modifyCourse(temp,
                            new Course(editTextName.getText().toString(),
                                    editTextPlace.getText().toString(),
                                    startHour * 100 + startMinute,
                                    endHour * 100 + endMinute,
                                    startYear * 10000 + startMonth * 100 + startDay,
                                    endYear * 10000 + endMonth * 100 + endDay));
                    finish();
                    break;
                case R.id.action_add_course_delete:
                    Database.deleteCourse(temp);
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if ((flag & 16) == 16) {
            getMenuInflater().inflate(R.menu.menu_add_course_modifymode, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_add_course_addmode, menu);
        }
        return true;
    }
}
