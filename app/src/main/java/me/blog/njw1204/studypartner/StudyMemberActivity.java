package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyMemberActivity extends AppCompatActivity {

    private ArrayList<HashMap<String,String>> Data = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_member);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi_study_member);
        bottomNavigationView.setSelectedItemId(R.id.navi_study_member);
        BottomNavi.LetStudyNaviClickListener(this, bottomNavigationView);

        listView = findViewById(R.id.listview_member_list);

        /*
        InputData.put("nickname", "[운영] 나종우");
        InputData.put("message", "안녕하세요.");
        Data.add(InputData);

        InputData = new HashMap<>();
        InputData.put("nickname", "주영석");
        InputData.put("message", "잘 부탁드려요");
        Data.add(InputData);
        */

        SimpleAdapter simpleAdapter = new
                SimpleAdapter(this , Data, R.layout.two_items,
                    new String[]{"nickname","message"}, new int[]{R.id.text1, R.id.text2});
        listView.setAdapter(simpleAdapter);
        FetchMemberAPI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FetchMemberAPI() {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.getStudyMemberList(getIntent().getIntExtra("study_no", 0));
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
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap<>();
                        String tmpNick = jsonObject.getString("nick");
                        hashMap.put("nickname",
                            jsonObject.getBoolean("staff")
                                ? String.format("[관리] %s", tmpNick) : tmpNick
                        );
                        hashMap.put("message", jsonObject.getString("msg"));
                        Data.add(hashMap);
                    }
                    ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                    CUtils.hideProgress(pd);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                CUtils.DefaultNetworkFailDialog(StudyMemberActivity.this);
            }
        });
    }
}
