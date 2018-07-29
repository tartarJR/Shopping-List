package com.tatar.shoppinglist.ui.manageitems;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.di.manageitems.component.DaggerManageItemsComponent;
import com.tatar.shoppinglist.di.manageitems.component.ManageItemsComponent;
import com.tatar.shoppinglist.di.manageitems.module.ManageItemsModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.utils.ui.recyclerview.RecyclerTouchListener;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.manageitems.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.manageitems.ItemsContract.ItemsView;

public class ItemsActivity extends BaseActivity implements ItemsView, AlertDialogActions {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ItemAdapter itemAdapter;

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
        ManageItemsComponent manageItemsComponent = DaggerManageItemsComponent.builder()
                .manageItemsModule(new ManageItemsModule(ItemsActivity.this, ItemsActivity.this, ItemsActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        manageItemsComponent.inject(ItemsActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(itemAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                Item item = itemAdapter.getItem(position);
                alertDialogHelper.displayActionsDialog(item.getId(), item.getName());
            }
        }));
    }

    @Override
    protected void makeInitialCalls() {
        itemsPresenter.loadItems();
    }

    @OnClick(R.id.floatingActionButton)
    void displayAddItemDialog() {
        alertDialogHelper.displayAddItemDialog();
    }

    @Override
    public void displayItems(List<Item> items) {
        itemAdapter.setItemList(items);
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
