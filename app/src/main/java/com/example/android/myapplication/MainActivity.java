package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=85abb5fdce1c441c916510773248d112";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button action = (Button)findViewById(R.id.exchange);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(API_URL, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("CATCHING", "HTTP Success");

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject ratesObject = jsonObj.getJSONObject("rates");

                            Double krwRate = ratesObject.getDouble("KRW");
                            Double eurRate = ratesObject.getDouble("EUR");
                            Log.i("CATCHING", "KRW: " + krwRate);
                            Log.i("CATCHING", "EUR: " + eurRate);

                            EditText usedValue = (EditText)findViewById(R.id.editTextUSD);
                            TextView krwValue = (TextView)findViewById(R.id.textViewKRW);
                            TextView eurValue = (TextView)findViewById(R.id.textViewEUR);
                            if(! usedValue.getText().toString().equals("")) {
                                Double usds = Double.valueOf(usedValue.getText().toString());
                                Double krws = usds * krwRate;
                                Double eurs = usds * eurRate;
                                krwValue.setText("KRW: " + String.valueOf(krws));
                                eurValue.setText("EUR: " + String.valueOf(eurs));
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter a USD value!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }


                });
            }
        });



    }
}
