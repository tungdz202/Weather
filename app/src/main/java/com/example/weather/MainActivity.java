package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editsearch;
    Button btnSearch, btnChangeActivity;
    TextView txttp, txtCountry, txtTemp, txtDoam, txtMay, txtGio, txtDay, txtstatus;
    ImageView imgIcon;
    String City = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        getCurrentWeatherData("HaNoi");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editsearch.getText().toString();
                if (city.equals("")){
                    City = "HaNoi";
                    getCurrentWeatherData(City);
                }else {
                    City = city;
                    getCurrentWeatherData(city);
                }

            }
        });
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editsearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name", city);
                startActivity(intent);


            }
        });

    }


    public void getCurrentWeatherData(String Data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+Data+"&units=metric&appid=5bfa61b4c8580ba27229f7c3d9893cf2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txttp.setText(" Thành phố: "+name);

                            long l = Long.parseLong(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm:ss");
                            String Day = simpleDateFormat.format(date);
                            txtDay.setText(Day);

                            JSONArray jsonArrayweather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                            String status = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");

                            Picasso.with(MainActivity.this).load("https://openweathermap.org/img/wn/"+icon+".png").into(imgIcon);
                            txtstatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtTemp.setText(Nhietdo+"°C");
                            txtDoam.setText(doam+"%");

                            JSONObject jsonObjectwind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectwind.getString("speed");
                            txtGio.setText(gio+" m/s");

                            JSONObject jsonObjectmay = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectmay.getString("all");
                            txtMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtCountry.setText(" Quốc Gia: "+ country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);


    }
    private void anhxa (){
        editsearch = (EditText) findViewById(R.id.edittextSearch);
        btnSearch = (Button) findViewById(R.id.butttonSearch);
        btnChangeActivity = (Button) findViewById(R.id.buttonActivity);
        txttp =  (TextView) findViewById(R.id.textviewtp);
        txtCountry =  (TextView) findViewById(R.id.textviewCountry);
        txtTemp =  (TextView) findViewById(R.id.textViewTemp);
        txtDoam =  (TextView) findViewById(R.id.textviewDoam);
        txtMay =  (TextView) findViewById(R.id.textviewmay);
        txtGio =  (TextView) findViewById(R.id.textviewtdgio);
        txtDay =  (TextView) findViewById(R.id.textviewDay);
        txtstatus = (TextView) findViewById(R.id.textViewStatus);
        imgIcon = (ImageView) findViewById(R.id.imageIcon);
    }
}










