package com.tatar.shoppinglist.utils.ui;

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
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

public class AlertDialogHelper {

    private Activity activity;
    private AlertDialogActions dialogActions;
    private LayoutInflater layoutInflater;

    public AlertDialogHelper(Activity activity, AlertDialogActions dialogActions) {
        this.activity = activity;
        this.dialogActions = dialogActions;
        layoutInflater = LayoutInflater.from(activity);
    }

    /**
     * Displays an alert dialog for adding an Item.
     */
    public void displayAddItemDialog() {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.add_an_new_item), activity.getString(R.string.item_name_hint), "ADD");

        final EditText nameEt = view.findViewById(R.id.nameEt);

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, "Name field is mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                dialogActions.create(nameEt.getText().toString());
            }
        });
    }

    /**
     * Displays an alert dialog for updating an Item.
     */
    private void displayUpdateItemDialog(final Item item) {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.update_an_existing_item), activity.getString(R.string.item_name_hint), "UPDATE");

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setText(item.getName());

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, "Name field is mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                dialogActions.update(item.getId(), nameEt.getText().toString());
            }
        });
    }

    /**
     * Creates an alert dialog with given parameters.
     */
    private AlertDialog createAlertDialog(View view, String title, String hint, String positiveBtnTxt) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
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
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        return dialogBuilder.create();
    }

    /**
     * Displays an alert dialog for creating a shopping list.
     */
    public void displayCreateShoppingListAlertDialog() {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.create_shopping_list_title), activity.getString(R.string.shopping_list_name_hint), "CREATE");

        final EditText nameEt = view.findViewById(R.id.nameEt);

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, "Name field is mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                dialogActions.create(nameEt.getText().toString());
            }
        });
    }

    /**
     * Displays an alert dialog for updating a shopping list.
     */
    public void displayUpdateShoppingListAlertDialog(final ShoppingList shoppingList) {
        View view = layoutInflater.inflate(R.layout.alert_dialog, null);

        final AlertDialog addItemDialog = createAlertDialog(view, activity.getString(R.string.update_an_existing_shopping_list), activity.getString(R.string.shopping_list_name_hint), "UPDATE");

        final EditText nameEt = view.findViewById(R.id.nameEt);
        nameEt.setText(shoppingList.getName());

        addItemDialog.show();

        addItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    Toast.makeText(activity, "Name field is mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addItemDialog.dismiss();
                }

                dialogActions.update(shoppingList.getId(), nameEt.getText().toString());
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
