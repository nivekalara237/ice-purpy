package com.nivekaa.ecommerce.infra.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.infra.view.LabelCardView;

public class ProductDetailViewHolder {
    public TextView price;
    public LabelCardView cardView;
    public TextView oldPrice;
    public TextView name;
    public TextView description;
    public ImageView photo;
    public CardView btnAddToCart;
    public RecyclerView suggestionRv;
    public ImageView closeDialog;
    public ProductDetailViewHolder(@NonNull View itemView) {
        cardView = itemView.findViewById(R.id.layout1);
        price = itemView.findViewById(R.id.price);
        oldPrice = itemView.findViewById(R.id.old_price);
        name = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        photo = itemView.findViewById(R.id.photo);
        btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        suggestionRv = itemView.findViewById(R.id.suggestionRV);
        closeDialog = itemView.findViewById(R.id.close_dialog);
    }
}
