package com.tatar.shoppinglist.ui.shoppinglist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.di.shoppinglist.component.DaggerShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.shoppinglist.component.ShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.shoppinglist.module.ShoppingListsActivityModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemActivity;
import com.tatar.shoppinglist.utils.ui.RecyclerTouchListener;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListsActivity extends BaseActivity implements ShoppingListsView, AlertDialogActions {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ShoppingListsAdapter shoppingListsAdapter;

    @Inject
    AlertDialogHelper alertDialogHelper;

    @Inject
    ShoppingListsPresenter shoppingListsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_lists;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.shopping_lists_activity_title);
    }

    @Override
    protected void provideDependencies() {
        ShoppingListsActivityComponent shoppingListsActivityComponent = DaggerShoppingListsActivityComponent.builder()
                .shoppingListsActivityModule(new ShoppingListsActivityModule(ShoppingListsActivity.this, ShoppingListsActivity.this, ShoppingListsActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        shoppingListsActivityComponent.injectShoppingListsActivity(ShoppingListsActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(shoppingListsAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                ShoppingList shoppingList = shoppingListsAdapter.getShoppingList(position);
                navigateToAddItemActivity(shoppingList.getName(), shoppingList.getId());
            }

            @Override
            public void onLongClick(View view, int position) {
                ShoppingList shoppingList = shoppingListsAdapter.getShoppingList(position);
                alertDialogHelper.displayActionsDialog(shoppingList.getId(), shoppingList.getName());
            }
        }));
    }

    @Override
    protected void makeInitialCalls() {
        shoppingListsPresenter.loadShoppingLists();
    }

    @OnClick(R.id.floatingActionButton)
    void floatingActionButtonClick() {
        alertDialogHelper.displayCreateShoppingListDialog();
    }

    @Override
    public void displayShoppingLists(List<ShoppingList> shoppingLists) {
        shoppingListsAdapter.setshoppingLists(shoppingLists);
    }

    /**
     * Displays a text view if there is no data in local database.
     */
    @Override
    public void toggleNoDataTv(int size) {
        switchNoDataTvVisibility(size);
    }

    /**
     * Navigates to ShoppingListItemActivity after ShoppingList creation or RecyclerView item touch
     */
    @Override
    public void navigateToAddItemActivity(String title, String id) {
        Intent intent = new Intent(ShoppingListsActivity.this, ShoppingListItemActivity.class);
        intent.putExtra(ShoppingListItemActivity.INCOMING_TITLE, title);
        intent.putExtra(ShoppingListItemActivity.INCOMING_SHOPPING_LIST_ID, id);
        startActivity(intent);
    }

    @Override
    public void toggleProgressBar() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            noDataTv.setVisibility(View.INVISIBLE);
            floatingActionButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            noDataTv.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayMessage(String message) {
        displayToast(message);
    }

    /**
     * AlertDialogActions interface method implementations.
     */
    @Override
    public void create(String name) {
        shoppingListsPresenter.createShoppingList(name);
    }

    @Override
    public void update(String id, String name) {
        shoppingListsPresenter.updateShoppingList(id, name);
    }

    @Override
    public void delete(String id) {
        shoppingListsPresenter.deleteShoppingList(id);
    }

    @Override
    public void displayUpdateDialog(String id, String name) {
        alertDialogHelper.displayUpdateShoppingListDialog(id, name);
    }
}
