package com.tatar.shoppinglist.di.item.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;
import com.tatar.shoppinglist.ui.item.ItemsActivity;
import com.tatar.shoppinglist.ui.item.ItemsAdapter;
import com.tatar.shoppinglist.ui.item.ItemsPresenterImpl;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

@Module
public class ItemsActivityModule {

    private ItemsActivity itemsActivity;
    private ItemsView itemView;
    private AlertDialogActions alertDialogActions;

    public ItemsActivityModule(ItemsActivity itemsActivity, ItemsView itemView, AlertDialogActions alertDialogActions) {
        this.itemsActivity = itemsActivity;
        this.itemView = itemView;
        this.alertDialogActions = alertDialogActions;
    }

    @ItemsActivityScope
    @Provides
    public ItemsActivity itemsActivity() {
        return itemsActivity;
    }

    @ItemsActivityScope
    @Provides
    public ItemsView itemView() {
        return itemView;
    }

    @ItemsActivityScope
    @Provides
    public AlertDialogActions alertDialogActions() {
        return alertDialogActions;
    }

    @ItemsActivityScope
    @Provides
    public ItemsPresenter itemPresenter(ItemsView itemView, ItemDao itemDao) {
        return new ItemsPresenterImpl(itemView, itemDao);
    }

    @ItemsActivityScope
    @Provides
    public ItemsAdapter itemsAdapter() {
        return new ItemsAdapter();
    }

    @ItemsActivityScope
    @Provides
    public AlertDialogHelper alertDialogHelper(ItemsActivity itemsActivity, AlertDialogActions alertDialogActions) {
        return new AlertDialogHelper(itemsActivity, alertDialogActions);
    }
}
