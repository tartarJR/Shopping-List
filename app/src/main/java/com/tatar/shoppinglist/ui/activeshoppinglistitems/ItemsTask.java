package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;

import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayView;

public class ItemsTask extends AsyncTask<String, Void, List<ShoppingListItem>> {

    private ItemDisplayView itemDisplayView;
    private ShoppingListDao shoppingListDao;

    public ItemsTask(ItemDisplayView itemDisplayView, ShoppingListDao shoppingListDao) {
        this.itemDisplayView = itemDisplayView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        itemDisplayView.toggleProgressBar();
    }

    @Override
    protected List<ShoppingListItem> doInBackground(String... strings) {
        return shoppingListDao.getShoppingListItemsById(strings[0]);
    }

    @Override
    protected void onPostExecute(List<ShoppingListItem> shoppingListItems) {
        super.onPostExecute(shoppingListItems);
        itemDisplayView.toggleProgressBar();

        if (shoppingListItems == null) {
            itemDisplayView.displayMessage("Upps ! Something went wrong.");
        } else {
            itemDisplayView.toggleNoDataTv(shoppingListItems.size());
            itemDisplayView.displayShoppingListItems(shoppingListItems);
        }
    }
}
