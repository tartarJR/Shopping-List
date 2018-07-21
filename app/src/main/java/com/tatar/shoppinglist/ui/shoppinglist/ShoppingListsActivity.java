package com.tatar.shoppinglist.ui.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tatar.shoppinglist.R;

import butterknife.ButterKnife;

public class ShoppingListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        ButterKnife.bind(this);
        setTitle(getString(R.string.shopping_lists_activity_title));
    }
}
