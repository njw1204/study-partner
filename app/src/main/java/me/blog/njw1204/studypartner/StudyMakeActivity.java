package me.blog.njw1204.studypartner;

import android.app.Application;
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

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void FetchStudyMakeAPI() {

    }
}
