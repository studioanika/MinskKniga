package by.minskkniga.minskkniga.activity.providers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.ProviderZayavkiListActivity;
import by.minskkniga.minskkniga.api.Class.providers.Book;
import by.minskkniga.minskkniga.api.Class.providers.ProvidersZayavkiId;

/**
 * Created by root on 14.4.18.
 */


public class ProvidersZayavkiAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ProvidersZayavkiId> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Book>> _listDataChild;

    public ProvidersZayavkiAdapter(Context context, List<ProvidersZayavkiId> listDataHeader,
                                 HashMap<String, List<Book>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        String s ="";
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition).getWhatZakazal().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        final Book book =  _listDataChild.get(String.valueOf(groupPosition)).get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_list_providers_zaayavki, null);
        }
        TextView txtName = (TextView) convertView
                .findViewById(R.id.item_list_providers_zayavki_name);

        TextView txtClass = (TextView) convertView
                .findViewById(R.id.item_list_providers_zayavki_class);

        TextView txtIzdanie = (TextView) convertView
                .findViewById(R.id.item_list_providers_zayavki_izdanie);

        TextView txtSokr = (TextView) convertView
                .findViewById(R.id.item_list_providers_zayavki_sokr);

        TextView txtZakaz = (TextView) convertView
                .findViewById(R.id.item_list_providers_zayavki_zakaz);
        LinearLayout lin = (LinearLayout) convertView
                .findViewById(R.id.lin);
        if(childPosition != 0){
            try{
                txtName.setText(book.getName());
                txtClass.setText(book.getClasss());
                txtIzdanie.setText(book.getIzdatel().substring(0,1));
                txtSokr.setText(book.getSokrName());
                txtZakaz.setText(_listDataHeader.get(groupPosition).getWhatZakazal()
                        .get(childPosition).getZav());


            }
            catch (Exception e){}
        }



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(_listDataHeader.get(groupPosition).getWhatZakazal() != null) {
           return  _listDataHeader.get(groupPosition).getWhatZakazal().size()+1;
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getDate();
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
            convertView = infalInflater.inflate(R.layout.item_list_group_providers_zayavki, null);
        }

        ImageView imgNext = (ImageView) convertView.findViewById(R.id.item_list_group_providers_zayavki_next);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProviderZayavkiListActivity activity = (ProviderZayavkiListActivity) _context;
                activity.startNextAct(_listDataHeader.get(groupPosition).getId());
            }
        });

        TextView txtDate = (TextView) convertView
                .findViewById(R.id.item_list_group_providers_zayavki_date);

        TextView txtStatus = (TextView) convertView
                .findViewById(R.id.item_list_group_providers_zayavki_status);

        TextView txtSumma = (TextView) convertView
                .findViewById(R.id.item_list_group_providers_zayavki_summa);

        CheckBox check = (CheckBox) convertView
                .findViewById(R.id.item_list_group_providers_zayavki_check);

        ImageView imgInfo = (ImageView) convertView
                .findViewById(R.id.item_list_group_providers_zayavki_info);

        try {
            txtDate.setText(_listDataHeader.get(groupPosition).getDate());
            //txtStatus.setText(String.valueOf(_listDataHeader.get(groupPosition).getStatus()));
            txtSumma.setText(String.valueOf(_listDataHeader.get(groupPosition).getSumma()));

            int opl = Integer.parseInt(_listDataHeader.get(groupPosition).getOpl());
            int staus = Integer.parseInt(_listDataHeader.get(groupPosition).getStatus());

            if(staus == 0) txtStatus.setText("Черновик");
            else if(staus == 1) txtStatus.setText("Не обработан");
            else if(staus == 2) txtStatus.setText("Оприходован");
            else if(staus == 3) txtStatus.setText("Ожидаем");
            else if(staus == 4) txtStatus.setText("Отменен");

            if(opl == 0) check.setChecked(false);
            else check.setChecked(true);
            check.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();

        }


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
