package com.savourcoach;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MealTimerActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mBtnStartStop;
    private TextView mTvDescreption;
    private TextView mTvTimer;
    MediaPlayer mPlayer;
//    int mAudioResource;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_timer);
        findViews();

//        mTvTimer.setText();
    }

    private void findViews() {
        mBtnStartStop = (ImageButton) findViewById( R.id.btn_start_stop );
        mTvDescreption = (TextView)findViewById( R.id.tv_descreption );
        mTvTimer = (TextView)findViewById( R.id.tv_timer );
        mBtnStartStop.setOnClickListener( this );
//        mAudioResource = R.raw.seven;
        mTvDescreption.setText(getResources().getString(R.string.meal_timer_descr));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlayer!=null && mPlayer.isPlaying()){
            mPlayer.stop();
        }else{
//            mPlayer.stop();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPlayer!=null && mPlayer.isPlaying()){
            mPlayer.stop();

        }

    }

    @Override
    public void onClick(View v) {
        if ( v == mBtnStartStop ) {
            // Handle clicks for mBtnStartStop
            if(isTimerRunning){
                mBtnStartStop.setImageResource(R.drawable.ic_media_play);
                if(mCountDownTimer !=null){
                    mCountDownTimer.cancel();
//                stopAudioFile();
                    mCountDownTimer.cancel();
                    mTvTimer.setText("00:00");
                }

            }else{

                mBtnStartStop.setImageResource(R.drawable.ic_media_stop);
                playAudioFile();

            }
//            playAudioFile();
        }
    }
boolean isTimerRunning = false;
    private void playAudioFile() {
        isTimerRunning = true;
//        mPlayer = MediaPlayer.create(this, mAudioResource);
//        mPlayer.start();
       /* mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }

        });*/
//        int duration = mPlayer.getDuration();
        int duration = 20 * 60 * 1000; //20 Mins
        mCountDownTimer =new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) ;

                long just_hours = seconds / 3600;
                long just_minutes = (seconds/60) % 60;
                long just_seconds = seconds % 60;

                String time = String.format("%02d:%02d:%02d", millisUntilFinished / 3600,
                        (millisUntilFinished % 3600) / 60, (millisUntilFinished % 60));

                mTvTimer.setText(" " +just_minutes + ":"+just_seconds);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                isTimerRunning = false;
                mTvTimer.setText("00:00");
            }

        }.start();

    }

   /* private void stopAudioFile(){
        if(mPlayer!=null ){
            if(mPlayer.isPlaying()){
                mPlayer.stop();
                mCountDownTimer.cancel();
                mTvTimer.setText("00:00");

            }
        }

    }*/
}
