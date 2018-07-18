package com.tatar.shoppinglist.di.item.module;

import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;
import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;
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

    @ItemsActivityScope
    @Provides
    public ItemsView itemView() {
        return itemView;
    }

    @ItemsActivityScope
    @Provides
    public ItemsPresenter itemPresenter(ItemsView itemView, ItemDao itemDao, ItemAlertDialogHelper itemAlertDialogHelper) {
        return new ItemsPresenterImpl(itemView, itemDao, itemAlertDialogHelper);
    }
}
