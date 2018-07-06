package by.minskkniga.minskkniga.activity.providers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.providers.GetZakKObjectList;

public class AdapterProviderZakazano extends BaseAdapter{
    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<GetZakKObjectList> _objects;

    public AdapterProviderZakazano(Context context, ArrayList<GetZakKObjectList> objects) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.item_pr_zakazano, parent, false);

        TextView tv1 = view.findViewById(R.id.ipro_name);
        TextView tv2 = view.findViewById(R.id.ipro_vozvrat);

        tv1.setText(_objects.get(position).getClient());
        tv2.setText(_objects.get(position).getZak_no());

        return view;
    }

}
