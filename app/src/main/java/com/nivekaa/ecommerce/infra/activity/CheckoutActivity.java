package com.nivekaa.ecommerce.infra.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developer.kalert.KAlertDialog;
import com.nivekaa.ecommerce.domain.listener.OnCloseCurrentFragmentListener;
import com.nivekaa.ecommerce.domain.listener.OnFragmentBackPressedListener;
import com.nivekaa.ecommerce.domain.listener.OnPaymentOptionListner;
import com.nivekaa.ecommerce.domain.listener.OnSubmitCardPaymentInfoListener;
import com.nivekaa.ecommerce.domain.listener.OnSubmitCashPaymentInfoListener;
import com.nivekaa.ecommerce.domain.model.CashOrCardPaymentInfo;
import com.nivekaa.ecommerce.domain.model.PaymentMode;
import com.nivekaa.ecommerce.infra.fragment.CardPaymentInfoFragment;
import com.nivekaa.ecommerce.application.AbstractAppActivity;
import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.infra.fragment.CashPaymentInfoFragment;
import com.nivekaa.ecommerce.infra.fragment.PaymentOptionsFragment;

import butterknife.BindView;

import static com.nivekaa.ecommerce.infra.activity.CartActivity.TOTAL_PRICE;

public class CheckoutActivity extends AbstractAppActivity implements OnSubmitCardPaymentInfoListener,
        OnPaymentOptionListner, OnCloseCurrentFragmentListener,
        View.OnClickListener, OnSubmitCashPaymentInfoListener {

    private final String TAG = this.getClass().getSimpleName();
    private float price = 0F;
    private CashOrCardPaymentInfo cashOrCardPaymentInfo = new CashOrCardPaymentInfo();

    @BindView(R.id.go_shop) RelativeLayout continueShop;
    @BindView(R.id.fragment_layout) FrameLayout fragmentLayout;

    @Override
    protected boolean hasErrorView() {
        return true;
    }

    @Override
    protected boolean hasToolBar() {
        return true;
    }

    @Override
    protected boolean displayBackPressedIcon() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_mode);
        continueShop.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            price = bundle.getFloat(TOTAL_PRICE, 0F);
        }
        displayPaymentOptionsFragment();
    }

    @Override
    public void cardSubmit(CashOrCardPaymentInfo form) {
        cashOrCardPaymentInfo = form;
        displayCashInfoFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_shop:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                Log.d("DEFAULT", "DEFAULT");
                break;
        }
    }

    private void displayCardInfoFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_layout, CardPaymentInfoFragment.newInstance(this, price));
        fragmentTransaction.commit();
    }

    public void displayCashInfoFragment() {
        displayCashInfoFragment(this);
    }

    public void displayCashInfoFragment(Context context) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_layout, CashPaymentInfoFragment.newInstance(context, price));
        fragmentTransaction.commit();
    }

    private void displayPaymentOptionsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_layout, PaymentOptionsFragment.newInstance(this));
        fragmentTransaction.commit();
    }

    @Override
    public void cashSubmit(CashOrCardPaymentInfo form) {
        cashOrCardPaymentInfo.setAddressInfo(form.getAddressInfo());
        cashOrCardPaymentInfo.setPhone(form.getPhone());
        cashOrCardPaymentInfo.setFullname(form.getFullname());
        cashOrCardPaymentInfo.setEmail(form.getEmail());
        KAlertDialog contactServerDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        contactServerDialog.setCancelable(true);
        contactServerDialog.setContentText("Loading...");
        contactServerDialog.setCanceledOnTouchOutside(false);
        contactServerDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        contactServerDialog.setConfirmClickListener(KAlertDialog::dismissWithAnimation);
        contactServerDialog.setOnDismissListener(alert -> {
            CheckoutActivity.this.addSuccessMessage("We prepare for your order,\n" +
                    "It will be delivered to you in a 3 or 4 hours.", event -> cleanOrderInDb());
            alert.dismiss();
        });
        contactServerDialog.show();
        new Handler().postDelayed(contactServerDialog::dismiss, 10000);
    }

    private void cleanOrderInDb() {
        dbHelper.deleteAllOrders();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void showErrorMessage(String message) {
        addErrorMessage(message);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        if (currentFragment instanceof OnFragmentBackPressedListener) {
            ((OnFragmentBackPressedListener)currentFragment).onBackPressed();
            displayPaymentOptionsFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void paymentOptionSelected(PaymentMode paymentMode) {
        if (paymentMode == PaymentMode.CARD)
            displayCardInfoFragment();
        if (paymentMode == PaymentMode.CASH)
            displayCashInfoFragment();
    }

    @Override
    public void onCloseCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_layout);
        if (currentFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment).commit();
        }
    }
}