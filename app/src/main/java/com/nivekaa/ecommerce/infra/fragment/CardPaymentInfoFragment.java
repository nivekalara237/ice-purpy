package com.nivekaa.ecommerce.infra.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.craftman.cardform.CardForm;
import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnFragmentBackPressedListener;
import com.nivekaa.ecommerce.domain.listener.OnSubmitCardPaymentInfoListener;
import com.nivekaa.ecommerce.domain.model.CashOrCardPaymentInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nivekaa.ecommerce.infra.activity.CartActivity.TOTAL_PRICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardPaymentInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NonConstantResourceId")
public class CardPaymentInfoFragment extends Fragment implements OnFragmentBackPressedListener {

    private final OnSubmitCardPaymentInfoListener submitBtnClickerListener;
    private float price;
    private final Context context;

    @BindView(R.id.card_form) CardForm cardForm;
    @BindView(R.id.payment_amount) TextView paymentAmount;
    @BindView(R.id.btn_pay) Button paymentBtn;

    public CardPaymentInfoFragment(Context ctxOrListener) {
        this.submitBtnClickerListener = (OnSubmitCardPaymentInfoListener)ctxOrListener;
        this.context = ctxOrListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardPaymentInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardPaymentInfoFragment newInstance(Context ctx, float price) {
        CardPaymentInfoFragment fragment = new CardPaymentInfoFragment(ctx);
        Bundle args = new Bundle();
        args.putFloat(TOTAL_PRICE, price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            price = getArguments().getFloat(TOTAL_PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_payment_info, container, false);
        ButterKnife.bind(this, view);
        cardForm.setAmount(String.valueOf(price));
        paymentAmount.setText(String.format("$%s", price));
        cardForm.setPayBtnClickListner(card -> {
            CashOrCardPaymentInfo info = new CashOrCardPaymentInfo();
            // if (card.validateCard())
            info.setCardInfo(card);
            submitBtnClickerListener.cardSubmit(info);
        });
        paymentBtn.setText("Continue");
        return view;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .popBackStack();
    }
}