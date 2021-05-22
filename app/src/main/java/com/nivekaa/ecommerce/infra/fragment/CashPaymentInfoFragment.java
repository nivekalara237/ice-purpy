package com.nivekaa.ecommerce.infra.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.developer.kalert.KAlertDialog;
import com.gbksoft.countrycodepickerlib.CountryCodePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnFragmentBackPressedListener;
import com.nivekaa.ecommerce.domain.listener.OnSubmitCashPaymentInfoListener;
import com.nivekaa.ecommerce.domain.model.AddressInfo;
import com.nivekaa.ecommerce.domain.model.CashOrCardPaymentInfo;
import com.nivekaa.ecommerce.infra.activity.CheckoutActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nivekaa.ecommerce.infra.activity.CartActivity.TOTAL_PRICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CashPaymentInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NonConstantResourceId")
public class CashPaymentInfoFragment extends Fragment implements OnFragmentBackPressedListener, Validator.ValidationListener {
    private final OnSubmitCashPaymentInfoListener cashPaymentInfoListener;
    private float price;
    private final Context context;
    private Validator validator;

    @NotEmpty
    @BindView(R.id.fullname) EditText fullname;
    @NotEmpty
    @BindView(R.id.phone) EditText phone;
    @Email @NotEmpty
    @BindView(R.id.email) EditText email;
    @NotEmpty
    @BindView(R.id.address) EditText address;
    @BindView(R.id.countryCodePickerView) CountryCodePicker countryCode;
    @BindView(R.id.city) EditText city;
    @BindView(R.id.state) EditText state;
    @NotEmpty
    @BindView(R.id.zipcode) EditText zipcode;
    @BindView(R.id.deserve) Button btnSubmit;

    public CashPaymentInfoFragment(Context ctxOrListener) {
        this.cashPaymentInfoListener = (OnSubmitCashPaymentInfoListener)ctxOrListener;
        this.context = ctxOrListener;
    }

    public static CashPaymentInfoFragment newInstance(Context ctxOrListener, float price) {
        CashPaymentInfoFragment fragment = new CashPaymentInfoFragment(ctxOrListener);
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

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_payment_info, container, false);
        ButterKnife.bind(this, view);
        countryCode.registerCarrierNumberEditText(phone);
        btnSubmit.setText(String.format("Place my order - $%s", price));
        return view;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this).commit();
    }

    @OnClick(R.id.deserve)
    public void submit(View v) {
        if (StringUtils.isEmpty(countryCode.getSelectedCountryCode())) {
            ((CheckoutActivity)context).showErrorMessage("The country is required");
        } else
            validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        CashOrCardPaymentInfo info = new CashOrCardPaymentInfo();
        info.setAddressInfo(new AddressInfo());
        info.setEmail(email.getText().toString());
        info.setFullname(fullname.getText().toString());
        info.setPhone(phone.getText().toString());
        if (StringUtils.isNotEmpty(phone.getText()))
            info.setPhone(phone.getText().toString());
        if (StringUtils.isNotEmpty(city.getText()))
            info.getAddressInfo().setCity(phone.getText().toString());
        if (StringUtils.isNotEmpty(state.getText()))
            info.getAddressInfo().setState(state.getText().toString());
        info.getAddressInfo().setZipcode(zipcode.getText().toString());
        info.getAddressInfo().setCountry(countryCode.getSelectedCountryCode());
        info.getAddressInfo().setAddress(address.getText().toString());
        info.getAddressInfo().setId(UUID.randomUUID().toString());
        confirmation(info);
    }

    private void confirmation(final CashOrCardPaymentInfo info) {
        KAlertDialog confirmDialog = new KAlertDialog(context, KAlertDialog.WARNING_TYPE);
        confirmDialog.setCancelable(false);
        confirmDialog.setContentText("Confirm the informations typed");
        confirmDialog.setConfirmText("I Confirm");
        confirmDialog.setCancelText("No! Return");
        confirmDialog.confirmButtonColor(R.color.warning_stroke_color, context);
        confirmDialog.cancelButtonColor(R.color.colorGrey, context);
        confirmDialog.setConfirmClickListener(alert -> {
            cashPaymentInfoListener.cashSubmit(info);
            alert.dismissWithAnimation();
        });
        confirmDialog.show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else{
                ((CheckoutActivity)context).showErrorMessage(message);
            }
        }
    }
}