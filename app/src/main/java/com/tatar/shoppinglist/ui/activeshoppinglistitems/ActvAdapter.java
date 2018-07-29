package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ActvAdapter extends ArrayAdapter<Item> {

    private Context context;
    private List<Item> items;

    public ActvAdapter(@NonNull Context context) {
        super(context, 0);

        this.context = context;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemsFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_actv_row, parent, false);
        }

        TextView itemNameTv = convertView.findViewById(R.id.itemNameTv);

        Item item = getItem(position);

        if (item != null) {
            itemNameTv.setText(item.getName());
        }

        return convertView;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Item> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(items);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : items) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Item) resultValue).getName();
        }
    };
}