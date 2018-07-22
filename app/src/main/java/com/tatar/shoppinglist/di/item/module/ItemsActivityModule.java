package com.tatar.shoppinglist.di.item.module;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;
import com.tatar.shoppinglist.ui.helpers.AlertDialogHelper;
import com.tatar.shoppinglist.ui.helpers.AlertDialogHelper.AlertDialogActions;
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
    public ItemsPresenter itemPresenter(ItemsView itemView, ItemDao itemDao, AlertDialogHelper alertDialogHelper) {
        return new ItemsPresenterImpl(itemView, itemDao, alertDialogHelper);
    }

    @ItemsActivityScope
    @Provides
    public ItemsAdapter itemsAdapter() {
        return new ItemsAdapter();
    }

    @ItemsActivityScope
    @Provides
    public AlertDialogHelper alertDialogHelper(ItemsActivity itemsActivity, AlertDialogActions dialogActions) {
        return new AlertDialogHelper(itemsActivity, dialogActions);
    }
}
