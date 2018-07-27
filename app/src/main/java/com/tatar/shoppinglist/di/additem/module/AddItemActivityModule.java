package com.tatar.shoppinglist.di.additem.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.di.additem.scope.AddItemActivityScope;
import com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemActivity;
import com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemPresenterImpl;
import com.tatar.shoppinglist.ui.shoppinglist.additem.ItemActvAdapter;
import com.tatar.shoppinglist.ui.shoppinglist.additem.ShoppingListItemsAdapter;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemView;

@Module
public class AddItemActivityModule {

    private AddItemActivity addItemActivity;
    private AddItemView addItemView;

    public AddItemActivityModule(AddItemActivity addItemActivity, AddItemView addItemView) {
        this.addItemActivity = addItemActivity;
        this.addItemView = addItemView;
    }

    @AddItemActivityScope
    @Provides
    public AddItemActivity addItemActivity() {
        return addItemActivity;
    }

    @AddItemActivityScope
    @Provides
    public AddItemView addItemView() {
        return addItemView;
    }

    @AddItemActivityScope
    @Provides
    public AddItemPresenter addItemPresenter(AddItemView addItemView, ItemDao itemDao, ShoppingListDao shoppingListDao) {
        return new AddItemPresenterImpl(addItemView, itemDao, shoppingListDao);
    }

    @AddItemActivityScope
    @Provides
    public ItemActvAdapter itemActvAdapter() {
        return new ItemActvAdapter(addItemActivity);
    }

    @AddItemActivityScope
    @Provides
    public ShoppingListItemsAdapter shoppingListItemsAdapter() {
        return new ShoppingListItemsAdapter();
    }
}
