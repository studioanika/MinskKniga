package by.minskkniga.minskkniga.adapter.Nomenklatura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;

public class Main extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> objects;

    public Main(Context context, ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_nomenklatura, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv5 = view.findViewById(R.id.tv5);
        TextView tv6 = view.findViewById(R.id.tv6);

        tv1.setText(objects.get(position).getName());
        tv2.setText(objects.get(position).getClass_());
        tv3.setText(objects.get(position).getIzdatel().substring(0,1));
        tv4.setText(objects.get(position).getArtikul());
        tv5.setText(objects.get(position).getSokrName());
        tv6.setText(objects.get(position).getProdCena());

        return view;
    }
}
