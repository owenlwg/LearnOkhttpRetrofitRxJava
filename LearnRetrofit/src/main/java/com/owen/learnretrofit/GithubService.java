package com.owen.learnretrofit;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Owen on 2016/4/21.
 */
public interface GithubService {
    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);

    @Headers({
         "Content-Type:text/plain",
         "apikey:123456789"
    })
    @FormUrlEncoded
    @POST("post")
    Call<PostMan> postUser(@Field("user") String user, @Field("password") String password);
}
