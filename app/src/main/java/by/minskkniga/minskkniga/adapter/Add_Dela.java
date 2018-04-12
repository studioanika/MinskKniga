package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;

public class Add_Dela extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<String> date;
    private ArrayList<String> status;
    private ArrayList<String> otvetstv;

    public Add_Dela(Context context, ArrayList<String> date, ArrayList<String> status, ArrayList<String> otvetstv) {
        this.context = context;
        this.date = date;
        this.status = status;
        this.otvetstv = otvetstv;
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view

        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_add_dela, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);

        tv1.setText(date.get(position));
        tv2.setText(status.get(position));
        tv3.setText(otvetstv.get(position));
        return view;
    }


}
