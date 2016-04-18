package br.com.reserveon.reserveon.rest.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Bruno on 17/04/2016.
 */
public class ClientTimeout {

    public static OkHttpClient getClientTimeout() {
        final OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
