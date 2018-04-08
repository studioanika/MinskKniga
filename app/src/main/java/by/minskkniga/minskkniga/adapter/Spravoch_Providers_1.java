package by.minskkniga.minskkniga.adapter;

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

public class Spravoch_Providers_1 extends BaseExpandableListAdapter {

    private Context _context;

    private ArrayList<String> _listDataHeader;

    private ArrayList<ArrayList<String>> _listDataChild;


    public Spravoch_Providers_1(Context context, ArrayList<String> listDataHeader, ArrayList<ArrayList<String>> listDataChild) {
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
            convertView = infalInflater.inflate(R.layout.adapter_spravoch_provider_1_podgroup, null);
        }

        TextView podgroup_1 = (TextView) convertView.findViewById(R.id.tv_pod_1);
        TextView podgroup_2 = (TextView) convertView.findViewById(R.id.tv_pod_2);


        String[] buffer = childText.split("@");
        podgroup_1.setText(buffer[0]);
        if (Double.parseDouble(buffer[1]) < 0) {
            podgroup_2.setTextColor(Color.RED);
        } else {
            podgroup_2.setTextColor(Color.BLACK);
        }
        podgroup_2.setText(buffer[1]);

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
            convertView = infalInflater.inflate(R.layout.adapter_spravoch_provider_1_group, null);
        }

        TextView group_1 = (TextView) convertView.findViewById(R.id.expanded_group_1);
        TextView group_2 = (TextView) convertView.findViewById(R.id.expanded_group_2);


        group_1.setTypeface(null, Typeface.BOLD);
        group_2.setTypeface(null, Typeface.BOLD);

        String[] buffer = headerTitle.split("@");
        group_1.setText(buffer[0]);
        if (Double.parseDouble(buffer[1]) < 0) {
            group_2.setTextColor(Color.RED);
        } else {
            group_2.setTextColor(Color.BLACK);
        }
        group_2.setText(buffer[1]);

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