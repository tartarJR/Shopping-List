package com.tatar.shoppinglist.di.activeshoppinglists.module;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.di.activeshoppinglists.scope.ActiveShoppingListsScope;
import com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListAdapter;
import com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayActivity;
import com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayPresenterImpl;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogActions;
import com.tatar.shoppinglist.utils.ui.alertdialog.AlertDialogHelper;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

@Module
public class ActiveShoppingListsModule {

    private ShoppingListDisplayActivity shoppingListDisplayActivity;
    private ShoppingListDisplayView shoppingListDisplayView;
    private AlertDialogActions alertDialogActions;

    public ActiveShoppingListsModule(ShoppingListDisplayActivity shoppingListDisplayActivity, ShoppingListDisplayView shoppingListDisplayView, AlertDialogActions alertDialogActions) {
        this.shoppingListDisplayActivity = shoppingListDisplayActivity;
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.alertDialogActions = alertDialogActions;
    }

    @ActiveShoppingListsScope
    @Provides
    public ShoppingListDisplayActivity shoppingListDisplayActivity() {
        return shoppingListDisplayActivity;
    }

    @ActiveShoppingListsScope
    @Provides
    public ShoppingListDisplayView shoppingListDisplayView() {
        return shoppingListDisplayView;
    }

    @ActiveShoppingListsScope
    @Provides
    public AlertDialogActions alertDialogActions() {
        return alertDialogActions;
    }

    @ActiveShoppingListsScope
    @Provides
    public ShoppingListDisplayPresenter shoppingListDisplayPresenter(ShoppingListDisplayView shoppingListDisplayView, ShoppingListDao shoppingListDao) {
        return new ShoppingListDisplayPresenterImpl(shoppingListDisplayView, shoppingListDao);
    }

    @ActiveShoppingListsScope
    @Provides
    public ShoppingListAdapter shoppingListsAdapter() {
        return new ShoppingListAdapter();
    }

    @ActiveShoppingListsScope
    @Provides
    public AlertDialogHelper alertDialogHelper(ShoppingListDisplayActivity shoppingListDisplayActivity, AlertDialogActions alertDialogActions) {
        return new AlertDialogHelper(shoppingListDisplayActivity, alertDialogActions);
    }
}
