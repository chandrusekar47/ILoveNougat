package cs.zappos.ilovenougat.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import cs.zappos.ilovenougat.BR;
import cs.zappos.ilovenougat.R;
import cs.zappos.ilovenougat.model.ZappoProduct;
import me.tatarka.bindingcollectionadapter.ItemView;

public class SearchViewModel {
    public final ObservableList<ZappoProduct> searchResults = new ObservableArrayList<>();
    public final ItemView itemView = ItemView.of(BR.item, R.layout.search_result_item);
}
