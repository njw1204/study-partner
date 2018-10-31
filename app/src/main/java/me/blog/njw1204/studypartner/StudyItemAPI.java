package me.blog.njw1204.studypartner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StudyItemAPI {
    @GET("studypartner/study-list.php")
    Call<ResponseBody> getStudyList(@Query("kind") String kind, @Query("area") String area, @Query("school") String school, @Query("cnt") String cnt);

    @GET("studypartner/user-info.php")
    Call<ResponseBody> getUserInfo(@Query("id") String id);

    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://apk.dothome.co.kr/")
                            .build();
}
