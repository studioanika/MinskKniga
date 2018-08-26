package by.minskkniga.minskkniga.activity.Kassa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.Main;
import by.minskkniga.minskkniga.api.Class.cassa.DescInfoSchet;

public class AdapterSchetaInfo extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<DescInfoSchet> _objects;
    Main calculator;

    public AdapterSchetaInfo(Context context, ArrayList<DescInfoSchet> objects) {
        this._context = context;
        this._objects = objects;
        calculator = (Main) context;
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        int type = 0;
        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.item_desc_scheta, parent, false);

        final DescInfoSchet item = _objects.get(position);

        LinearLayout lin = view.findViewById(R.id.desc_schet_lin);
        TextView tv_cat_podcat = view.findViewById(R.id.desc_schet_category_podcategory);
        TextView tv_date = view.findViewById(R.id.desc_schet_date);
        TextView tv_dohod = view.findViewById(R.id.desc_schet_dohod);
        TextView tv_rashod = view.findViewById(R.id.desc_schet_rashod);
        TextView tv_note = view.findViewById(R.id.desc_schet_note);
        TextView tv_schet_perevoda = view.findViewById(R.id.desc_schet_schet_perevoda);
        TextView tv_contr = view.findViewById(R.id.desc_schet_contr);

        if(item.getKontragent() != null) tv_contr.setText(item.getKontragent());
        if(item.getDate() != null) tv_date.setText(item.getDate());
        if(item.getCategory() != null) tv_cat_podcat.setText(item.getCategory());
        if(item.getPod_cat() != null) tv_cat_podcat.setText(
                tv_cat_podcat.getText().toString()+ "-"+ item.getPod_cat()
        );

        if(item.getDohod() != null && (item.getDohod() > 0.01)) {
            type = 0;
            tv_rashod.setTextColor(_context.getResources().getColor(R.color.green));
            tv_rashod.setText(String.valueOf(item.getDohod()));
        }
        else {

            type = 1;
            tv_rashod.setTextColor(_context.getResources().getColor(R.color.red));
            tv_rashod.setText(String.valueOf(item.getRashod()));
        }
        if(item.getOperation_sum() != null) tv_dohod.setText(String.valueOf(item.getOperation_sum()));

        if(item.getComment() != null) tv_note.setText(item.getComment());

        if(item.getSchet_perevoda() != null && !item.getSchet_perevoda().isEmpty()){
            type = 2;
            if(item.getDohod() != null && (item.getDohod() > 0.01)) {

                if(item.getSchet_perevoda() != null && !item.getSchet_perevoda().isEmpty()) tv_schet_perevoda.setText(item.getSchet_perevoda());


                if(item.getSchet() != null && !item.getSchet().isEmpty()) tv_schet_perevoda.setText(
                        tv_schet_perevoda.getText().toString()+">>"+item.getSchet()
                );

            }
            else {

                if(item.getSchet() != null ) tv_schet_perevoda.setText(item.getSchet());
                else tv_schet_perevoda.setText(item.getSchet());

                if(item.getSchet_perevoda() != null && !item.getSchet_perevoda().isEmpty()) tv_schet_perevoda.setText(
                        tv_schet_perevoda.getText().toString()+">>"+item.getSchet_perevoda()
                );

            }
        }else {
            tv_schet_perevoda.setText(item.getSchet());

        }

        final int finalType = type;
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getId() != null) calculator.startInfoOperation(item.getId(), finalType);
            }
        });

        lin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(item.getId() != null) calculator.startLongInfoOperation(item.getId(), finalType);
                return false;
            }
        });

        return view;
    }
}
