package me.blog.njw1204.studypartner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                    String school = CUtils.NNNull(jsonObject.getString("school"));
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

    public void nickedit(View v){
        popupedit(1);
    }

    public void univedit(View v){
        popupedit(2);
    }

    public void pwedit(View v){
        popupedit(0);
    }

    void popupedit(int type)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (type == 0) {
            builder.setTitle("비밀번호 변경");
            final EditText curpw = new EditText(this);
                curpw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            final EditText newpw = new EditText(this);
                newpw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            final EditText newpwcheck = new EditText(this);
                newpwcheck.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(curpw);
            builder.setView(newpw);
            builder.setView(newpwcheck);
            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //server
                            //Toast.makeText(getApplicationContext(),curpw.getText().toString() ,Toast.LENGTH_LONG).show();
                        }
                    });
            builder.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
        }
        else {
            if(type == 1) builder.setTitle("닉네임 변경");
            else builder.setTitle("대학교 변경");
            final EditText edittext = new EditText(this);
            builder.setView(edittext);
            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //server
                            //Toast.makeText(getApplicationContext(),edittext.getText().toString() ,Toast.LENGTH_LONG).show();
                        }
                    });
            builder.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
        }
        //builder.setMessage("AlertDialog Content");
    }



}