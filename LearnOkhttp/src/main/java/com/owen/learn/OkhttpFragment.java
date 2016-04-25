package com.owen.learn;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okio.BufferedSink;

/**
 * A placeholder fragment containing a simple view.
 */
public class OkhttpFragment extends Fragment {

    private static final String URL_GET = "https://raw.github.com/square/okhttp/master/README.md";
    private static final String URL_POST = "https://echo.getpostman.com/post";
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mTextView.setText((String) msg.obj);
            return false;
        }
    });

    final Runnable mRefreshDone = new Runnable() {

        @Override
        public void run() {
            mRefreshLayout.setRefreshing(false);
        }

    };

    public OkhttpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //取消手势滑动监听
        mRefreshLayout.setEnabled(false);

        return view;
    }

/*    Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(20));
    dispatcher.setMaxRequests(20);
    dispatcher.setMaxRequestsPerHost(1);

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                        .dispatcher(dispatcher)
                                        .connectionPool(new ConnectionPool(100, 30, TimeUnit.SECONDS))
                                        .build();*/

    @OnClick(R.id.button_get)
    void okhttpGet() {
        mRefreshLayout.setRefreshing(true);

        OkHttpClient client = new OkHttpClient();

        int cacheSize = 5 * 1024 * 1024; //5MiB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);
        client.setCache(cache);

        client.networkInterceptors().add(new StethoInterceptor());

        Request request = new Request.Builder()
                                  .url(URL_GET)
                                  .build();

//        try {
//            Response response = client.newCall(request).execute();
//            mTextView.setText(response.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = "";
                if (!response.isSuccessful()) {
                    result = "error!!!!!!!";
                }
                result = response.body().string();

                setRefreshDone();
                mHandler.sendMessage(Message.obtain(mHandler, 0, result));
            }
        });
    }

    @OnClick(R.id.button_post)
    void okhttpPost() {
        mRefreshLayout.setRefreshing(true);

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());

/*        String json = generateJson("Jesse", "Jake");
        RequestBody body = RequestBody.create(MEDIA_TYPE, json);*/
        RequestBody body = new FormEncodingBuilder()
                                   .add("user", "owen")
                                   .add("password", "123456")
                                   .build();

        Request request = new Request.Builder()
                                  .url(URL_POST)
                                  .addHeader("Content-Type", "text/plain")
                                  .addHeader("apikey", "123456789")
                                  .post(body)
                                  .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = "";
                if (!response.isSuccessful()) {
                    result = "error!!!!!!!";
                }
                result = response.body().string();

                setRefreshDone();
                mHandler.sendMessage(Message.obtain(mHandler, 0, result));
            }
        });
    }

    String generateJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                       + "'name':'Bowling',"
                       + "'round':4,"
                       + "'lastSaved':1367702411696,"
                       + "'dateStarted':1367702378785,"
                       + "'players':["
                       + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                       + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                       + "]}";
    }

    private void setRefreshDone() {
        mHandler.removeCallbacks(mRefreshDone);
        mHandler.post(mRefreshDone);
    }

}
