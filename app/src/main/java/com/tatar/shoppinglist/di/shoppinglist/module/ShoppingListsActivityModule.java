package com.tatar.shoppinglist.di.shoppinglist.module;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.di.shoppinglist.scope.ShoppingListsActivityScope;
import com.tatar.shoppinglist.utils.ui.AlertDialogHelper;
import com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsActivity;
import com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsAdapter;
import com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.utils.ui.AlertDialogHelper.AlertDialogActions;
import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

@Module
public class ShoppingListsActivityModule {

    private ShoppingListsActivity shoppingListsActivity;
    private ShoppingListsView shoppingListsView;
    private AlertDialogActions dialogActions;

    public ShoppingListsActivityModule(ShoppingListsActivity shoppingListsActivity, ShoppingListsView shoppingListsView, AlertDialogActions dialogActions) {
        this.shoppingListsActivity = shoppingListsActivity;
        this.shoppingListsView = shoppingListsView;
        this.dialogActions = dialogActions;
    }

    @ShoppingListsActivityScope
    @Provides
    public ShoppingListsActivity shoppingListsActivity() {
        return shoppingListsActivity;
    }

    @ShoppingListsActivityScope
    @Provides
    public ShoppingListsView shoppingListsView() {
        return shoppingListsView;
    }

    @ShoppingListsActivityScope
    @Provides
    public AlertDialogActions dialogActions() {
        return dialogActions;
    }

    @ShoppingListsActivityScope
    @Provides
    public ShoppingListsPresenter shoppingListsPresenter(ShoppingListsView shoppingListsView, ShoppingListDao shoppingListDao, AlertDialogHelper alertDialogHelper) {
        return new ShoppingListsPresenterImpl(shoppingListsView, shoppingListDao, alertDialogHelper);
    }

    @ShoppingListsActivityScope
    @Provides
    public ShoppingListsAdapter shoppingListsAdapter() {
        return new ShoppingListsAdapter();
    }

    @ShoppingListsActivityScope
    @Provides
    public AlertDialogHelper alertDialogHelper(ShoppingListsActivity shoppingListsActivity, AlertDialogActions dialogActions) {
        return new AlertDialogHelper(shoppingListsActivity, dialogActions);
    }
}
