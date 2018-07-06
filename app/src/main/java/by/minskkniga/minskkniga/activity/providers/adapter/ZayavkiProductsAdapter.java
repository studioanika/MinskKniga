package by.minskkniga.minskkniga.activity.providers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.ProvidersZayavkiRaschetActivity;
import by.minskkniga.minskkniga.api.Class.providers.ZayavkaInfo;

public class ZayavkiProductsAdapter extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<ZayavkaInfo> _objects;

    public ZayavkiProductsAdapter(Context context, ArrayList<ZayavkaInfo> objects) {
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
        View view = lInflater.inflate(R.layout.item_providers_raschet, parent, false);

        TextView tv1 = view.findViewById(R.id.ipr_name);
        final TextView tv2 = view.findViewById(R.id.ipr_zak);
        TextView tv3 = view.findViewById(R.id.ipr_ost);
        TextView tv4 = view.findViewById(R.id.ipr_ozh);
        TextView tv5 = view.findViewById(R.id.ipr_zayavka);

        tv1.setText(_objects.get(position).getName());
        // TODO pltсь нужно еще 4 поля

        if(_objects.get(position).getZakazano() != null ) {
            if(!_objects.get(position).getZakazano().isEmpty()) tv2.setText(_objects.get(position).getZakazano());
            else tv2.setText("0");
        }
        else tv2.setText("0");
        if(_objects.get(position).getOstatok() != null) {
            if(!_objects.get(position).getOstatok().isEmpty())tv3.setText(_objects.get(position).getOstatok());
            else tv3.setText("0");
        }
        else tv3.setText("0");
        if(_objects.get(position).getOjidaem() != null) {
            if(!_objects.get(position).getOjidaem().isEmpty()) tv4.setText(_objects.get(position).getOjidaem());
            else tv4.setText("0");
        }
        else tv4.setText("0");
        if(_objects.get(position).getZayavka() != null) {
            if(!_objects.get(position).getZayavka().isEmpty()) tv5.setText(_objects.get(position).getZayavka());
            else tv5.setText("0");
        }
        else tv5.setText("0");

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProvidersZayavkiRaschetActivity activity = (ProvidersZayavkiRaschetActivity) _context;
                        activity.showAlertZakazano(_objects.get(position).getId());
                    }
                });
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProvidersZayavkiRaschetActivity activity = (ProvidersZayavkiRaschetActivity) _context;
                activity.showAlertInfo(_objects.get(position).getId());
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProvidersZayavkiRaschetActivity activity = (ProvidersZayavkiRaschetActivity) _context;
                activity.showAlertOzhidaem(_objects.get(position).getId());
            }
        });

        return view;
    }
}
