package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;


public class Add_Contacts extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<String> strings;
    String[] buffer;

    public Add_Contacts(Context context, ArrayList<String> str) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view

        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_add_client_contacts, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        Button b1 = view.findViewById(R.id.b1);



        buffer = strings.get(position).split("/~/");



        tv1.setText(buffer[1]);
        return view;
    }


}