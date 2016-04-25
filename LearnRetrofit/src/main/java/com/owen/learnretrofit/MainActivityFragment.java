package com.owen.learnretrofit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String URL_GET = "https://api.github.com/";
    private static final String URL_POST = "https://echo.getpostman.com/";
    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setEnabled(false);

        return view;
    }

    @OnClick(R.id.button_get)
    void retrofitGet() {
        mRefreshLayout.setRefreshing(true);

        //创建一个指向API_URL的REST适配器
        Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(URL_GET)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

        Gson gson = new GsonBuilder().serializeNulls().create();

        //实现GithubService
        GithubService githubService = retrofit.create(GithubService.class);
        //创建Call的一个实例
        Call<User> call = githubService.getUser("owenplus");
        //异步方式获取User信息
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(response.body().toString());
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.button_post)
    void retrofitPost() {
        mRefreshLayout.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL_POST)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        GithubService githubService = retrofit.create(GithubService.class);
        Call<PostMan> postCall = githubService.postUser("owen", "123456");

        postCall.enqueue(new Callback<PostMan>() {
            @Override
            public void onResponse(Call<PostMan> call, final Response<PostMan> response) {
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(response.body().toString());
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<PostMan> call, Throwable t) {

            }
        });

    }
}
