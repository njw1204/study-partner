package me.blog.njw1204.studypartner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StudyItemAPI {
    @GET("studypartner/study-list.php")
    Call<ResponseBody> getStudyList(@Query("kind") String kind, @Query("area") String area, @Query("school") String school, @Query("cnt") String cnt);

    @GET("studypartner/user-info.php")
    Call<ResponseBody> getUserInfo(@Query("id") String id);

    @GET("studypartner/user-study.php")
    Call<ResponseBody> getUserStudy(@Query("id") String id);

    @FormUrlEncoded
    @POST("studypartner/login.php")
    Call<ResponseBody> Login(@Query("id") String id, @Query("pw") String pw);

    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://apk.dothome.co.kr/")
                            .build();
}
