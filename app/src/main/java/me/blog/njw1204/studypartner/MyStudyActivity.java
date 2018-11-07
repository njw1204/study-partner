package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStudyActivity extends AppCompatActivity {
    private StudyPartner application;
    private ArrayList<StudyItem> items;
    private StudyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_study);
        getSupportActionBar().setTitle("나의 스터디");

        application = (StudyPartner)getApplication();
        items = application.getStudyListItems();

        InitStudyList();
        FetchStudyListAPI();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview_mystudy);
        bottomNavigationView.setSelectedItemId(R.id.navi_my_study);
        BottomNavi.LetNaviClickListener(this, bottomNavigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.study_add_menu, menu);
        final MenuItem item = menu.findItem(R.id.navi_study_add);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStudyActivity.this, StudyMakeActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
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
            case R.id.navi_study_add:
                Intent intent = new Intent(MyStudyActivity.this, StudyMakeActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.setMessage("앱을 종료하시겠습니까?");
        builder.setCancelable(true);
        builder.show();
    }

    private void FetchStudyListAPI() {
        Intent intent = getIntent();
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.getUserStudy(
            intent.getStringExtra("id")
        );
        final ProgressDialog pd = CUtils.showProgress(this, "로딩 중...", false);
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
                        int cnt, study_no;
                        String icon, kind, school, area, title;
                        boolean staff;
                        cnt = jsonObject.getInt("cnt");
                        study_no = jsonObject.getInt("study_no");
                        icon = jsonObject.getString("icon");
                        kind = CUtils.NNNull(jsonObject.getString("kind"));
                        school = CUtils.NNNull(jsonObject.getString("school"));
                        area = CUtils.NNNull(jsonObject.getString("area"));
                        title = jsonObject.getString("title");
                        staff = jsonObject.getBoolean("staff");
                        StudyItem item = new StudyItem(icon, kind, school, area, title, cnt, study_no, staff);
                        items.add(item);
                        adapter.notifyItemChanged(items.size() - 1);
                    }
                    CUtils.hideProgress(pd);
                    if (items.size() == 0) {
                        findViewById(R.id.textview_not_my_study).setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                adapter.notifyDataSetChanged();
                CUtils.hideProgress(pd);
                CUtils.DefaultNetworkFailDialog(MyStudyActivity.this);
            }
        });
    }

    private void InitStudyList() {
        items.clear();
        adapter = new StudyRecyclerAdapter(items, Glide.with(this));
        RecyclerView recyclerView = findViewById(R.id.recyclerview_mystudy);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(300);
        recyclerView.addOnItemTouchListener(new StudyItemEventListener(this, new StudyItemEventListener.ClickEvents() {
            @Override
            public void onItemSingleTapUp(View view, int position) {
                Intent intent = new Intent(MyStudyActivity.this, StudyMainActivity.class);
                intent.putExtra("id", MyStudyActivity.this.getIntent().getStringExtra("id"));
                intent.putExtra("pw", MyStudyActivity.this.getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", MyStudyActivity.this.getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", MyStudyActivity.this.getIntent().getStringExtra("my_nick"));
                intent.putExtra("title", items.get(position).getTitle());
                intent.putExtra("kind", items.get(position).getKind());
                intent.putExtra("school", items.get(position).getSchool());
                intent.putExtra("area", items.get(position).getArea());
                intent.putExtra("study_no", items.get(position).getStudy_no());
                intent.putExtra("icon", items.get(position).getIcon());
                intent.putExtra("show_icon", !CUtils.IsPseudoEmpty(items.get(position).getIcon()));
                startActivity(intent);
            }
        }));
    }
}
