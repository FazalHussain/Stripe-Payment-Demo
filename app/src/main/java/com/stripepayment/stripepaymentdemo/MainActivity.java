package com.stripepayment.stripepaymentdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.StripeRawJsonObject;
import com.stripe.wrap.pay.AndroidPayConfiguration;
import com.stripe.wrap.pay.activity.StripeAndroidPayActivity;
import com.stripe.wrap.pay.utils.CartContentException;
import com.stripe.wrap.pay.utils.CartManager;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


    //pk_live_IzEfPhXJbBmrzX9R9gAoK8vd
    //pk_test_O1t9JYCoEhuCOBsE10RIe1hB
    private static final String PUBLISHABLE_KEY = "pk_test_O1t9JYCoEhuCOBsE10RIe1hB";

    private CardInputWidget mCardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidPayConfiguration payConfiguration = AndroidPayConfiguration.init(PUBLISHABLE_KEY, "USD");
        AndroidPayConfiguration.getInstance().setShippingAddressRequired(true);
        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);



    }

    public void Pay(View view){
        ////sk_live_AFUSOdsH9IQLBklYNzrN1oYW
        com.stripe.Stripe.apiKey = "sk_test_tMQM0LFWIg3zFgXQokf392ZH";
        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
            //mErrorDialogHandler.showError("Invalid Card Data");
        }

        Stripe stripe = new Stripe(this, PUBLISHABLE_KEY);
        stripe.createToken(
                cardToSave,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        Log.d("Token",token.getId());

                        new AsyncTask<String,Void,Void>(){

                            @Override
                            protected Void doInBackground(String... tokens) {

                                try {

                                Map<String, Object> customerParams = new HashMap<String, Object>();
                                customerParams.put("email", "fazalcs13@gmail.com");
                                customerParams.put("source", tokens[0]);
                                Customer customer = Customer.create(customerParams);

                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("amount", 200);
                                params.put("currency", "usd");
                                params.put("description", "Example5 charge");
                                params.put("customer",customer.getId());
                                //params.put("source", tokens[0]);


                                    Charge charge = Charge.create(params);
                                } catch (AuthenticationException e) {
                                    e.printStackTrace();
                                } catch (InvalidRequestException e) {
                                    e.printStackTrace();
                                } catch (APIConnectionException e) {
                                    e.printStackTrace();
                                } catch (CardException e) {
                                    e.printStackTrace();
                                } catch (APIException e) {
                                    e.printStackTrace();
                                }


                                return null;
                            }
                        }.execute(token.getId());



                    }
                    public void onError(Exception error) {
                        // Show localized error message

                        Log.d("Error",error.toString());

                    }
                });



    }

    public void AndroidPay(View view) throws CartContentException {
        CartManager cartManager = new CartManager();
// Add an item called "Llama Food", priced at $50.00
        cartManager.addLineItem("Basic Example", 500L);

        Cart cart = cartManager.buildCart();
        Intent intent = new Intent(this, AndroidPayActivity.class)
                .putExtra(StripeAndroidPayActivity.EXTRA_CART, cart);
        startActivity(intent);
    }
}
