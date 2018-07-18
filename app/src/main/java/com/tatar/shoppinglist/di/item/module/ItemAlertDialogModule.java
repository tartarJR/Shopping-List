package com.tatar.shoppinglist.di.item.module;

import android.app.Activity;

import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;
import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;

import dagger.Module;
import dagger.Provides;

import static android.support.v7.app.AlertDialog.Builder;
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
    public ItemAlertDialogHelper itemDialogUtils(Activity activity, Builder builder, AlertDialogActions dialogActions) {
        return new ItemAlertDialogHelper(activity, builder, dialogActions);
    }

    @ItemsActivityScope
    @Provides
    public Builder alertDialogBuilder(Activity activity) {
        return new Builder(activity);
    }

}
