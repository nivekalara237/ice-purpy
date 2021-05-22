package com.nivekaa.ecommerce.infra.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;

public class TransactionsViewHolder  extends RecyclerView.ViewHolder {
    public TextView date_trans;
    public  TextView numero_trans;
    public ImageView icon_carte;
    public ImageView print_btn;
    public  TextView prix_trans;
    public  TextView status_trans;
    public  TextView numero_carte;

    public  TransactionsViewHolder(View itemView){
        super(itemView);
        date_trans = itemView.findViewById(R.id.date_transaction);
        numero_trans = itemView.findViewById(R.id.numero_transaction);
        numero_carte = itemView.findViewById(R.id.numero_carte);
        icon_carte = itemView.findViewById(R.id.icon_carte);
        print_btn = itemView.findViewById(R.id.print_btn);
        prix_trans = itemView.findViewById(R.id.prix_transaction);
        status_trans = itemView.findViewById(R.id.status_transaction);
    }
}
