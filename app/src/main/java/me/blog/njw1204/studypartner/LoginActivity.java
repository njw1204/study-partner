package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signUp(View v) {
        Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signup);
    }

    public void login(View v) {
        LoginRequestToServer(
            ((EditText)findViewById(R.id.ID)).getText().toString(),
            ((EditText)findViewById(R.id.PW)).getText().toString()
        );
    }

    private void LoginRequestToServer(final String id, final String pw) {
        Intent intent = getIntent();
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.Login(id, pw);
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

                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                login.putExtra("id", id);
                startActivity(login);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.SimpleDialogShow(LoginActivity.this, "로그인에 실패했습니다.", true);
            }
        });
    }
}