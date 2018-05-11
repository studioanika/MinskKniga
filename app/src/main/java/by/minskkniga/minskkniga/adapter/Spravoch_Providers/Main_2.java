package by.minskkniga.minskkniga.adapter.Spravoch_Providers;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Providers;

public class Main_2 extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Providers> _objects;

    public Main_2(Context context, ArrayList<Providers> objects) {
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

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_spravoch_client_2, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);

        tv1.setText(_objects.get(position).getName());

        if (_objects.get(position).getCreditSize() >= 0) {
            tv2.setTextColor(Color.BLACK);
        } else {
            tv2.setTextColor(Color.RED);
        }
        tv2.setText(_objects.get(position).getCreditSize().toString());

        return view;
    }
    }