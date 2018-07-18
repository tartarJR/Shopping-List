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

    private static boolean SHOULD_NOT_UPDATE = false;
    private static boolean SHOULD_UPDATE = true;
    private static int ADD_ITEM_NO_POSITION = -1;

    private Activity activity;
    private AlertDialogActions dialogActions;

    private AlertDialog alertDialog;


    public ItemAlertDialogHelper(Activity activity, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogActions = dialogActions;
    }

    public void displayAddItemDialog() {
        setUpAndDisplayItemAlertDialog(SHOULD_NOT_UPDATE, null, ADD_ITEM_NO_POSITION);
    }

    public void displayUpdateItemDialog(Item item, int position) {
        setUpAndDisplayItemAlertDialog(SHOULD_UPDATE, item, position);
    }

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

        alertDialog = dialogBuilder.create();
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

    public void showActionsDialog(final int position, final Item item) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("Choose option");
        dialogBuilder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    displayUpdateItemDialog(item, position);
                } else {

                }
            }
        });

        dialogBuilder.show();
    }

    public interface AlertDialogActions {
        void addItem(String name);

        void updateItem(String id, String name, int position);

        void deleteItem();
    }

}
