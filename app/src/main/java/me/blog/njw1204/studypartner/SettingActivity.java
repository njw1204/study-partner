package me.blog.njw1204.studypartner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
        getSupportActionBar().setTitle("설정");

        RestorePushChecked();
        FetchUserInfoAPI();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview_setting);
        bottomNavigationView.setSelectedItemId(R.id.navi_setting);
        BottomNavi.LetNaviClickListener(this, bottomNavigationView);

        LetClickListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.study_add_menu, menu);
        final MenuItem item = menu.findItem(R.id.navi_study_add);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, StudyMakeActivity.class);
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
                Intent intent = new Intent(SettingActivity.this, StudyMakeActivity.class);
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
        final Intent intent = getIntent();
        findViewById(R.id.button_developer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CUtils.SimpleDialogShow(SettingActivity.this, "TEAM 파티구함\n개발자 : 나종우, 주영석", true);
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
        findViewById(R.id.button_change_nick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupedit(1);
            }
        });
        findViewById(R.id.button_change_school).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupedit(2);
            }
        });
        findViewById(R.id.button_pwedit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupedit(0);
            }
        });
        findViewById(R.id.button_byebye).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CUtils.SimpleDialogShow(SettingActivity.this, "현재는 데모 기간으로, 이 기능을 사용하실 수 없습니다.", true);
                Intent plan = new Intent(getApplicationContext(), StudyPlanActivity.class);
                startActivity(plan);
            }
        });
        findViewById(R.id.button_license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent license = new Intent(getApplicationContext(), StudyChatActivity.class);
                license.putExtra("userid", intent.getStringExtra("id"));
                license.putExtra("nick", intent.getStringExtra("my_nick"));
                license.putExtra("chatname", "test_license");
                startActivity(license);
            }
        });
        findViewById(R.id.button_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDB tinyDB = new TinyDB(SettingActivity.this);
                tinyDB.putBoolean("auto_login", false);
                tinyDB.remove("auto_id");
                tinyDB.remove("auto_pw");
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    /*
        라이센스 1 : fontawesome
        라이센스 2 : glide
        라이센스 3 : Book free icon : Icons made by Freepik from CC 3.0 BY
        라이센스 4 : retrofit
        라이센스 5 : TinyDB
        라이센스 6 : wasabeef/recyclerview-animators
        라이센스 7 : JakeWharton/butterknife
        라이센스 8 : firebase/firebase-android-sdk
        라이센스 9 : materialCalendarView
     */

    void popupedit(int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (type == 0) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_pwedit, null);
            builder.setView(view);
            builder.setTitle("비밀번호 변경");
            final EditText curpw = view.findViewById(R.id.curpw_dialog);
            final EditText newpw = view.findViewById(R.id.newpw_dialog);
            final EditText newpwcheck = view.findViewById(R.id.newpwcheck_dialog);

            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //server
                            Toast.makeText(getApplicationContext(), "관리자 승인후 변경됩니다." , Toast.LENGTH_LONG).show();
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
            final FrameLayout frameLayout = new FrameLayout(this);
            final EditText edittext = new EditText(this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(
                CUtils.DP(this, 15),
                CUtils.DP(this, 15),
                CUtils.DP(this, 15),
                CUtils.DP(this, 15)
            );
            edittext.setLayoutParams(layoutParams);
            frameLayout.addView(edittext);
            builder.setView(frameLayout);
            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //server
                            Toast.makeText(getApplicationContext(), "관리자 승인후 변경됩니다." , Toast.LENGTH_LONG).show();
                        }
                    });
            builder.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
        }
    }
}