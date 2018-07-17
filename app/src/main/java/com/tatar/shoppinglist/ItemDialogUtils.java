package com.tatar.shoppinglist;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.data.db.item.model.Item;

import javax.inject.Inject;

public class ItemDialogUtils {

    private static boolean SHOULD_NOT_UPDATE = false;
    private static boolean SHOULD_UPDATE = true;
    private static int ADD_ITEM_NO_POSITION = -1;

    private Activity activity;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialogActions dialogActions;

    private AlertDialog alertDialog;

    @Inject
    public ItemDialogUtils(Activity activity, AlertDialog.Builder dialogBuilder, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogBuilder = dialogBuilder;
        this.dialogActions = dialogActions;
    }

    public void displayAddItemDialog() {
        setUpItemAlertDialog(SHOULD_NOT_UPDATE, null, ADD_ITEM_NO_POSITION);
    }

    public void displayUpdateItemDialog(Item item, int position) {
        setUpItemAlertDialog(SHOULD_UPDATE, null, ADD_ITEM_NO_POSITION);
    }

    private void setUpItemAlertDialog(final boolean shouldUpdate, final Item item, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View view = layoutInflaterAndroid.inflate(R.layout.item_dialog, null);

        dialogBuilder.setView(view);

        final EditText inputNote = view.findViewById(R.id.itemNameEt);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        dialogTitle.setText(!shouldUpdate ? activity.getString(R.string.add_an_new_item) : activity.getString(R.string.edit_an_existing_item));

        if (shouldUpdate && item != null) {
            inputNote.setText(item.getName());
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
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(activity, "Please enter an item name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if (shouldUpdate && item != null) {
                    dialogActions.updateItem();
                } else {
                    dialogActions.addItem();
                }
            }
        });
    }

    public interface AlertDialogActions {
        void addItem();

        void updateItem();
    }

}
