package com.savourcoach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG= "MainActivity";
    Animation zoomin, zoomout; //declared as public
    private final int ZOOM_IN_HOLD_LENGTH = 2000;
    private final int ZOOM_OUT_HOLD_LENGTH = 2000;

    private ImageView mImageView;
    private Button mBtnStartStop;
    private Button mBtnSettings;
    private Button mBtnMindfulMom;
    private TextView mTvlabel;

    PreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferenceManager = new PreferenceManager(this);

        findViews();

        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);



        zoomin.setAnimationListener(new ZoomInAnimationListener() );


        zoomout.setAnimationListener(new ZoomOutAnimationListener() );

    }


    private void findViews() {
        mImageView = (ImageView)findViewById( R.id.imageView );
        mTvlabel = (TextView)findViewById( R.id.tv_breathe_label );
        mBtnStartStop = (Button)findViewById( R.id.btn_start_stop );
        mBtnSettings = (Button)findViewById( R.id.btn_settings );
        mBtnMindfulMom = (Button)findViewById( R.id.btn_mindful_mom );

        mBtnStartStop.setOnClickListener( this );
        mBtnSettings.setOnClickListener( this );
        mBtnMindfulMom.setOnClickListener( this );
    }

    @Override
    protected void onResume() {
        super.onResume();
        zoomin.setDuration(mPreferenceManager.getBreatheIn()*1000);
        zoomout.setDuration(mPreferenceManager.getBreatheOut()*1000);

    }
    @Override
    public void onClick(View v) {
        if ( v == mBtnStartStop ) {
            // Handle clicks for mBtnStartStop
            if(mBtnStartStop.getText().toString().equalsIgnoreCase("Start")){
                mBtnStartStop.setText("Stop");
//                zoomin.start();
//                zoomout.start();
                mImageView.setAnimation(zoomout);
                zoomout.setAnimationListener(new ZoomOutAnimationListener());
                zoomin.setAnimationListener(new ZoomInAnimationListener());
                mImageView.startAnimation(zoomout);
//                mImageView.setAnimation(zoomin);

            }else{
                mBtnStartStop.setText("Start");
                /*zoomout.cancel();
                zoomin.cancel();*/
                mTvlabel.setText("");
                zoomout.cancel();
                zoomin.cancel();
                zoomout.setAnimationListener(null);
                zoomin.setAnimationListener(null);
//                mImageView.getAnimation().setAnimationListener(null);
                mImageView.clearAnimation();
//                zoomout.cancel();

            }

        } else if ( v == mBtnSettings ) {
            // Handle clicks for mBtnSettings
            startActivity(new Intent(this,SettingsActivity.class));
        }else if ( v == mBtnMindfulMom ) {
            // Handle clicks for mBtnSettings
            startActivity(new Intent(this,MindfulMomeListActivity.class));
        }
    }

    private class ZoomInAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation arg0) {
            // TODO Auto-generated method stub
            Log.d(TAG,"onAnimationStart--");
            mTvlabel.setText("BREATHE IN");
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
            // TODO Auto-generated method stub
            Log.d(TAG,"onAnimationRepeat--");

        }

        @Override
        public void onAnimationEnd(final Animation arg0) {
            Log.d(TAG,"onAnimationEnd--");
            mTvlabel.setText("HOLD");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"onAnimationEnd-- run");
                    mImageView.startAnimation(zoomout);

                }
            }, mPreferenceManager.getBreatheOutHold()*1000);


        }
    }

    private class ZoomOutAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation arg0) {
            Log.d(TAG, "zoomout onAnimationStart--");
            mTvlabel.setText("BREATHE OUT");

            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            Log.d(TAG, "zoomout onAnimationEnd--");
            mTvlabel.setText("HOLD");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "onAnimationEnd-- run");
                    mImageView.startAnimation(zoomin);

                }
            }, mPreferenceManager.getBreatheInHold() * 1000);

        }
    }

}
