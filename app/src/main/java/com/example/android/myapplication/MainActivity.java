package com.example.android.myapplication;

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


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MP6:Main";
    private Spinner sp;
    private String names[] = {"KRW | South-Korean Won", "EUR | Euro", "GBP | British Pound", "CAD | Canadian Dollar",
                              "AUD | Australian Dollar", "CHF | Swiss Franc", "CNY | Chinese Yuan", "JPY | Japanese Yen", "MXN | Mexican Peso"};
    private ArrayAdapter <String> adapter;

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=85abb5fdce1c441c916510773248d112";
    private String record = "";
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = (Spinner)findViewById(R.id.SpinnerChoose);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
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
                    case 2:
                        record = "GBP";
                        break;
                    case 3:
                        record = "CAD";
                        break;
                    case 4:
                        record = "AUD";
                        break;
                    case 5:
                        record = "CHF";
                        break;
                    case 6:
                        record = "CNY";
                        break;
                    case 7:
                        record = "JPY";
                        break;
                    case 8:
                        record = "MXN";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        result = (TextView)findViewById(R.id.TextViewresult);
        Button convert = (Button)findViewById(R.id.ButtonConvert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(API_URL, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(TAG, "HTTP Success");

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject ratesObject = jsonObj.getJSONObject("rates");

                            Double rate = ratesObject.getDouble(record);
                            Log.i(TAG, record + rate);


                            EditText usdValue = (EditText)findViewById(R.id.editTextUSD);
                            Double usd = Double.valueOf(usdValue.getText().toString());
                            Double resultValue = usd * rate;
                            if(usdValue.getText().toString().equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter a USD value", Toast.LENGTH_LONG).show();
                            }
                            result.setTextSize(18);
                            result.setText(resultValue.toString());
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
