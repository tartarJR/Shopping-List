package com.tatar.shoppinglist.di.activeshoppinglistitems.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.network.ShoppingListAPI;
import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;
import com.tatar.shoppinglist.di.activeshoppinglistitems.scope.ActiveShoppingListItemsScope;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ActvAdapter;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemAdapter;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayActivity;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayPresenterImpl;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemAdapter.OnItemCheckListener;
import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayView;

@Module
public class ActiveShoppingListItemsModule {

    private ItemDisplayActivity itemDisplayActivity;
    private ItemDisplayView itemDisplayView;
    private OnItemCheckListener onItemCheckListener;

    public ActiveShoppingListItemsModule(ItemDisplayActivity itemDisplayActivity, ItemDisplayView itemDisplayView, OnItemCheckListener onItemCheckListener) {
        this.itemDisplayActivity = itemDisplayActivity;
        this.itemDisplayView = itemDisplayView;
        this.onItemCheckListener = onItemCheckListener;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemDisplayActivity itemDisplayActivity() {
        return itemDisplayActivity;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public OnItemCheckListener onItemCheckListener() {
        return onItemCheckListener;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemDisplayView itemDisplayView() {
        return itemDisplayView;
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemDisplayPresenter itemDisplayPresenter(ItemDisplayView itemDisplayView, ItemDao itemDao, ShoppingListDao shoppingListDao, ShoppingListService shoppingListService, SharedPreferencesManager sharedPreferencesManager) {
        return new ItemDisplayPresenterImpl(itemDisplayView, itemDao, shoppingListDao, shoppingListService, sharedPreferencesManager);
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ShoppingListService shoppingListService(ShoppingListAPI shoppingListAPI) {
        return new ShoppingListService(shoppingListAPI);
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ActvAdapter actvAdapter(ItemDisplayActivity itemDisplayActivity) {
        return new ActvAdapter(itemDisplayActivity);
    }

    @ActiveShoppingListItemsScope
    @Provides
    public ItemAdapter itemAdapter(ItemDisplayPresenter itemDisplayPresenter, OnItemCheckListener onItemCheckListener) {
        return new ItemAdapter(itemDisplayPresenter, onItemCheckListener);
    }

    @ActiveShoppingListItemsScope
    @Provides
    public AlertDialogHelper alertDialogHelper(ItemDisplayActivity itemDisplayActivity) {
        return new AlertDialogHelper(itemDisplayActivity);
    }
}
