package com.tatar.shoppinglist.ui.completedshoppinglist;

import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.di.completedshoppinglist.component.CompletedShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.completedshoppinglist.component.DaggerCompletedShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.completedshoppinglist.module.CompletedShoppingListsActivityModule;
import com.tatar.shoppinglist.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import static com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsContract.CompletedShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsContract.CompletedShoppingListsView;

public class CompletedShoppingListsActivity extends BaseActivity implements CompletedShoppingListsView {

    @Inject
    CompletedShoppingListsPresenter completedShoppingListsPresenter;

    @Inject
    CompletedShoppingListAdapter completedShoppingListAdapter;

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
        CompletedShoppingListsActivityComponent completedShoppingListsActivityComponent = DaggerCompletedShoppingListsActivityComponent.builder()
                .completedShoppingListsActivityModule(new CompletedShoppingListsActivityModule(this, this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        completedShoppingListsActivityComponent.injectCompletedShoppingListsActivity(CompletedShoppingListsActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(completedShoppingListAdapter);
    }

    @Override
    protected void makeInitialCalls() {
        completedShoppingListsPresenter.loadCompletedShoppingLists("1"); // TODO get user id from prefs
    }

    @Override
    public void displayCompletedShoppingLists(List<ShoppingList> shoppingLists) {
        completedShoppingListAdapter.setShoppingLists(shoppingLists);
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
