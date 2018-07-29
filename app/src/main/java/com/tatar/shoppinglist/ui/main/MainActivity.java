package com.tatar.shoppinglist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayActivity;
import com.tatar.shoppinglist.ui.item.ItemsActivity;
import com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.shoppingListsBtn)
    Button shoppingListsBtn;

    @BindView(R.id.itemsActivityBtn)
    Button itemsActivityBtn;

    @BindView(R.id.completedShoppingListsBtn)
    Button completedShoppingListsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.itemsActivityBtn)
    void seeItemsActivity() {
        Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.shoppingListsBtn)
    void seeActiveShoppingListActivity() {
        Intent intent = new Intent(MainActivity.this, ShoppingListsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.completedShoppingListsBtn)
    void seeToCompletedShoppingLists() {
        Intent intent = new Intent(MainActivity.this, ShoppingListDisplayActivity.class);
        startActivity(intent);
    }
}
