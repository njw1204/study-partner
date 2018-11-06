package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyInfoActivity extends AppCompatActivity {
    private boolean showIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_info);
        BottomNavi.LetStudyNaviClickListener(this, (BottomNavigationView)findViewById(R.id.bottomnavi_study_info));

        getSupportActionBar().setTitle(intent.getStringExtra("title"));
        ((TextView)findViewById(R.id.Name_info)).setText(intent.getStringExtra("title"));
        ((TextView)findViewById(R.id.Field_info)).setText(
            (CUtils.IsPseudoEmpty(intent.getStringExtra("kind"))
                 ? "기타" : intent.getStringExtra("kind"))
        );
        ((TextView)findViewById(R.id.RegionUniv_info)).setText(
            String.format("%s / %s",
                (CUtils.IsPseudoEmpty(intent.getStringExtra("area"))
                     ? "지역 상관 없음" : intent.getStringExtra("area")),
                (CUtils.IsPseudoEmpty(intent.getStringExtra("school"))
                     ? "학교 상관 없음" : intent.getStringExtra("school"))
            )
        );

        showIcon = intent.getBooleanExtra("show_icon", false);
        if (showIcon) {
            RequestOptions options = new RequestOptions()
                                         .centerCrop()
                                         .placeholder(R.color.bottom_navi_color)
                                         .error(R.color.bottom_navi_color);
            Glide.with(this)
                .load(intent.getStringExtra("icon"))
                .apply(options)
                .into((ImageView)findViewById(R.id.imageview_study_info_icon));
        }
        else {
            ImageView imageView = findViewById(R.id.imageview_study_info_icon);
            imageView.setVisibility(View.INVISIBLE);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        }

        FetchStudyInfoAPI(intent.getIntExtra("study_no", 0));
    }

    private void FetchStudyInfoAPI(int study_no) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.getStudyInfo(study_no);
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
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String contact = CUtils.NNNull(jsonObject.getString("contact"));
                    String info = CUtils.NNNull(jsonObject.getString("info"));
                    ((TextView)findViewById(R.id.Contact_info)).setText(contact);
                    ((TextView)findViewById(R.id.Description_info)).setText(info);
                    CUtils.hideProgress(pd);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                CUtils.DefaultNetworkFailDialog(StudyInfoActivity.this);
            }
        });
    }
}
