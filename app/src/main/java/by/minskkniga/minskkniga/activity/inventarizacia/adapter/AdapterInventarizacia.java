package by.minskkniga.minskkniga.activity.inventarizacia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.inventarizacia.InventarizaciaObject;

public class AdapterInventarizacia extends BaseAdapter {

    private Context _context;
    private List<InventarizaciaObject> _objects;

    public AdapterInventarizacia(Context context, List<InventarizaciaObject> objects) {
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

        InventarizaciaObject object = _objects.get(position);

        return view;
    }



}
