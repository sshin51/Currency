package com.example.android.myapplication;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MP6:Main";
    private Spinner sp1, sp2;
    private String names[] = {"USD | US Dollar", "KRW | South-Korean Won", "EUR | Euro", "GBP | British Pound", "CAD | Canadian Dollar", "AUD | Australian Dollar",
                              "CHF | Swiss Franc", "CNY | Chinese Yuan", "INR | Indian Rupee", "JPY | Japanese Yen", "MXN | Mexican Peso"};
    private ArrayAdapter <String> adapter;
    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=85abb5fdce1c441c916510773248d112";
    private String currency1 = "";
    private String currency2 = "";
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp1 = (Spinner) findViewById(R.id.SpinnerChoose1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        sp1.setAdapter(adapter);
        sp2 = (Spinner) findViewById(R.id.SpinnerChoose2);
        sp2.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        currency1 = "USD";
                        break;
                    case 1:
                        currency1 = "KRW";
                        break;
                    case 2:
                        currency1 = "EUR";
                        break;
                    case 3:
                        currency1 = "GBP";
                        break;
                    case 4:
                        currency1 = "CAD";
                        break;
                    case 5:
                        currency1 = "AUD";
                        break;
                    case 6:
                        currency1 = "CHF";
                        break;
                    case 7:
                        currency1 = "CNY";
                        break;
                    case 8:
                        currency1 = "INR";
                        break;
                    case 9:
                        currency1 = "JPY";
                        break;
                    case 10:
                        currency1 = "MXN";
                        break;
                }
                TextView currencyText = (TextView) findViewById(R.id.textViewCurrency);
                currencyText.setTextSize(16);
                currencyText.setText("Enter " + currency1 + " amount:");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        currency2 = "USD";
                        break;
                    case 1:
                        currency2 = "KRW";
                        break;
                    case 2:
                        currency2 = "EUR";
                        break;
                    case 3:
                        currency2 = "GBP";
                        break;
                    case 4:
                        currency2 = "CAD";
                        break;
                    case 5:
                        currency2 = "AUD";
                        break;
                    case 6:
                        currency2 = "CHF";
                        break;
                    case 7:
                        currency2 = "CNY";
                        break;
                    case 8:
                        currency2 = "INR";
                        break;
                    case 9:
                        currency2 = "JPY";
                        break;
                    case 10:
                        currency2 = "MXN";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        result = (TextView) findViewById(R.id.textViewresult);
        Button convert = (Button) findViewById(R.id.ButtonConvert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(API_URL, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(TAG, "HTTP Success");

                        try {
                            EditText textCurrency = (EditText) findViewById(R.id.editTextCurrency);
                            if(!(textCurrency.getText().toString().isEmpty())) {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject ratesObject = jsonObj.getJSONObject("rates");

                                Double rate1 = ratesObject.getDouble(currency1);
                                Double rate2 = ratesObject.getDouble(currency2);
                                Log.i(TAG, currency1 + rate1);
                                Log.i(TAG, currency2 + rate2);

                                Double currencyValue = Double.valueOf(textCurrency.getText().toString());
                                Double resultValue = (currencyValue / rate1) * rate2;

                                DecimalFormat df = new DecimalFormat("#.##");
                                result.setTextSize(16);
                                result.setTextColor(Color.BLUE);
                                result.setText("Converted amount:   " + df.format(resultValue) + " " + currency2);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter a currency value", Toast.LENGTH_LONG).show();
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
