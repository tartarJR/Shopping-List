package com.tatar.shoppinglist.ui.activeshoppinglists;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;

import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListsTask extends AsyncTask<Void, Void, List<ShoppingList>> {

    private ShoppingListDisplayView shoppingListDisplayView;
    private ShoppingListDao shoppingListDao;

    public ShoppingListsTask(ShoppingListDisplayView shoppingListDisplayView, ShoppingListDao shoppingListDao) {
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        shoppingListDisplayView.toggleProgressBar();
    }

    @Override
    protected List<ShoppingList> doInBackground(Void... voids) {
        return shoppingListDao.getAllShoppingLists();
    }

    @Override
    protected void onPostExecute(List<ShoppingList> shoppingLists) {
        super.onPostExecute(shoppingLists);
        shoppingListDisplayView.toggleProgressBar();

        if (shoppingLists == null) {
            shoppingListDisplayView.displayMessage("Upps ! Something went wrong.");
        } else {
            shoppingListDisplayView.toggleNoDataTv(shoppingLists.size());
            shoppingListDisplayView.displayShoppingLists(shoppingLists);
        }
    }
}
