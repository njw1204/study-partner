package me.blog.njw1204.studypartner;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface StudyItemAPI {
    @GET("studypartner/study-list.php")
    Call<ResponseBody> getStudyList(@Query("kind") String kind, @Query("area") String area, @Query("school") String school);

    @GET("studypartner/study-info.php")
    Call<ResponseBody> getStudyInfo(@Query("study_no") int study_no);

    @GET("studypartner/study-member-list.php")
    Call<ResponseBody> getStudyMemberList(@Query("study_no") int study_no);

    @GET("studypartner/user-info.php")
    Call<ResponseBody> getUserInfo(@Query("id") String id);

    @GET("studypartner/user-study.php")
    Call<ResponseBody> getUserStudy(@Query("id") String id);

    @GET("studypartner/study-check-staff.php")
    Call<ResponseBody> getStudyCheckStaff(@Query("id") String id, @Query("study_no") int study_no);

    @FormUrlEncoded
    @POST("studypartner/study-make.php")  // 아이콘 추가 구현
    Call<ResponseBody> postStudyMake(@Field("id") String id, @Field("pw") String pw, @Field("title") String title,
                                     @Field("kind") String kind, @Field("area") String area, @Field("school") String school,
                                     @Field("contact") String contact, @Field("info") String info);

    @Multipart
    @POST("studypartner/upload-icon.php")
    Call<ResponseBody> postIcon(@Part("id") RequestBody id, @Part("pw") RequestBody pw,
                                @Part("title") RequestBody title, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("studypartner/study-apply.php")
    Call<ResponseBody> postStudyApply(@Field("study_no") int study_no, @Field("id") String id, @Field("pw") String pw, @Field("msg") String msg);

    @FormUrlEncoded
    @POST("studypartner/login.php")
    Call<ResponseBody> Login(@Field("id") String id, @Field("pw") String pw);

    @FormUrlEncoded
    @POST("studypartner/signup.php")
    Call<ResponseBody> Signup(@Field("id") String id, @Field("pw") String pw, @Field("school") String school, @Field("nick") String nick);

    @FormUrlEncoded
    @POST("studypartner/study-apply-answer.php")
    Call<ResponseBody> postStudyApplyAnswer(@Field("id") String id, @Field("pw") String pw, @Field("study_no") int study_no,
                                            @Field("apply_id") String apply_id, @Field("accept") String accept);

    @FormUrlEncoded
    @POST("studypartner/study-apply-list.php")
    Call<ResponseBody> postStudyApplyList(@Field("id") String id, @Field("pw") String pw, @Field("study_no") int study_no);

    @FormUrlEncoded
    @POST("studypartner/study-assign-staff.php")
    Call<ResponseBody> postStudyAssignStaff(@Field("id") String id, @Field("pw") String pw, @Field("study_no") int study_no, @Field("apply_id") String apply_id);

    @FormUrlEncoded
    @POST("studypartner/study-change-msg.php")
    Call<ResponseBody> postStudyChangeMsg(@Field("id") String id, @Field("pw") String pw, @Field("study_no") int study_no, @Field("msg") String msg);

    @FormUrlEncoded
    @POST("studypartner/study-kick-member.php")
    Call<ResponseBody> postStudyKickMember(@Field("id") String id, @Field("pw") String pw, @Field("study_no") int study_no, @Field("apply_id") String apply_id);

    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://apk.dothome.co.kr/")
                            .build();
}
