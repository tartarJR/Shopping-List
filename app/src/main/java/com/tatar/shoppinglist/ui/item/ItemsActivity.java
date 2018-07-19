package com.tatar.shoppinglist.ui.item;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.di.item.component.DaggerItemsActivityComponent;
import com.tatar.shoppinglist.di.item.component.ItemsActivityComponent;
import com.tatar.shoppinglist.di.item.module.ItemsModule;
import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;
import com.tatar.shoppinglist.ui.helpers.RecyclerTouchListener;
import com.tatar.shoppinglist.ui.helpers.RecyclerViewDividerDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;

public class ItemsActivity extends AppCompatActivity implements ItemAlertDialogHelper.AlertDialogActions, ItemsContract.ItemsView {

    private static final String TAG = ItemsActivity.class.getSimpleName();

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.noItemsTv)
    TextView noItemsTv;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ItemsAdapter itemsAdapter;

    @Inject
    ItemsPresenter itemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);

        provideDependencies();

        setUpRecyclerView();

        itemsPresenter.loadItems();
    }

    /**
     * Displays an alert dialog for adding an Item.
     */
    @OnClick(R.id.floatingActionButton)
    void floatingActionButtonClick() {
        itemsPresenter.displayAddOrUpdateItemDialog();
    }

    /**
     * Displays all items in a RecyclerView.
     */
    @Override
    public void displayItems(List<Item> items) {
        itemsAdapter.setItemList(items);
    }

    /**
     * Displays noItemsTV if there is no item in local database.
     */
    @Override
    public void toggleNoItemsTv() {
        if (itemsPresenter.getItemList().size() > 0) {
            noItemsTv.setVisibility(View.GONE);
        } else {
            noItemsTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Displays the given String as a Toast message.
     */
    @Override
    public void displayMessage(String message) {
        Toast.makeText(ItemsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * ItemAlertDialogHelper.AlertDialogActions interface method implementation.
     * Used under the create action of the alert dialog to add an Item.
     */
    @Override
    public void addItem(String name) {
        itemsPresenter.createItem(name);
    }

    /**
     * ItemAlertDialogHelper.AlertDialogActions interface method implementation.
     * Used under the update action of the alert dialog to update an Item.
     */
    @Override
    public void updateItem(String id, String name, int position) {
        itemsPresenter.updateItem(id, name, position);
    }

    /**
     * ItemAlertDialogHelper.AlertDialogActions interface method implementation.
     * Used under the delete action click event of the alert dialog to delete an Item.
     */
    @Override
    public void deleteItem(String id, int position) {
        itemsPresenter.deleteItem(id, position);
        displayMessage("Item deleted.");
    }

    /**
     * Builds itemsActivityComponent and inject ItemActivity's dependencies.
     */
    private void provideDependencies() {
        ItemsActivityComponent itemsActivityComponent = DaggerItemsActivityComponent.builder()
                .itemsModule(new ItemsModule(ItemsActivity.this, ItemsActivity.this, ItemsActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        itemsActivityComponent.injectItemsActivity(ItemsActivity.this);
    }

    /**
     * Sets up itemsRecyclerView along with its ItemAnimator, ItemDecoration and ItemTouchListener.
     */
    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this, LinearLayoutManager.VERTICAL, 16));
        itemsRecyclerView.setAdapter(itemsAdapter);

        itemsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, itemsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                itemsPresenter.displayActionsDialog(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
