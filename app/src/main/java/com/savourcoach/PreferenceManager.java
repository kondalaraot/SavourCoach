package com.savourcoach;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KTirumalsetty on 11/2/2016.
 */

public class PreferenceManager {

    private static final String KEY_BREATHE_IN = "key_breathe_in";
    private static final String KEY_BREATHE_IN_HOLD = "key_breathe_in_hold";
    private static final String KEY_BREATHE_OUT = "key_breathe_out";
    private static final String KEY_BREATHE_OUT_HOLD = "key_breathe_out_hold";
    private static final String KEY_REMINDER_ON = "key_is_everyday_remin_on";
    private static final String KEY_REMINDER_TIME = "key_reminder_time";

    // Shared Preferences
    SharedPreferences mPreferences;

    // Editor for Shared preferences
    SharedPreferences.Editor mEditor;

    // Context
    Context mContext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SavourCoach";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Constructor
    public PreferenceManager(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public void setBreatheInDur(int brIn) {
        mEditor.putInt(KEY_BREATHE_IN, brIn).commit();

    }

    public void setBreatheInHoldDur(int brInHold) {
        mEditor.putInt(KEY_BREATHE_IN_HOLD, brInHold).commit();

    }

    public void setBreatheOutDur(int brOut) {
        mEditor.putInt(KEY_BREATHE_OUT, brOut).commit();

    }

    public void setBreatheOutHoldDur(int brOutHold) {
        mEditor.putInt(KEY_BREATHE_OUT_HOLD, brOutHold).commit();

    }

    public void setReminderOn(boolean isRemindOn) {
        mEditor.putBoolean(KEY_REMINDER_ON, isRemindOn).commit();

    }

    public void setReminderTime(String remindTime) {
        mEditor.putString(KEY_REMINDER_TIME, remindTime).commit();

    }


    /*public String getAuthToken() {
        String bearerToken = mPreferences.getString(KEY_AUTH_TOKEN,"");
        return bearerToken;
    }*/
    public int getBreatheIn() {
        return mPreferences.getInt(KEY_BREATHE_IN, 2);
    }

    public int getBreatheInHold() {
        return mPreferences.getInt(KEY_BREATHE_IN_HOLD, 0);
    }

    public int getBreatheOut() {
        return mPreferences.getInt(KEY_BREATHE_OUT, 2);
    }

    public int getBreatheOutHold() {
        return mPreferences.getInt(KEY_BREATHE_OUT_HOLD, 0);
    }

    public boolean isReminderOn() {
        return mPreferences.getBoolean(KEY_REMINDER_ON, false);
    }

    public String getReminderTime() {
        return mPreferences.getString(KEY_REMINDER_TIME, "12:00");
    }


   /* *//**
     * Clear session details
     * *//*
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        mEditor.clear();
        mEditor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }*/
}
