package com.tatar.shoppinglist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayActivity;
import com.tatar.shoppinglist.ui.manageitems.ItemsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.seeActiveShoppingListsImgBtn)
    ImageButton seeActiveShoppingListsImgBtn;

    @BindView(R.id.seeItemsBtn)
    ImageButton seeItemsBtn;

    @BindView(R.id.seeCompletedShoppingListsBtn)
    ImageButton seeCompletedShoppingListsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.seeActiveShoppingListsImgBtn)
    void seeActiveShoppingListActivity() {
        Intent intent = new Intent(MainActivity.this, ShoppingListDisplayActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.seeItemsBtn)
    void seeItemsActivity() {
        Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.seeCompletedShoppingListsBtn)
    void seeToCompletedShoppingLists() {
        Intent intent = new Intent(MainActivity.this, com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayActivity.class);
        startActivity(intent);
    }
}
