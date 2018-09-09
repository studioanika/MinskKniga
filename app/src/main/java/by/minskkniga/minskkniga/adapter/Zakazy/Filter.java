package by.minskkniga.minskkniga.adapter.Zakazy;

import android.annotation.SuppressLint;
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
import by.minskkniga.minskkniga.api.Class.Spinner_filter;

public class Filter extends BaseAdapter {

    private Context _context;
    private ArrayList<Spinner_filter> _objects;
    private int _status;

    public Filter(Context context, int status, ArrayList<Spinner_filter> objects) {
        this._context = context;
        this._status = status;
        this._objects = new ArrayList<>();
//        switch (status) {
//            case 1:
//                _objects.add(new Spinner_filter("Статус", "0"));
//                break;
//            case 2:
//                _objects.add(new Spinner_filter("Город", "0"));
//                break;
//            case 3:
//                _objects.add(new Spinner_filter("Школа", "0"));
//                break;
//        }
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

        Spinner_filter spinner_filter = _objects.get(position);

        TextView tv1 = view.findViewById(R.id.tv1);
        CheckBox ch1 = view.findViewById(R.id.ch1);

        tv1.setText(spinner_filter.getName());

        if(_status == 1){
            switch (_objects.get(position).getName()) {
                case "1":
                    tv1.setTextColor(Color.rgb(97, 184, 126));
                    tv1.setText("Новый");
                    break;
                case "2":
                    tv1.setTextColor(Color.rgb(242, 201, 76));
                    tv1.setText("В сборке");
                    break;
                case "3":
                    tv1.setTextColor(Color.BLUE);
                    tv1.setText("Собран");
                    break;
                case "4":
                    tv1.setTextColor(Color.rgb(242, 0, 86));
                    tv1.setText("В доставке");
                    break;
                case "5":
                    tv1.setTextColor(Color.rgb(139, 0, 0));
                    tv1.setText("Отгружен");
                    break;
                case "6"://возвращение darkred
                    tv1.setText("Возвращение");
                    tv1.setTextColor(Color.rgb(100, 0, 0));
                    break;
            }
        }

//        if (position == 0) {
//            tv1.setText(_objects.get(position).getName());
//            tv1.setVisibility(View.VISIBLE);
//            ch1.setVisibility(View.GONE);
//        } else {
//            tv1.setVisibility(View.GONE);
//            ch1.setVisibility(View.VISIBLE);
//            if (_status==1) {

//            } else {
//                ch1.setText(_objects.get(position).getName());
//            }
//            ch1.setChecked(_objects.get(position).getChecked().equals("1"));
//        }
//
//        ch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                _objects.get(position).setChecked(b ? "1" : "0");
//
//                Main act = (Main)_context;
//                act.response(_status, _objects);
//            }
//        });

//          tv1.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  Main act = (Main)_context;
//                  if(_status == 1 ) act.filter_status = _objects.get(position).getName();
//                  else if(_status == 2) act.filter_school = _objects.get(position).getName();
//              }
//          });

        return view;
    }
}