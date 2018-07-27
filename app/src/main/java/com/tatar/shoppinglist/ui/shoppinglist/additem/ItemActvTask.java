package com.tatar.shoppinglist.ui.shoppinglist.additem;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemView;

public class ItemActvTask extends AsyncTask<Void, Void, List<Item>> {

    private AddItemView addItemView;
    private ItemDao itemDao;

    public ItemActvTask(AddItemView addItemView, ItemDao itemDao) {
        this.addItemView = addItemView;
        this.itemDao = itemDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        addItemView.toggleProgressBar();
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        return itemDao.getAllItems();
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        addItemView.toggleProgressBar();

        if (items == null) {
            addItemView.displayMessage("Upps ! Something went wrong.");
        } else {
            addItemView.populateItemActv(items);
        }
    }
}
