package com.savourcoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisclaimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        TextView tv = (TextView) findViewById(R.id.tv_disclaimer);
        tv.setText(R.string.i_am_ok);
    }
}
