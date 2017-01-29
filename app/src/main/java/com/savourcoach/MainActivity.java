package com.savourcoach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG= "MainActivity";
    Animation zoomin, zoomout; //declared as public
    private final int ZOOM_IN_HOLD_LENGTH = 2000;
    private final int ZOOM_OUT_HOLD_LENGTH = 2000;

    private ImageView mImageView;
    private ImageView mBtnStartStop;
    private ImageView mBtnSettings;
    private ImageView mBtnMindfulMom;
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
        mBtnStartStop = (ImageView) findViewById( R.id.btn_start_stop );
        mBtnSettings = (ImageView) findViewById( R.id.btn_settings );
        mBtnMindfulMom = (ImageView) findViewById( R.id.btn_mindful_mom );

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
//            if(mBtnStartStop.getText().toString().equalsIgnoreCase("Start")){
            //Animation has not started
            if(!(zoomin.hasStarted() || zoomout.hasStarted())){

//                mBtnStartStop.setText("Stop");
//                zoomin.start();
//                zoomout.start();
                mImageView.setAnimation(zoomout);
                zoomout.setAnimationListener(new ZoomOutAnimationListener());
                zoomin.setAnimationListener(new ZoomInAnimationListener());
                mImageView.startAnimation(zoomout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // Show your dialog here
                        zoomout.cancel();
                        zoomin.cancel();
                        zoomout.setAnimationListener(null);
                        zoomin.setAnimationListener(null);
//                mImageView.getAnimation().setAnimationListener(null);
                        mImageView.clearAnimation();
                        showAlert();
                    }
                }, 1000 * 60 * mPreferenceManager.getBreatheDuration());
//                mImageView.setAnimation(zoomin);

            }else{
//                mBtnStartStop.setText("Start");
                /*zoomout.cancel();
                zoomin.cancel();*/
//                mTvlabel.setText("");
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

    private void showAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Savour Coach");

        // set dialog message
        alertDialogBuilder
                .setMessage("Time over!")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.dismiss();
                    }
                });
              /* *//* .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }*//*
                });*/

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

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
