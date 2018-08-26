package by.minskkniga.minskkniga.adapter.Spravoch_Couriers.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Spravoch_Couriers.Zakaz_info;
import by.minskkniga.minskkniga.activity.Spravoch_Couriers.Zakazy;
import by.minskkniga.minskkniga.api.Class.couriers.CourierClients;
import by.minskkniga.minskkniga.api.Class.couriers.ZakazCourier;

public class CourierClientsAdapter extends BaseExpandableListAdapter{

    Context _context;
    List<CourierClients> header_list = new ArrayList<>();

    public CourierClientsAdapter(Context _context, List<CourierClients> header_list) {
        this._context = _context;
        this.header_list = header_list;
    }

    @Override
    public int getGroupCount() {
        return header_list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return header_list.get(i).getWhat_zakazal().size();
    }

    @Override
    public Object getGroup(int i) {
        return header_list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return header_list.get(i).getWhat_zakazal().get(i1);
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
            view = infalInflater.inflate(R.layout.item_header_courier_clients, null);
        }
        TextView tv1 = view.findViewById(R.id.client);
        TextView tv2 = view.findViewById(R.id.summa);

        tv1.setText(header_list.get(i).getName());
        tv2.setText(header_list.get(i).getAll_summa());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_body_courier_clients, null);
        }

        try {
            final ZakazCourier zakazCourier = header_list.get(i).getWhat_zakazal().get(i1);

            TextView tv1 = view.findViewById(R.id.data);
            TextView status = view.findViewById(R.id.status);
            TextView tv3 = view.findViewById(R.id.summa);
            TextView tv4 = view.findViewById(R.id.txt_oplacheno);
            LinearLayout lin = view.findViewById(R.id.lin);
            CheckBox checkBox = view.findViewById(R.id.oplata);

            if(i1 == 0) {
                if(zakazCourier.getObrazec().contains("1")) tv4.setVisibility(View.GONE);
                lin.setVisibility(View.VISIBLE);
            }else {
                lin.setVisibility(View.GONE);
            }

            if(zakazCourier.getObrazec().contains("1")) checkBox.setVisibility(View.GONE);
            else checkBox.setVisibility(View.VISIBLE);

            if(zakazCourier.getOplacheno() != null) {
                if(zakazCourier.getOplacheno().contains("0")) checkBox.setChecked(false);
                else checkBox.setChecked(true);
            }

            tv1.setText(zakazCourier.getDate());
            tv3.setText(zakazCourier.getSumma());


            switch (zakazCourier.getStatus()) {
                case "0"://chernovik новый green
                    status.setText("Новый");
                    status.setTextColor(Color.rgb(97, 184, 126));
                    break;
                case "1"://новый green
                    status.setText("Новый");
                    status.setTextColor(Color.rgb(97, 184, 126));
                    break;
                case "2"://в сборке yellow
                    status.setText("В сборке");
                    status.setTextColor(Color.rgb(242, 201, 76));
                    break;
                case "3"://собран blue
                    status.setText("Собран");
                    status.setTextColor(Color.BLUE);
                    break;
                case "4"://в доставке lightred
                    status.setText("В доставке");
                    status.setTextColor(Color.rgb(242, 0, 86));
                    break;
                case "5"://отгружен darkred
                    status.setText("Отгружен");
                    status.setTextColor(Color.rgb(139, 0, 0));
                    break;
                case "6"://возвращение darkred
                    status.setText("Возвращение");
                    status.setTextColor(Color.rgb(100, 0, 0));
                    break;

            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Zakazy zakazy = (Zakazy) _context;
                    Intent intent = new Intent(zakazy, Zakaz_info.class);
                    if(zakazCourier.getObrazec().contains("0")) intent.putExtra("o", "0");
                    else intent.putExtra("o", "1");
                    intent.putExtra("id", zakazCourier.getId_zakaz());
                    intent.putExtra("name", zakazCourier.getDate());
                    zakazy.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}


