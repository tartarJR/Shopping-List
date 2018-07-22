package com.tatar.shoppinglist.di.item.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;
import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;
import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper.AlertDialogActions;
import com.tatar.shoppinglist.ui.item.ItemsActivity;
import com.tatar.shoppinglist.ui.item.ItemsAdapter;
import com.tatar.shoppinglist.ui.item.ItemsPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

@Module
public class ItemsActivityModule {

    private ItemsActivity itemsActivity;
    private ItemsView itemView;
    private AlertDialogActions dialogActions;

    public ItemsActivityModule(ItemsActivity itemsActivity, ItemsView itemView, AlertDialogActions dialogActions) {
        this.itemsActivity = itemsActivity;
        this.itemView = itemView;
        this.dialogActions = dialogActions;
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
    public AlertDialogActions dialogActions() {
        return dialogActions;
    }

    @ItemsActivityScope
    @Provides
    public ItemsPresenter itemPresenter(ItemsView itemView, ItemDao itemDao, ItemAlertDialogHelper itemAlertDialogHelper) {
        return new ItemsPresenterImpl(itemView, itemDao, itemAlertDialogHelper);
    }

    @ItemsActivityScope
    @Provides
    public ItemsAdapter itemsAdapter() {
        return new ItemsAdapter();
    }

    @ItemsActivityScope
    @Provides
    public ItemAlertDialogHelper itemAlertDialogHelper(ItemsActivity itemsActivity, AlertDialogActions dialogActions) {
        return new ItemAlertDialogHelper(itemsActivity, dialogActions);
    }
}
