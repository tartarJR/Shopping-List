package com.tatar.shoppinglist.ui.completedshoppinglists;

import android.content.Intent;
import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.di.completedshoppinglists.component.CompletedShoppingListsComponent;
import com.tatar.shoppinglist.di.completedshoppinglists.component.DaggerCompletedShoppingListsComponent;
import com.tatar.shoppinglist.di.completedshoppinglists.module.CompletedShoppingListsModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.ui.completedshoppinglistitems.ItemDisplayActivity;
import com.tatar.shoppinglist.utils.ui.recyclerview.RecyclerTouchListener;

import java.util.List;

import javax.inject.Inject;

import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListDisplayActivity extends BaseActivity implements ShoppingListDisplayView {

    @Inject
    ShoppingListDisplayPresenter shoppingListDisplayPresenter;

    @Inject
    ShoppingListAdapter shoppingListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_completed_shopping_lists;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.completed_shopping_lists_activity_title);
    }

    @Override
    protected void provideDependencies() {
        CompletedShoppingListsComponent completedShoppingListsComponent = DaggerCompletedShoppingListsComponent.builder()
                .completedShoppingListsModule(new CompletedShoppingListsModule(this, this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        completedShoppingListsComponent.inject(ShoppingListDisplayActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(shoppingListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                ShoppingList shoppingList = shoppingListAdapter.getShoppingList(position);
                navigateToItemDisplayActivity(shoppingList);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void makeInitialCalls() {
        shoppingListDisplayPresenter.loadCompletedShoppingLists("1"); // TODO get user id from prefs
    }

    @Override
    public void displayCompletedShoppingLists(List<ShoppingList> shoppingLists) {
        shoppingListAdapter.setShoppingLists(shoppingLists);
    }

    @Override
    public void navigateToItemDisplayActivity(ShoppingList shoppingList) {
        Intent intent = new Intent(ShoppingListDisplayActivity.this, ItemDisplayActivity.class);
        intent.putExtra(INCOMING_SHOPPING_LIST, shoppingList);
        startActivity(intent);
    }

    @Override
    public void toggleProgressBar() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            noDataTv.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            noDataTv.setVisibility(View.VISIBLE);
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
}
