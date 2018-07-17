package com.tatar.shoppinglist.ui.item;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.ItemAlertDialogUtils;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.di.component.ActivityComponent;
import com.tatar.shoppinglist.di.component.DaggerActivityComponent;
import com.tatar.shoppinglist.di.module.ItemAlertDialogModule;
import com.tatar.shoppinglist.di.module.ItemsModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;

public class ItemsActivity extends AppCompatActivity implements ItemAlertDialogUtils.AlertDialogActions, ItemsContract.ItemsView {

    private static final String TAG = ItemsActivity.class.getSimpleName();
    
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.noItemsTv)
    TextView noItemsTv;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ItemsPresenter itemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);

        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .itemsModule(new ItemsModule(ItemsActivity.this))
                .itemAlertDialogModule(new ItemAlertDialogModule(this, this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        activityComponent.injectItemsActivity(ItemsActivity.this);

        itemsPresenter.loadItems();
    }

    @OnClick(R.id.floatingActionButton)
    void displayItemDialog() {
        itemsPresenter.handleFabClick();
    }

    @Override
    public void addItem(String name) {
        itemsPresenter.addItem(name);
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void displayItems() {

    }
}
