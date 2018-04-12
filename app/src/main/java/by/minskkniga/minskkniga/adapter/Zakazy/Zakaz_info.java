package by.minskkniga.minskkniga.adapter.Zakazy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.WhatZakazal;

public class Zakaz_info extends BaseAdapter{

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<WhatZakazal> objects;

    public Zakaz_info(Context context, ArrayList<WhatZakazal> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_zakaz_info, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv5 = view.findViewById(R.id.tv5);
        EditText ed1 = view.findViewById(R.id.ed1);

        tv1.setText(objects.get(position).getName());
        tv2.setText(objects.get(position).getClass_());
        tv3.setText(objects.get(position).getIzdanie().substring(0,1));
        tv4.setText(objects.get(position).getArtikyl());
        tv5.setText(objects.get(position).getSokr());
        ed1.setText(objects.get(position).getZak());

        return view;
    }
}
