package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;

public class Nomenklatura extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> objects;

    public Nomenklatura(Context context, ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> objects) {
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

        TextView cap = view.findViewById(R.id.nomen_cap);
        TextView _class = view.findViewById(R.id.nomen_class);
        TextView izd = view.findViewById(R.id.nomen_izd);
        TextView art = view.findViewById(R.id.nomen_art);
        TextView sokr = view.findViewById(R.id.nomen_sokr);
        TextView cena = view.findViewById(R.id.nomen_cena);

        cap.setText(objects.get(position).getName());
        _class.setText(objects.get(position).getClass_());
        izd.setText(objects.get(position).getIzdatel().substring(0,1));
        art.setText(objects.get(position).getArtikul());
        sokr.setText(objects.get(position).getSokrName());
        cena.setText(objects.get(position).getProdCena());

        return view;
    }
}
