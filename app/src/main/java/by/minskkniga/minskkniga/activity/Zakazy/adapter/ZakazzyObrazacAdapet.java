package by.minskkniga.minskkniga.activity.Zakazy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Nomenklatura.Main;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.nomenclatura.ObjectZakazyObrazci;

public class ZakazzyObrazacAdapet extends BaseExpandableListAdapter{

    List<ObjectZakazyObrazci> header_list = new ArrayList<>();
    Context _context;

    public ZakazzyObrazacAdapet(List<ObjectZakazyObrazci> header_list,
                                Context _context) {
        this.header_list = header_list;
        this._context = _context;
    }

    @Override
    public int getGroupCount() {
        return header_list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return header_list.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return header_list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return header_list.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_header_zakazy_obrazci, null);
        }
        TextView tv1 = view.findViewById(R.id.tv_name);
        TextView tv2 = view.findViewById(R.id.tv_cena);
        TextView tv3 = view.findViewById(R.id.tv_ves);
        TextView tv4 = view.findViewById(R.id.tv_summa);
        ImageView img = view.findViewById(R.id.img_i);
        Main main = null;
        try {
            main = (Main) _context;
        } catch (Exception e) {
            e.printStackTrace();
            img.setVisibility(View.GONE);

        }

        tv1.setText(header_list.get(i).getName());
        tv2.setText(String.valueOf(header_list.get(i).getCena()));
        tv3.setText(String.valueOf(header_list.get(i).getVes()));
        if(header_list.get(i).getList() != null)
            tv4.setText(String.valueOf(header_list.get(i).getList().size()));

        RelativeLayout rel = view.findViewById(R.id.rel);
        final Main finalMain = main;
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    finalMain.addToListComplect(header_list.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_body_zakazy_obrazci, null);
        }

        TextView tv1 = view.findViewById(R.id.tv_name);
        TextView tv2 = view.findViewById(R.id.tv_cena);
        TextView tv3 = view.findViewById(R.id.tv_ves);
        TextView tv4 = view.findViewById(R.id.tv_summa);

        Product product = header_list.get(i).getList().get(i1);

        tv1.setText(product.getName());
        tv2.setText(product.getProdCena());
        tv3.setText(product.getVes());
        //tv4.setText(product.getArtikul());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
