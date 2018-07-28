package com.tatar.shoppinglist.ui.shoppinglist.additem;

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
import com.tatar.shoppinglist.di.additem.component.AddItemActivityComponent;
import com.tatar.shoppinglist.di.additem.component.DaggerAddItemActivityComponent;
import com.tatar.shoppinglist.di.additem.module.AddItemActivityModule;
import com.tatar.shoppinglist.utils.ui.RecyclerItemTouchHelper;
import com.tatar.shoppinglist.utils.ui.RecyclerViewDividerDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemView;

public class AddItemActivity extends AppCompatActivity implements AddItemView, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = AddItemActivity.class.getSimpleName();

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
    ShoppingListItemsAdapter shoppingListItemsAdapter;

    @Inject
    ItemActvAdapter itemActvAdapter;

    @Inject
    AddItemPresenter addItemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
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
        shoppingListItemsAdapter.setShoppingListItems(shoppingListItems);
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
        Toast.makeText(AddItemActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearActv() {
        itemNameActv.setText("");
    }

    /**
     * Builds addItemActivityComponent and inject AddItemActivity's dependencies.
     */
    private void provideDependencies() {
        AddItemActivityComponent addItemActivityComponent = DaggerAddItemActivityComponent.builder()
                .addItemActivityModule(new AddItemActivityModule(AddItemActivity.this, AddItemActivity.this))
                .appComponent(App.get(this).getAppComponent())
                .build();

        addItemActivityComponent.injectItemsActivity(AddItemActivity.this);
    }

    /**
     * Sets up shoppingListItemsRecyclerView along with its ItemAnimator, ItemDecoration and ItemTouchListener.
     */
    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        shoppingListItemsRecyclerView.setLayoutManager(mLayoutManager);
        shoppingListItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shoppingListItemsRecyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this, LinearLayoutManager.VERTICAL, 16));
        shoppingListItemsRecyclerView.setAdapter(shoppingListItemsAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(shoppingListItemsRecyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ShoppingListItemsAdapter.ViewHolder) {
            final int indexToBeDeleted = viewHolder.getAdapterPosition();
            final ShoppingListItem removedItem = shoppingListItemsAdapter.getShoppingListItem(indexToBeDeleted);
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
