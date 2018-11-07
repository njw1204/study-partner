package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class StudyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_main);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        LetClickListener();

        if (getIntent().getBooleanExtra("show_icon", false)) {
            RequestOptions options = new RequestOptions()
                                         .centerCrop()
                                         .placeholder(R.color.bottom_navi_color)
                                         .error(R.drawable.book);
            Glide.with(this)
                .load(getIntent().getStringExtra("icon"))
                .apply(options)
                .into((ImageView)findViewById(R.id.studyImage));
        }
        else {
            findViewById(R.id.studyImage).setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.study_staff_menu, menu);
        final MenuItem item = menu.findItem(R.id.navi_study_staff);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMainActivity.this, StaffActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                intent.putExtra("study_no", getIntent().getIntExtra("study_no", 0));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navi_study_staff:
                Intent intent = new Intent(StudyMainActivity.this, StaffActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                intent.putExtra("study_no", getIntent().getIntExtra("study_no", 0));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            default:
                return false;
        }
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
    }
}
