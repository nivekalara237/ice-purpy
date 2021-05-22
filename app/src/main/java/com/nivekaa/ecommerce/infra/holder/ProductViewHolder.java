package com.nivekaa.ecommerce.infra.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.infra.view.LabelCardView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView price;
    public LabelCardView cardView;
    public TextView oldPrice;
    public TextView name;
    public RatingBar rate;
    public ImageView photo;
    public CardView btnAddToCart;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card);
        price = itemView.findViewById(R.id.price);
        oldPrice = itemView.findViewById(R.id.old_price);
        name = itemView.findViewById(R.id.title);
        rate = itemView.findViewById(R.id.rate);
        photo = itemView.findViewById(R.id.photo);
        btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
    }
}
