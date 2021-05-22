package com.nivekaa.ecommerce.infra.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnAddToCartListener;
import com.nivekaa.ecommerce.domain.model.ProductVM;
import com.nivekaa.ecommerce.infra.storage.DBHelper;
import com.nivekaa.ecommerce.infra.adapter.ProductAdapter;
import com.nivekaa.ecommerce.infra.holder.ProductDetailViewHolder;
import com.nivekaa.ecommerce.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends DialogFragment {

    private static final String ARG_PRODUCT_ID = "product_id";

    private ProductVM product;
    private final OnAddToCartListener cartListener;
    private final Context context;
    private final DBHelper dbHelper;

    public ProductDetailFragment(Context activity) {
        this.cartListener = (OnAddToCartListener)activity;
        this.context = activity;
        dbHelper = DBHelper.getInstance(activity);
    }/**/

    public static ProductDetailFragment newInstance(Context activity, ProductVM product) {
        ProductDetailFragment fragment = new ProductDetailFragment(activity);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (ProductVM) getArguments().getSerializable(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ProductDetailViewHolder holder = new ProductDetailViewHolder(viewRoot);
        setViewHolder(holder);
        return viewRoot;
    }

    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss();
                return true;
            }
            return false;
        });
        return dialog;
    }

    private void setViewHolder(ProductDetailViewHolder holder) {
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
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
                //.setDebugging(true)
                .load(product.getImageUrl())
                .error(R.drawable.shoe_nike_air_max_red_128dp)
                .into(holder.photo);
        holder.btnAddToCart.setOnClickListener(v -> cartListener.addProduct(product));
        holder.closeDialog.setOnClickListener(v -> ProductDetailFragment.this.dismiss());
        if (product.getCategoryType() != null) {
            List<ProductVM> vms = new ArrayList<>();
            List<ProductVM> fromDb = dbHelper.searchByCategory(product.getCategoryType().name());
            for (ProductVM prod: fromDb) if (!prod.getId().equals(product.getId())) vms.add(prod);
            ProductAdapter productAdapter = new ProductAdapter(context, vms, cartListener);
            holder.suggestionRv.setAdapter(productAdapter);
            holder.suggestionRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }
}