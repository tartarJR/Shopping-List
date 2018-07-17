package com.tatar.shoppinglist.di.module;

import android.app.Activity;

import com.tatar.shoppinglist.ItemAlertDialogUtils;
import com.tatar.shoppinglist.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import static android.support.v7.app.AlertDialog.Builder;
import static com.tatar.shoppinglist.ItemAlertDialogUtils.AlertDialogActions;

@Module
public class ItemAlertDialogModule {

    private Activity activity;
    private AlertDialogActions dialogActions;

    public ItemAlertDialogModule(Activity activity, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogActions = dialogActions;
    }

    @ActivityScope
    @Provides
    public Activity activity() {
        return activity;
    }

    @ActivityScope
    @Provides
    public AlertDialogActions dialogActions() {
        return dialogActions;
    }

    @ActivityScope
    @Provides
    public ItemAlertDialogUtils itemDialogUtils(Activity activity, Builder builder, AlertDialogActions dialogActions) {
        return new ItemAlertDialogUtils(activity, builder, dialogActions);
    }

    @ActivityScope
    @Provides
    public Builder alertDialogBuilder(Activity activity) {
        return new Builder(activity);
    }

}
