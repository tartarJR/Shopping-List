package com.tatar.shoppinglist.ui.helpers;

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
import com.tatar.shoppinglist.data.db.item.model.Item;

public class AlertDialogHelper {

    private Activity activity;
    private AlertDialogActions dialogActions;

    public AlertDialogHelper(Activity activity, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogActions = dialogActions;
    }

    /**
     * Displays an alert dialog for either adding an Item.
     */
    public void displayAddItemDialog() {
        setUpAndDisplayItemAlertDialog(false, null);
    }

    /**
     * Displays an alert dialog for either updating an Item.
     */
    private void displayUpdateItemDialog(Item item) {
        setUpAndDisplayItemAlertDialog(true, item);
    }

    /**
     * Creates and displays an alert dialog for either adding or updating an item.
     */
    private void setUpAndDisplayItemAlertDialog(final boolean shouldUpdate, final Item item) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(view);

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setHint(R.string.enter_an_item_name_hint);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        dialogTitle.setText(!shouldUpdate ? activity.getString(R.string.add_an_new_item) : activity.getString(R.string.edit_an_existing_item));

        if (shouldUpdate && item != null) {
            nameEt.setText(item.getName());
        }

        dialogBuilder
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "UPDATE" : "ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, "Please enter an item name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if (shouldUpdate && item != null) {
                    dialogActions.update(item.getId(), nameEt.getText().toString());
                } else {
                    dialogActions.create(nameEt.getText().toString());
                }
            }
        });
    }

    /**
     * Creates and displays an alert dialog for creating a shopping list.
     * TODO refactor this method since it violates DRY principle by doing almost the same job with setUpAndDisplayItemAlertDialog method
     * TODO make alert dialog methods more generic
     */
    public void setUpAndDisplayShoppingListAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(view);

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setHint("Enter a shopping list name");
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        dialogTitle.setText(R.string.create_shopping_list_title);

        dialogBuilder
                .setCancelable(false)
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, "Please enter a shopping list name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                dialogActions.create(nameEt.getText().toString());
            }
        });
    }

    /**
     * Creates and displays an alert dialog for picking an option to update or delete an Item.
     */
    public void setUpAndDisplayActionsDialog(final Item item) {
        CharSequence actions[] = new CharSequence[]{"Update", "Delete"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("Choose option");
        dialogBuilder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int actionType) {
                // actionType 0 mean update action will bee performed, otherwise delete operation will be performed
                if (actionType == 0) {
                    displayUpdateItemDialog(item);
                } else {
                    dialogActions.delete(item.getId());
                }
            }
        });

        dialogBuilder.show();
    }

    /**
     * An interface for managing AlertDialog actions. It will be implemented by an Activity
     */
    public interface AlertDialogActions {
        void create(String name);

        void update(String id, String name);

        void delete(String id);
    }

}
