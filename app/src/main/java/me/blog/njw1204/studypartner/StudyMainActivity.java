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
        final Intent intent = getIntent();
        findViewById(R.id.layout_Info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMainActivity.this, StudyInfoActivity.class);
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                for (String i : keys) {
                    CUtils.SendStringExtra(i, intent, getIntent());
                }
                CUtils.SendIntExtra("study_no", intent, getIntent(), 0);
                CUtils.SendBooleanExtra("show_icon", intent, getIntent(), false);
                intent.putExtra("hide_navi", true);
                startActivity(intent);
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
                Intent intent = new Intent(StudyMainActivity.this, StudyMemberActivity.class);
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                for (String i : keys) {
                    CUtils.SendStringExtra(i, intent, getIntent());
                }
                CUtils.SendIntExtra("study_no", intent, getIntent(), 0);
                CUtils.SendBooleanExtra("show_icon", intent, getIntent(), false);
                intent.putExtra("hide_navi", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_Plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyMainActivity.this, StudyPlanActivity.class);
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                for (String i : keys) {
                    CUtils.SendStringExtra(i, intent, getIntent());
                }
                CUtils.SendIntExtra("study_no", intent, getIntent(), 0);
                CUtils.SendBooleanExtra("show_icon", intent, getIntent(), false);
                intent.putExtra("hide_navi", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_Chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(getApplicationContext(), StudyChatActivity.class);
                chatIntent.putExtra("userid", intent.getStringExtra("id"));
                chatIntent.putExtra("nick", intent.getStringExtra("my_nick"));
                chatIntent.putExtra("chatname", intent.getStringExtra("title"));
                startActivity(chatIntent);
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
