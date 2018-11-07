package me.blog.njw1204.studypartner;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText Edit_Time;
    private String ScheduleName;
    private String ScheduleTime;
    Integer fyear, fmonth, fday;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Edit_Time = findViewById(R.id.newScheduleStartTime);

        Intent intent = getIntent();
        fyear = intent.getIntExtra("year", Calendar.getInstance().get(Calendar.YEAR));
        fmonth = intent.getIntExtra("month", Calendar.getInstance().get(Calendar.MONTH));
        fday = intent.getIntExtra("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        LetClickListener();
    }

    private void LetClickListener() {
        Edit_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        Edit_Time.setText(hour + ":" + minute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });
        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "일정이 추가되었습니다.", Toast.LENGTH_LONG).show();
                ScheduleName = ((EditText)findViewById(R.id.newScheduleName)).getText().toString();
                ScheduleTime =  fyear.toString() + "/" + fmonth.toString() + "/" + fday.toString() + "-"
                        + (Edit_Time.getText().toString());
                PlanDTO schedule = new PlanDTO(ScheduleName, ScheduleTime);
                databaseReference.child("Plan").child(getIntent().getStringExtra("studyname")).push().setValue(schedule);
                finish();
            }
        });
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
