package by.minskkniga.minskkniga.adapter.Spravoch_Couriers;

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
import by.minskkniga.minskkniga.api.Class.Couriers;

public class Main extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Couriers> _objects;

    public Main(Context context, ArrayList<Couriers> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_spravoch_couriers, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        CheckBox ch1 = view.findViewById(R.id.ch1);

        tv1.setText(_objects.get(position).getName());

        if (_objects.get(position).getSumma() < 0) {
            tv2.setTextColor(Color.RED);
        } else {
            tv2.setTextColor(Color.BLACK);
        }
        tv2.setText(_objects.get(position).getSumma().toString());


        if (_objects.get(position).getKnigi() > 0) {
            ch1.setChecked(true);
        } else {
            ch1.setChecked(false);
        }


        return view;
    }
}
