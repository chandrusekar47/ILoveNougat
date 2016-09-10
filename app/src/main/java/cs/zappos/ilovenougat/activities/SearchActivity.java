package cs.zappos.ilovenougat.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cs.zappos.ilovenougat.BuildConfig;
import cs.zappos.ilovenougat.databinding.ActivitySearchBinding;
import cs.zappos.ilovenougat.model.ZappoSearchResults;
import cs.zappos.ilovenougat.util.RetrofitUtils;
import cs.zappos.ilovenougat.viewmodel.SearchViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding activitySearchBinding;
    private final SearchViewModel searchViewModel = new SearchViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(activitySearchBinding.getRoot());
        activitySearchBinding.setEventHandler(this);
        activitySearchBinding.setViewModel(searchViewModel);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    public boolean onEditorAction(TextView view, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchZappos(view.getText().toString());
            return true;
        }
        return false;
    }

    private void searchZappos(String searchQuery) {
        RetrofitUtils.zapposApi().search(searchQuery, BuildConfig.ZAPPOS_API_KEY)
                .enqueue(new Callback<ZappoSearchResults>() {
                    @Override
                    public void onResponse(Call<ZappoSearchResults> call, Response<ZappoSearchResults> response) {
                        if (response.isSuccessful()) {
                            searchViewModel.searchResults.clear();
                            searchViewModel.searchResults.addAll(response.body().getResults());
                            activitySearchBinding.executePendingBindings();
                        } else {
                            Log.e(SearchActivity.class.getName(), "Unable to reach server. Please try again later " + response.message());
                            Toast.makeText(SearchActivity.this, "Unable to reach server. Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ZappoSearchResults> call, Throwable t) {
                        Log.e(SearchActivity.class.getName(), t.getMessage(), t);
                        Toast.makeText(SearchActivity.this, "Unable to reach server. Please try again later", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
