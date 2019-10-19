package com.example.bikecompaniesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClass {
        private static final String TAG = "RetrofitClass";
        private static final String BASE_URL = "https://api.citybik.es/v2/";
        public static final String HEADER_CACHE_CONTROL = "Cache-Control";
        public static final String HEADER_PRAGMA = "Pragma";
       private static Context context;

        private static RetrofitClass instance;

        public static RetrofitClass getInstance(Context mcontext){
            context=mcontext;
            return instance;
        }

        private static final long cacheSize = 5 * 1024 * 1024; // 5 MB


        private static Retrofit retrofit(){
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        private static OkHttpClient okHttpClient(){
            return new OkHttpClient.Builder()
                    .cache(cache())
                    .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                    .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                    .addInterceptor(offlineInterceptor())
                    .build();
        }

        private static Cache cache(){
            return new Cache(context.getCacheDir(),cacheSize);
        }

        /**
         * This interceptor will be called both if the network is available and if the network is not available
         * @return
         */
        private static Interceptor offlineInterceptor() {
            return new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //if the user doesn't specify the default value for the maxStale will be 10 minutes
                    int maxStale=10;
                    Log.d(TAG, "offline interceptor: called.");
                    Request request = chain.request();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    int expiryMinutes = prefs.getInt("expiry", 0);
                    Log.i(TAG,"expiry minutes"+ expiryMinutes);
                    if(expiryMinutes!=0){
                        maxStale=expiryMinutes;
                    }

                    // prevent caching when network is on.
                    if (!isNetworkConnected()) {
                        CacheControl cacheControl = new CacheControl.Builder()
                                //check the data,if it newer than the value of maxStale
                                .maxStale(maxStale, TimeUnit.MINUTES)
                                .build();

                        request = request.newBuilder()
                                .removeHeader(HEADER_PRAGMA)
                                .removeHeader(HEADER_CACHE_CONTROL)
                                .cacheControl(cacheControl)
                                .build();
                    }

                    return chain.proceed(request);
                }
            };
        }



         //only if the network is available
        private static Interceptor networkInterceptor() {
            return new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Log.d(TAG, "network interceptor: called.");

                    Response response = chain.proceed(chain.request());

                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxAge(5, TimeUnit.MINUTES)
                            .build();

                    return response.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                            .build();
                }
            };
        }

        private static HttpLoggingInterceptor httpLoggingInterceptor ()
        {
            HttpLoggingInterceptor httpLoggingInterceptor =
                    new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                    {
                        @Override
                        public void log (String message)
                        {
                            Log.d(TAG, "log: http log: " + message);
                        }
                    } );
            httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
            return httpLoggingInterceptor;
        }
    private static boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

        public static Api getApi(){
            return retrofit().create(Api.class);
        }

    }



