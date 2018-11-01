package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View v) {
        String id, pw, pww, school, nick;
        id = ((TextView)findViewById(R.id.newid)).getText().toString();
        pw = ((TextView)findViewById(R.id.newpw)).getText().toString();
        pww = ((TextView)findViewById(R.id.newpwcheck)).getText().toString();
        school = ((TextView)findViewById(R.id.newuniv)).getText().toString();
        nick = ((TextView)findViewById(R.id.newnickname)).getText().toString();
        switch (CheckValid(id, pw, pww, school, nick)) {
            case 1:
                CUtils.SimpleDialogShow(this, "아이디를 5~30자로 입력해주세요.", true);
                break;
            case 2:
                CUtils.SimpleDialogShow(this, "비밀번호를 5~30자로 입력해주세요.", true);
                break;
            case 3:
                CUtils.SimpleDialogShow(this, "비밀번호가 일치하지 않습니다.", true);
                break;
            case 4:
                CUtils.SimpleDialogShow(this, "대학교명을 5~20자로 입력해주세요.", true);
                break;
            case 5:
                CUtils.SimpleDialogShow(this, "닉네임을 2~10자로 입력해주세요.", true);
                break;
            case 6:
                CUtils.SimpleDialogShow(this, "아이디와 비밀번호는 공백을 포함할 수 없습니다.", true);
                break;
            default:
                SignupRequestToServer(id, pw, school, nick);
                break;
        }
    }

    private int CheckValid(String id, String pw, String pww, String school, String nick) {
        if (id.length() < 5 || id.length() > 30)
            return 1;
        if (pw.length() < 5 || pw.length() > 30)
            return 2;
        if (!pw.equals(pww))
            return 3;
        if (school.length() != 0 && (school.length() < 5 || school.length() > 20))
            return 4;
        if (nick.length() < 2 || nick.length() > 10)
            return 5;
        if (id.contains(" ") || pw.contains(" "))
            return 6;

        return 0;
    }

    private void SignupRequestToServer(final String id, final String pw, final String school, final String nick) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.Signup(id, pw, school, nick);
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

                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.SimpleDialogShow(SignUpActivity.this, "회원가입에 실패했습니다.", true);
            }
        });
    }
}
