package com.example.lab5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

class DataRow {
    @SerializedName("time")
    @Expose
    String time = null;

    @SerializedName("high")
    @Expose
    String high = null;

    @SerializedName("low")
    @Expose
    String low = null;

    @SerializedName("open")
    @Expose
    String open = null;

    @SerializedName("close")
    @Expose
    String close = null;

    @SerializedName("volumeFrom")
    @Expose
    String volumeFrom = null;

    @SerializedName("volumeTo")
    @Expose
    String volumeTo = null;
}

class Data {
    @SerializedName("Data")
    @Expose
    ArrayList<DataRow> rows = null;
}

class Response {
    @SerializedName("Data")
    @Expose
    Data data = null;
}

interface Request {
    @GET("/data/v2/histoday?")
    Call<Response> getData(@Query("fsym") String fsym,
                           @Query("tsym") String tsym,
                           @Query("limit") int limit);
}

public class RestApi {
    private static final String BASE_URL = "https://min-api.cryptocompare.com";

    private static Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Request getApi() {
        return mRetrofit.create(Request.class);
    }

}
