package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Currency;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyListActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> Data;
    @BindView(R.id.listView_applyList) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);

        Data = new ArrayList<>();
        getSupportActionBar().setTitle("가입 신청 관리");

        SimpleAdapter simpleAdapter = new SimpleAdapter(this , Data, R.layout.two_items,
            new String[]{"nick", "msg"}, new int[]{R.id.text1, R.id.text2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplyListActivity.this);
                builder.setPositiveButton("가입 승인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FetchAccept(Data.get(position).get("id"), true);
                    }
                });
                builder.setNegativeButton("가입 반려", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FetchAccept(Data.get(position).get("id"), false);
                    }
                });
                builder.setNeutralButton("취소", null);
                builder.setMessage(String.format("%s 님을...", Data.get(position).get("nick")));
                builder.setCancelable(true);
                builder.show();
            }
        });

        FetchApplyListAPI();
    }

    private void FetchAccept(String applyId, final boolean accept) {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyApplyAnswer(
            getIntent().getStringExtra("id"),
            getIntent().getStringExtra("pw"),
            getIntent().getIntExtra("study_no", 0),
            applyId,
            (accept ? "T" : "F")
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
                if (accept) {
                    Toast.makeText(getApplicationContext(), "가입 승인 완료", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "가입 반려 완료", Toast.LENGTH_SHORT).show();
                }
                FetchApplyListAPI();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                if (t.getMessage().equals("already")) {
                    Toast.makeText(getApplicationContext(), "이미 가입된 멤버입니다.", Toast.LENGTH_SHORT).show();
                    FetchApplyListAPI();
                }
                else {
                    CUtils.DefaultNetworkFailDialog(ApplyListActivity.this);
                }
            }
        });
    }

    private void FetchApplyListAPI() {
        StudyItemAPI api = StudyItemAPI.retrofit.create(StudyItemAPI.class);
        Call<ResponseBody> http = api.postStudyApplyList(
            getIntent().getStringExtra("id"),
            getIntent().getStringExtra("pw"),
            getIntent().getIntExtra("study_no", 0)
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
                        hashMap.put("id", jsonObject.getString("id"));
                        hashMap.put("nick", jsonObject.getString("nick"));
                        hashMap.put("msg", jsonObject.getString("msg"));
                        Data.add(hashMap);
                    }
                    ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                    CUtils.hideProgress(pd);
                    Toast.makeText(getApplicationContext(), "가입 승인(또는 거절)할 사람을 클릭하세요.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CUtils.hideProgress(pd);
                CUtils.DefaultNetworkFailDialog(ApplyListActivity.this);
            }
        });
    }
}
