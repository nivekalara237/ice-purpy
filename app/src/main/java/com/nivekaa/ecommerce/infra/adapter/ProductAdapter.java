package com.nivekaa.ecommerce.infra.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnProductSelectedListener;
import com.nivekaa.ecommerce.infra.holder.ProductViewHolder;
import com.nivekaa.ecommerce.domain.listener.OnAddToCartListener;
import com.nivekaa.ecommerce.domain.model.ProductVM;
import com.nivekaa.ecommerce.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<ProductVM> products;
    private final Context context;
    private final OnAddToCartListener cartListener;
    private final OnProductSelectedListener selectedListener;
    public ProductAdapter(Context ctx, List<ProductVM> vms, OnAddToCartListener listener) {
        this.products = vms;
        this.context = ctx;
        this.cartListener = listener;
        this.selectedListener = (OnProductSelectedListener) ctx;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(v);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int i) {
        final ProductVM product = products.get(i);
        holder.rate.setRating(product.getRate());
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(Util.getFloatValAvoidingNullable(product.getPrice())));
        if (product.getDiscount() != null && product.getDiscount() > 0) {
            holder.cardView.setText("-" +product.getDiscount() + "%");
        } else {
            holder.cardView.setLabelColor(Color.TRANSPARENT);
        }

        if (product.getOldPrice() != null && product.getOldPrice().compareTo(product.getPrice()) > 0) {
            holder.oldPrice.setText(product.getOldPrice().toPlainString());
            holder.oldPrice.setVisibility(View.VISIBLE);
            holder.oldPrice.getPaint().setStrikeThruText(true);
        } else {
            holder.oldPrice.setVisibility(View.GONE);
        }
        Picasso.get()
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.shoe_nike_air_max_red_128dp)
                .into(holder.photo);
        holder.btnAddToCart.setOnClickListener(v -> cartListener.addProduct(product));
        holder.cardView.setOnClickListener(v -> {
            if (v.getId() != R.id.btn_add_to_cart) {
                selectedListener.productSelected(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateItems(List<ProductVM> vms) {
        this.products = vms;
        notifyDataSetChanged();
    }
}
