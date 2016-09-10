package cs.zappos.ilovenougat.api;

import cs.zappos.ilovenougat.model.ZapposSearchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZapposApi {
    @GET("Search")
    Call<ZapposSearchResults> search(@Query("term") String searchTerm, @Query("key") String apiKey);
}
