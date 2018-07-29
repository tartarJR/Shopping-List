package com.tatar.shoppinglist.ui.activeshoppinglists;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.di.activeshoppinglists.component.ActiveShoppingListsComponent;
import com.tatar.shoppinglist.di.activeshoppinglists.component.DaggerActiveShoppingListsComponent;
import com.tatar.shoppinglist.di.activeshoppinglists.module.ActiveShoppingListsModule;
import com.tatar.shoppinglist.ui.BaseActivity;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayActivity;
import com.tatar.shoppinglist.utils.ui.recyclerview.RecyclerTouchListener;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListDisplayActivity extends BaseActivity implements ShoppingListDisplayView, AlertDialogActions {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ShoppingListAdapter shoppingListAdapter;

    @Inject
    AlertDialogHelper alertDialogHelper;

    @Inject
    ShoppingListDisplayPresenter shoppingListDisplayPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.active_shopping_lists_layout;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.shopping_lists_activity_title);
    }

    @Override
    protected void provideDependencies() {
        ActiveShoppingListsComponent activeShoppingListsComponent = DaggerActiveShoppingListsComponent.builder()
                .activeShoppingListsModule(new ActiveShoppingListsModule(ShoppingListDisplayActivity.this, ShoppingListDisplayActivity.this, ShoppingListDisplayActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        activeShoppingListsComponent.inject(ShoppingListDisplayActivity.this);
    }

    @Override
    protected void setUpViews() {
        recyclerView.setAdapter(shoppingListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                ShoppingList shoppingList = shoppingListAdapter.getShoppingList(position);
                navigateToAddItemActivity(shoppingList.getName(), shoppingList.getId());
            }

            @Override
            public void onLongClick(View view, int position) {
                ShoppingList shoppingList = shoppingListAdapter.getShoppingList(position);
                alertDialogHelper.displayActionsDialog(shoppingList.getId(), shoppingList.getName());
            }
        }));
    }

    @Override
    protected void makeInitialCalls() {
        shoppingListDisplayPresenter.loadShoppingLists();
    }

    @OnClick(R.id.floatingActionButton)
    void displayCreateShoppingListDialog() {
        alertDialogHelper.displayCreateShoppingListDialog();
    }

    @Override
    public void displayShoppingLists(List<ShoppingList> shoppingLists) {
        shoppingListAdapter.setshoppingLists(shoppingLists);
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
        Intent intent = new Intent(ShoppingListDisplayActivity.this, ItemDisplayActivity.class);
        intent.putExtra(INCOMING_TITLE, title);
        intent.putExtra(INCOMING_SHOPPING_LIST_ID, id);
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
        shoppingListDisplayPresenter.createShoppingList(name);
    }

    @Override
    public void update(String id, String name) {
        shoppingListDisplayPresenter.updateShoppingList(id, name);
    }

    @Override
    public void delete(String id) {
        shoppingListDisplayPresenter.deleteShoppingList(id);
    }

    @Override
    public void displayUpdateDialog(String id, String name) {
        alertDialogHelper.displayUpdateShoppingListDialog(id, name);
    }
}
