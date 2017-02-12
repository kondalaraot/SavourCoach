package com.savourcoach;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MindfulDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mBtnStartStop;
    private TextView mTvDescreption;
    private TextView mTvTimer;
    private String mSelectedItem;
    MediaPlayer mPlayer;
    int mAudioResource;
    CountDownTimer mCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful_details);
        mSelectedItem = getIntent().getStringExtra("SelectedItem");
        setTitle(mSelectedItem);
        findViews();

//        mTvTimer.setText();
    }

    private void findViews() {
        mBtnStartStop = (ImageButton) findViewById( R.id.btn_start_stop );
        mTvDescreption = (TextView)findViewById( R.id.tv_descreption );
        mTvTimer = (TextView)findViewById( R.id.tv_timer );

      /*  mArrayList.add("Mindful eating");
        mArrayList.add("Savour your breathe");
        mArrayList.add("I am ok");
        mArrayList.add("Hunger awareness body scan");
        mArrayList.add("Your locus of control");
        mArrayList.add("Bonus:Take a breath");*/
        
        mBtnStartStop.setOnClickListener( this );
        String descreption = "";
        if(mSelectedItem.equalsIgnoreCase("Mindful eating")){
            descreption = getResources().getString(R.string.mindful_eating);
            mAudioResource = R.raw.one;
        }else if (mSelectedItem.equalsIgnoreCase("Savour your breathe")){
            descreption = getResources().getString(R.string.savour_your_breath);
            mAudioResource = R.raw.two;
        }else if (mSelectedItem.equalsIgnoreCase("I am ok")){
            descreption = getResources().getString(R.string.i_am_ok);
            mAudioResource = R.raw.three;
        }else if (mSelectedItem.equalsIgnoreCase("Hunger awareness body scan")){
            descreption = getResources().getString(R.string.the_hunger_awareness_body_scan);
            mAudioResource = R.raw.four;
        }else if (mSelectedItem.equalsIgnoreCase("Your locus of control")){
            descreption = getResources().getString(R.string.your_locus_of_control);
            mAudioResource = R.raw.five;
        }else if (mSelectedItem.equalsIgnoreCase("What is really eating you?")){
            descreption = getResources().getString(R.string.whats_really_eating_you);
            mAudioResource = R.raw.six;
        }else if (mSelectedItem.equalsIgnoreCase("Bonus:Take a breath")){
            descreption = getResources().getString(R.string.take_a_breathe);
            mAudioResource = R.raw.seven;
        }
        mTvDescreption.setText(descreption);
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if(mPlayer!=null && mPlayer.isPlaying()){
            mBtnStartStop.setImageResource(R.drawable.ic_media_play);
//                mCountDownTimer.cancel();
            stopAudioFile();
        }*/
        /*if(mPlayer!=null && mPlayer.isPlaying()){
            mPlayer.stop();
        }else{
//            mPlayer.stop();

        }*/
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
            if(mPlayer!=null && mPlayer.isPlaying()){
                mBtnStartStop.setImageResource(R.drawable.ic_media_play);
//                mCountDownTimer.cancel();
                stopAudioFile();
            }else{
                mBtnStartStop.setImageResource(R.drawable.ic_media_stop);
                playAudioFile();

            }
//            playAudioFile();
        }
    }

    private void playAudioFile() {
        mPlayer = MediaPlayer.create(this, mAudioResource);
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }

        });
        int duration = mPlayer.getDuration();
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
                mTvTimer.setText("00:00");
            }

        }.start();

    }

    private void stopAudioFile(){
        if(mPlayer!=null ){
            if(mPlayer.isPlaying()){
                mPlayer.stop();
                mCountDownTimer.cancel();
                mTvTimer.setText("00:00");

            }
        }

    }
}
