package com.tatar.shoppinglist.utils.ui.alertdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.R;

public class AlertDialogHelper {

    private Activity activity;
    private AlertDialogActions alertDialogActions;
    private LayoutInflater layoutInflater;

    public AlertDialogHelper(Activity activity, AlertDialogActions alertDialogActions) {
        this.activity = activity;
        this.alertDialogActions = alertDialogActions;
        layoutInflater = LayoutInflater.from(activity);
    }

    public AlertDialogHelper(Activity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
    }

    /**
     * Creates an alert dialog with given parameters.
     */
    private AlertDialog createAlertDialog(View view, String title, String hint, String positiveBtnTxt) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        dialogBuilder.setView(view);

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setHint(hint);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        dialogTitle.setText(title);

        dialogBuilder
                .setCancelable(false)
                .setPositiveButton(positiveBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel_txt,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        return dialogBuilder.create();
    }

    /**
     * Displays an alert dialog for adding an Item.
     */
    public void displayAddItemDialog() {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.add_an_new_item), activity.getString(R.string.item_name_hint), activity.getString(R.string.alert_dialog_add_txt));

        final EditText nameEt = view.findViewById(R.id.nameEt);

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, R.string.alert_dialog_validation_msg, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                alertDialogActions.create(nameEt.getText().toString());
            }
        });
    }

    /**
     * Displays an alert dialog for updating an Item.
     */
    public void displayUpdateItemDialog(final String id, String name) {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.update_an_existing_item), activity.getString(R.string.item_name_hint), activity.getString(R.string.alert_dialog_update_txt));

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setText(name);

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, R.string.alert_dialog_validation_msg, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                alertDialogActions.update(id, nameEt.getText().toString());
            }
        });
    }

    /**
     * Displays an alert dialog for creating a shopping list.
     */
    public void displayCreateShoppingListDialog() {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.create_shopping_list_title), activity.getString(R.string.shopping_list_name_hint), activity.getString(R.string.alert_dialog_create_txt));

        final EditText nameEt = view.findViewById(R.id.nameEt);

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, R.string.alert_dialog_validation_msg, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                alertDialogActions.create(nameEt.getText().toString());
            }
        });
    }

    /**
     * Displays an alert dialog for updating a shopping list.
     */
    public void displayUpdateShoppingListDialog(final String id, String name) {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.update_an_existing_shopping_list), activity.getString(R.string.shopping_list_name_hint), activity.getString(R.string.alert_dialog_update_txt));

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setText(name);

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, R.string.alert_dialog_validation_msg, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                alertDialogActions.update(id, nameEt.getText().toString());
            }
        });
    }

    /**
     * Creates and displays an alert dialog for picking an option to update or delete an Item.
     */
    public void displayActionsDialog(final String id, final String name) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        dialogBuilder.setTitle(R.string.display_actions_dialog_title);
        dialogBuilder.setItems(activity.getResources().getStringArray(R.array.action_dialog_options), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int actionType) {
                if (actionType == 0) {
                    alertDialogActions.displayUpdateDialog(id, name);
                } else {
                    alertDialogActions.delete(id);
                }
            }
        });

        dialogBuilder.show();
    }

    public void displayShoppingCompletedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.completed_shopping_dialog_title)
                .setMessage(R.string.completed_shopping_dialog_msg)
                .setPositiveButton(R.string.alert_dialog_ok_txt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create();
        builder.show();
    }
}
