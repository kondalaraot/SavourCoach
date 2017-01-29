package com.savourcoach;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.savourcoach.util.IabBroadcastReceiver;
import com.savourcoach.util.IabHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MindfulMomeListActivity extends AppCompatActivity  {


    private static final String TAG = "MindfulMomeListActivity";
    ArrayList<String> mArrayList;

    // The helper object
    IabHelper mHelper;
    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;
    IInAppBillingService mService;
    ServiceConnection mConnection;
    String inappid = "android.test.purchased";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful_mome_list);

        ServiceConnection mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
                Log.d(TAG,"onServiceDisconnected");
            }

            @Override
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                Log.d(TAG,"onServiceConnected");
                mService = IInAppBillingService.Stub.asInterface(service);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Thread getSkuDetails = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                queryInAppProducts();
                            }
                        });
                        getSkuDetails.start();
                    }
                });
            }
        };
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

         /* base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY
         * (that you got from the Google Play developer console). This is not your
         * developer public key, it's the *app-specific* public key.
         *
         * Instead of just storing the entire literal string here embedded in the
         * program,  construct the key at runtime from pieces or
         * use bit manipulation (for example, XOR with some other string) to hide
         * the actual key.  The key itself is not secret information, but we don't
         * want to make it easy for an attacker to replace the public key with one
         * of their own and then fake messages from the server.
         */
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv9TcwKE7ZVsSnqF" +
                "hM717OHiabsBVEsAJa1aueBsLXFjoaU7On2gQB6wYlA+1beUpCXeecH2n4FLO8nbczj4swxPn8NBqxLPRpt" +
                "Qbh9kCv42RxtJs2W6+Tb8aivYV6Web3ndhJPNCdphWFw5vbU0h6DE9TzEXxgDx6yzO82SFOcLsijIV4BGq3c" +
                "1wMdjdtvjMXyV0euf6lx6aVNsD1KWJtHyKs1nbIPK7IwxNr+8F7zTqcxOI7WQqdN/7cXKGPKcTMlyKBlbf" +
                "CEMhEsJO2C8VdgnGyi92Rx8u/elgNsj8Le6c9joaZHa0k3ZOKhlMw4ImfkbIHkZhKIhj0OWsKH3IMQIDAQAB";



        final ListView listView = (ListView) findViewById(R.id.lv_moments);


        mArrayList = new ArrayList<String>();
        mArrayList.add("Mindful eating");
        mArrayList.add("Savour your breathe");
        mArrayList.add("I am ok");
        mArrayList.add("Hunger awareness body scan");
        mArrayList.add("Your locus of control");
        mArrayList.add("What is really eating you?");
        mArrayList.add("Bonus:Take a breath");

       /* *//*ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("premiumUpgrade");
        skuList.add("gas");*//*
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", mArrayList);

        try {

            if(mService !=null){
                Bundle skuDetails = mService.getSkuDetails(3,
                        getPackageName(), "inapp", querySkus);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }*/



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

    private void queryInAppProducts(){
       /* mArrayList = new ArrayList<String>();
        mArrayList.add("Mindful eating");
        mArrayList.add("Savour your breathe");
        mArrayList.add("I am ok");
        mArrayList.add("Hunger awareness body scan");
        mArrayList.add("Your locus of control");
        mArrayList.add("What is really eating you?");
        mArrayList.add("Bonus:Take a breath");*/

        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("mindful_eating");
//        skuList.add("gas");
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        try {

            if(mService !=null){
                Bundle skuDetails = mService.getSkuDetails(3,
                        getPackageName(), "inapp", querySkus);
                int response = skuDetails.getInt("RESPONSE_CODE");
                Log.d(TAG,"get skuDetails resp code "+response);
                if (response == 0) {
                    ArrayList<String> responseList
                            = skuDetails.getStringArrayList("DETAILS_LIST");

                    for (String thisResponse : responseList) {
                        JSONObject object = new JSONObject(thisResponse);
                        String sku = object.getString("productId");
                        String price = object.getString("price");
                        /*if (sku.equals("premiumUpgrade")) mPremiumUpgradePrice = price;
                        else if (sku.equals("gas")) mGasPrice = price;*/
                    }
                }

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (mService != null) {
            unbindService(mConnection);
        }*/
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1001) {
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

            if (resultCode == RESULT_OK) {
                System.out.println("re ssuut ood");
                try{
                    JSONObject jo = new  JSONObject(purchaseData);
                    String sku = jo.getString(inappid);
                    Toast.makeText(MindfulMomeListActivity.this,
                            "u have bo ught"+ sku,
                            Toast.LENGTH_LONG).show();
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
