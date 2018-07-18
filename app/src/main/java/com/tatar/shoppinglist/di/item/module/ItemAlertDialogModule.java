package com.tatar.shoppinglist.di.item.module;

import android.app.Activity;

import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;
import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper.AlertDialogActions;

@Module
public class ItemAlertDialogModule {

    private Activity activity;
    private AlertDialogActions dialogActions;

    public ItemAlertDialogModule(Activity activity, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogActions = dialogActions;
    }

    @ItemsActivityScope
    @Provides
    public Activity activity() {
        return activity;
    }

    @ItemsActivityScope
    @Provides
    public AlertDialogActions dialogActions() {
        return dialogActions;
    }

    @ItemsActivityScope
    @Provides
    public ItemAlertDialogHelper itemDialogUtils(Activity activity, AlertDialogActions dialogActions) {
        return new ItemAlertDialogHelper(activity, dialogActions);
    }
}
