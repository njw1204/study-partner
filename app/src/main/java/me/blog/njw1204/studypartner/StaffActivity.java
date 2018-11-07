package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        findViewById(R.id.staff_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffActivity.this, ApplyListActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                intent.putExtra("study_no", getIntent().getIntExtra("study_no", 0));
                startActivity(intent);
            }
        });

        findViewById(R.id.staff_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffActivity.this, EditUserActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                intent.putExtra("study_no", getIntent().getIntExtra("study_no", 0));
                startActivity(intent);
            }
        });

        findViewById(R.id.staff_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoNotice();
            }
        });

        Fetch();
    }

    private void DoNotice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        builder.setTitle("공지사항 변경");
        builder.setPositiveButton("확인",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FetchNoticeChange(edittext.getText().toString());
                }
            });
        builder.setNegativeButton("취소",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        builder.show();
    }

    private void FetchNoticeChange(String notice) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyAssignNotice(
            getIntent().getStringExtra("id"),
            getIntent().getStringExtra("pw"),
            getIntent().getIntExtra("study_no", 0),
            notice
        );
        final ProgressDialog pd = CUtils.showProgress(this, "로딩 중...", false);
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
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                CUtils.hideProgress(pd);
                Toast.makeText(getApplicationContext(), "공지사항을 변경했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                Toast.makeText(getApplicationContext(), "공지사항 변경에 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Fetch() {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.getStudyCheckStaff(
            getIntent().getStringExtra("id"),
            getIntent().getIntExtra("study_no", 0)
        );
        final ProgressDialog pd = CUtils.showProgress(this, "로딩 중...", false);
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
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                CUtils.hideProgress(pd);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                Toast.makeText(getApplicationContext(), "관리 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
