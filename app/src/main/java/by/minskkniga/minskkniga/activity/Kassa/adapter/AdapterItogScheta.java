package by.minskkniga.minskkniga.activity.Kassa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.cassa.Scheta;


public class AdapterItogScheta extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Scheta> _objects;

    public AdapterItogScheta(Context context, ArrayList<Scheta> objects) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.item_scheta, parent, false);

        TextView tv1 = view.findViewById(R.id.item_schet_name);
        TextView tv2 = view.findViewById(R.id.item_schet_value);
        TextView tv3 = view.findViewById(R.id.item_schet_comment);
        TextView tv4 = view.findViewById(R.id.item_schet_type);

        tv1.setText(_objects.get(position).getName());
        tv2.setText(_objects.get(position).getValue());
        tv3.setText(_objects.get(position).getComment());
        tv4.setText("Тип: "+_objects.get(position).getType());
        tv4.setVisibility(View.VISIBLE);

        try{

            String a = tv2.getText().toString();
            if(Double.parseDouble(a) < 0) tv2.setTextColor(_context.getResources().getColor(R.color.red));
        }
        catch (Exception e){}

        return view;
    }
}