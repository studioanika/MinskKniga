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
    private ArrayList<String> strings;

    public Add_Dela(Context context, ArrayList<String> str) {
        this.context = context;
        this.strings = str;
    }


    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
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
        View view = lInflater.inflate(R.layout.adapter_add_client_dela, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);

        String[] buffer = strings.get(position).split("/~/");
        tv1.setText(buffer[0]);
        tv2.setText(buffer[1]);
        tv3.setText(buffer[2]);
        return view;
    }


}
