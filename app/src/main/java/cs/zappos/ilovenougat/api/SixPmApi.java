package cs.zappos.ilovenougat.api;

import cs.zappos.ilovenougat.model.SixPmSearchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SixPmApi {
    @GET("Search")
    Call<SixPmSearchResults> search(@Query("term") String searchTerm, @Query("key") String apiKey);
}
