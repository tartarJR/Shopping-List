package com.tatar.shoppinglist.ui.completedshoppinglistitems;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.di.completedshoppinglistitems.component.CompletedShoppingListItemsComponent;
import com.tatar.shoppinglist.di.completedshoppinglistitems.component.DaggerCompletedShoppingListItemsComponent;
import com.tatar.shoppinglist.ui.BaseActivity;

import javax.inject.Inject;

public class ItemDisplayActivity extends BaseActivity {

    @Inject
    ItemsAdapter itemsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_completed_shopping_list_item_display;
    }

    @Override
    protected String getActivityTitle() {
        ShoppingList shoppingList = getIntent().getExtras().getParcelable(INCOMING_SHOPPING_LIST);
        return shoppingList.getName();
    }

    @Override
    protected void provideDependencies() {
        CompletedShoppingListItemsComponent completedShoppingListItemsComponent = DaggerCompletedShoppingListItemsComponent.builder().build();
        completedShoppingListItemsComponent.inject(ItemDisplayActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(itemsAdapter);
    }

    @Override
    protected void makeInitialCalls() {
        ShoppingList shoppingList = getIntent().getExtras().getParcelable(INCOMING_SHOPPING_LIST);
        itemsAdapter.setShoppingListItems(shoppingList.getShoppingListItems());
    }
}
