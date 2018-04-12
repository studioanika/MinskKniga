package by.minskkniga.minskkniga.adapter.Spravoch_Clients;

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


public class Main_2 extends BaseAdapter{

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Clients> objects;

    public Main_2(Context context, ArrayList<Clients> objects) {
        this._context = context;
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

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_spravoch_client_2, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        CheckBox ch1 = view.findViewById(R.id.ch1);
        TextView tv2 = view.findViewById(R.id.tv2);

        tv1.setText(objects.get(position).getName());
        if (objects.get(position).getObraz().equals("0")) {
            ch1.setChecked(false);
        } else {
            ch1.setChecked(true);
        }

        if (objects.get(position).getDolg()>=0) {
            tv2.setTextColor(Color.BLACK);
        } else {
            tv2.setTextColor(Color.RED);
        }
        tv2.setText(objects.get(position).getDolg().toString());


        return view;
    }

}