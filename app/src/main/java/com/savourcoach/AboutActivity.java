package com.savourcoach;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.VideoView;

public class AboutActivity extends AppCompatActivity {

    VideoView mVideoView;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mTextView = (TextView) findViewById(R.id.tv_descreption);

        playVideo();
        mTextView.setText(getResources().getString(R.string.about));
    }

    private void playVideo(){
        String path = "android.resource://" + getPackageName() + "/" + R.raw.the_savour_coach_female_british;
        mVideoView.setVideoURI(Uri.parse(path));
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoView!=null && mVideoView.isPlaying()){
            mVideoView.stopPlayback();

        }
    }
}
