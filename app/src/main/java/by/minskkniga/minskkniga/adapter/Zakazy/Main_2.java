package by.minskkniga.minskkniga.adapter.Zakazy;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Clients;

public class Main_2 extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Clients> _objects;

    public Main_2(Context context, ArrayList<Clients> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_zakazy_2, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        CheckBox ch1 = view.findViewById(R.id.ch1);

        tv1.setText(_objects.get(position).getName());

        if (_objects.get(position).getObrazec().equals("0")) {
            ch1.setChecked(false);
        } else {
            ch1.setChecked(true);
        }

        tv2.setText(_objects.get(position).getDolg());
        if (Double.parseDouble(_objects.get(position).getDolg())>=0) {
            tv2.setTextColor(Color.BLACK);
        } else {
            tv2.setTextColor(Color.RED);
        }

        return view;
    }
}