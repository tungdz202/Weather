package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    String tenthanhpho = "";
    ImageView imgBack;
    TextView textname;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<thoiTiet> mangthoiTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "Du lieu truyen qua: " + city);
        if(city.equals("")){
            tenthanhpho = "HaNoi";
            Get7DaysData(tenthanhpho);
        }else{
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void AnhXa(){
        imgBack = (ImageView) findViewById(R.id.imageViewBack);
        textname = (TextView) findViewById(R.id.textviewtenTP);
        lv = (ListView) findViewById(R.id.listView);
        mangthoiTiet = new ArrayList<thoiTiet>();
        customAdapter = new CustomAdapter(MainActivity2.this, mangthoiTiet);
        lv.setAdapter(customAdapter);
    }

    private void Get7DaysData(String data){
        String url = "https://api.openweathermap.org/data/2.5/forecast/daily?q="+ data +"&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            textname.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for(int i = 0; i<jsonArrayList.length(); i++){
                                JSONObject jsonObjectlist = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectlist.getString("dt");

                                long l = Long.parseLong(ngay); //chuyển kiểu từ string về Long
                                Date date = new Date(l*1000L); // khởi tạo biến date với kiểu ms
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectlist.getJSONObject("temp");
                                String max = jsonObjectTemp.getString("max");
                                String min = jsonObjectTemp.getString("min");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String nhietdoMax = String.valueOf(a.intValue());
                                String nhietdoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectlist.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                mangthoiTiet.add(new thoiTiet(Day,status,icon,nhietdoMax,nhietdoMin));
                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}