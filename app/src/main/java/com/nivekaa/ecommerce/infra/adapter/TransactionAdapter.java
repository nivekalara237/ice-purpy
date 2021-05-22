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
import com.nivekaa.ecommerce.infra.holder.TransactionsViewHolder;
import com.nivekaa.ecommerce.domain.model.TransVM;

import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionsViewHolder> {

    List<TransVM> transactions;
    Context context;
    OnPrintButtonClickListener mListener;

    public TransactionAdapter(List<TransVM> list, Context c) {
        this.context = c;
        this.transactions = list;
        if (c instanceof OnPrintButtonClickListener)
            mListener = (OnPrintButtonClickListener) c;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        return new TransactionsViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {

        final TransVM trans = transactions.get(position);
        holder.numero_trans.setText(trans.getNumero());
        holder.prix_trans.setText(String.format("%,.2f %s", trans.getPrix(), "USD"));
        String date;
        if (DateTimeUtils.isToday(trans.getDate())) {
            date = DateTimeUtils.formatTime(trans.getDate(), true);
        } else {
            date = DateTimeUtils.formatWithStyle(trans.getDate(), DateTimeStyle.MEDIUM);
        }
        holder.date_trans.setText(date);
        if (trans.isStatus()) {
            holder.status_trans.setBackgroundResource(R.drawable.bg_transaction_item_successeded);
            holder.status_trans.setTextColor(Color.parseColor("#484BAC"));
            holder.status_trans.setText("succeeded");
            holder.prix_trans.setTextColor(Color.parseColor("#14de14"));
        } else {
            holder.print_btn.setVisibility(View.GONE);
            holder.status_trans.setBackgroundResource(R.drawable.bg_transaction_item_failed);
            holder.status_trans.setTextColor(Color.parseColor("#AAAAAA"));
            holder.status_trans.setText("Failed");
            holder.prix_trans.setTextColor(Color.parseColor("#AAAAAA"));
        }
        holder.numero_carte.setText(trans.getNumeroAffichable());
        holder.print_btn.setVisibility(View.GONE);

        holder.print_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.printTransaction(trans);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public interface OnPrintButtonClickListener {
        void printTransaction(TransVM trans);
    }
}