package com.tatar.shoppinglist.ui.activeshoppinglistitems;

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
import com.tatar.shoppinglist.di.activeshoppinglistitems.component.ActiveShoppingListItemsComponent;
import com.tatar.shoppinglist.di.activeshoppinglistitems.component.DaggerActiveShoppingListItemsComponent;
import com.tatar.shoppinglist.di.activeshoppinglistitems.module.ActiveShoppingListItemsModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.utils.ui.recyclerview.RecyclerItemTouchHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayView;
import static com.tatar.shoppinglist.utils.ui.recyclerview.RecyclerItemTouchHelper.RecyclerItemTouchHelperListener;

public class ItemDisplayActivity extends BaseActivity implements ItemDisplayView, RecyclerItemTouchHelperListener {

    @BindView(R.id.addItemLayout)
    ConstraintLayout addItemLayout;

    @BindView(R.id.itemNameActv)
    AutoCompleteTextView itemNameActv;

    @BindView(R.id.addItemBtn)
    Button addItemBtn;

    @Inject
    ItemAdapter itemAdapter;

    @Inject
    ActvAdapter actvAdapter;

    @Inject
    ItemDisplayPresenter itemDisplayPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.active_shopping_list_items_layout;
    }

    @Override
    protected String getActivityTitle() {
        return getIntent().getStringExtra(INCOMING_TITLE);
    }

    @Override
    protected void provideDependencies() {
        ActiveShoppingListItemsComponent activeShoppingListItemsComponent = DaggerActiveShoppingListItemsComponent.builder()
                .activeShoppingListItemsModule(new ActiveShoppingListItemsModule(ItemDisplayActivity.this, ItemDisplayActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        activeShoppingListItemsComponent.inject(ItemDisplayActivity.this);
    }

    @Override
    protected void setUpViews() {
        itemNameActv.setAdapter(actvAdapter);
        recyclerView.setAdapter(itemAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void makeInitialCalls() {
        itemDisplayPresenter.getActvItems();
        itemDisplayPresenter.getShoppingListItems(getIntent().getStringExtra(INCOMING_SHOPPING_LIST_ID));
    }

    @OnClick(R.id.addItemBtn)
    void floatingActionButtonClick() {
        itemDisplayPresenter.addItemToShoppingList(itemNameActv.getText().toString());
    }

    @Override
    public void populateItemActv(List<Item> items) {
        actvAdapter.setItems(items);
    }

    @Override
    public void displayShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        itemAdapter.setShoppingListItems(shoppingListItems);
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
        if (viewHolder instanceof ItemAdapter.ViewHolder) {
            final int indexToBeDeleted = viewHolder.getAdapterPosition();
            final ShoppingListItem removedItem = itemAdapter.getShoppingListItem(indexToBeDeleted);
            itemDisplayPresenter.removeItemFromShoppingList(indexToBeDeleted);

            Snackbar snackbar = Snackbar.make(addItemLayout, removedItem.getName() + " removed from the shopping list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemDisplayPresenter.addItemToShoppingList(removedItem.getName());
                }
            });

            snackbar.setActionTextColor(Color.YELLOW); // TODO change color, redesign snackbar
            snackbar.show();
        }
    }
}
