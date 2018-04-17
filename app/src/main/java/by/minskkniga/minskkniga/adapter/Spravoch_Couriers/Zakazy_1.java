package by.minskkniga.minskkniga.adapter.Spravoch_Couriers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_knigi;

public class Zakazy_1 extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<Zakazy_courier_knigi> _object;


    public Zakazy_1(Context context, ArrayList<Zakazy_courier_knigi> object) {
        this._context = context;
        this._object = object;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._object.get(groupPosition).getKnigi().size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_spravoch_courier_1_podgroup, null);
        }

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);

        tv1.setText(_object.get(groupPosition).getKnigi().get(childPosition).getName());
        tv2.setText(_object.get(groupPosition).getKnigi().get(childPosition).getOtgruz());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._object.get(groupPosition).getKnigi().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._object.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._object.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_spravoch_courier_1_group, null);
        }

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);

        tv1.setText(_object.get(groupPosition).getName());
        tv2.setText(_object.get(groupPosition).getOtgruz());


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
