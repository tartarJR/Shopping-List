package com.tatar.shoppinglist.ui.completedshoppinglists;

import android.content.Intent;
import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;
import com.tatar.shoppinglist.di.completedshoppinglists.component.CompletedShoppingListsComponent;
import com.tatar.shoppinglist.di.completedshoppinglists.component.DaggerCompletedShoppingListsComponent;
import com.tatar.shoppinglist.di.completedshoppinglists.module.CompletedShoppingListsModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.ui.completedshoppinglistitems.ItemDisplayActivity;
import com.tatar.shoppinglist.ui.main.MainActivity;
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
        return R.layout.completed_shopping_lists_layout;
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
                RemoteShoppingList remoteShoppingList = shoppingListAdapter.getShoppingList(position);
                navigateToItemDisplayActivity(remoteShoppingList);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void makeInitialCalls() {
        shoppingListDisplayPresenter.loadCompletedShoppingLists();
    }

    @Override
    public void displayCompletedShoppingLists(List<RemoteShoppingList> remoteShoppingLists) {
        shoppingListAdapter.setRemoteShoppingLists(remoteShoppingLists);
    }

    @Override
    public void navigateToItemDisplayActivity(RemoteShoppingList remoteShoppingList) {
        Intent intent = new Intent(ShoppingListDisplayActivity.this, ItemDisplayActivity.class);
        intent.putExtra(INCOMING_SHOPPING_LIST, remoteShoppingList);
        startActivity(intent);
    }

    @Override
    public void navigateToMainActivity() {
        Intent intent = new Intent(ShoppingListDisplayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNoInternetMessage() {
        displayToast("Please check your internet connection and try again.");
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
    public void showErrorMessage() {
        displayErrorMessage();
    }
}
