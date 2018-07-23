package com.tatar.shoppinglist.ui.item;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.di.item.component.DaggerItemsActivityComponent;
import com.tatar.shoppinglist.di.item.component.ItemsActivityComponent;
import com.tatar.shoppinglist.di.item.module.ItemsActivityModule;
import com.tatar.shoppinglist.ui.helpers.RecyclerTouchListener;
import com.tatar.shoppinglist.ui.helpers.RecyclerViewDividerDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.helpers.AlertDialogHelper.AlertDialogActions;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

public class ItemsActivity extends AppCompatActivity implements AlertDialogActions, ItemsView {

    private static final String TAG = ItemsActivity.class.getSimpleName();

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.noItemsTv)
    TextView noItemsTv;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    ItemsAdapter itemsAdapter;

    @Inject
    ItemsPresenter itemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);
        setTitle(getString(R.string.items_activity_title));

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
    public void toggleNoItemsTv(int size) {
        if (size > 0) {
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

    @Override
    public void toggleProgressBar() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            itemsRecyclerView.setVisibility(View.INVISIBLE);
            noItemsTv.setVisibility(View.INVISIBLE);
            floatingActionButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            itemsRecyclerView.setVisibility(View.VISIBLE);
            noItemsTv.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * AlertDialogHelper.AlertDialogActions interface method implementation.
     * Used under the create action of the alert dialog to add an Item.
     */
    @Override
    public void create(String name) {
        itemsPresenter.createItem(name);
    }

    /**
     * AlertDialogHelper.AlertDialogActions interface method implementation.
     * Used under the update action of the alert dialog to update an Item.
     */
    @Override
    public void update(String id, String name) {
        itemsPresenter.updateItem(id, name);
    }

    /**
     * AlertDialogHelper.AlertDialogActions interface method implementation.
     * Used under the delete action click event of the alert dialog to delete an Item.
     */
    @Override
    public void delete(String id) {
        itemsPresenter.deleteItem(id);
    }

    /**
     * Builds itemsActivityComponent and inject ItemActivity's dependencies.
     */
    private void provideDependencies() {
        ItemsActivityComponent itemsActivityComponent = DaggerItemsActivityComponent.builder()
                .itemsActivityModule(new ItemsActivityModule(ItemsActivity.this, ItemsActivity.this, ItemsActivity.this))
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
                itemsPresenter.displayActionsDialog(itemsAdapter.getItem(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
