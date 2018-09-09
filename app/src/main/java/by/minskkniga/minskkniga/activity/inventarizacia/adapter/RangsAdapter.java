package by.minskkniga.minskkniga.activity.inventarizacia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.inventarizacia.RangItem;

public class RangsAdapter extends BaseAdapter {

    private Context _context;
    private List<RangItem> _objects;

    public RangsAdapter(Context context, List<RangItem> objects) {
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
        View view = LayoutInflater.from(_context).inflate(R.layout.item_rangs, parent, false);

        RangItem object = _objects.get(position);

        TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
        TextView tv_kom = (TextView) view.findViewById(R.id.tv_kom);
        TextView tv_rang = (TextView) view.findViewById(R.id.tv_rang);
        TextView tv_zakazano = (TextView) view.findViewById(R.id.tv_zakazano);
        TextView tv_ispolneno = (TextView) view.findViewById(R.id.tv_ispolneno);
        TextView client = (TextView) view.findViewById(R.id.client);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.check);

        tv_date.setText(object.getDate());
        tv_kom.setText(object.getKom());
        tv_rang.setText(object.getId());
        tv_zakazano.setText(object.getZakazano());
        tv_ispolneno.setText(object.getOtgruzeno());
        client.setText(object.getClient());

        if(Integer.parseInt(object.getOplacheno()) == 0) checkBox.setChecked(false);
        else checkBox.setChecked(true);

        return view;
    }



}
