package com.tatar.shoppinglist.ui.item;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.ui.helpers.RecyclerTouchListener;
import com.tatar.shoppinglist.ui.helpers.RecyclerViewDividerDecoration;
import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.di.item.component.DaggerItemsActivityComponent;
import com.tatar.shoppinglist.di.item.component.ItemsActivityComponent;
import com.tatar.shoppinglist.di.item.module.ItemAlertDialogModule;
import com.tatar.shoppinglist.di.item.module.ItemsModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;

public class ItemsActivity extends AppCompatActivity implements ItemAlertDialogHelper.AlertDialogActions, ItemsContract.ItemsView {

    private static final String TAG = ItemsActivity.class.getSimpleName();

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.noItemsTv)
    TextView noItemsTv;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    ItemsAdapter itemsAdapter;

    @Inject
    ItemsPresenter itemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);

        setUpDaggerComponentandInjectActivity();

        setUpRecyclerView();

        itemsPresenter.loadItems();
    }

    @OnClick(R.id.floatingActionButton)
    void displayItemDialog() {
        itemsPresenter.handleFabClick();
    }


    @Override
    public void displayItems(List<Item> items) {
        itemsAdapter.setItemList(items);
    }

    @Override
    public void notifyNewItemAdded(Item item) {
        List<Item> items = itemsPresenter.getItemList();
        items.add(item);
        itemsAdapter.notifyItemInserted(items.size());
    }

    @Override
    public void notifyItemUpdated(int position, String name) {
        List<Item> items = itemsPresenter.getItemList();
        Item item = items.get(position);
        item.setName(name);
        itemsAdapter.notifyItemChanged(position);
    }

    @Override
    public void toggleEmptyItems() {
        List<Item> items = itemsPresenter.getItemList();

        if (items.size() > 0) {
            noItemsTv.setVisibility(View.GONE);
        } else {
            noItemsTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addItem(String name) {
        itemsPresenter.addItem(name);
    }

    @Override
    public void updateItem(String id, String name, int position) {
        itemsPresenter.updateItem(id, name, position);
    }

    @Override
    public void deleteItem() {

    }

    private void setUpDaggerComponentandInjectActivity() {
        ItemsActivityComponent itemsActivityComponent = DaggerItemsActivityComponent.builder()
                .itemsModule(new ItemsModule(ItemsActivity.this))
                .itemAlertDialogModule(new ItemAlertDialogModule(this, this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        itemsActivityComponent.injectItemsActivity(ItemsActivity.this);
    }

    private void setUpRecyclerView() {
        itemsAdapter = new ItemsAdapter(ItemsActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this, LinearLayoutManager.VERTICAL, 16));
        itemsRecyclerView.setAdapter(itemsAdapter);

        itemsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, itemsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                itemsPresenter.handleRecyclerViewItemClick(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.i(TAG, "onLongClick: ");
            }
        }));
    }


}
