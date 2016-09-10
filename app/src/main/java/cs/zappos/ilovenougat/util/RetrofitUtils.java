package cs.zappos.ilovenougat.util;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cs.zappos.ilovenougat.api.SixPmApi;
import cs.zappos.ilovenougat.api.ZapposApi;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtils {
    public static ZapposApi zapposApi() {
        return createRetrofit("https://api.zappos.com/")
                .create(ZapposApi.class);
    }

    public static SixPmApi sixPmApi() {
        return createRetrofit("https://api.6pm.com")
                .create(SixPmApi.class);
    }

    @NonNull
    private static Retrofit createRetrofit(String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Retrofit.Builder()
                .client(new okhttp3.OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl(baseUrl)
                .build();
    }
}
