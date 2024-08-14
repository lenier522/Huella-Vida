package com.zonassoft.footprintforlife.connection;
import com.google.gson.GsonBuilder;
import com.zonassoft.footprintforlife.data.Constant;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BuildConfig;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAdapter {

    public static API createAPI(Cache myCache, final boolean hasInternet) {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        if (hasInternet)
                            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                        else
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();

                        return chain.proceed(request);
                    }
                }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.WEB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(API.class);
    }

    public static API createAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging);
        }
        builder.cache(null);
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.WEB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(API.class);
    }

    /**
     * createShortAPI use only for GCM registration
     * use 2 second only to connect and register
     */
    public static API createShortAPI() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .cache(null)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("Constant.WEB_URL")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(okHttpClient)
                .build();

        return retrofit.create(API.class);
    }
}
