package com.tatar.shoppinglist.di.manageitems.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.manageitems.scope.ManageItemsScope;
import com.tatar.shoppinglist.ui.manageitems.ItemsActivity;
import com.tatar.shoppinglist.ui.manageitems.ItemsAdapter;
import com.tatar.shoppinglist.ui.manageitems.ItemsPresenterImpl;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.manageitems.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.manageitems.ItemsContract.ItemsView;

@Module
public class ManageItemsModule {

    private ItemsActivity itemsActivity;
    private ItemsView itemView;
    private AlertDialogActions alertDialogActions;

    public ManageItemsModule(ItemsActivity itemsActivity, ItemsView itemView, AlertDialogActions alertDialogActions) {
        this.itemsActivity = itemsActivity;
        this.itemView = itemView;
        this.alertDialogActions = alertDialogActions;
    }

    @ManageItemsScope
    @Provides
    public ItemsActivity itemsActivity() {
        return itemsActivity;
    }

    @ManageItemsScope
    @Provides
    public ItemsView itemView() {
        return itemView;
    }

    @ManageItemsScope
    @Provides
    public AlertDialogActions alertDialogActions() {
        return alertDialogActions;
    }

    @ManageItemsScope
    @Provides
    public ItemsPresenter itemPresenter(ItemsView itemView, ItemDao itemDao) {
        return new ItemsPresenterImpl(itemView, itemDao);
    }

    @ManageItemsScope
    @Provides
    public ItemsAdapter itemsAdapter() {
        return new ItemsAdapter();
    }

    @ManageItemsScope
    @Provides
    public AlertDialogHelper alertDialogHelper(ItemsActivity itemsActivity, AlertDialogActions alertDialogActions) {
        return new AlertDialogHelper(itemsActivity, alertDialogActions);
    }
}
