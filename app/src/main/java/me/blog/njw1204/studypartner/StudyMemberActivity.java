package me.blog.njw1204.studypartner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class StudyMemberActivity extends AppCompatActivity {

    private ArrayList<HashMap<String,String>> Data = new ArrayList<>();
    private HashMap<String,String> InputData;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_member);

        listView = findViewById(R.id.listview_member_list);

        InputData = new HashMap<>();
        InputData.put("nickname", "나종우");
        InputData.put("admin", "운영진");
        Data.add(InputData);

        InputData = new HashMap<>();
        InputData.put("nickname", "주영석");
        InputData.put("admin", "");
        Data.add(InputData);

        SimpleAdapter simpleAdapter = new
                SimpleAdapter(this , Data, android.R.layout.simple_list_item_2,
                    new String[]{"nickname","admin"}, new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
    }
}
