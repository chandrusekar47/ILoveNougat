package cs.zappos.ilovenougat.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import cs.zappos.ilovenougat.BR;
import cs.zappos.ilovenougat.R;
import cs.zappos.ilovenougat.model.ZapposProduct;
import cs.zappos.ilovenougat.model.ZapposSearchResults;
import me.tatarka.bindingcollectionadapter.ItemView;

public class SearchViewModel extends BaseObservable {
    private final ObservableList<ZapposProduct> searchResults = new ObservableArrayList<>();
    public final ItemView itemView = ItemView.of(BR.item, R.layout.search_result_item);
    private String lastSearchTerm = "";
    private boolean isSearching = false;

    @Bindable
    public int getEmptyResultsMessageVisibility() {
        return isSearchResultEmpty() ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getSearchResultsVisibility() {
        return !isSearchResultEmpty() ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getProgressBarVisibility() {
        return isSearching ? View.VISIBLE : View.GONE;
    }

    public void searchStarted() {
        isSearching = true;
        notifyPropertyChanged(BR.progressBarVisibility);
        notifyPropertyChanged(BR.emptyResultsMessageVisibility);
    }

    private boolean isSearchResultEmpty() {
        return !this.lastSearchTerm.isEmpty() && searchResults.isEmpty();
    }

    public ObservableList<ZapposProduct> getSearchResults() {
        return searchResults;
    }

    public void updateSearchResults(ZapposSearchResults searchResults) {
        this.searchResults.clear();
        this.searchResults.addAll(searchResults.results);
        this.lastSearchTerm = searchResults.originalTerm;
        this.isSearching = false;
        notifyPropertyChanged(BR.emptyResultsMessageVisibility);
        notifyPropertyChanged(BR.searchResultsVisibility);
        notifyPropertyChanged(BR.progressBarVisibility);
    }
}
