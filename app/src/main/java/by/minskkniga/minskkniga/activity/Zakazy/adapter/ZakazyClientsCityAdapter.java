package by.minskkniga.minskkniga.activity.Zakazy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Zakazy.Main;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.zakazy.ClientsCity;
import by.minskkniga.minskkniga.api.Class.zakazy.ZakazyCity;

public class ZakazyClientsCityAdapter extends BaseExpandableListAdapter {

    private Context _context;
    Map<String, ZakazyCity> l;
    private List<ClientsCity> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<ZakazyCity, List<Clients>> _listDataChild;

    public ZakazyClientsCityAdapter(Context context, List<ClientsCity> listDataHeader,
                                    HashMap<ZakazyCity, List<Clients>> listChildData,
                                    Map<String, ZakazyCity> l) {

        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.l = l;
        String s ="";

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition).getList_city().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {




        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_zakazy_2_1, null);
        }

        //ImageView imgNext = (ImageView) convertView.findViewById(R.id.item_list_group_providers_zayavki_next);

        try{

            final Clients client =  _listDataChild.get(l.get(String.valueOf(groupPosition))).get(childPosition);
            TextView tv1 = convertView.findViewById(R.id.tv1);
            TextView tv2 = convertView.findViewById(R.id.tv2);
            CheckBox ch1 = convertView.findViewById(R.id.ch1);

            int obr = Integer.parseInt(_listDataHeader.get(groupPosition).getObrazec());

            if(obr == 1) ch1.setChecked(true);
            else ch1.setChecked(false);

            tv1.setText(client.getName());
            tv2.setText(client.getDolg());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Main main = (Main) _context;
                    main.actStart(Integer.parseInt(client.getId()), client.getName());
                }
            });

        }
        catch (Exception e){
            String DS = e.toString();
        }



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(_listDataHeader.get(groupPosition).getList_city() != null) {
            return  _listDataHeader.get(groupPosition).getList_city().size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_zakazy_2, null);
        }

        //ImageView imgNext = (ImageView) convertView.findViewById(R.id.item_list_group_providers_zayavki_next);
        TextView tv1 = convertView.findViewById(R.id.tv1);
        TextView tv2 = convertView.findViewById(R.id.tv2);
        CheckBox ch1 = convertView.findViewById(R.id.ch1);

        tv1.setText(_listDataHeader.get(groupPosition).getName_city());
        tv2.setText(_listDataHeader.get(groupPosition).getDolg());
        int obr = Integer.parseInt(_listDataHeader.get(groupPosition).getObrazec());

        if(obr == 1) ch1.setChecked(true);
        else ch1.setChecked(false);

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
