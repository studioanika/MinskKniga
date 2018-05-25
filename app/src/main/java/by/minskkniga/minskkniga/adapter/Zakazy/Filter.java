package by.minskkniga.minskkniga.adapter.Zakazy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Zakazy.Main;
import by.minskkniga.minskkniga.api.Class.Spinner_filter;

import static android.app.Activity.RESULT_OK;

public class Filter extends BaseAdapter {

    private Context _context;
    private ArrayList<Spinner_filter> _objects;
    private int _status;

    public Filter(Context context, int status, ArrayList<Spinner_filter> objects) {
        this._context = context;
        this._status = status;
        this._objects = new ArrayList<>();
        switch (status) {
            case 1:
                _objects.add(new Spinner_filter("Статус", "0"));
                break;
            case 2:
                _objects.add(new Spinner_filter("Город", "0"));
                break;
            case 3:
                _objects.add(new Spinner_filter("Школа", "0"));
                break;
        }
        this._objects.addAll(objects);
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
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(_context).inflate(R.layout.adapter_zakaz_filter, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        CheckBox ch1 = view.findViewById(R.id.ch1);

        if (position == 0) {
            tv1.setText(_objects.get(position).getName());
            tv1.setVisibility(View.VISIBLE);
            ch1.setVisibility(View.GONE);
        } else {
            tv1.setVisibility(View.GONE);
            ch1.setVisibility(View.VISIBLE);
            if (_status==1) {
                switch (_objects.get(position).getName()) {
                    case "1":
                        ch1.setTextColor(Color.rgb(97, 184, 126));
                        ch1.setText("Новый");
                        break;
                    case "2":
                        ch1.setTextColor(Color.rgb(242, 201, 76));
                        ch1.setText("В сборке");
                        break;
                    case "3":
                        ch1.setTextColor(Color.BLUE);
                        ch1.setText("Собран");
                        break;
                    case "4":
                        ch1.setTextColor(Color.rgb(242, 0, 86));
                        ch1.setText("В доставке");
                        break;
                    case "5":
                        ch1.setTextColor(Color.rgb(139, 0, 0));
                        ch1.setText("Отгружен");
                        break;
                    case "6"://возвращение darkred
                        ch1.setText("Возвращение");
                        ch1.setTextColor(Color.rgb(100, 0, 0));
                        break;
                }
            } else {
                ch1.setText(_objects.get(position).getName());
            }
            ch1.setChecked(_objects.get(position).getChecked().equals("1"));
        }

        ch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _objects.get(position).setChecked(b ? "1" : "0");

                Main act = (Main)_context;
                act.response(_status, _objects);
            }
        });
        return view;
    }
}