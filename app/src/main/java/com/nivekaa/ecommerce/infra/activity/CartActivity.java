package com.nivekaa.ecommerce.infra.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.application.AbstractAppActivity;
import com.nivekaa.ecommerce.domain.listener.OnXxCreaseQteItemListener;
import com.nivekaa.ecommerce.domain.model.OrderItemVM;
import com.nivekaa.ecommerce.infra.adapter.OrderApdater;
import com.nivekaa.ecommerce.util.Util;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

@SuppressLint("NonConstantResourceId")
public class CartActivity extends AbstractAppActivity implements OnXxCreaseQteItemListener {
    public static final String TOTAL_PRICE = "total_price";
    private final String TAG = getClass().getSimpleName();
    private final BigDecimal deliveryPrice = BigDecimal.valueOf(Util.DELIVERY_PRICE);
    @BindView(R.id.cartRv) RecyclerView cartRecyclerView;
    @BindView(R.id.total_old_price) TextView totalOldPriceView;
    @BindView(R.id.delivery_price) TextView deliveryPriceView;
    @BindView(R.id.total_price) TextView totalPriceView;
    @BindView(R.id.default_nodata) RelativeLayout noDataView;
    @BindView(R.id.cart_items_block) RelativeLayout blockItems;
    private OrderApdater apdater;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal totalOldPrice = BigDecimal.ZERO;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        apdater = new OrderApdater(this, dbHelper.getAllOrders(), this);
        cartRecyclerView.setAdapter(apdater);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        deliveryPriceView.setText("$" + deliveryPrice);
        totalOldPriceView.getPaint().setStrikeThruText(true);
        updatePrize();
    }

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

    @SuppressLint("DefaultLocale")
    private void updatePrize() {
        float prizze = 0.0F;
        float oldPrizze = 0.0F;
        totalOldPrice = BigDecimal.ZERO;
        totalPrice = BigDecimal.ZERO;
        List<OrderItemVM> orders = dbHelper.getAllOrders();
        if (orders.size() == 0) {
            noDataView.setVisibility(View.VISIBLE);
            blockItems.setVisibility(View.GONE);
        } else {
            for (OrderItemVM ordered: orders) {
                prizze  = prizze + ordered.getQuantity() * ordered.getProduct().getPrice().floatValue();
                oldPrizze  = oldPrizze + ordered.getQuantity() * ordered.getProduct().getOldPrice().floatValue();
            }
            totalPrice = totalPrice
                    .add(BigDecimal.valueOf(prizze))
                    .add(deliveryPrice)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN);
            totalOldPrice = totalOldPrice.add(BigDecimal.valueOf(oldPrizze))
                    .add(deliveryPrice)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN);
            totalPriceView.setText(String.format("$%.2f", totalPrice.floatValue()));
            totalOldPriceView.setText(String.format("$%.2f", totalOldPrice.floatValue()));
        }
    }

    @Override
    public void increase(OrderItemVM order) {
        order.setQuantity(order.getQuantity() + 1);
        int i = dbHelper.updateOrder(order);
        updatePrize();
    }

    @Override
    public void decrease(OrderItemVM order) {
        order.setQuantity(order.getQuantity() - 1);
        int i = dbHelper.updateOrder(order);
        updatePrize();
    }

    @Override
    public void remove(OrderItemVM order) {
        boolean res = dbHelper.deleteOrder(order.getId());
        if (res) {
            apdater.notifyDataSetChanged();
            updateAlertIcon();
            updatePrize();
        }
    }

    @Override
    public void allItemsHaveRemoved() {
        noDataView.setVisibility(View.VISIBLE);
        blockItems.setVisibility(View.GONE);
        //updateAlertIcon();
        updatePrize();
    }

    public void cashout(View view) {
        Intent i = new Intent(this, CheckoutActivity.class);
        i.putExtra(TOTAL_PRICE, totalPrice.floatValue());
        startActivity(i);
    }

    @OnTouch(R.id.slide_down)
    public void slideDown(View v, MotionEvent event) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.start_shopping)
    public void startShopping(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}