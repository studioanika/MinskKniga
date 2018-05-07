package by.minskkniga.minskkniga.adapter.Nomenklatura;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;

public class New_zakaz extends BaseAdapter {

    private Context _context;
    private ArrayList<String> _name;

    public New_zakaz(Context context, ArrayList<String> name) {
        this._context = context;
        this._name = name;
    }

    @Override
    public int getCount() {
        return _name.size();
    }

    @Override
    public Object getItem(int position) {
        return _name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater lInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_nomenklatura_new_zakaz, parent, false);

        TextView tv = view.findViewById(R.id.tv);
        EditText ed = view.findViewById(R.id.ed);

        tv.setTextColor(Color.WHITE);
        ed.setTextColor(Color.WHITE);

        tv.setText(_name.get(position));
        ed.setText("0");

        return view;
    }
}
