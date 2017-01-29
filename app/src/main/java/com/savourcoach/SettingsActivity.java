package com.savourcoach;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements OnCompleteListener, SeekBar.OnSeekBarChangeListener,View.OnClickListener {

    private SeekBar mSeekBarDuration;
    private TextView mTvDurationValue;
    private SeekBar mSeekBarBreatheIn;
    private TextView mTvBreatheInValue;
    private SeekBar mSeekBarBreatheInHold;
    private TextView mTvBreatheInHoldValue;
    private SeekBar mSeekBarBreatheOut;
    private TextView mTvBreatheOutValue;
    private SeekBar mSeekBarBreatheOutHold;
    private TextView mTvBreatheOutHoldValue;
    private SwitchCompat mSwitchCompat;
    private TextView mTvTime;

    private TextView mTvMealTimer;
    private TextView mTvDisclaimer;
    private TextView mTvAbout;

    PreferenceManager mPreferenceManager;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mPreferenceManager = new PreferenceManager(this);
        findViews();
    }

    private void findViews() {

        mSeekBarDuration = (SeekBar) findViewById(R.id.seekBarDuration);
        mSeekBarBreatheIn = (SeekBar) findViewById(R.id.seekBarBreatheIn);
        mTvDurationValue = (TextView) findViewById(R.id.tv_duration_value);
        mTvBreatheInValue = (TextView) findViewById(R.id.tv_breathe_in_value);
        mSeekBarBreatheInHold = (SeekBar) findViewById(R.id.seekBarBreatheInHold);
        mTvBreatheInHoldValue = (TextView) findViewById(R.id.tv_breathe_in_hold_value);
        mSeekBarBreatheOut = (SeekBar) findViewById(R.id.seekBarBreatheOut);
        mTvBreatheOutValue = (TextView) findViewById(R.id.tv_breathe_out_value);
        mSeekBarBreatheOutHold = (SeekBar) findViewById(R.id.seekBarBreatheOutHold);
        mTvBreatheOutHoldValue = (TextView) findViewById(R.id.tv_breathe_out_hold_value);
        mSwitchCompat = (SwitchCompat) findViewById(R.id.switch_compat);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        mTvMealTimer = (TextView) findViewById(R.id.tv_meal_timer);
        mTvDisclaimer = (TextView) findViewById(R.id.tv_disclaimer);
        mTvAbout = (TextView) findViewById(R.id.tv_about);

        mTvMealTimer.setOnClickListener(this);
        mTvDisclaimer.setOnClickListener(this);
        mTvAbout.setOnClickListener(this);

        if (mPreferenceManager.isReminderOn()){
            mSwitchCompat.setChecked(true);
        }else {
            mSwitchCompat.setChecked(false);

        }

        mTvTime.setText(mPreferenceManager.getReminderTime());

        mSeekBarDuration.setMax(Constans.BREATHE_SEEK_MAX);
        mSeekBarDuration.setProgress(mPreferenceManager.getBreatheDuration());
        mTvDurationValue.setText(" " + mPreferenceManager.getBreatheDuration() + "m");

        mSeekBarBreatheIn.setMax(Constans.BREATHE_SEEK_MAX);
        mSeekBarBreatheIn.setProgress(mPreferenceManager.getBreatheIn());
        mTvBreatheInValue.setText(" " + mPreferenceManager.getBreatheIn() + "s");

        mSeekBarBreatheInHold.setMax(Constans.BREATHE_SEEK_MAX);
        mSeekBarBreatheInHold.setProgress(mPreferenceManager.getBreatheInHold());
        mTvBreatheInHoldValue.setText(" " + mPreferenceManager.getBreatheInHold() + "s");


        mSeekBarBreatheOut.setMax(Constans.BREATHE_SEEK_MAX);
        mSeekBarBreatheOut.setProgress(mPreferenceManager.getBreatheOut());
        mTvBreatheOutValue.setText(" " + mPreferenceManager.getBreatheOut() + "s");


        mSeekBarBreatheOutHold.setMax(Constans.BREATHE_SEEK_MAX);
        mSeekBarBreatheOutHold.setProgress(mPreferenceManager.getBreatheOutHold());
        mTvBreatheOutHoldValue.setText(" " + mPreferenceManager.getBreatheOutHold() + "s");

        mSeekBarDuration.setOnSeekBarChangeListener(this);
        mSeekBarBreatheIn.setOnSeekBarChangeListener(this);
        mSeekBarBreatheInHold.setOnSeekBarChangeListener(this);
        mSeekBarBreatheOut.setOnSeekBarChangeListener(this);
        mSeekBarBreatheOutHold.setOnSeekBarChangeListener(this);

        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPreferenceManager.setReminderOn(b);
                if(b){
                    String[] remTime =  mPreferenceManager.getReminderTime().split(":");
                    int hour = Integer.parseInt(remTime[0]);
                    int min = Integer.parseInt(remTime[1]);
                    setAlarmReminder(hour,min);
                }else{
                   if(alarmManager!=null && pendingIntent!=null) {
                       alarmManager.cancel(pendingIntent);
                       pendingIntent.cancel();
                   }

                }

            }
        });

        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPreferenceManager.isReminderOn()){
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getFragmentManager(), "timePicker");
                }

            }
        });
    }

    @Override
    public void onComplete(int hour, int min) {

        setAlarmReminder(hour,min);
    }

    private void setAlarmReminder(int hour, int min){
        String time = String.valueOf(hour) + ":" + String.valueOf(min);
        mPreferenceManager.setReminderTime(time);
        mTvTime.setText(time);
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, min);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

      /*  if(calSet.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }*/
        scheduleNotification(getNotification("I eat slowly and mindfully"), calSet);
    }

    private void scheduleNotification(Notification notification, Calendar calendar) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//            long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

       /* alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                AlarmManager.INTERVAL_DAY, pendingIntent);*/

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private Notification getNotification(String content) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setSound(soundUri);
        return builder.build();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar == mSeekBarBreatheIn) {
            if (i <= Constans.BREATHE_SEEK_DEFAULT) {
                seekBar.setProgress(2);
                mTvBreatheInValue.setText("  " + Constans.BREATHE_SEEK_DEFAULT + "s");
                mPreferenceManager.setBreatheInDur(Constans.BREATHE_SEEK_DEFAULT);
            } else {
                mTvBreatheInValue.setText("  " + i + "s");
                mPreferenceManager.setBreatheInDur(i);
            }

        } else if (seekBar == mSeekBarBreatheInHold) {
            mTvBreatheInHoldValue.setText("  " + i + "s");
            mPreferenceManager.setBreatheInHoldDur(i);
        } else if (seekBar == mSeekBarBreatheOut) {
            if (i <= Constans.BREATHE_SEEK_DEFAULT) {
                seekBar.setProgress(2);
                mTvBreatheOutValue.setText("  " + Constans.BREATHE_SEEK_DEFAULT + "s");
                mPreferenceManager.setBreatheOutDur(Constans.BREATHE_SEEK_DEFAULT);
            } else {
                mTvBreatheOutValue.setText("  " + i + "s");
                mPreferenceManager.setBreatheOutDur(i);
            }

        } else if (seekBar == mSeekBarBreatheOutHold) {
            mTvBreatheOutHoldValue.setText("  " + i + "s");
            mPreferenceManager.setBreatheOutHoldDur(i);

        }else if (seekBar == mSeekBarDuration) {

            if (i <= Constans.BREATHE_SEEK_DEFAULT) {
                seekBar.setProgress(2);
                mTvDurationValue.setText("  " + Constans.BREATHE_SEEK_DURATION_DEFAULT + "m");
                mPreferenceManager.setDuration(Constans.BREATHE_SEEK_DURATION_DEFAULT);
            } else {
                mTvDurationValue.setText("  " + i + "m");
                mPreferenceManager.setDuration(i);

            }

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {

        if(view == mTvMealTimer){
            startActivity(new Intent(this,MealTimerActivity.class));
        }else if (view == mTvDisclaimer){
            startActivity(new Intent(this,DisclaimerActivity.class));

        }else if (view == mTvAbout){
            startActivity(new Intent(this,AboutActivity.class));

        }

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private OnCompleteListener mListener;

        // make sure the Activity implemented it
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                this.mListener = (OnCompleteListener) activity;
            } catch (final ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            this.mListener.onComplete(hourOfDay, minute);
        }


    }


}
