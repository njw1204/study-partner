package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RestorePushChecked();
        FetchUserInfoAPI();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview_setting);
        bottomNavigationView.setSelectedItemId(R.id.navi_setting);
        BottomNavi.LetNaviClickListener(this, (BottomNavigationView)findViewById(R.id.bottomnavigationview_setting));

        LetClickListener();
    }

    private void RestorePushChecked() {
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        ((CheckBox)findViewById(R.id.checkbox_push_gongji)).setChecked(tinyDB.getBoolean("push_gongji"));
        ((CheckBox)findViewById(R.id.checkbox_push_study)).setChecked(tinyDB.getBoolean("push_study"));
    }

    private void FetchUserInfoAPI() {
        Intent intent = getIntent();
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.getUserInfo(intent.getStringExtra("id"));
        http.enqueue(new Callback<ResponseBody>() {
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
                    JSONObject jsonObject = new JSONObject(result);
                    String id = jsonObject.getString("id");
                    String nick = jsonObject.getString("nick");
                    String school = jsonObject.getString("school");
                    ((TextView)findViewById(R.id.textview_now_id)).setText(id);
                    ((TextView)findViewById(R.id.textView_now_nickname)).setText(nick);
                    ((TextView)findViewById(R.id.textview_now_school)).setText(school);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.DefaultNetworkFailDialog(SettingActivity.this);
            }
        });
    }

    private void LetClickListener() {
        findViewById(R.id.button_developer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CUtils.SimpleDialogShow(getApplicationContext(), "TEAM 파티구함\n개발자 : 나종우, 주영석", true);
            }
        });
        findViewById(R.id.checkbox_push_gongji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDB tinyDB = new TinyDB(getApplicationContext());
                tinyDB.putBoolean("push_gongji", ((CheckBox)v).isChecked());
            }
        });
        findViewById(R.id.checkbox_push_study).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDB tinyDB = new TinyDB(getApplicationContext());
                tinyDB.putBoolean("push_study", ((CheckBox)v).isChecked());
            }
        });
    }

    /*
        라이센스 1 : fontawesome
        라이센스 2 : glide
        라이센스 3 : Book free icon : Icons made by Freepik from CC 3.0 BY
        라이센스 4 : retrofit
        라이센스 5 : TinyDB
     */
}
