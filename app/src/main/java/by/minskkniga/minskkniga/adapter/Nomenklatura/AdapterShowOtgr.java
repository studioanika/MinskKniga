package by.minskkniga.minskkniga.adapter.Nomenklatura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;


public class AdapterShowOtgr extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<ItemShowOtgr> _objects;

    public AdapterShowOtgr(Context context, ArrayList<ItemShowOtgr> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_show_otgr, parent, false);

        ItemShowOtgr item = _objects.get(position);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv5 = view.findViewById(R.id.tv5);
        TextView tv6 = view.findViewById(R.id.tv6);
        TextView tv7 = view.findViewById(R.id.tv7);

        tv1.setText(item.getDate());
        tv2.setText(item.getName_zak());
        tv3.setText(item.getCena());
        tv4.setText(item.getOtgruzeno());
        tv5.setText(String.valueOf(item.getVozvrat()));
        tv6.setText(String.valueOf(item.getPodarki()));
        tv7.setText(String.valueOf(item.getSumma()));

//        tv1.setText(_objects.get(position).getName());
//        tv2.setText(_objects.get(position).getClas());
//        if (!_objects.get(position).getIzdatel().equals(""))
//            tv3.setText(_objects.get(position).getIzdatel().substring(0,1));
//        else
//            tv3.setText("");
//        tv4.setText(_objects.get(position).getArtikul());
//        tv5.setText(_objects.get(position).getSokrName());
//        tv6.setText(_objects.get(position).getProdCena());

        return view;
    }
}

