package com.example.apartmentcitizen.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    
    //    public static final String BASE_URL = "http://ahtapartment.ddns.net/api/";
    public static final String BASE_URL = "http://192.168.1.254/api/";
    //    public static final String BASE_URL = "http://192.168.0.112/api/";
//   public static final String BASE_URL = "http://192.168.1.8:8080/api/";
//    public static final String BASE_URL = "http://ahtapartment.ddns.net/api/";

    public static final String VERSION_API = "v1/";
    public static final String GET_POSTIMAGE_IMAGE = "postImages/image/";
    public static final String GET_USER_IMAGE = "user/image/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL + VERSION_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
