package by.minskkniga.minskkniga.adapter.Spravoch_Couriers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier;

public class Zakazy_2 extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<Zakazy_courier> objects;

    public Zakazy_2(Context context, ArrayList<Zakazy_courier> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_zakazy_courier_2, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        CheckBox ch1 = view.findViewById(R.id.ch1);
        CheckBox ch2 = view.findViewById(R.id.ch2);


        tv1.setText(objects.get(position).getClient());
        tv2.setText(objects.get(position).getSumma());
        tv3.setText(objects.get(position).getKomment());
        tv4.setText(objects.get(position).getInfo());

        if (objects.get(position).getStatus().equals("3")) {
            ch1.setChecked(true);
        } else {
            ch1.setChecked(false);
        }

        if (objects.get(position).getStatus().equals("2")) {
            ch2.setChecked(true);
        } else {
            ch2.setChecked(false);
        }


        return view;
    }
}
