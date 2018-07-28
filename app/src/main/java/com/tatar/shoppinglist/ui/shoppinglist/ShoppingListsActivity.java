package com.tatar.shoppinglist.ui.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.di.shoppinglist.component.DaggerShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.shoppinglist.component.ShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.shoppinglist.module.ShoppingListsActivityModule;
import com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemActivity;
import com.tatar.shoppinglist.utils.ui.RecyclerTouchListener;
import com.tatar.shoppinglist.utils.ui.RecyclerViewDividerDecoration;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListsActivity extends AppCompatActivity implements ShoppingListsView, AlertDialogActions {

    @BindView(R.id.shoppingListsRecyclerView)
    RecyclerView shoppingListsRecyclerView;

    @BindView(R.id.noShoppingListsTv)
    TextView noShoppingListsTv;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    ShoppingListsAdapter shoppingListsAdapter;

    @Inject
    AlertDialogHelper alertDialogHelper;

    @Inject
    ShoppingListsPresenter shoppingListsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        ButterKnife.bind(this);
        setTitle(getString(R.string.shopping_lists_activity_title));

        provideDependencies();

        setUpRecyclerView();

        shoppingListsPresenter.loadShoppingLists();
    }

    /**
     * Displays an alert dialog for creating a shopping list.
     */
    @OnClick(R.id.floatingActionButton)
    void floatingActionButtonClick() {
        alertDialogHelper.displayCreateShoppingListDialog();
    }

    /**
     * Displays all ShoppingLists in a RecyclerView.
     */
    @Override
    public void displayShoppingLists(List<ShoppingList> shoppingLists) {
        shoppingListsAdapter.setshoppingLists(shoppingLists);
    }

    /**
     * Displays noShoppingListsTv if there is no shopping list in local database.
     */
    @Override
    public void toggleNoShoppingListTv(int size) {
        if (size > 0) {
            noShoppingListsTv.setVisibility(View.GONE);
        } else {
            noShoppingListsTv.setVisibility(View.VISIBLE);
        }
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
            shoppingListsRecyclerView.setVisibility(View.INVISIBLE);
            noShoppingListsTv.setVisibility(View.INVISIBLE);
            floatingActionButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            shoppingListsRecyclerView.setVisibility(View.VISIBLE);
            noShoppingListsTv.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Displays the given String as a Toast message.
     */
    @Override
    public void displayMessage(String message) {
        Toast.makeText(ShoppingListsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * AlertDialogActions interface method implementation.
     * Used under the create action of the alert dialog to add an ShoppingList.
     */
    @Override
    public void create(String name) {
        shoppingListsPresenter.createShoppingList(name);
    }

    /**
     * AlertDialogActions interface method implementation.
     * Used under the update action of the alert dialog to update an ShoppingList.
     */
    @Override
    public void update(String id, String name) {
        shoppingListsPresenter.updateShoppingList(id, name);
    }

    /**
     * AlertDialogActions interface method implementation.
     * Used under the delete action click event of the alert dialog to delete an ShoppingList.
     */
    @Override
    public void delete(String id) {
        shoppingListsPresenter.deleteShoppingList(id);
    }

    @Override
    public void displayUpdateDialog(String id, String name) {
        alertDialogHelper.displayUpdateShoppingListDialog(id, name);
    }

    /**
     * Builds shoppingListsActivityComponent and inject ShoppingListsActivity's dependencies.
     */
    private void provideDependencies() {
        ShoppingListsActivityComponent shoppingListsActivityComponent = DaggerShoppingListsActivityComponent.builder()
                .shoppingListsActivityModule(new ShoppingListsActivityModule(ShoppingListsActivity.this, ShoppingListsActivity.this, ShoppingListsActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        shoppingListsActivityComponent.injectShoppingListsActivity(ShoppingListsActivity.this);
    }

    /**
     * Sets up shoppingListsRecyclerView along with its ItemAnimator, ItemDecoration and ItemTouchListener.
     */
    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        shoppingListsRecyclerView.setLayoutManager(mLayoutManager);
        shoppingListsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shoppingListsRecyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this, LinearLayoutManager.VERTICAL, 16));
        shoppingListsRecyclerView.setAdapter(shoppingListsAdapter);

        shoppingListsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, shoppingListsRecyclerView, new RecyclerTouchListener.ClickListener() {
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
}
