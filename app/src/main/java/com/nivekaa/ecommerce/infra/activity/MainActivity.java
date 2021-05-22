package com.nivekaa.ecommerce.infra.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nivekaa.ecommerce.application.AbstractAppActivity;
import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.listener.OnFragmentBackPressedListener;
import com.nivekaa.ecommerce.domain.listener.OnLabelSelectedListener;
import com.nivekaa.ecommerce.domain.listener.OnProductSelectedListener;
import com.nivekaa.ecommerce.domain.model.CategoryType;
import com.nivekaa.ecommerce.domain.spi.DBStoragePort;
import com.nivekaa.ecommerce.infra.adapter.LabelAdapter;
import com.nivekaa.ecommerce.infra.adapter.ProductAdapter;
import com.nivekaa.ecommerce.domain.listener.OnAddToCartListener;
import com.nivekaa.ecommerce.domain.model.LabelVM;
import com.nivekaa.ecommerce.domain.model.OrderItemVM;
import com.nivekaa.ecommerce.domain.model.ProductVM;
import com.nivekaa.ecommerce.infra.fragment.ProductDetailFragment;
import com.nivekaa.ecommerce.infra.storage.DBStorageAdapter;
import com.nivekaa.ecommerce.infra.view.EmptiableRecyclerView;

import java.util.List;

public class MainActivity extends AbstractAppActivity implements OnAddToCartListener,
        OnLabelSelectedListener,
        OnProductSelectedListener {
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView labelRecyclerView;
    private EmptiableRecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private DBStoragePort storagePort;

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
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storagePort = new DBStorageAdapter(this);
        initView();
        mountLabelComponent();
        mountProductComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void mountProductComponent() {
        List<ProductVM> vms = storagePort.searchAny(CategoryType.ALL);
        productAdapter = new ProductAdapter(this, vms, this);
        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter.updateItems(vms);
    }

    private void initView() {
        this.labelRecyclerView = findViewById(R.id.labelRv);
        this.productRecyclerView = findViewById(R.id.productRv);
        productRecyclerView.setEmptyView(findViewById(R.id.empty_view));
    }

    private void mountLabelComponent() {
        List<LabelVM> vms = storagePort.getLabels();
        LabelAdapter labelAdapter = new LabelAdapter(this, vms);
        labelRecyclerView.setAdapter(labelAdapter);
        labelRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void addProduct(ProductVM product) {
        //Toast.makeText(this, product.getName() + " has been added to cart", Toast.LENGTH_SHORT).show();
        List<OrderItemVM> currentOrders = storagePort.getOrders();
        boolean found = false;
        for (OrderItemVM ord: currentOrders) {
            if (ord.getProduct().getId().equals(product.getId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItemVM orderItem = new OrderItemVM(product, 1);
            storagePort.addOrder(orderItem);
            alertCount++;
            updateAlertIcon();
        } else {
            displayWarnDialog(product.getName() + " has been already added in the cart.");
        }
    }

    @Override
    public void productSelected(ProductVM product) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(this, product);
        fragment.setCancelable(false);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.modal_in, R.anim.modal_out);
        fragment.show(fm, "product_detail_frag");
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentByTag("product_detail_frag");
        if (currentFragment instanceof ProductDetailFragment) {
            ((OnFragmentBackPressedListener)currentFragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void LabelSelected(LabelVM label) {
        if (label.getCategory() == CategoryType.ALL) {
            productAdapter.updateItems(storagePort.searchAny(CategoryType.ALL));
        } else {
            productAdapter.updateItems(storagePort.searchAny(label.getCategory()));
        }
    }
}
