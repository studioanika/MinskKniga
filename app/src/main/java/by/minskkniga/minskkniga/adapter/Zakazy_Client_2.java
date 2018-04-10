package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.WhatZakazal;
import by.minskkniga.minskkniga.api.Zakazy;

public class Zakazy_Client_2 extends BaseExpandableListAdapter {

    private Context _context;
    ArrayList<Zakazy> _zakazy;

    public Zakazy_Client_2(Context context, ArrayList<Zakazy> zakazy) {
        this._context = context;
        this._zakazy = zakazy;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._zakazy.get(groupPosition).getWhatZakazal().get(childPosititon);
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
            convertView = infalInflater.inflate(R.layout.adapter_zakazy_client_2_podgroup, null);
        }



        TextView tv1 = convertView.findViewById(R.id.tv1);
        TextView tv2 = convertView.findViewById(R.id.tv2);
        TextView tv3 = convertView.findViewById(R.id.tv3);
        TextView tv4 = convertView.findViewById(R.id.tv4);
        TextView tv5 = convertView.findViewById(R.id.tv5);




        if (childPosition==0) {
            convertView.setBackgroundColor(Color.YELLOW);
            tv1.setText("Наим.");
            tv2.setText("Кл.");
            tv3.setText("Изд.");
            tv4.setText("Сокр.");
            tv5.setText("Заказ");
        }else{

            tv1.setText(_zakazy.get(groupPosition).getWhatZakazal().get(childPosition-1).getName());
            tv2.setText(_zakazy.get(groupPosition).getWhatZakazal().get(childPosition-1).getClass_());
            tv3.setText(_zakazy.get(groupPosition).getWhatZakazal().get(childPosition-1).getIzdanie().substring(0,1));
            tv4.setText(_zakazy.get(groupPosition).getWhatZakazal().get(childPosition-1).getSokr());
            tv5.setText(_zakazy.get(groupPosition).getWhatZakazal().get(childPosition-1).getIdZakaza());
        }




        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._zakazy.get(groupPosition).getWhatZakazal().size()+1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._zakazy.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._zakazy.size();
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
            convertView = infalInflater.inflate(R.layout.adapter_zakazy_client_2_group, null);
        }

        TextView tv1 = (TextView)convertView.findViewById(R.id.tv1);
        TextView tv2 = (TextView)convertView.findViewById(R.id.tv2);
        CheckBox ch1 = (CheckBox)convertView.findViewById(R.id.ch1);
        TextView tv3 = (TextView)convertView.findViewById(R.id.tv3);




        tv1.setText(_zakazy.get(groupPosition).getDate());
        tv2.setText(_zakazy.get(groupPosition).getStatus());

        if (_zakazy.get(groupPosition).getOplacheno()=="0"){
            ch1.setChecked(false);
        }else{
            ch1.setChecked(true);
        }

        tv3.setText(_zakazy.get(groupPosition).getSumma());

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
