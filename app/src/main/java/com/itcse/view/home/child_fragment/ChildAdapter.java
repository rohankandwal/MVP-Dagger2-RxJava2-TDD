package com.itcse.view.home.child_fragment;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itcse.BR;
import com.itcse.R;
import com.itcse.data.network.model.TabChildResponse;
import com.itcse.databinding.ItemChildFragmentBinding;

import java.util.List;


public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    ChildAdapter(@NonNull List<TabChildResponse> childResponseList) {
        this.childResponseList = childResponseList;
    }

    private List<TabChildResponse> childResponseList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemChildFragmentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_child_fragment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(childResponseList.get(position));
    }

    @Override
    public int getItemCount() {
        return childResponseList.size();
    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter({"changeVisibility"})
    public static void adjustVisibility(ImageView view, String status) {
        view.setVisibility("sold_out".equalsIgnoreCase(status) ? View.VISIBLE : View.GONE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemChildFragmentBinding binding;

        ViewHolder(ItemChildFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull final TabChildResponse childResponse) {
            binding.setVariable(BR.item, childResponse);
            binding.executePendingBindings();
        }
    }
}
