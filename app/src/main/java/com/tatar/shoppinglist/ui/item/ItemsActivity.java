package com.tatar.shoppinglist.ui.item;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.di.item.component.DaggerItemsActivityComponent;
import com.tatar.shoppinglist.di.item.component.ItemsActivityComponent;
import com.tatar.shoppinglist.di.item.module.ItemsActivityModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.utils.ui.RecyclerTouchListener;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

public class ItemsActivity extends BaseActivity implements ItemsView, AlertDialogActions {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ItemsAdapter itemsAdapter;

    @Inject
    AlertDialogHelper alertDialogHelper;

    @Inject
    ItemsPresenter itemsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_items;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.items_activity_title);
    }

    @Override
    protected void provideDependencies() {
        ItemsActivityComponent itemsActivityComponent = DaggerItemsActivityComponent.builder()
                .itemsActivityModule(new ItemsActivityModule(ItemsActivity.this, ItemsActivity.this, ItemsActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        itemsActivityComponent.injectItemsActivity(ItemsActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(itemsAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                Item item = itemsAdapter.getItem(position);
                alertDialogHelper.displayActionsDialog(item.getId(), item.getName());
            }
        }));
    }

    @Override
    protected void makeInitialCalls() {
        itemsPresenter.loadItems();
    }

    @OnClick(R.id.floatingActionButton)
    void floatingActionButtonClick() {
        alertDialogHelper.displayAddItemDialog();
    }

    @Override
    public void displayItems(List<Item> items) {
        itemsAdapter.setItemList(items);
    }

    /**
     * Displays a text view if there is no data in local database.
     */
    @Override
    public void toggleNoDataTv(int size) {
        switchNoDataTvVisibility(size);
    }

    @Override
    public void displayMessage(String message) {
        displayToast(message);
    }

    @Override
    public void toggleProgressBar() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            noDataTv.setVisibility(View.INVISIBLE);
            floatingActionButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            noDataTv.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * AlertDialogActions interface method implementations.
     */
    @Override
    public void create(String name) {
        itemsPresenter.createItem(name);
    }

    @Override
    public void update(String id, String name) {
        itemsPresenter.updateItem(id, name);
    }

    @Override
    public void delete(String id) {
        itemsPresenter.deleteItem(id);
    }

    @Override
    public void displayUpdateDialog(String id, String name) {
        alertDialogHelper.displayUpdateItemDialog(id, name);
    }
}
