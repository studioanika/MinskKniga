package by.minskkniga.minskkniga.adapter;

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

public class Zakazy_Client_2 extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<by.minskkniga.minskkniga.api.Zakazy> objects;

    public Zakazy_Client_2(Context context, ArrayList<by.minskkniga.minskkniga.api.Zakazy> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_zakazy_client_2, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        CheckBox ch1 = view.findViewById(R.id.ch1);

        tv1.setText(objects.get(position).getDate());

        switch (objects.get(position).getStatus()){
            case "0":
                tv2.setText("Новый");
                tv2.setTextColor(Color.GREEN);
                break;
            case "1":
                tv2.setText("В сборке");
                tv2.setTextColor(Color.YELLOW);
                break;
            case "2":
                tv2.setText("Новый");
                tv2.setTextColor(Color.GREEN);
                break;
        }

        /*новый
        частично собран
        собран
        в доставку
        отгружен
        ожидание
        оплачен*/

        if (objects.get(position).getOplacheno().equals("0")) {
            ch1.setChecked(false);
        } else {
            ch1.setChecked(true);
        }

        tv3.setText(objects.get(position).getSumma().toString());


        return view;
    }
}
