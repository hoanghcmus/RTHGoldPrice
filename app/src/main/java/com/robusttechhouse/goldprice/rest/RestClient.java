package com.robusttechhouse.goldprice.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robusttechhouse.goldprice.common.Setting;
import com.robusttechhouse.goldprice.utils.LogUtils;
import com.robusttechhouse.goldprice.utils.NetworkUtils;
import com.robusttechhouse.goldprice.utils.NoNetworkException;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Rest client used for synchronize data via network
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class RestClient {
    private static RestClient sInstance = null;
    private OkHttpClient mHttpClient;
    private UserService mUserService;
    private boolean isInitialized = false;

    public static synchronized RestClient getInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    private void setupCache(Context context) {
        try {
            File cacheDir = new File(context.getCacheDir(), Setting.OK_HTTP_CLIENT_CACHE_FILE_NAME);
            Cache cache = new Cache(cacheDir, Setting.OK_HTTP_CLIENT_CACHE_SIZE);
            mHttpClient.setCache(cache);
            mHttpClient.setReadTimeout(Setting.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            mHttpClient.setConnectTimeout(Setting.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            mHttpClient.setWriteTimeout(Setting.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            mHttpClient.interceptors().add(new NetworkInterceptor(context));
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    public void init(final Context context) {
        if (isInitialized) {
            return;
        }

        mHttpClient = new OkHttpClient();
        setupCache(context);

        Gson gson = new GsonBuilder().setDateFormat(Setting.GSON_DATE_FORMAT).create();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader(Setting.REQUEST_HEADER_TOKEN, Setting.REQUEST_HEADER_TOKEN_VALUE);
                request.addHeader(Setting.REQUEST_HEADER_CONTENT_TYPE, Setting.REQUEST_HEADER_CONTENT_TYPE_JSON_VALUE);
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Setting.REST_ROOT_URL)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient(mHttpClient))
                .build();

        mUserService = restAdapter.create(UserService.class);

        isInitialized = true;
    }

    public UserService getUserService() {
        return mUserService;
    }

    class NetworkInterceptor implements Interceptor {

        Context mContext;

        public NetworkInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            if (mContext != null && !NetworkUtils.isNetworkConnected(mContext)) {
                throw new NoNetworkException();
            }
            mContext = null;
            return chain.proceed(chain.request());
        }
    }
}
