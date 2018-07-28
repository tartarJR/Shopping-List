package com.tatar.shoppinglist.ui.shoppinglistitem;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;

public class ShoppingListItemTask extends AsyncTask<String, Void, List<ShoppingListItem>> {

    private ShoppingListItemContract.ShoppingListItemView shoppingListItemView;
    private ShoppingListDao shoppingListDao;

    public ShoppingListItemTask(ShoppingListItemContract.ShoppingListItemView shoppingListItemView, ShoppingListDao shoppingListDao) {
        this.shoppingListItemView = shoppingListItemView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        shoppingListItemView.toggleProgressBar();
    }

    @Override
    protected List<ShoppingListItem> doInBackground(String... strings) {
        return shoppingListDao.getShoppingListItemsById(strings[0]);
    }

    @Override
    protected void onPostExecute(List<ShoppingListItem> shoppingListItems) {
        super.onPostExecute(shoppingListItems);
        shoppingListItemView.toggleProgressBar();

        if (shoppingListItems == null) {
            shoppingListItemView.displayMessage("Upps ! Something went wrong.");
        } else {
            shoppingListItemView.toggleNoItemsTv(shoppingListItems.size());
            shoppingListItemView.displayShoppingListItems(shoppingListItems);
        }
    }
}
