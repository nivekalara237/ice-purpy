package com.nivekaa.ecommerce.infra.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.infra.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentResultFragment extends Fragment {
    private final int resourceId;
    public PaymentResultFragment(int res) {
        // Required empty public constructor
        this.resourceId = res;
    }

    public static PaymentResultFragment newInstance(int res) {
        PaymentResultFragment fragment = new PaymentResultFragment(res);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(resourceId, container, false);
        view.findViewById(R.id.backshopping).setOnClickListener(v -> startActivity(new Intent(getContext(), MainActivity.class)));
        return view;
    }
}