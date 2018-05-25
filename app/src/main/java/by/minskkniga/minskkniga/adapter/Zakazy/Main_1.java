package by.minskkniga.minskkniga.adapter.Zakazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Zakazy_short;

public class Main_1 extends BaseAdapter {

    private Context _context;
    private ArrayList<Zakazy_short> _objects;

    public Main_1(Context context, ArrayList<Zakazy_short> objects) {
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(_context).inflate(R.layout.adapter_zakazy_1, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);

        tv1.setText(_objects.get(position).getDate());
        switch (_objects.get(position).getStatus()) {
            case "1":
                tv2.setTextColor(Color.rgb(97, 184, 126));
                tv2.setText("Новый");
                break;
            case "2":
                tv2.setTextColor(Color.rgb(242, 201, 76));
                tv2.setText("В сборке");
                break;
            case "3":
                tv2.setTextColor(Color.BLUE);
                tv2.setText("Собран");
                break;
            case "4":
                tv2.setTextColor(Color.rgb(242, 0, 86));
                tv2.setText("В доставке");
                break;
            case "5":
                tv2.setTextColor(Color.rgb(139, 0, 0));
                tv2.setText("Отгружен");
                break;
            case "6"://возвращение darkred
                tv2.setText("Возвращение");
                tv2.setTextColor(Color.rgb(100, 0, 0));
                break;
        }
        tv3.setText(_objects.get(position).getClient());
        return view;
    }
}