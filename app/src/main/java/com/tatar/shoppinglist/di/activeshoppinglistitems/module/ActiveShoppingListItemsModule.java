package com.tatar.shoppinglist.di.activeshoppinglistitems.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.di.activeshoppinglistitems.scope.ActiveShoppingListItemsScope;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ActvAdapter;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemAdapter;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayActivity;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayView;

@Module
public class ActiveShoppingListItemsModule {

    private ItemDisplayActivity itemDisplayActivity;
    private ItemDisplayView itemDisplayView;

    public ActiveShoppingListItemsModule(ItemDisplayActivity itemDisplayActivity, ItemDisplayView itemDisplayView) {
        this.itemDisplayActivity = itemDisplayActivity;
        this.itemDisplayView = itemDisplayView;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemDisplayActivity itemDisplayActivity() {
        return itemDisplayActivity;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemDisplayView itemDisplayView() {
        return itemDisplayView;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemDisplayPresenter itemDisplayPresenter(ItemDisplayView itemDisplayView, ItemDao itemDao, ShoppingListDao shoppingListDao) {
        return new ItemDisplayPresenterImpl(itemDisplayView, itemDao, shoppingListDao);
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ActvAdapter actvAdapter(ItemDisplayActivity itemDisplayActivity) {
        return new ActvAdapter(itemDisplayActivity);
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemAdapter itemAdapter(ItemDisplayPresenter itemDisplayPresenter) {
        return new ItemAdapter(itemDisplayPresenter);
    }
}
