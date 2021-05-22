package com.nivekaa.ecommerce.infra.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnLabelSelectedListener;
import com.nivekaa.ecommerce.infra.holder.LabelViewHolder;
import com.nivekaa.ecommerce.domain.model.LabelVM;

import java.util.List;

public class LabelAdapter extends RecyclerView.Adapter<LabelViewHolder> {
    private final List<LabelVM> labels;
    private final Context context;
    private final OnLabelSelectedListener labelSelectedListener;
    public LabelAdapter(Context ctx, List<LabelVM> vms) {
        this.labels = vms;
        this.context = ctx;
        this.labelSelectedListener = (OnLabelSelectedListener)ctx;
    }

    @NonNull
    @Override
    public LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_item, parent, false);
        return new LabelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelViewHolder labelViewHolder, int i) {
        final LabelVM vm = labels.get(i);
        labelViewHolder.button.setText(vm.getTitle());
        if (vm.getColor() != null)
            labelViewHolder.button.setBackgroundColor(Color.parseColor(vm.getColor()));
        labelViewHolder.button.setOnClickListener(v -> labelSelectedListener.LabelSelected(vm));
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
