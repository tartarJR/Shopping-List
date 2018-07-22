package com.tatar.shoppinglist.ui.shoppinglist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.di.shoppinglist.component.DaggerShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.shoppinglist.component.ShoppingListsActivityComponent;
import com.tatar.shoppinglist.di.shoppinglist.module.ShoppingListsActivityModule;
import com.tatar.shoppinglist.ui.helpers.AlertDialogHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListsActivity extends AppCompatActivity implements ShoppingListsView, AlertDialogHelper.AlertDialogActions {

    @BindView(R.id.shoppingListsRecyclerView)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.noShoppingListsTv)
    TextView noItemsTv;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    ShoppingListsPresenter itemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        ButterKnife.bind(this);
        setTitle(getString(R.string.shopping_lists_activity_title));

        provideDependencies();
    }

    @Override
    public void displayShoppingLists(List<ShoppingList> shoppingLists) {

    }

    @Override
    public void toggleNoShoppingListTv(int size) {

    }

    @Override
    public void navigateToAddItemActivity() {

    }

    @Override
    public void toggleProgressBar() {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void addItem(String name) {

    }

    @Override
    public void updateItem(String id, String name) {

    }

    @Override
    public void deleteItem(String id) {

    }

    /**
     * Builds itemsActivityComponent and inject ItemActivity's dependencies.
     */
    private void provideDependencies() {
        ShoppingListsActivityComponent shoppingListsActivityComponent = DaggerShoppingListsActivityComponent.builder()
                .shoppingListsActivityModule(new ShoppingListsActivityModule(ShoppingListsActivity.this, ShoppingListsActivity.this, ShoppingListsActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        shoppingListsActivityComponent.injectShoppingListsActivity(ShoppingListsActivity.this);
    }
}
