package com.tatar.shoppinglist.ui.completedshoppinglistitems;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.di.completedshoppinglistitems.component.CompletedShoppingListItemsComponent;
import com.tatar.shoppinglist.di.completedshoppinglistitems.component.DaggerCompletedShoppingListItemsComponent;
import com.tatar.shoppinglist.ui.BaseActivity;

import javax.inject.Inject;

public class ItemDisplayActivity extends BaseActivity {

    @Inject
    ItemAdapter itemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.completed_shopping_list_items_layout;
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
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    protected void makeInitialCalls() {
        ShoppingList shoppingList = getIntent().getExtras().getParcelable(INCOMING_SHOPPING_LIST);
        itemAdapter.setShoppingListItems(shoppingList.getShoppingListItems());
    }
}
