package by.minskkniga.minskkniga.adapter.Zakazy;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;

public class Zakaz_sborka extends BaseAdapter {

    private Context _context;
    private ArrayList<Zakaz_product> _objects;

    public Zakaz_sborka(Context context, ArrayList<Zakaz_product> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_zakaz_sborka, parent, false);

        LinearLayout linear = view.findViewById(R.id.linear);
        ImageView iv1 = view.findViewById(R.id.iv1);
        TextView tv1 = view.findViewById(R.id.tv1); //clas
        TextView tv2 = view.findViewById(R.id.tv2); //name
        TextView tv3 = view.findViewById(R.id.tv3); //zak
        TextView tv4 = view.findViewById(R.id.tv4); //otgr
        ImageView iv2 = view.findViewById(R.id.iv2);

        if (_objects.get(position).barcode_status.equals("1")) {
            linear.setBackgroundColor(Color.LTGRAY);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(_context).load("http://cc96297.tmweb.ru/admin/img/nomen/" + _objects.get(position).image).into(iv1);
        } else {
            Glide.with(_context).load("http://cc96297.tmweb.ru/admin/img/nomen/" +  _objects.get(position).image).into(iv1);
        }
        tv1.setText(_objects.get(position).clas);
        tv2.setText(_objects.get(position).name);
        tv3.setText(_objects.get(position).col_zakaz);
        tv4.setText(_objects.get(position).otgruzeno);

        if (_objects.get(position).col_zakaz.equals(_objects.get(position).otgruzeno)){
            Glide.with(_context).load(R.drawable.ic_check_2).into(iv2);
        }else{
            if (Integer.parseInt(_objects.get(position).otgruzeno) >
                    Integer.parseInt(_objects.get(position).col_zakaz)) {
                Glide.with(_context).load(R.drawable.ic_check_2).into(iv2);
            }else{
                Glide.with(_context).load(R.drawable.ic_check_1).into(iv2);
            }
        }

        return view;
    }
}
