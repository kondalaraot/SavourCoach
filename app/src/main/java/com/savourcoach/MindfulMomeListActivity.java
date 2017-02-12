package com.savourcoach;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.savourcoach.util.IabBroadcastReceiver;
import com.savourcoach.util.IabHelper;
import com.savourcoach.util.IabResult;
import com.savourcoach.util.Inventory;
import com.savourcoach.util.Purchase;
import com.savourcoach.util.SkuDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MindfulMomeListActivity extends AppCompatActivity  {


    private static final String TAG = "MindfulMomeListActivity";
    ArrayList<String> mArrayList;

    // The helper object
    IabHelper mHelper;
    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;
    IInAppBillingService mService;
    ServiceConnection mConnection;
//    String inappid = "android.test.purchased";
    static final String SKU_ITEM_MIND_FUL_EATING ="mindful_eating";
    static final String SKU_ITEM_SAVOUR_BREATHE ="savour_your_breathe";
    static final String SKU_ITEM_I_AM_OK ="i_am_ok";
    static final String SKU_ITEM_HUNGER_BODY_SCAN ="hunger_awareness_body_scan";
    static final String SKU_ITEM_YOUR_LOCUS_OF_CONTROL ="your_locus_of_control";
    static final String SKU_ITEM_WHAT_IS_REALLY_EATING_YOU ="what_is_really_eating_you";

     ProgressDialog mProgressDialog;
    List<MindfulMoment> mindfulMoments;
    ListView listView;
    MomentsAdapter mAdapter;
    MindfulMoment selectedMindfulMoment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful_mome_list);

   /*     ServiceConnection mServiceConn = new ServiceConnection() {
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
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);*/

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

        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app Billing is set up OK");
                                               List<String> identifiers = new ArrayList<String> ();
                                               identifiers.add(SKU_ITEM_MIND_FUL_EATING);
                                               identifiers.add(SKU_ITEM_SAVOUR_BREATHE);
                                               identifiers.add(SKU_ITEM_I_AM_OK);
                                               identifiers.add(SKU_ITEM_HUNGER_BODY_SCAN);
                                               identifiers.add(SKU_ITEM_YOUR_LOCUS_OF_CONTROL);
                                               identifiers.add(SKU_ITEM_WHAT_IS_REALLY_EATING_YOU);

                                               try {
                                                   mProgressDialog = ProgressDialog.show(MindfulMomeListActivity.this,"Please wait","Please wait Loading...");
                                                   mHelper.queryInventoryAsync(true, identifiers,mQueryFinishedListener);
                                               } catch (IabHelper.IabAsyncInProgressException e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       }
                                   });




        listView = (ListView) findViewById(R.id.lv_moments);


       /* mArrayList = new ArrayList<String>();
        mArrayList.add("Mindful eating");
        mArrayList.add("Savour your breathe");
        mArrayList.add("I am ok");
        mArrayList.add("Hunger awareness body scan");
        mArrayList.add("Your locus of control");
        mArrayList.add("What is really eating you?");
        mArrayList.add("Bonus:Take a breath");

               ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mArrayList);

        listView.setAdapter(adapter);*/

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;
                selectedMindfulMoment = mindfulMoments.get(position);
                if(selectedMindfulMoment.isPurchased()){
                    String  itemValue    = selectedMindfulMoment.getProdDescr();
                    Intent intent = new Intent(MindfulMomeListActivity.this,MindfulDetailsActivity.class);
                    intent.putExtra("SelectedItem",itemValue);
                    startActivity(intent);
                }else{
                    alert("Do you want to purchase?");
                }
                // ListView Clicked item value

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
        if (mConnection != null) {
            unbindService(mConnection);
        }
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buyItem();
            }
        });
        bld.setNeutralButton("Cancel", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void showAlert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
//        bld.setNeutralButton("Cancel", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    private void buyItem(){
        try {
            mHelper.launchPurchaseFlow(MindfulMomeListActivity.this, selectedMindfulMoment.getProdID(), 10001,
                    mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            else if (purchase.getSku().equals(SKU_ITEM_MIND_FUL_EATING)) {
                // consume the gas and update the UI
                Log.d(TAG,"SKU_ITEM_MIND_FUL_EATING ");
                updateUIOnPurchase(SKU_ITEM_MIND_FUL_EATING);
            }
            else if (purchase.getSku().equals(SKU_ITEM_SAVOUR_BREATHE)) {
                Log.d(TAG,"SKU_ITEM_SAVOUR_BREATHE ");
                updateUIOnPurchase(SKU_ITEM_SAVOUR_BREATHE);

                // give user access to premium content and update the UI
            }else if(purchase.getSku().equals(SKU_ITEM_I_AM_OK)){
                Log.d(TAG,"SKU_ITEM_I_AM_OK ");
                updateUIOnPurchase(SKU_ITEM_I_AM_OK);


            }else if(purchase.getSku().equals(SKU_ITEM_WHAT_IS_REALLY_EATING_YOU)){
                Log.d(TAG,"SKU_ITEM_WHAT_IS_REALLY_EATING_YOU ");
                updateUIOnPurchase(SKU_ITEM_WHAT_IS_REALLY_EATING_YOU);

            }else if(purchase.getSku().equals(SKU_ITEM_YOUR_LOCUS_OF_CONTROL)){
                Log.d(TAG,"SKU_ITEM_YOUR_LOCUS_OF_CONTROL ");
                updateUIOnPurchase(SKU_ITEM_YOUR_LOCUS_OF_CONTROL);

            }else if(purchase.getSku().equals(SKU_ITEM_HUNGER_BODY_SCAN)){
                Log.d(TAG,"SKU_ITEM_HUNGER_BODY_SCAN ");
                updateUIOnPurchase(SKU_ITEM_HUNGER_BODY_SCAN);

            }
        }
    };

    private void updateUIOnPurchase(String prodID){
        for (MindfulMoment mindfulMoment : mindfulMoments) {
            if(mindfulMoment.getProdID().equalsIgnoreCase(SKU_ITEM_SAVOUR_BREATHE)){
                mindfulMoment.setPurchased(true);
                mAdapter.notifyDataSetChanged();
            }

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1001) {
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

            if (resultCode == RESULT_OK) {
                System.out.println("re ssuut ood");
                try{
                    JSONObject jo = new  JSONObject(purchaseData);
                    String sku = jo.getString("");
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

    IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory)
        {
            mProgressDialog.dismiss();
            if (result.isFailure()) {
                Log.d(TAG,"getProducts failed");
                showAlert("Getting In-App prodcuts failed");
//                callback.failure(null);
            } else {
                Log.d(TAG,"getProducts succeeded");
                mindfulMoments = new ArrayList<MindfulMoment>();
                SkuDetails skuDetails_mindful_eating = inventory.getSkuDetails(SKU_ITEM_MIND_FUL_EATING);
                SkuDetails skuDetails_item_savour_breathe = inventory.getSkuDetails(SKU_ITEM_SAVOUR_BREATHE);
                SkuDetails skuDetails_i_am_ok = inventory.getSkuDetails(SKU_ITEM_I_AM_OK);
                SkuDetails skuDetails_hunger_bodyScan = inventory.getSkuDetails(SKU_ITEM_HUNGER_BODY_SCAN);
                SkuDetails skuDetails_locus_of_control = inventory.getSkuDetails(SKU_ITEM_YOUR_LOCUS_OF_CONTROL);
                SkuDetails skuDetails_really_eating_you = inventory.getSkuDetails(SKU_ITEM_WHAT_IS_REALLY_EATING_YOU);

                List<String> skus = inventory.getAllOwnedSkus();

                if(skuDetails_mindful_eating !=null){
                    String descr = skuDetails_mindful_eating.getDescription();
                    String price = skuDetails_mindful_eating.getPrice();
                    Log.d(TAG,"Descr "+descr +"Price" + price);
                    mindfulMoments.add(new MindfulMoment(SKU_ITEM_MIND_FUL_EATING,descr,price));
                }

                if(skuDetails_item_savour_breathe !=null){
                    String descr = skuDetails_item_savour_breathe.getDescription();
                    String price = skuDetails_item_savour_breathe.getPrice();
                    Log.d(TAG,"Descr "+descr +"Price" + price);
                    mindfulMoments.add(new MindfulMoment(SKU_ITEM_SAVOUR_BREATHE,descr,price));
                }

                if(skuDetails_i_am_ok !=null){
                    String descr = skuDetails_i_am_ok.getDescription();
                    String price = skuDetails_i_am_ok.getPrice();
                    Log.d(TAG,"Descr "+descr +"Price" + price);
                    mindfulMoments.add(new MindfulMoment(SKU_ITEM_I_AM_OK,descr,price));
                }

                if(skuDetails_hunger_bodyScan !=null){
                    String descr = skuDetails_hunger_bodyScan.getDescription();
                    String price = skuDetails_hunger_bodyScan.getPrice();
                    Log.d(TAG,"Descr "+descr +"Price" + price);
                    mindfulMoments.add(new MindfulMoment(SKU_ITEM_HUNGER_BODY_SCAN,descr,price));
                }

                if(skuDetails_locus_of_control !=null){
                    String descr = skuDetails_locus_of_control.getDescription();
                    String price = skuDetails_locus_of_control.getPrice();
                    Log.d(TAG,"Descr "+descr +"Price" + price);
                    mindfulMoments.add(new MindfulMoment(SKU_ITEM_YOUR_LOCUS_OF_CONTROL,descr,price));
                }

                if(skuDetails_really_eating_you !=null){
                    String descr = skuDetails_really_eating_you.getDescription();
                    String price = skuDetails_really_eating_you.getPrice();
                    Log.d(TAG,"Descr "+descr +"Price" + price);
                    mindfulMoments.add(new MindfulMoment(SKU_ITEM_WHAT_IS_REALLY_EATING_YOU,descr,price));
                }
                MindfulMoment freeProd = new MindfulMoment(SKU_ITEM_MIND_FUL_EATING,"Bonus:Take a breath","");
                freeProd.setPurchased(true);
                mindfulMoments.add(freeProd);


                mAdapter = new MomentsAdapter(MindfulMomeListActivity.this,mindfulMoments);
                listView.setAdapter(mAdapter);

//                callback.success(inventory);
            }
        }
    };
}
