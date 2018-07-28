package com.tatar.shoppinglist.ui.shoppinglistitem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.App;
import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;
import com.tatar.shoppinglist.di.shoppinglistItem.component.ShoppingListItemActivityComponent;
import com.tatar.shoppinglist.di.shoppinglistItem.component.DaggerShoppingListItemActivityComponent;
import com.tatar.shoppinglist.di.shoppinglistItem.module.ShoppingListItemActivityModule;
import com.tatar.shoppinglist.utils.ui.RecyclerItemTouchHelper;
import com.tatar.shoppinglist.utils.ui.RecyclerViewDividerDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemPresenter;
import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemView;

public class ShoppingListItemActivity extends AppCompatActivity implements ShoppingListItemView, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = ShoppingListItemActivity.class.getSimpleName();

    public static final String INCOMING_TITLE = "title";
    public static final String INCOMING_SHOPPING_LIST_ID = "id";

    @BindView(R.id.addItemLayout)
    ConstraintLayout addItemLayout;

    @BindView(R.id.itemNameActv)
    AutoCompleteTextView itemNameActv;

    @BindView(R.id.addItemBtn)
    Button addItemBtn;

    @BindView(R.id.shoppingListItemsRecyclerView)
    RecyclerView shoppingListItemsRecyclerView;

    @BindView(R.id.noShoppingListItemsTv)
    TextView noShoppingListItemsTv;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    ShoppingListItemAdapter shoppingListItemAdapter;

    @Inject
    ItemActvAdapter itemActvAdapter;

    @Inject
    ShoppingListItemPresenter addItemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_item);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra(INCOMING_TITLE);
        String shoppingListId = intent.getStringExtra(INCOMING_SHOPPING_LIST_ID);
        setTitle(title);

        provideDependencies();

        itemNameActv.setAdapter(itemActvAdapter);
        setUpRecyclerView();

        addItemsPresenter.getActvItems();
        addItemsPresenter.getShoppingListItems(shoppingListId);
    }

    @OnClick(R.id.addItemBtn)
    void floatingActionButtonClick() {
        addItemsPresenter.addItemToShoppingList(itemNameActv.getText().toString());
    }

    @Override
    public void populateItemActv(List<Item> items) {
        itemActvAdapter.setItems(items);
    }

    @Override
    public void displayShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        shoppingListItemAdapter.setShoppingListItems(shoppingListItems);
    }

    @Override
    public void toggleProgressBar() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            itemNameActv.setVisibility(View.INVISIBLE);
            addItemBtn.setVisibility(View.INVISIBLE);
            shoppingListItemsRecyclerView.setVisibility(View.INVISIBLE);
            noShoppingListItemsTv.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            itemNameActv.setVisibility(View.VISIBLE);
            addItemBtn.setVisibility(View.VISIBLE);
            shoppingListItemsRecyclerView.setVisibility(View.VISIBLE);
            if (noShoppingListItemsTv.getVisibility() != View.INVISIBLE) {
                noShoppingListItemsTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void toggleNoItemsTv(int size) {
        if (size > 0) {
            noShoppingListItemsTv.setVisibility(View.GONE);
        } else {
            noShoppingListItemsTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(ShoppingListItemActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearActv() {
        itemNameActv.setText("");
    }

    /**
     * Builds addItemActivityComponent and inject ShoppingListItemActivity's dependencies.
     */
    private void provideDependencies() {
        ShoppingListItemActivityComponent shoppingListItemActivityComponent = DaggerShoppingListItemActivityComponent.builder()
                .shoppingListItemActivityModule(new ShoppingListItemActivityModule(ShoppingListItemActivity.this, ShoppingListItemActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        shoppingListItemActivityComponent.injectItemsActivity(ShoppingListItemActivity.this);
    }

    /**
     * Sets up shoppingListItemsRecyclerView along with its ItemAnimator, ItemDecoration and ItemTouchListener.
     */
    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        shoppingListItemsRecyclerView.setLayoutManager(mLayoutManager);
        shoppingListItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shoppingListItemsRecyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this, LinearLayoutManager.VERTICAL, 16));
        shoppingListItemsRecyclerView.setAdapter(shoppingListItemAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(shoppingListItemsRecyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ShoppingListItemAdapter.ViewHolder) {
            final int indexToBeDeleted = viewHolder.getAdapterPosition();
            final ShoppingListItem removedItem = shoppingListItemAdapter.getShoppingListItem(indexToBeDeleted);
            addItemsPresenter.removeItemFromShoppingList(indexToBeDeleted);

            Snackbar snackbar = Snackbar.make(addItemLayout, removedItem.getName() + " removed from the shopping list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItemsPresenter.addItemToShoppingList(removedItem.getName());
                }
            });

            snackbar.setActionTextColor(Color.YELLOW); // TODO change color, redesign snackbar
            snackbar.show();
        }
    }
}
