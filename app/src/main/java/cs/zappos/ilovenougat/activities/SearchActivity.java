package cs.zappos.ilovenougat.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cs.zappos.ilovenougat.BR;
import cs.zappos.ilovenougat.BuildConfig;
import cs.zappos.ilovenougat.R;
import cs.zappos.ilovenougat.SearchResultsRecyclerViewAdapter;
import cs.zappos.ilovenougat.databinding.ActivitySearchBinding;
import cs.zappos.ilovenougat.model.Product;
import cs.zappos.ilovenougat.model.SearchResults;
import cs.zappos.ilovenougat.util.RetrofitUtils;
import cs.zappos.ilovenougat.viewmodel.SearchViewModel;
import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;
import me.tatarka.bindingcollectionadapter.factories.BindingRecyclerViewAdapterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements BindingRecyclerViewAdapterFactory, SearchResultsRecyclerViewAdapter.Listener {

    private ActivitySearchBinding activitySearchBinding;
    private final SearchViewModel searchViewModel = new SearchViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(activitySearchBinding.getRoot());
        activitySearchBinding.setEventHandler(this);
        activitySearchBinding.setViewModel(searchViewModel);
        activitySearchBinding.setAdapterFactory(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    public boolean onEditorAction(TextView view, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            searchZappos(view.getText().toString());
            return true;
        }
        return false;
    }

    private void searchZappos(String searchQuery) {
        searchViewModel.searchStarted();
        RetrofitUtils.zapposApi().search(searchQuery, BuildConfig.ZAPPOS_API_KEY)
                .enqueue(new Callback<SearchResults>() {
                    @Override
                    public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                        if (response.isSuccessful()) {
                            searchViewModel.updateSearchResults(response.body());
                            activitySearchBinding.executePendingBindings();
                        } else {
                            Log.e(SearchActivity.class.getName(), "Unable to reach server. Please try again later " + response.message());
                            Toast.makeText(SearchActivity.this, "Unable to reach server. Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResults> call, Throwable t) {
                        Log.e(SearchActivity.class.getName(), t.getMessage(), t);
                        Toast.makeText(SearchActivity.this, "Unable to reach server. Please try again later", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, ItemViewArg<T> arg) {
        return (BindingRecyclerViewAdapter<T>) new SearchResultsRecyclerViewAdapter((ItemViewArg<Product>) arg, this);
    }

    @Override
    public void onItemClicked(final Product zapposProduct, final ViewDataBinding  viewDataBinding) {
        viewDataBinding.setVariable(BR.shouldShowProgress, true);
        RetrofitUtils.sixPmApi()
                .search(zapposProduct.productId, BuildConfig.SIX_PM_API_KEY)
                .enqueue(new Callback<SearchResults>() {
                    @Override
                    public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                        viewDataBinding.setVariable(BR.shouldShowProgress, false);
                        if (response.isSuccessful()) {
                            Product matchingSixPmProduct = response.body().getMatchingProduct(zapposProduct);
                            if (matchingSixPmProduct != null) {
                                showZapposAndSixPmProduct(matchingSixPmProduct, zapposProduct);
                            } else {
                                Toast.makeText(SearchActivity.this, R.string.product_not_found_in_6_pm, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.e(SearchActivity.class.getName(), "Unable to reach server. Please try again later " + response.message());
                            Toast.makeText(SearchActivity.this, R.string.server_error_message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResults> call, Throwable t) {
                        viewDataBinding.setVariable(BR.shouldShowProgress, false);
                        Log.e(SearchActivity.class.getName(), t.getMessage(), t);
                        Toast.makeText(SearchActivity.this, R.string.server_error_message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showZapposAndSixPmProduct(Product matchingSixPmProduct, Product zapposProduct) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(SearchActivity.this.getLayoutInflater(), R.layout.product_comparison, null, false);
        viewDataBinding.setVariable(BR.zapposProduct, zapposProduct);
        viewDataBinding.setVariable(BR.sixPmProduct, matchingSixPmProduct);
        AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).setView(viewDataBinding.getRoot()).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
