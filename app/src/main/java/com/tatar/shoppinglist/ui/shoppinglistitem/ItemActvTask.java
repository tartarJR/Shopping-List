package com.tatar.shoppinglist.ui.shoppinglistitem;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

import static com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemContract.ShoppingListItemView;

public class ItemActvTask extends AsyncTask<Void, Void, List<Item>> {

    private ShoppingListItemContract.ShoppingListItemView shoppingListItemView;
    private ItemDao itemDao;

    public ItemActvTask(ShoppingListItemView shoppingListItemView, ItemDao itemDao) {
        this.shoppingListItemView = shoppingListItemView;
        this.itemDao = itemDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        shoppingListItemView.toggleProgressBar();
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        return itemDao.getAllItems();
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        shoppingListItemView.toggleProgressBar();

        if (items == null) {
            shoppingListItemView.displayMessage("Upps ! Something went wrong.");
        } else {
            shoppingListItemView.populateItemActv(items);
        }
    }
}
