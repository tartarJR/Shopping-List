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

public class ItemAlertDialogHelper {

    private Activity activity;
    private AlertDialogActions dialogActions;

    public ItemAlertDialogHelper(Activity activity, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogActions = dialogActions;
    }

    /**
     * Displays an alert dialog for either adding an Item.
     */
    public void displayAddItemDialog() {
        setUpAndDisplayItemAlertDialog(false, null, -1);
    }

    /**
     * Displays an alert dialog for either updating an Item.
     */
    private void displayUpdateItemDialog(Item item, int position) {
        setUpAndDisplayItemAlertDialog(true, item, position);
    }

    /**
     * Creates and displays an alert dialog for either adding or updating an Item.
     */
    private void setUpAndDisplayItemAlertDialog(final boolean shouldUpdate, final Item item, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View view = layoutInflaterAndroid.inflate(R.layout.item_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(view);

        final EditText itemNameEt = view.findViewById(R.id.itemNameEt);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        dialogTitle.setText(!shouldUpdate ? activity.getString(R.string.add_an_new_item) : activity.getString(R.string.edit_an_existing_item));

        if (shouldUpdate && item != null) {
            itemNameEt.setText(item.getName());
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
                if (TextUtils.isEmpty(itemNameEt.getText().toString())) {
                    Toast.makeText(activity, "Please enter an item name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if (shouldUpdate && item != null) {
                    dialogActions.updateItem(item.getId(), itemNameEt.getText().toString(), position);
                } else {
                    dialogActions.addItem(itemNameEt.getText().toString());
                }
            }
        });
    }

    /**
     * Creates and displays an alert dialog for picking an option to update or delete an Item.
     */
    public void setUpAndDisplayActionsDialog(final int position, final Item item) {
        CharSequence actions[] = new CharSequence[]{"Update", "Delete"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("Choose option");
        dialogBuilder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int actionType) {
                // actionType 0 mean update action will bee performed, otherwise delete operation will be performed
                if (actionType == 0) {
                    displayUpdateItemDialog(item, position);
                } else {
                    dialogActions.deleteItem(item.getId(), position);
                }
            }
        });

        dialogBuilder.show();
    }

    /**
     * An interface for managing AlertDialog actions. It will be implemented by an Activity
     */
    public interface AlertDialogActions {
        void addItem(String name);

        void updateItem(String id, String name, int position);

        void deleteItem(String id, int position);
    }

}
