package me.blog.njw1204.studypartner;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class BottomNavi {

    public static void LetNaviClickListener(final Activity activity, BottomNavigationView navi) {
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navi_my_study:
                        intent = new Intent(activity, MyStudyActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                    case R.id.navi_study_list:
                        intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                    case R.id.navi_setting:
                        intent = new Intent(activity, SettingActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                }
                return false;
            }
        });
    }
}
