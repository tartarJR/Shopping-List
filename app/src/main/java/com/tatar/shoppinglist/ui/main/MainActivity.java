package com.tatar.shoppinglist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsActivity;
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
    void goToItemsActivity() {
        Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.shoppingListsBtn)
    void goToShoppingListActivity() {
        Intent intent = new Intent(MainActivity.this, ShoppingListsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.completedShoppingListsBtn)
    void goToCompletedShoppingListsActivity() {
        Intent intent = new Intent(MainActivity.this, CompletedShoppingListsActivity.class);
        startActivity(intent);
    }
}
