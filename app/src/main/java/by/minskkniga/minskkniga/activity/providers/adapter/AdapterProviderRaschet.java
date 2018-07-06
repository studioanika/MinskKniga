package by.minskkniga.minskkniga.activity.providers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.providers.OzhidaemObject;

public class AdapterProviderRaschet extends BaseAdapter{

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<OzhidaemObject> _objects;

    public AdapterProviderRaschet(Context context, ArrayList<OzhidaemObject> objects) {
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
        View view = lInflater.inflate(R.layout.item_pr_ozhidaem, parent, false);

        TextView tv1 = view.findViewById(R.id.ipro_name);
        TextView tv2 = view.findViewById(R.id.ipro_vozvrat);
        TextView tv3 = view.findViewById(R.id.ipro_ozid);

        OzhidaemObject  ozhidaemObject = _objects.get(position);

        tv1.setText(ozhidaemObject.getClient());
        tv2.setText(ozhidaemObject.getVozvrat());
        tv3.setText(ozhidaemObject.getOjidaem());
        // TODO pltсь нужно еще 3 поля

        return view;
    }

}
