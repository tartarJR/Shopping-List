package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayView;

public class ActvTask extends AsyncTask<Void, Void, List<Item>> {

    private ItemDisplayView itemDisplayView;
    private ItemDao itemDao;

    public ActvTask(ItemDisplayView itemDisplayView, ItemDao itemDao) {
        this.itemDisplayView = itemDisplayView;
        this.itemDao = itemDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        itemDisplayView.toggleProgressBar();
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        return itemDao.getAllItems();
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        itemDisplayView.toggleProgressBar();

        if (items == null) {
            itemDisplayView.displayMessage("Upps ! Something went wrong.");
        } else {
            itemDisplayView.populateItemActv(items);
        }
    }
}
