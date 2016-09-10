package cs.zappos.ilovenougat;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cs.zappos.ilovenougat.model.ZappoProduct;
import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

public class SearchResultsRecyclerViewAdapter extends BindingRecyclerViewAdapter<ZappoProduct> {
    private final Listener listener;

    public SearchResultsRecyclerViewAdapter(@NonNull ItemViewArg<ZappoProduct> arg, Listener listener) {
        super(arg);
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        return super.onCreateViewHolder(binding);
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int bindingVariable, @LayoutRes int layoutRes, int position, final ZappoProduct item) {
        super.onBindBinding(binding, bindingVariable, layoutRes, position, item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(item);
            }
        });
    }

    public interface Listener {
        void onItemClicked(ZappoProduct item);
    }

}
