package me.blog.njw1204.studypartner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavi {

    public static void LetNaviClickListener(final Activity activity, BottomNavigationView navi) {
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navi_my_study:
                        intent = new Intent(activity, MyStudyActivity.class);
                        intent.putExtra("id", activity.getIntent().getStringExtra("id"));
                        intent.putExtra("pw", activity.getIntent().getStringExtra("pw"));
                        intent.putExtra("my_school", activity.getIntent().getStringExtra("my_school"));
                        intent.putExtra("my_nick", activity.getIntent().getStringExtra("my_nick"));
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                    case R.id.navi_study_list:
                        intent = new Intent(activity, MainActivity.class);
                        intent.putExtra("id", activity.getIntent().getStringExtra("id"));
                        intent.putExtra("pw", activity.getIntent().getStringExtra("pw"));
                        intent.putExtra("my_school", activity.getIntent().getStringExtra("my_school"));
                        intent.putExtra("my_nick", activity.getIntent().getStringExtra("my_nick"));
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                    case R.id.navi_setting:
                        intent = new Intent(activity, SettingActivity.class);
                        intent.putExtra("id", activity.getIntent().getStringExtra("id"));
                        intent.putExtra("pw", activity.getIntent().getStringExtra("pw"));
                        intent.putExtra("my_school", activity.getIntent().getStringExtra("my_school"));
                        intent.putExtra("my_nick", activity.getIntent().getStringExtra("my_nick"));
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                }
                return false;
            }
        });
    }

    public static void LetStudyNaviClickListener(final Activity activity, BottomNavigationView navi) {
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent, origin = activity.getIntent();
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                switch (item.getItemId()) {
                    case R.id.navi_study_info:
                        intent = new Intent(activity, StudyInfoActivity.class);
                        for (String i : keys) {
                            SendStringExtra(i, intent, origin);
                        }
                        SendIntExtra("study_no", intent, origin, 0);
                        SendBooleanExtra("show_icon", intent, origin, false);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                    case R.id.navi_study_member:
                        intent = new Intent(activity, StudyMemberActivity.class);
                        for (String i : keys) {
                            SendStringExtra(i, intent, origin);
                        }
                        SendIntExtra("study_no", intent, origin, 0);
                        SendBooleanExtra("show_icon", intent, origin, false);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                        return true;
                    case R.id.navi_study_apply:
                        StartJoinStudy(activity);
                        return false;
                }
                return false;
            }
        });
    }

    private static void SendStringExtra(String key, Intent to, Intent from) {
        to.putExtra(key, from.getStringExtra(key));
    }

    private static void SendIntExtra(String key, Intent to, Intent from, int defaultValue) {
        to.putExtra(key, from.getIntExtra(key, defaultValue));
    }

    private static void SendBooleanExtra(String key, Intent to, Intent from, boolean defaultValue) {
        to.putExtra(key, from.getBooleanExtra(key, defaultValue));
    }

    private static void StartJoinStudy(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final LinearLayout linearLayout = new LinearLayout(activity);
        final EditText edittext = new EditText(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(
            CUtils.DP(activity, 15),
            CUtils.DP(activity, 15),
            CUtils.DP(activity, 15),
            CUtils.DP(activity, 15)
        );
        builder.setTitle("가입 신청");
        edittext.setLayoutParams(layoutParams);
        edittext.setHint("운영진에게 전송할\n메세지를 입력해주세요.\n(자기소개, 연락처 등)");
        linearLayout.addView(edittext);
        builder.setView(linearLayout);
        builder.setPositiveButton("확인",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //server
                    final String msgData = edittext.getText().toString();
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
                    builder2.setMessage("정말로 신청하시겠습니까?\n이전에 신청한 기록은 지워집니다.");
                    builder2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FetchStudyApplyAPI(activity, msgData);
                            }
                        }
                    );
                    builder2.setNegativeButton("취소", null);
                    builder2.show();
                }
            });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private static void FetchStudyApplyAPI(final Activity activity, final String msg) {
        Intent intent = activity.getIntent();
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyApply(
            intent.getIntExtra("study_no", 0),
            intent.getStringExtra("id"),
            intent.getStringExtra("pw"),
            msg
        );
        final ProgressDialog pd = CUtils.showProgress(activity, "로딩 중...", false);
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
                    if (!result.equals("OK")) {
                        onFailure(call, new Throwable(result));
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                CUtils.hideProgress(pd);
                Toast.makeText(activity.getApplicationContext(), "가입 신청이 작성되었습니다.\n운영진 승인후 가입이 완료됩니다.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);

                if (t.getMessage().equals("already")) {
                    Toast.makeText(activity.getApplicationContext(), "이미 가입한 스터디입니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                CUtils.DefaultNetworkFailDialog(activity, false);
            }
        });
    }
}
