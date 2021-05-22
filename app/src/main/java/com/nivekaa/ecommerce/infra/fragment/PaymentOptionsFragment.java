package com.nivekaa.ecommerce.infra.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnPaymentOptionListner;
import com.nivekaa.ecommerce.domain.model.PaymentMode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentOptionsFragment extends Fragment implements View.OnClickListener {
    private final OnPaymentOptionListner paymentOptionListner;

    public PaymentOptionsFragment(OnPaymentOptionListner listner) {
        // Required empty public constructor
        this.paymentOptionListner = listner;
    }

    public static PaymentOptionsFragment newInstance(OnPaymentOptionListner listner) {
        PaymentOptionsFragment fragment = new PaymentOptionsFragment(listner);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_options, container, false);
        RelativeLayout cashMode = view.findViewById(R.id.cash_mode);
        RelativeLayout cardMode = view.findViewById(R.id.card_mode);
        cardMode.setOnClickListener(this);
        cashMode.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cash_mode) {
            paymentOptionListner.paymentOptionSelected(PaymentMode.CASH);
        } else if (v.getId() == R.id.card_mode) {
            paymentOptionListner.paymentOptionSelected(PaymentMode.CARD);
        }
    }
}