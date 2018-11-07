package me.blog.njw1204.studypartner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class StudyPlanActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String,String>> Data = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_plan);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        listView = findViewById(R.id.schedule_listview);
        SimpleAdapter simpleAdapter = new
                SimpleAdapter(this , Data, R.layout.two_items,
                new String[]{"title","content"}, new int[]{R.id.text1, R.id.text2});
        listView.setAdapter(simpleAdapter);

        findViewById(R.id.newSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "날짜를 선택해야 일정을 추가할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator(), new OneDayDecorator());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear(), month = date.getMonth(), day = date.getDay();
                LetClickListener(year, month + 1, day);
            }
        });

        openPlan(getIntent().getStringExtra("title"));
    }

    private void LetClickListener(int year, int month, int day) {
        final int fyear = year, fmonth = month, fday = day;
        findViewById(R.id.newSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyPlanActivity.this, AddScheduleActivity.class);
                intent.putExtra("year", fyear);
                intent.putExtra("month", fmonth);
                intent.putExtra("day", fday);
                intent.putExtra("studyname", getIntent().getStringExtra("title"));
                startActivity(intent);
            }
        });
    }

    private void addSchedule(DataSnapshot dataSnapshot) {
        PlanDTO planDTO = dataSnapshot.getValue(PlanDTO.class);
        HashMap<String,String> data = new HashMap<>();

        String dt = planDTO.getScheduleTime();
        Integer ayear = Integer.parseInt(dt.split("/")[0]);
        Integer amonth = Integer.parseInt(dt.split("/")[1]);
        Integer aday = Integer.parseInt(dt.split("/")[2].split("-")[0]);
        Integer ahour = Integer.parseInt(dt.split("/")[2].split("-")[1].split(":")[0]);
        Integer aminute = Integer.parseInt(dt.split("/")[2].split("-")[1].split(":")[1]);

        if (ayear.intValue()==Calendar.getInstance().get(Calendar.YEAR) && amonth.intValue()==Calendar.getInstance().get(Calendar.MONTH)+1) {
            if (aday.intValue()<Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                return;
            } else {
                data.put("title", planDTO.getScheduleName());
                data.put("content", "시작 시간: " + amonth.toString() + "월 " + aday.toString() + "일  " + ahour.toString() + "시 " + aminute.toString() + "분");
                Data.add(data);
                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
            }
        } else {
            return;
        }
    }

    private void openPlan(String studyName) {
        databaseReference.child("Plan").child(studyName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addSchedule(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

