package by.minskkniga.minskkniga.adapter.Nomenklatura;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Nomenklatura.Add;

public class Main extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> _objects;

    public Main(Context context, ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> objects) {
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
        View view = lInflater.inflate(R.layout.adapter_nomenklatura, parent, false);

        LinearLayout ll1 = view.findViewById(R.id.ll1);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv5 = view.findViewById(R.id.tv5);
        TextView tv6 = view.findViewById(R.id.tv6);

        tv1.setText(_objects.get(position).getName());
        tv2.setText(_objects.get(position).getClas());
        if (!_objects.get(position).getIzdatel().equals(""))
            tv3.setText(_objects.get(position).getIzdatel().substring(0,1));
        else
            tv3.setText("");
        tv4.setText(_objects.get(position).getArtikul());
        tv5.setText(_objects.get(position).getSokrName());
        tv6.setText(_objects.get(position).getProdCena());

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, Add.class);
                intent.putExtra("id", _objects.get(position).getId());
                intent.putExtra("name", _objects.get(position).getName());
                intent.putExtra("clas", _objects.get(position).getClas());
                intent.putExtra("obrazec", _objects.get(position).getObrazec());
                intent.putExtra("artikul", _objects.get(position).getArtikul());
                intent.putExtra("sokr_name", _objects.get(position).getSokrName());
                intent.putExtra("izdatel", _objects.get(position).getIzdatel());
                intent.putExtra("autor", _objects.get(position).getAutor());
                intent.putExtra("barcode", _objects.get(position).getBarcode());
                intent.putExtra("zakup_cena", _objects.get(position).getZakupCena());
                intent.putExtra("prod_cena", _objects.get(position).getProdCena());
                intent.putExtra("standart", _objects.get(position).getStandart());
                intent.putExtra("ves", _objects.get(position).getVes());
                _context.startActivity(intent);
            }
        });

        return view;
    }
}
