package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
        getSupportActionBar().hide();
        ((EditText)findViewById(R.id.PW)).setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(v);
                    return true;
                }
                return false;
            }
        });

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        if (tinyDB.getBoolean("auto_login")) {
            ((EditText)findViewById(R.id.ID)).setText(tinyDB.getString("auto_id"));
            ((EditText)findViewById(R.id.PW)).setText("password123456789");
            LoginRequestToServer(
                tinyDB.getString("auto_id"),
                tinyDB.getString("auto_pw"),
                true
            );
        }
    }

    public void signUp(View v) {
        Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signup);
    }

    public void login(View v) {
        LoginRequestToServer(
            ((EditText)findViewById(R.id.ID)).getText().toString(),
            ((EditText)findViewById(R.id.PW)).getText().toString(),
            false
        );
    }

    private void LoginRequestToServer(final String id, final String pw, final boolean autoLogin) {
        final StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.Login(id, pw);
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
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                    Call<ResponseBody> http = api.getUserInfo(id);
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

                            String nick = "", school = "";
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                nick = jsonObject.getString("nick");
                                school = CUtils.NNNull(jsonObject.getString("school"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                onFailure(call, new Throwable(e.getMessage()));
                            }

                            if (!autoLogin) {
                                TinyDB tinyDB = new TinyDB(getApplicationContext());
                                tinyDB.putString("auto_id", id);
                                tinyDB.putString("auto_pw", pw);
                                tinyDB.putBoolean("auto_login", true);
                            }

                            CUtils.hideProgress(pd);
                            Toast.makeText(getApplicationContext(), "스터디파트너에 오신 것을 환영합니다.", Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(getApplicationContext(), MainActivity.class);
                            login.putExtra("id", id);
                            login.putExtra("pw", pw);
                            login.putExtra("my_nick", nick);
                            login.putExtra("my_school", school);
                            startActivity(login);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            CUtils.hideProgress(pd);
                            if (autoLogin) {
                                ((EditText)findViewById(R.id.ID)).setText("");
                                ((EditText)findViewById(R.id.PW)).setText("");
                                Toast.makeText(getApplicationContext(), "자동 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                if (autoLogin) {
                    ((EditText)findViewById(R.id.ID)).setText("");
                    ((EditText)findViewById(R.id.PW)).setText("");
                    Toast.makeText(getApplicationContext(), "자동 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}