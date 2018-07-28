package com.tatar.shoppinglist.ui.completedshoppinglist;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.ui.BaseActivity;

public class CompletedShoppingListsActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_completed_shopping_lists;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.completed_shopping_lists_activity_title);
    }

    @Override
    protected void provideDependencies() {

    }

    @Override
    protected void setUpViews() {

    }

    @Override
    protected void makeInitialCalls() {

    }
}
