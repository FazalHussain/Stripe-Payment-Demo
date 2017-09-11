package com.stripepayment.stripepaymentdemo;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;
import com.stripepayment.stripepaymentdemo.CheckBox.CustomAdapter;
import com.stripepayment.stripepaymentdemo.CheckBox.Item;

import java.util.ArrayList;

public class AndroidPayActivity extends StripeAndroidPayActivity {

    ListView list;
    CustomAdapter adapter;
    private ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_pay);
        list = (ListView) findViewById(R.id.list_item);
        itemList = new ArrayList<>();
        itemList.add(new Item(101, "Hello"));
        itemList.add(new Item(102, "Hi"));
        itemList.add(new Item(103, "Hello"));
        itemList.add(new Item(104, "Hi"));
        itemList.add(new Item(105, "Hello"));

        adapter = new CustomAdapter(this,R.layout.checked_item,itemList);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setItemsCanFocus(false);
        list.setAdapter(adapter);






    }

    @Override
    protected void onAndroidPayAvailable() {
        Log.d("AndroidPay","Available");
    }

    @Override
    protected void onAndroidPayNotAvailable() {
        Log.d("AndroidPay","Not Available");
    }

    @Override
    protected void addBuyButtonWalletFragment(@NonNull SupportWalletFragment walletFragment) {
        FragmentTransaction buttonTransaction = getSupportFragmentManager().beginTransaction();
        buttonTransaction.add(R.id.activity_android_pay, walletFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_save){
            save();
        }
        return true;
    }

    private void save() {
        ArrayList<Item> list_item = new ArrayList<>();
        SparseBooleanArray checked = list.getCheckedItemPositions();
        if(checked!=null){
            for(int i=0; i<list.getAdapter().getCount(); i++){
                if(checked.get(i)){
                    Item item = itemList.get(i);
                    list_item.add(item);
                }
            }

            Log.d("Size", String.valueOf(list_item.size()));
        }
    }
}
