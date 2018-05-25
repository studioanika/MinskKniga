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

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<WhatZakazal> _objects;

    public Zakaz_info(Context context, ArrayList<WhatZakazal> objects) {
        this._context = context;
        this._objects = objects;
    }

    @Override
    public int getCount() {
        return _objects.size();
    }

    @Override
    public Object getItem(int position) {
        return _objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_zakaz_new, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv5 = view.findViewById(R.id.tv5);
        TextView tv6 = view.findViewById(R.id.tv6);
        TextView tv7 = view.findViewById(R.id.tv7);


        tv1.setText(_objects.get(position).getName());
        tv2.setText(_objects.get(position).getArtikul());
        tv3.setText(_objects.get(position).getCena());
        tv4.setText(_objects.get(position).getZakazano());
        tv5.setText(_objects.get(position).getOtgruzeno().equals("")?"0":_objects.get(position).getOtgruzeno());
        tv6.setText(_objects.get(position).getPodarki());
        tv7.setText(String.valueOf(Double.parseDouble(_objects.get(position).getZakazano())*Double.parseDouble(_objects.get(position).getCena())));

        return view;
    }
}
