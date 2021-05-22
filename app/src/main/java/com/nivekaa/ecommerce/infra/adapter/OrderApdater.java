package com.nivekaa.ecommerce.infra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.infra.holder.OrderViewHolder;
import com.nivekaa.ecommerce.domain.listener.OnXxCreaseQteItemListener;
import com.nivekaa.ecommerce.domain.model.OrderItemVM;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

public class OrderApdater extends RecyclerView.Adapter<OrderViewHolder> {
    private final Context context;
    private final OnXxCreaseQteItemListener qteItemListener;
    private final List<OrderItemVM> orderItems;
    public OrderApdater(Context ctx, List<OrderItemVM> vms, OnXxCreaseQteItemListener listener) {
        this.context = ctx;
        this.orderItems = vms;
        this.qteItemListener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int i) {
        final OrderItemVM orderItem = orderItems.get(i);
        holder.qte.setText(String.valueOf(orderItem.getQuantity()));
        holder.name.setText(orderItem.getProduct().getName());
        holder.price.setText(String.valueOf(orderItem.getProduct().getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN)));

        if (orderItem.getProduct().getOldPrice() != null && orderItem.getProduct().getOldPrice().compareTo(orderItem.getProduct().getPrice()) > 0) {
            holder.oldPrice.setText(orderItem.getProduct().getOldPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
            holder.oldPrice.setVisibility(View.VISIBLE);
            holder.oldPrice.getPaint().setStrikeThruText(true);
        } else {
            holder.oldPrice.setVisibility(View.GONE);
        }

        holder.remove.setOnClickListener((v -> {
            qteItemListener.remove(orderItem);
            orderItems.remove(orderItem);
            if (orderItems.size() == 0)
                qteItemListener.allItemsHaveRemoved();
        }));

        holder.increateQte.setOnClickListener((v -> {
            String val = holder.qte.getText().toString();
            qteItemListener.increase(orderItem);
            holder.qte.setText(String.valueOf(Integer.parseInt(val) + 1));
        }));

        holder.decreateQte.setOnClickListener(v -> {
            String val = holder.qte.getText().toString();
            if (val != null && Integer.parseInt(val)>1) {
                qteItemListener.decrease(orderItem);
                holder.qte.setText(String.valueOf(Integer.parseInt(val) - 1));
            }

        });
        Picasso.get().load(orderItem.getProduct().getImageUrl())
                .error(R.drawable.shoe_nike_air_max_red_128dp)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
