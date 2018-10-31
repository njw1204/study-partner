package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signUp(View v){
        Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signup);
    }

    public void login(View v){
        Intent login = new Intent(getApplicationContext(), MainActivity.class);
        //server
        login.putExtra("ID", "exampleID");
        startActivity(login);
        finish();
    }
}