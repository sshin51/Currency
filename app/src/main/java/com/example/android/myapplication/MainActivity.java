package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Spinner sp;
    String names[] = {"KRW","EUR"};
    ArrayAdapter <String> adapter;

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=85abb5fdce1c441c916510773248d112";
    String record = "";
    TextView display_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = (Spinner)findViewById(R.id.country);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        record = "KRW";
                        break;
                    case 1:
                        record = "EUR";
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        display_data = (TextView)findViewById(R.id.result);

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
                            TextView moneyValue = findViewById(R.id.result);
                            Double usds = Double.valueOf(usedValue.getText().toString());
                            Double krws = usds * krwRate;
                            Double eurs = usds * eurRate;
                            if(! usedValue.getText().toString().equals("")) {
                                if (moneyValue.getText().toString().equals("KRW")) {
                                    moneyValue.setText("KRW: " + String.valueOf(krws));
                                } else if (moneyValue.getText().toString().equals("EUR")) {
                                    moneyValue.setText("EUR: " + String.valueOf(eurs));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please choose a currency!", Toast.LENGTH_LONG).show();
                                }
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

    public void displayResult(View view) {
        display_data.setTextSize(18);
        display_data.setText(record);

    }
}
