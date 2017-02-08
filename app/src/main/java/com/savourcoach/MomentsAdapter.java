package com.savourcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kondal on 2/8/2017.
 */

public class MomentsAdapter extends ArrayAdapter<MindfulMoment> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView price;
    }

    public MomentsAdapter(Context context, ArrayList<MindfulMoment> users) {
        super(context, R.layout.list_row_in_app_purchase, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MindfulMoment user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_in_app_purchase, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_prod_details);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_price);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(user.getProdDescr());
        viewHolder.price.setText(user.getProdPrice());
        // Return the completed view to render on screen
        return convertView;
    }
}