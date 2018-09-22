package by.minskkniga.minskkniga.activity.inventarizacia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.inventarizacia.Kont;

public class AdapterInventarizacia extends BaseAdapter {

    private Context _context;
    private List<Kont> _objects;

    public AdapterInventarizacia(Context context, List<Kont> objects) {
        this._context = context;
        this._objects = new ArrayList<>();
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
        View view = LayoutInflater.from(_context).inflate(R.layout.item_inventarizacia, parent, false);

        Kont object = _objects.get(position);

        TextView tv_date = (TextView) view.findViewById(R.id.item_inventarizacia_date);
        TextView tv_colvo = (TextView) view.findViewById(R.id.item_inventarizacia_colvo);
        TextView tv_contragent = (TextView) view.findViewById(R.id.item_inventarizacia_contragent);
        TextView tv_type = (TextView) view.findViewById(R.id.item_inventarizacia_type);

        tv_date.setText(object.getDate());
        tv_contragent.setText(object.getClient());
        tv_colvo.setText(object.getKolVo());
//        if(Integer.parseInt(object.getKolVo()) > 0){
//            tv_colvo.setTextColor(_context.getResources().getColor(R.color.green));
//        }else tv_colvo.setTextColor(_context.getResources().getColor(R.color.red));


        // 1 получено - зел
        // 2 продажа - кр
        // 3 возврат - зел

        if(object.getType() == 1) {
            tv_type.setText("Получено");
            tv_type.setTextColor(_context.getResources().getColor(R.color.green));
            tv_colvo.setTextColor(_context.getResources().getColor(R.color.green));
        }else if(object.getType() == 2) {
            tv_type.setText("Продажа");
            tv_type.setTextColor(_context.getResources().getColor(R.color.red));
            tv_colvo.setTextColor(_context.getResources().getColor(R.color.red));
        }else if(object.getType() == 3){
            tv_type.setText("Возврат");
            tv_type.setTextColor(_context.getResources().getColor(R.color.red));
            tv_colvo.setTextColor(_context.getResources().getColor(R.color.red));
        }else if(object.getType() == 4){
            tv_type.setText("Недостача");
            tv_type.setTextColor(_context.getResources().getColor(R.color.red));
            tv_colvo.setTextColor(_context.getResources().getColor(R.color.red));
        }else if(object.getType() == 5){
            tv_type.setText("Излишек");
            tv_type.setTextColor(_context.getResources().getColor(R.color.green));
            tv_colvo.setTextColor(_context.getResources().getColor(R.color.green));
        }


        return view;
    }



}
