package com.tatar.shoppinglist.ui.completedshoppinglistitems;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;
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
        RemoteShoppingList remoteShoppingList = getIntent().getExtras().getParcelable(INCOMING_SHOPPING_LIST);
        return remoteShoppingList.getName();
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
        RemoteShoppingList remoteShoppingList = getIntent().getExtras().getParcelable(INCOMING_SHOPPING_LIST);
        itemAdapter.setRemoteShoppingListItems(remoteShoppingList.getRemoteShoppingListItems());
    }
}
