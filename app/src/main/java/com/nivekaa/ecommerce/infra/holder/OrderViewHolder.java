package com.nivekaa.ecommerce.infra.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView price;
    public TextView oldPrice;
    public ImageView image;
    public ImageButton increateQte;
    public ImageButton decreateQte;
    public ImageButton remove;
    public EditText qte;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.title);
        price = itemView.findViewById(R.id.price);
        oldPrice = itemView.findViewById(R.id.old_price);
        image = itemView.findViewById(R.id.photo);
        increateQte = itemView.findViewById(R.id.increase_qte);
        decreateQte = itemView.findViewById(R.id.decrease_qte);
        remove = itemView.findViewById(R.id.remove);
        qte = itemView.findViewById(R.id.qte);
    }
}
