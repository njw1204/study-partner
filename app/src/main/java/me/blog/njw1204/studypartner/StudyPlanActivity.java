package me.blog.njw1204.studypartner;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StudyPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_plan);

        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator(), new OneDayDecorator());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear(), month = date.getMonth(), day = date.getDay();
                LetClickListener(year, month + 1, day);
                //Toast.makeText(StudyPlanActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LetClickListener(int year, int month, int day) {
        final int fyear = year, fmonth = month, fday = day;
        findViewById(R.id.newSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScheduleDialog(fyear, fmonth, fday);
            }
        });
    }

    void addScheduleDialog(final int fyear, final int fmonth, final int fday) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_schedule, null);
        builder.setView(view);
        builder.setTitle("시작 시간");
        final EditText newScheduleName = view.findViewById(R.id.newScheduleName);
        final EditText newScheduleStartTime = view.findViewById(R.id.newScheduleStartTime);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = newScheduleName.getText().toString();
                        String string = newScheduleStartTime.getText().toString();
                        Integer year = fyear, month = fmonth, day = fday;
                        Integer hour = Integer.parseInt(string.split(":")[0]);
                        Integer minute = Integer.parseInt(string.split(":")[1]);
                        Toast.makeText(getApplicationContext(), title + " : " + year.toString() + "/" + month.toString() + "/" + day.toString() + " "
                                + hour.toString() + ":" + minute.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        builder.show();
    }
}

