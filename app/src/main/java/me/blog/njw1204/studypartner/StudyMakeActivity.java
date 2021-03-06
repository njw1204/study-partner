package me.blog.njw1204.studypartner;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class StudyMakeActivity extends AppCompatActivity {
    @BindView(R.id.edittext_set_title) EditText eTitle;
    @BindView(R.id.textview_set_kind) TextView tKind;
    @BindView(R.id.textview_set_area) TextView tArea;
    @BindView(R.id.radio_set_yes) RadioButton rYes;
    @BindView(R.id.edittext_contact) EditText eContact;
    @BindView(R.id.edittext_set_info) EditText eInfo;
    @BindView(R.id.textview_info_cnt) TextView tCnt;
    @BindView(R.id.button_set_kind) Button bKind;
    @BindView(R.id.button_set_area) Button bArea;
    @BindView(R.id.button_make_study) Button bMake;
    @BindView(R.id.button_add_icon) Button bImg;

    private StudyPartner application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_make);
        ButterKnife.bind(this);
        application = (StudyPartner)getApplication();
        application.setAddIconUrl("");

        getSupportActionBar().setTitle("스터디 만들기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tCnt.setText(
                    String.format(Locale.KOREA, "%d/500자", s.length())
                );
            }
        });

        bKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {
                    "IT/컴퓨터", "어학/외국어",
                    "과학/공학", "시사/경제", "취업/창업",
                    "철학/사회", "문학/글쓰기", "사교/취미",
                    "예체능/음악", "봉사활동", "기타"
                };
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudyMakeActivity.this);
                alertDialogBuilder.setTitle("분야 선택");
                alertDialogBuilder.setItems(items,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            tKind.setText(items[id]);
                            dialog.dismiss();
                        }
                    });
                AlertDialog alertDialog2 = alertDialogBuilder.create();
                alertDialog2.show();
            }
        });

        bArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {
                    "서울", "경기", "인천", "강원", "대전",
                    "세종", "충남", "충북", "부산", "울산",
                    "경남", "경북", "대구", "광주", "전남",
                    "전북", "제주", "해외", "기타"
                };
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudyMakeActivity.this);
                alertDialogBuilder.setTitle("지역 선택");
                alertDialogBuilder.setItems(items,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            tArea.setText(items[id]);
                            dialog.dismiss();
                        }
                    });
                AlertDialog alertDialog2 = alertDialogBuilder.create();
                alertDialog2.show();
            }
        });

        bMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 글자수 제한 좀더 강화
                if (CUtils.IsEmpty(eTitle.getText().toString())) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "스터디명을 입력하세요.", true);
                }
                else if (CUtils.IsEmpty(tKind.getText().toString())) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "분야를 골라주세요.", true);
                }
                else if (CUtils.IsEmpty(tArea.getText().toString())) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "지역을 골라주세요.", true);
                }
                else if (CUtils.IsEmpty(eContact.getText().toString())) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "연락처를 입력하세요.", true);
                }
                else if (CUtils.IsEmpty(eInfo.getText().toString())) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "스터디 설명 및 소개를 입력하세요.", true);
                }
                else if (eInfo.getText().toString().length() > 500) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "설명 및 소개를 500자 이하로 입력하세요.", true);
                }
                else {
                    FetchStudyMakeAPI();
                }
            }
        });

        bImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMakeActivity.this, StudyMakeAddIconActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                login.putExtra("id", getIntent().getStringExtra("id"));
                login.putExtra("pw", getIntent().getStringExtra("pw"));
                login.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                login.putExtra("my_school", getIntent().getStringExtra("my_school"));
                startActivity(login);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent login = new Intent(getApplicationContext(), MainActivity.class);
        login.putExtra("id", getIntent().getStringExtra("id"));
        login.putExtra("pw", getIntent().getStringExtra("pw"));
        login.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
        login.putExtra("my_school", getIntent().getStringExtra("my_school"));
        startActivity(login);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void FetchStudyMakeAPI() {
        final Intent intent = getIntent();
        final StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        final ProgressDialog pd = CUtils.showProgress(this, "로딩 중...", false);

        Call<ResponseBody> http = api.postStudyMake(
            intent.getStringExtra("id"),
            intent.getStringExtra("pw"),
            eTitle.getText().toString(),
            tKind.getText().toString(),
            tArea.getText().toString(),
            (rYes.isChecked() ? "T" : "F"),
            eContact.getText().toString(),
            eInfo.getText().toString()
        );

        http.enqueue(new retrofit2.Callback<ResponseBody>() {
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

                if (!CUtils.IsEmpty(application.getAddIconUrl())) {
                    File file = new File(application.getAddIconUrl());
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                        "file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file)
                    );
                    Call<ResponseBody> http = api.postIcon(
                        RequestBody.create(MediaType.parse("multipart/form-data"), intent.getStringExtra("id")),
                        RequestBody.create(MediaType.parse("multipart/form-data"), intent.getStringExtra("pw")),
                        RequestBody.create(MediaType.parse("multipart/form-data"), eTitle.getText().toString()),
                        filePart
                    );

                    http.enqueue(new retrofit2.Callback<ResponseBody>() {
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
                            Toast.makeText(getApplicationContext(), "성공적으로 등록했습니다.", Toast.LENGTH_LONG).show();

                            Intent login = new Intent(getApplicationContext(), MainActivity.class);
                            login.putExtra("id", getIntent().getStringExtra("id"));
                            login.putExtra("pw", getIntent().getStringExtra("pw"));
                            login.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                            login.putExtra("my_school", getIntent().getStringExtra("my_school"));
                            startActivity(login);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            CUtils.hideProgress(pd);
                            Toast.makeText(getApplicationContext(), "사진을 제외하고 성공적으로 등록했습니다.", Toast.LENGTH_LONG).show();

                            Intent login = new Intent(getApplicationContext(), MainActivity.class);
                            login.putExtra("id", getIntent().getStringExtra("id"));
                            login.putExtra("pw", getIntent().getStringExtra("pw"));
                            login.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                            login.putExtra("my_school", getIntent().getStringExtra("my_school"));
                            startActivity(login);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }
                    });
                }
                else {
                    CUtils.hideProgress(pd);
                    Toast.makeText(getApplicationContext(), "성공적으로 등록했습니다.", Toast.LENGTH_LONG).show();

                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    login.putExtra("id", getIntent().getStringExtra("id"));
                    login.putExtra("pw", getIntent().getStringExtra("pw"));
                    login.putExtra("my_nick", getIntent().getStringExtra("my_nick"));
                    login.putExtra("my_school", getIntent().getStringExtra("my_school"));
                    startActivity(login);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);

                if (t.getMessage().equals("already")) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "이미 존재하는 스터디명입니다.", true);
                }
                else if (t.getMessage().equals("no school")) {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "프로필의 학교 정보가 비어있어 [우리 학교만] 옵션을 사용할 수 없습니다.", true);
                }
                else {
                    CUtils.SimpleDialogShow(StudyMakeActivity.this, "스터디 등록에 실패했습니다.", true);
                }
            }
        });
    }
}
