package com.tatar.shoppinglist.ui.shoppinglist.additem;

import android.os.AsyncTask;
import android.util.Log;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;

import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemView;

public class ShoppingListItemsTask extends AsyncTask<String, Void, List<ShoppingListItem>> {

    private AddItemView addItemView;
    private ShoppingListDao shoppingListDao;

    public ShoppingListItemsTask(AddItemView addItemView, ShoppingListDao shoppingListDao) {
        this.addItemView = addItemView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        addItemView.toggleProgressBar();
    }

    @Override
    protected List<ShoppingListItem> doInBackground(String... strings) {
        return shoppingListDao.getShoppingListItemsById(strings[0]);
    }

    @Override
    protected void onPostExecute(List<ShoppingListItem> shoppingListItems) {
        super.onPostExecute(shoppingListItems);
        addItemView.toggleProgressBar();

        if (shoppingListItems == null) {
            addItemView.displayMessage("Upps ! Something went wrong.");
        } else {
            addItemView.toggleNoItemsTv(shoppingListItems.size());
            addItemView.displayShoppingListItems(shoppingListItems);
        }
    }
}
