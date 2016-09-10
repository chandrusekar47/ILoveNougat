package cs.zappos.ilovenougat;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cs.zappos.ilovenougat.model.Product;
import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

public class SearchResultsRecyclerViewAdapter extends BindingRecyclerViewAdapter<Product> {
    private final Listener listener;

    public SearchResultsRecyclerViewAdapter(@NonNull ItemViewArg<Product> arg, Listener listener) {
        super(arg);
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        return super.onCreateViewHolder(binding);
    }

    @Override
    public void onBindBinding(final ViewDataBinding binding, int bindingVariable, @LayoutRes int layoutRes, int position, final Product item) {
        super.onBindBinding(binding, bindingVariable, layoutRes, position, item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(item, binding);
            }
        });
    }

    public interface Listener {
        void onItemClicked(Product item, ViewDataBinding viewDataBinding);
    }

}
