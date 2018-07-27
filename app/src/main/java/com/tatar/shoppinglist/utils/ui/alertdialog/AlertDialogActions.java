package com.tatar.shoppinglist.utils.ui.alertdialog;

/**
 * An interface for managing AlertDialog actions. Can be implemented by any activity
 */
public interface AlertDialogActions {
    void create(String name);

    void update(String id, String name);

    void delete(String id);

    void displayUpdateDialog(String id, String name);
}
