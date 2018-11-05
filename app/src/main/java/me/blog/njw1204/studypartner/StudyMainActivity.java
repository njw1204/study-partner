package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_main);

        LetClickListener();
    }

    private void LetClickListener() {
        findViewById(R.id.layout_Info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getApplicationContext(), StudyInfoActivity.class);
                startActivity(infoIntent);
            }
        });
        findViewById(R.id.layout_Notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent noticeIntent = new Intent(getApplicationContext(), StudyNoticeActivity.class);
                //startActivity(noticeIntent);
            }
        });
        findViewById(R.id.layout_Member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent memberIntent = new Intent(getApplicationContext(), StudyMemberActivty.class);
                //startActivity(memberIntent);
            }
        });
        findViewById(R.id.layout_Plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent planIntent = new Intent(getApplicationContext(), StudyPlanActivity.class);
                //startActivity(planIntent);
            }
        });
        findViewById(R.id.layout_Goal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent goalIntent = new Intent(getApplicationContext(), StudyGoalActivity.class);
                //startActivity(goalIntent);
            }
        });
        findViewById(R.id.layout_Chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent chatIntent = new Intent(getApplicationContext(), StudyChatActivity.class);
                //startActivity(chatIntent);
            }
        });
        findViewById(R.id.layout_Mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent mailIntent = new Intent(getApplicationContext(), StudyMailActivity.class);
                //startActivity(mailIntent);
            }
        });
    }
}
