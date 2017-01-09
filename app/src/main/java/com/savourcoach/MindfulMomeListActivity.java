package com.savourcoach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MindfulMomeListActivity extends AppCompatActivity {


    ArrayList<String> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful_mome_list);

        final ListView listView = (ListView) findViewById(R.id.lv_moments);
        /*<item>The hunger awareness body scan</item>
        <item>Savour your breath</item>
        <item>I am ok</item>
        <item>Mindful eating</item>
        <item>Your locus of control</item>
        <item>What\'s really eating you?</item>
                <item>Take a breath.</item>*/

        mArrayList = new ArrayList<String>();
        mArrayList.add("Mindful eating");
        mArrayList.add("Savour your breathe");
        mArrayList.add("I am ok");
        mArrayList.add("Hunger awareness body scan");
        mArrayList.add("Your locus of control");
        mArrayList.add("What is really eating you?");
        mArrayList.add("Bonus:Take a breath");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mArrayList);

        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(MindfulMomeListActivity.this,MindfulDetailsActivity.class);
                intent.putExtra("SelectedItem",itemValue);
                startActivity(intent);
                // Show Alert

            }

        });

    }
}
