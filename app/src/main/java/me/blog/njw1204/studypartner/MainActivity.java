package me.blog.njw1204.studypartner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<StudyItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppFirstInstall();
        applyFontAwesome();

        InitStudyList();
        FetchStudyListAPI();

        BottomNavi.LetNaviClickListener(this, (BottomNavigationView)findViewById(R.id.bottomnavigationview_main));
        findViewById(R.id.edittext_search_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudySearchActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });
    }

    private void AppFirstInstall() {
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        if (!tinyDB.getBoolean("setup")) {
            tinyDB.putBoolean("push_gongji", true);
            tinyDB.putBoolean("push_study", true);
            tinyDB.putBoolean("setup", true);
        }
    }

    private void FetchStudyListAPI() {
        Intent intent = getIntent();
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.getStudyList("", "", "");
        http.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() >= 400) {
                    onFailure(call, new Throwable(response.message()));
                    return;
                }
                String result;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int cnt;
                        String icon, kind, school, title;
                        cnt = jsonObject.getInt("cnt");
                        icon = jsonObject.getString("icon");
                        kind = jsonObject.getString("kind");
                        school = jsonObject.getString("school");
                        title = jsonObject.getString("title");
                        StudyItem item = new StudyItem(icon, kind, school, title, cnt);
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.DefaultNetworkFailDialog(MainActivity.this);
            }
        });
    }

    private void InitStudyList() {
        items.clear();
        RecyclerView recyclerView = findViewById(R.id.recyclerview_study_list);
        recyclerView.setAdapter(new StudyRecyclerAdapter(items, Glide.with(this)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new StudyItemEventListener(this, new StudyItemEventListener.ClickEvents() {
            @Override
            public void onItemSingleTapUp(View view, int position) {
                Toast.makeText(getApplicationContext(), items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void applyFontAwesome() {
        Typeface fas = Typeface.createFromAsset(getAssets(), "fa_solid_900.ttf");
        int[] editTextIdArrForFas = {
            R.id.edittext_search_click
        };

        for (int i : editTextIdArrForFas) {
            EditText editText = findViewById(i);
            editText.setTypeface(fas);
        }
    }
}
