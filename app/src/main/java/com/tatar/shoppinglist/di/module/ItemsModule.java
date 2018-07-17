package com.tatar.shoppinglist.di.module;

import com.tatar.shoppinglist.ItemAlertDialogUtils;
import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.scope.ActivityScope;
import com.tatar.shoppinglist.ui.item.ItemsPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

@Module(includes = ItemAlertDialogModule.class)
public class ItemsModule {

    private ItemsView itemView;

    public ItemsModule(ItemsView itemView) {
        this.itemView = itemView;
    }

    @ActivityScope
    @Provides
    public ItemsView itemView() {
        return itemView;
    }

    @ActivityScope
    @Provides
    public ItemsPresenter itemPresenter(ItemsView itemView, ItemDao itemDao, ItemAlertDialogUtils itemAlertDialogUtils) {
        return new ItemsPresenterImpl(itemView, itemDao, itemAlertDialogUtils);
    }
}
