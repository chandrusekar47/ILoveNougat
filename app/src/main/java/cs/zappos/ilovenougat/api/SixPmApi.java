package cs.zappos.ilovenougat.api;

import cs.zappos.ilovenougat.model.SearchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SixPmApi {
    @GET("Search")
    Call<SearchResults> search(@Query("term") String searchTerm, @Query("key") String apiKey);
}
