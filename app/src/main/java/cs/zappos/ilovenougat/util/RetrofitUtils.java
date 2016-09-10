package cs.zappos.ilovenougat.util;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cs.zappos.ilovenougat.api.ZapposApi;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtils {
    public static ZapposApi zapposApi() {
        return createRetrofit("https://api.zappos.com/")
                .create(ZapposApi.class);
    }

    public static ZapposApi sixPmApi() {
        return createRetrofit("https://api.6pm.com")
                .create(ZapposApi.class);
    }

    @NonNull
    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .client(new okhttp3.OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper().disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)))
                .baseUrl(baseUrl)
                .build();
    }
}
