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
        View view = lInflater.inflate(R.layout.adapter_zakaz_info, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv5 = view.findViewById(R.id.tv5);
        EditText ed1 = view.findViewById(R.id.ed1);

        tv1.setText(_objects.get(position).getName());
        tv2.setText(_objects.get(position).getClas());
        tv3.setText(_objects.get(position).getIzdatel().substring(0,1));
        tv4.setText(_objects.get(position).getArtikul());
        tv5.setText(_objects.get(position).getSokrName());
        ed1.setText(_objects.get(position).getZakazano());

        return view;
    }
}
