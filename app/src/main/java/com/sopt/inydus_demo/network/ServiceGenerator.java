package com.sopt.inydus_demo.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient();
    private static String endPoint = "http://52.32.184.176:5000";
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(endPoint)
                    .addConverterFactory(GsonConverterFactory.create());

/*
    public static <S> S createService(Class<S> serviceClass){
        return createService(serviceClass, null, null);
    }
*/

    public static <S> S createService(Class<S> serviceClass) {


        client.interceptors().clear();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "applicaton/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
