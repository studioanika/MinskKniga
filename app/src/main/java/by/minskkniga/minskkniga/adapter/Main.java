package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import by.minskkniga.minskkniga.R;

public class Main extends BaseAdapter{

    private LayoutInflater lInflater;
    private Context _context;
    private String[] object = {"Заказы", "Поставщики", "Касса", "Номенклатура", "Органайзер", "Инвентаризация", "Справочники", "Клиенты", "Поставщики", "Курьеры"};

    public Main(Context context){
        this._context = context;
    }

    @Override
    public int getCount() {
        return object.length;
    }

    @Override
    public Object getItem(int position) {
        return object[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isEnabled(int position) {
        if (position == 6) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_menu, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);

        if (position==6){
            tv1.setTextColor(Color.GRAY);
            tv1.setPadding(10,5,0,5);
        }

        tv1.setText(object[position]);

        return view;
    }
}
