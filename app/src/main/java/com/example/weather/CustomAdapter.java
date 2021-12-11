package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<thoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<thoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_listview,null);

        thoiTiet thoiTiet = arrayList.get(i);
        TextView txtDay = (TextView) view.findViewById(R.id.textViewNgay);
        TextView txtStatus = (TextView) view.findViewById(R.id.textViewStatus);
        TextView txtMax = (TextView) view.findViewById(R.id.textViewMaxTemp);
        TextView txtMin = (TextView) view.findViewById(R.id.textViewMinTemp);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageviewStatus);

        txtDay.setText(thoiTiet.Day);
        txtStatus.setText(thoiTiet.Status);
        txtMax.setText(thoiTiet.MaxTemp + "°C");
        txtMin.setText(thoiTiet.MinTemp + "°C");

        Picasso.with(context).load("https://openweathermap.org/img/wn/"+thoiTiet.Image+".png").into(imgStatus);
        return view;
    }
}
