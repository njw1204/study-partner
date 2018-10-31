package me.blog.njw1204.studypartner;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_study);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview_mystudy);
        bottomNavigationView.setSelectedItemId(R.id.navi_my_study);
        BottomNavi.LetNaviClickListener(this, (BottomNavigationView)findViewById(R.id.bottomnavigationview_mystudy));
    }
}
