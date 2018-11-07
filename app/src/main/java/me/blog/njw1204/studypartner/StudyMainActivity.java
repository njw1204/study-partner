package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_main);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        LetClickListener();

        if (getIntent().getBooleanExtra("show_icon", false)) {
            RequestOptions options = new RequestOptions()
                                         .centerCrop()
                                         .placeholder(R.color.bottom_navi_color)
                                         .error(R.drawable.book);
            Glide.with(this)
                .load(getIntent().getStringExtra("icon"))
                .apply(options)
                .into((ImageView)findViewById(R.id.studyImage));
        }
        else {
            findViewById(R.id.studyImage).setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.study_staff_menu, menu);
        final MenuItem item = menu.findItem(R.id.navi_study_staff);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMainActivity.this, StaffActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                intent.putExtra("study_no", getIntent().getIntExtra("study_no", 0));
                startActivity(intent);
            }
        });
        final MenuItem item2 = menu.findItem(R.id.navi_study_nick_change);
        item2.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FuncItem2();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navi_study_staff:
                Intent intent = new Intent(StudyMainActivity.this, StaffActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("pw", getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                intent.putExtra("study_no", getIntent().getIntExtra("study_no", 0));
                startActivity(intent);
                return true;
            case R.id.navi_study_nick_change:
                FuncItem2();
                return true;
            default:
                return false;
        }
    }

    private void FuncItem2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudyMainActivity.this);
        final FrameLayout frameLayout = new FrameLayout(StudyMainActivity.this);
        final EditText edittext = new EditText(StudyMainActivity.this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(
            CUtils.DP(StudyMainActivity.this, 15),
            CUtils.DP(StudyMainActivity.this, 15),
            CUtils.DP(StudyMainActivity.this, 15),
            CUtils.DP(StudyMainActivity.this, 15)
        );
        edittext.setLayoutParams(layoutParams);
        frameLayout.addView(edittext);
        builder.setView(frameLayout);
        builder.setTitle("프로필 메시지 변경");
        builder.setPositiveButton("확인",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FetchChange(edittext.getText().toString());
                }
            });
        builder.setNegativeButton("취소",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        builder.show();
    }

    private void FetchChange(String msg) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyChangeMsg(
            getIntent().getStringExtra("id"),
            getIntent().getStringExtra("pw"),
            getIntent().getIntExtra("study_no", 0),
            msg
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
                Toast.makeText(getApplicationContext(), "프로필 메시지를 변경했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                Toast.makeText(getApplicationContext(), "프로필 메시지 변경에 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void LetClickListener() {
        final Intent intent = getIntent();
        findViewById(R.id.layout_Info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMainActivity.this, StudyInfoActivity.class);
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                for (String i : keys) {
                    CUtils.SendStringExtra(i, intent, getIntent());
                }
                CUtils.SendIntExtra("study_no", intent, getIntent(), 0);
                CUtils.SendBooleanExtra("show_icon", intent, getIntent(), false);
                intent.putExtra("hide_navi", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_Notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
                Call<ResponseBody> http = api.getStudyNotice(getIntent().getIntExtra("study_no", 0));
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

                        CUtils.SimpleDialogShow(StudyMainActivity.this, result, true);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        CUtils.DefaultNetworkFailDialog(StudyMainActivity.this);
                    }
                });
            }
        });
        findViewById(R.id.layout_Member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMainActivity.this, StudyMemberActivity.class);
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                for (String i : keys) {
                    CUtils.SendStringExtra(i, intent, getIntent());
                }
                CUtils.SendIntExtra("study_no", intent, getIntent(), 0);
                CUtils.SendBooleanExtra("show_icon", intent, getIntent(), false);
                intent.putExtra("hide_navi", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_Plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyMainActivity.this, StudyPlanActivity.class);
                String keys[] = {"id", "pw", "my_school", "my_nick", "title", "kind", "school", "area", "icon"};
                for (String i : keys) {
                    CUtils.SendStringExtra(i, intent, getIntent());
                }
                CUtils.SendIntExtra("study_no", intent, getIntent(), 0);
                CUtils.SendBooleanExtra("show_icon", intent, getIntent(), false);
                intent.putExtra("hide_navi", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_Chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(getApplicationContext(), StudyChatActivity.class);
                chatIntent.putExtra("title", intent.getStringExtra("title"));
                chatIntent.putExtra("userid", intent.getStringExtra("id"));
                chatIntent.putExtra("nick", intent.getStringExtra("my_nick"));
                chatIntent.putExtra("chatname", intent.getStringExtra("title"));
                startActivity(chatIntent);
            }
        });
    }
}
