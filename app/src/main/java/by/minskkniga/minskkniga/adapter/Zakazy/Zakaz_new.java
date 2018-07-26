package by.minskkniga.minskkniga.adapter.Zakazy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;

public class Zakaz_new extends BaseAdapter {

    private Context _context;
    private ArrayList<Zakaz_product> _objects;

    public Zakaz_new(Context context, ArrayList<Zakaz_product> objects) {
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

        LayoutInflater lInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_zakaz_new, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1); //имя
        TextView tv2 = view.findViewById(R.id.tv2); //артикул
        TextView tv3 = view.findViewById(R.id.tv3); //цена
        TextView tv4 = view.findViewById(R.id.tv4); //заказано
        TextView tv5 = view.findViewById(R.id.tv5); //собрано
        TextView tv6 = view.findViewById(R.id.tv6); //подарков
        TextView tv7 = view.findViewById(R.id.tv7); //сумма

        try{
            Double d = Double.parseDouble(_objects.get(position).summa);
            tv7.setText(String.format("%.2f", d));

        }catch (Exception e){
            tv7.setText(_objects.get(position).summa);
        }

        tv1.setText(_objects.get(position).name);
        tv2.setText(_objects.get(position).artukil);
        tv3.setText(_objects.get(position).cena);
        tv4.setText(_objects.get(position).col_zakaz);
        tv5.setText(_objects.get(position).otgruzeno);
        tv6.setText(_objects.get(position).col_podar);


        return view;
    }
}
