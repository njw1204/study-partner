package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class EditUserActivity extends AppCompatActivity {
    private ArrayList<HashMap<String,String>> Data;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setTitle("멤버 관리");

        Data = new ArrayList<>();
        listView = findViewById(R.id.listview_edit_user);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this , Data, R.layout.two_items,
            new String[]{"nickname","message"}, new int[]{R.id.text1, R.id.text2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);
                builder.setPositiveButton("관리진 승격", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FetchStaff(Data.get(position).get("id"));
                    }
                });
                builder.setNegativeButton("강제 탈퇴", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FetchKick(Data.get(position).get("id"));
                    }
                });
                builder.setNeutralButton("취소", null);
                builder.setMessage(String.format("%s 님을...", Data.get(position).get("nickname")));
                builder.setCancelable(true);
                builder.show();
            }
        });

        FetchMemberAPI();
    }

    private void FetchStaff(String applyId) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyAssignStaff(
            getIntent().getStringExtra("id"),
            getIntent().getStringExtra("pw"),
            getIntent().getIntExtra("study_no", 0),
            applyId
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
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                CUtils.hideProgress(pd);
                Toast.makeText(getApplicationContext(), "관리 권한 부여 완료", Toast.LENGTH_SHORT).show();
                FetchMemberAPI();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                if (t.getMessage().equals("already")){
                    Toast.makeText(getApplicationContext(), "이미 관리 권한을 가진 멤버입니다.", Toast.LENGTH_SHORT).show();
                    FetchMemberAPI();
                }
                else {
                    CUtils.DefaultNetworkFailDialog(EditUserActivity.this);
                }
            }
        });
    }

    private void FetchKick(String applyId) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyKickMember(
            getIntent().getStringExtra("id"),
            getIntent().getStringExtra("pw"),
            getIntent().getIntExtra("study_no", 0),
            applyId
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
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                CUtils.hideProgress(pd);
                Toast.makeText(getApplicationContext(), "강제 탈퇴 완료", Toast.LENGTH_SHORT).show();
                FetchMemberAPI();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                if (t.getMessage().equals("staff")){
                    Toast.makeText(getApplicationContext(), "관리진은 추방할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    FetchMemberAPI();
                }
                else {
                    CUtils.DefaultNetworkFailDialog(EditUserActivity.this);
                }
            }
        });
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
                    Data.clear();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap<>();
                        String tmpNick = jsonObject.getString("nick");
                        hashMap.put("id", jsonObject.getString("id"));
                        hashMap.put("nickname",
                            jsonObject.getBoolean("staff")
                                ? String.format("[관리] %s", tmpNick) : tmpNick
                        );
                        hashMap.put("message", jsonObject.getString("msg"));
                        Data.add(hashMap);
                    }
                    ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                    CUtils.hideProgress(pd);
                    CUtils.hideProgress(pd);
                    Toast.makeText(getApplicationContext(), "권한을 변경할 사람을 클릭하세요.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                CUtils.DefaultNetworkFailDialog(EditUserActivity.this);
            }
        });
    }
}
