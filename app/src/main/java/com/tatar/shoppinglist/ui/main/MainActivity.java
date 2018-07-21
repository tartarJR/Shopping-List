package com.tatar.shoppinglist.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.ui.item.ItemsActivity;
import com.tatar.shoppinglist.ui.shoppinglist.ShoppingListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.shoppingListsBtn)
    Button shoppingListsBtn;

    @BindView(R.id.itemsActivityBtn)
    Button itemsActivityBtn;

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
        Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
        startActivity(intent);
    }
}
