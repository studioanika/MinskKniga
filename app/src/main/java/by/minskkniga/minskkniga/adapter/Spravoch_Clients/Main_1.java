package by.minskkniga.minskkniga.adapter.Spravoch_Clients;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import by.minskkniga.minskkniga.R;

public class Main_1 extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<String> _listDataHeader;
    private ArrayList<ArrayList<String>> _listDataChild;

    public Main_1(Context context, ArrayList<String> listDataHeader, ArrayList<ArrayList<String>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_spravoch_client_1_podgroup, null);
        }

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
        CheckBox ch1 = (CheckBox) convertView.findViewById(R.id.ch1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);


        String[] buffer = childText.split("@");
        tv1.setText(buffer[0]);
        if (buffer[1].equals("1")){
            ch1.setChecked(true);
        }else{
            ch1.setChecked(false);
        }
        try {
            if (Double.parseDouble(buffer[2])<0){
                tv2.setTextColor(Color.RED);
            }else{
                tv2.setTextColor(Color.BLACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tv2.setText(buffer[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(groupPosition).size();
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_spravoch_client_1_group, null);
        }

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
        CheckBox ch1 = (CheckBox) convertView.findViewById(R.id.ch1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);


        tv1.setTypeface(null, Typeface.BOLD);
        tv2.setTypeface(null, Typeface.BOLD);

        String[] buffer = headerTitle.split("@");
        tv1.setText(buffer[0]);
        if (buffer[1].equals("1")){
            ch1.setChecked(true);
        }else{
            ch1.setChecked(false);
        }
        if (Double.parseDouble(buffer[2])<0){
            tv2.setTextColor(Color.RED);
        }else{
            tv2.setTextColor(Color.BLACK);
        }
        tv2.setText(buffer[2]);

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