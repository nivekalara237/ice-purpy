package com.nivekaa.ecommerce.application;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.infra.activity.CartActivity;
import com.nivekaa.ecommerce.infra.activity.MainActivity;
import com.nivekaa.ecommerce.infra.storage.DBHelper;
import com.nivekaa.ecommerce.infra.storage.SessionStorage;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class AbstractAppActivity extends AppCompatActivity  implements ConnectivityChangeListener {
    private KAlertDialog dialog = null;
    public SessionStorage sessionStorage;
    public DBHelper dbHelper;
    public int alertCount = 0;
    private FrameLayout yellowCircleCart;
    private TextView countCartTextView;
    public TextView errorTextView;

    protected abstract boolean hasErrorView();
    protected abstract boolean hasToolBar();
    protected boolean displayBackPressedIcon() {
        return false;
    }

    private void initView() {
        if (hasErrorView()) {
            this.errorTextView = findViewById(R.id.error);
        }
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if(!(event!=null && event.getState()!=null && event.getState().getValue() == ConnectivityState.CONNECTED)){
            //no_connection.setVisibility(View.VISIBLE);
            dialog = new KAlertDialog(this, KAlertDialog.CUSTOM_IMAGE_TYPE);
            dialog.setTitleText("Run your data bundle or active your wifi if is not.");
            dialog.setIcon(R.drawable.ic_baseline_signal_wifi_off_24);
            dialog.setCustomImage(R.drawable.ic_baseline_signal_wifi_off_24, this);
            dialog.setCancelable(false);
            dialog.setConfirmText("Reload app");
            dialog.confirmButtonColor(R.color.colorPrimary, this);
            dialog.setConfirmClickListener(kAlertDialog -> {
                kAlertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            });
            dialog.show();
        } else {
            if (dialog != null) {
                dialog.dismissWithAnimation();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionBuddy.getInstance().clearNetworkCache(this, savedInstanceState);
        sessionStorage = new SessionStorage(this);
        dbHelper = DBHelper.getInstance(this);
        alertCount = dbHelper.getAllOrders().size();
        initView();
    }

    @Override
    public void setContentView(int layoutResID){
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (!hasToolBar()) {
                actionBar.hide();
            } else {
                actionBar.show();
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(displayBackPressedIcon());
                actionBar.setDisplayShowHomeEnabled(displayBackPressedIcon());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                return true;
            case R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.action_cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
        yellowCircleCart = rootView.findViewById(R.id.view_cart_circle);
        countCartTextView = rootView.findViewById(R.id.view_cart_badge_count);

        rootView.setOnClickListener(v -> onOptionsItemSelected(alertMenuItem));
        updateAlertIcon();

        return super.onPrepareOptionsMenu(menu);
    }


    protected void updateAlertIcon() {
        if (0 < alertCount && alertCount < 10) {
            countCartTextView.setText(String.valueOf(alertCount));
        } else {
            countCartTextView.setText(""); // if alert count extends into two digits, just show the red circle
        }
        yellowCircleCart.setVisibility((alertCount > 0) ? VISIBLE : GONE);
    }


    protected void gotoMain(){
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    protected void addErrorMessage(String newErr) {
        KAlertDialog dialog  = new KAlertDialog(this, KAlertDialog.ERROR_TYPE);
        dialog.setCancelable(true);
        dialog.setContentText(newErr);
        dialog.setConfirmText("Close");
        dialog.show();
    }

    protected void addErrorMessage(String newErr, DialogInterface.OnCancelListener event) {
        KAlertDialog dialog  = new KAlertDialog(this, KAlertDialog.ERROR_TYPE);
        dialog.setCancelable(true);
        dialog.setContentText(newErr);
        dialog.setOnCancelListener(event);
        dialog.setConfirmText("Close");
        dialog.show();
    }

    protected void displayWarnDialog(String warn) {
        KAlertDialog dialog = new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
        dialog.setCancelable(true);
        dialog.confirmButtonColor(R.color.kalertWarnColor, this);
        dialog.setContentText(warn);
        dialog.setConfirmText("Close");
        dialog.show();
    }

    protected void displayInfoDialog(String info) {
        KAlertDialog dialog = new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
        dialog.setCancelable(true);
        dialog.setContentText(info);
        dialog.confirmButtonColor(R.color.kalertInfoColor, this);
        dialog.setConfirmText("Close");
        dialog.show();
    }

    protected void addSuccessMessage(String msg) {
        KAlertDialog dialogSuccess =  new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setContentText(msg);
        dialogSuccess.setConfirmText("Close");
        dialogSuccess.confirmButtonColor(R.color.colorSuccess, this);
        dialogSuccess.show();
    }

    protected void addSuccessMessage(String msg, KAlertDialog.KAlertClickListener event) {
        KAlertDialog dialogSuccess =  new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setContentText(msg);
        dialogSuccess.setConfirmText("Close");
        dialogSuccess.confirmButtonColor(R.color.colorSuccess, this);
        dialogSuccess.setConfirmClickListener(event);
        dialogSuccess.show();
    }
}
