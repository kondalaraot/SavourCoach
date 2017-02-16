package com.savourcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kondal on 2/8/2017.
 */

public class MomentsAdapter extends ArrayAdapter<MindfulMoment> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView price;
        public TextView priceLabel;
    }

    public MomentsAdapter(Context context, List<MindfulMoment> users) {
        super(context, R.layout.list_row_in_app_purchase, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MindfulMoment mindfulMoment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_in_app_purchase, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_prod_details);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.priceLabel = (TextView) convertView.findViewById(R.id.tv_buy_label);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(mindfulMoment.getProdDescr());
//        viewHolder.priceLabel.setText(mindfulMoment.getProdPrice());
        viewHolder.price.setText(mindfulMoment.getProdPrice());

        if(mindfulMoment.isPurchased()){
            viewHolder.price.setText("");
            viewHolder.price.setVisibility(View.GONE);
            viewHolder.priceLabel.setVisibility(View.GONE);
        }else {
            viewHolder.price.setVisibility(View.VISIBLE);
            viewHolder.price.setText(mindfulMoment.getProdPrice());
            viewHolder.priceLabel.setVisibility(View.VISIBLE);

        }
        // Return the completed view to render on screen
        return convertView;
    }
}