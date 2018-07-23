package com.tatar.shoppinglist.ui.shoppinglist;

import android.os.AsyncTask;
import android.util.Log;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListTask extends AsyncTask<Void, Void, List<ShoppingList>> {

    private ShoppingListsView shoppingListsView;
    private ShoppingListDao shoppingListDao;

    public ShoppingListTask(ShoppingListsView shoppingListsView, ShoppingListDao shoppingListDao) {
        this.shoppingListsView = shoppingListsView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        shoppingListsView.toggleProgressBar();
    }

    @Override
    protected List<ShoppingList> doInBackground(Void... voids) {
        return shoppingListDao.getAllShoppingLists();
    }

    @Override
    protected void onPostExecute(List<ShoppingList> shoppingLists) {
        super.onPostExecute(shoppingLists);
        shoppingListsView.toggleProgressBar();

        if (shoppingLists == null) {
            shoppingListsView.displayMessage("Upps ! Something went wrong.");
        } else {
            Log.i("BOYZ", "onPostExecute: " + shoppingLists.size());
            shoppingListsView.toggleNoShoppingListTv(shoppingLists.size());
            shoppingListsView.displayShoppingLists(shoppingLists);
        }
    }
}
