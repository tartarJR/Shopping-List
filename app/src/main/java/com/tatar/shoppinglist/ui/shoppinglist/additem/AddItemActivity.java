package com.tatar.shoppinglist.ui.shoppinglist.additem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tatar.shoppinglist.R;

public class AddItemActivity extends AppCompatActivity {

    public static final String INCOMING_TITLE = "title";
    public static final String INCOMING_SHOPPING_LIST_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();
        String title = intent.getStringExtra(INCOMING_TITLE);
        String id = intent.getStringExtra(INCOMING_SHOPPING_LIST_ID);
        setTitle(title);
    }
}
