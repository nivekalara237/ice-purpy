package com.nivekaa.ecommerce.infra.holder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;

public class LabelViewHolder extends RecyclerView.ViewHolder {
    public Button button;
    public LabelViewHolder(@NonNull View itemView) {
        super(itemView);
        button = itemView.findViewById(R.id.label);
    }
}
