package com.tatar.shoppinglist.ui.shoppinglistitem;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;
import com.tatar.shoppinglist.di.shoppinglistItem.component.DaggerShoppingListItemActivityComponent;
import com.tatar.shoppinglist.di.shoppinglistItem.component.ShoppingListItemActivityComponent;
import com.tatar.shoppinglist.di.shoppinglistItem.module.ShoppingListItemActivityModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.utils.ui.RecyclerItemTouchHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemPresenter;
import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemView;

public class ShoppingListItemActivity extends BaseActivity implements ShoppingListItemView, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @BindView(R.id.addItemLayout)
    ConstraintLayout addItemLayout;

    @BindView(R.id.itemNameActv)
    AutoCompleteTextView itemNameActv;

    @BindView(R.id.addItemBtn)
    Button addItemBtn;

    @Inject
    ShoppingListItemAdapter shoppingListItemAdapter;

    @Inject
    ItemActvAdapter itemActvAdapter;

    @Inject
    ShoppingListItemPresenter addItemsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_list_item;
    }

    @Override
    protected String getActivityTitle() {
        return getIntent().getStringExtra(INCOMING_TITLE);
    }

    @Override
    protected void provideDependencies() {
        ShoppingListItemActivityComponent shoppingListItemActivityComponent = DaggerShoppingListItemActivityComponent.builder()
                .shoppingListItemActivityModule(new ShoppingListItemActivityModule(ShoppingListItemActivity.this, ShoppingListItemActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        shoppingListItemActivityComponent.injectItemsActivity(ShoppingListItemActivity.this);
    }

    @Override
    protected void setUpViews() {
        itemNameActv.setAdapter(itemActvAdapter);
        recyclerView.setAdapter(shoppingListItemAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void makeInitialCalls() {
        addItemsPresenter.getActvItems();
        addItemsPresenter.getShoppingListItems(getIntent().getStringExtra(INCOMING_SHOPPING_LIST_ID));
    }

    @OnClick(R.id.addItemBtn)
    void floatingActionButtonClick() {
        addItemsPresenter.addItemToShoppingList(itemNameActv.getText().toString());
    }

    @Override
    public void populateItemActv(List<Item> items) {
        itemActvAdapter.setItems(items);
    }

    @Override
    public void displayShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        shoppingListItemAdapter.setShoppingListItems(shoppingListItems);
    }

    @Override
    public void toggleProgressBar() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            itemNameActv.setVisibility(View.INVISIBLE);
            addItemBtn.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            noDataTv.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            itemNameActv.setVisibility(View.VISIBLE);
            addItemBtn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            if (noDataTv.getVisibility() != View.INVISIBLE) {
                noDataTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void toggleNoDataTv(int size) {
        switchNoDataTvVisibility(size);
    }

    @Override
    public void displayMessage(String message) {
        displayToast(message);
    }

    @Override
    public void clearActv() {
        itemNameActv.setText("");
    }


    // RecyclerView swipe to delete event
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ShoppingListItemAdapter.ViewHolder) {
            final int indexToBeDeleted = viewHolder.getAdapterPosition();
            final ShoppingListItem removedItem = shoppingListItemAdapter.getShoppingListItem(indexToBeDeleted);
            addItemsPresenter.removeItemFromShoppingList(indexToBeDeleted);

            Snackbar snackbar = Snackbar.make(addItemLayout, removedItem.getName() + " removed from the shopping list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItemsPresenter.addItemToShoppingList(removedItem.getName());
                }
            });

            snackbar.setActionTextColor(Color.YELLOW); // TODO change color, redesign snackbar
            snackbar.show();
        }
    }
}
