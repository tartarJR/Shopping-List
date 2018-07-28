package com.tatar.shoppinglist.di.shoppinglistItem.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.di.shoppinglistItem.scope.ShoppingListItemActivityScope;
import com.tatar.shoppinglist.ui.shoppinglistitem.ItemActvAdapter;
import com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemActivity;
import com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemAdapter;
import com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemPresenter;
import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemView;

@Module
public class ShoppingListItemActivityModule {

    private ShoppingListItemActivity shoppingListItemActivity;
    private ShoppingListItemView shoppingListItemView;

    public ShoppingListItemActivityModule(ShoppingListItemActivity shoppingListItemActivity, ShoppingListItemView shoppingListItemView) {
        this.shoppingListItemActivity = shoppingListItemActivity;
        this.shoppingListItemView = shoppingListItemView;
    }

    @ShoppingListItemActivityScope
    @Provides
    public ShoppingListItemActivity shoppingListItemActivity() {
        return shoppingListItemActivity;
    }

    @ShoppingListItemActivityScope
    @Provides
    public ShoppingListItemView shoppingListItemView() {
        return shoppingListItemView;
    }

    @ShoppingListItemActivityScope
    @Provides
    public ShoppingListItemPresenter shoppingListItemPresenter(ShoppingListItemView shoppingListItemView, ItemDao itemDao, ShoppingListDao shoppingListDao) {
        return new ShoppingListItemPresenterImpl(shoppingListItemView, itemDao, shoppingListDao);
    }

    @ShoppingListItemActivityScope
    @Provides
    public ItemActvAdapter itemActvAdapter(ShoppingListItemActivity shoppingListItemActivity) {
        return new ItemActvAdapter(shoppingListItemActivity);
    }

    @ShoppingListItemActivityScope
    @Provides
    public ShoppingListItemAdapter shoppingListItemAdapter(ShoppingListItemPresenter shoppingListItemPresenter) {
        return new ShoppingListItemAdapter(shoppingListItemPresenter);
    }
}
