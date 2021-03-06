package by.minskkniga.minskkniga.adapter.Zakazy;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Zakazy.Zakazy_Client;
import by.minskkniga.minskkniga.api.Class.Zakazy;

public class Zakazy_2 extends BaseExpandableListAdapter {

    private Context _context;
    ArrayList<Zakazy> _zakazy;
    AlertDialog.Builder ad;
    Zakazy_Client client;

    public Zakazy_2(Context context, ArrayList<Zakazy> zakazy) {
        this._context = context;
        this._zakazy = zakazy;
        client = (Zakazy_Client) context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._zakazy.get(groupPosition).getClassWhatZakazal().get(childPosititon-1);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition-1;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
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

        if (childPosition == 0) {
            convertView.setBackgroundColor(Color.LTGRAY);
            tv1.setText("Наим.");
            tv2.setText("Кл.");
            tv3.setText("Изд.");
            tv4.setText("Сокр.");
            tv5.setText("Заказ");
        } else {
            try {
                convertView.setBackgroundColor(Color.WHITE);
                tv1.setText(_zakazy.get(groupPosition).getClassWhatZakazal().get(childPosition - 1).getName());
                tv2.setText(_zakazy.get(groupPosition).getClassWhatZakazal().get(childPosition - 1).getClas());
                tv3.setText(_zakazy.get(groupPosition).getClassWhatZakazal().get(childPosition - 1).getIzdatel().substring(0, 1));
                tv4.setText(_zakazy.get(groupPosition).getClassWhatZakazal().get(childPosition - 1).getSokrName());
                tv5.setText(_zakazy.get(groupPosition).getClassWhatZakazal().get(childPosition - 1).getZakazano());
            }catch (Exception e){
                tv1.setText("Пустой заказ");
                tv2.setText("");
                tv3.setText("");
                tv4.setText("");
                tv5.setText("");
            }

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (_zakazy.get(groupPosition).getClassWhatZakazal().size()==0){
            return this._zakazy.get(groupPosition).getClassWhatZakazal().size() + 2;
        }else{
            return this._zakazy.get(groupPosition).getClassWhatZakazal().size() + 1;
        }
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
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_zakazy_client_2_group, null);
        }

        RelativeLayout rel = convertView.findViewById(R.id.rel);
        TextView tv1 = convertView.findViewById(R.id.tv1);
        TextView tv2 = convertView.findViewById(R.id.tv2);
        final CheckBox ch1 = convertView.findViewById(R.id.ch1);
        final ImageView ch2 = convertView.findViewById(R.id.ch2);
        TextView tv3 = convertView.findViewById(R.id.tv3);
        ImageView info = convertView.findViewById(R.id.info);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Zakazy_Client)_context).expanded(groupPosition);
            }
        });

        tv1.setText(_zakazy.get(groupPosition).getDate());

        switch (_zakazy.get(groupPosition).getStatus()) {
            case "0":
                //tv1.setTextColor(Color.GREEN);
                tv2.setText("Черновик");
                break;
            case "1":
                //tv1.setTextColor(Color.GREEN);
                tv2.setTextColor(Color.rgb(97, 184, 126));
                if(!_zakazy.get(groupPosition).getType().equals("vozvrat"))tv2.setText("Новый");
                else tv2.setText("К возврату");
                break;
            case "2":
                tv2.setTextColor(Color.rgb(242, 201, 76));
                tv2.setText("В сборке");
                break;
            case "3":
                tv2.setTextColor(Color.BLUE);
                tv2.setText("Собран");
                break;
            case "4":
                tv2.setTextColor(Color.rgb(242, 0, 86));
                tv2.setText("В доставке");
                break;
            case "5":
                tv2.setTextColor(Color.rgb(139, 0, 0));
                tv2.setText("Отгружен");
                break;
            case "6"://возвращение darkred
                tv2.setText("Возвращение");
                tv2.setTextColor(Color.rgb(100, 0, 0));
                break;
        }

        if (_zakazy.get(groupPosition).getOplacheno().equals("0")) {
            ch1.setVisibility(View.GONE);
            ch2.setVisibility(View.VISIBLE);
            ch1.setChecked(false);
            ch1.setEnabled(true);
            ch2.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_check_1));
        } else {
            ch2.setVisibility(View.VISIBLE);
            ch1.setVisibility(View.GONE);
            ch2.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_check_2));
            ch1.setChecked(true);
            ch1.setEnabled(false);
        }

        tv3.setText(_zakazy.get(groupPosition).getSumma());

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.startNew(groupPosition);
            }
        });
//        ch1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ch1.isEnabled()) {
//                    ad = new AlertDialog.Builder(_context);
//                    ad.setTitle("Вы уверены, что хотите изменить статус оплаты?");
//                    ad.setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int arg1) {
//
//                            App.getApi().setOplata(_zakazy.get(groupPosition).getId()).enqueue(new Callback<ResultBody>() {
//                                @Override
//                                public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
//                                    _zakazy.get(groupPosition).setOplacheno("1");
//                                    ch1.setEnabled(false);
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResultBody> call, Throwable t) {
//
//                                }
//                            });
//                        }
//
//                    });
//                    ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int arg1) {
//                            dialog.cancel();
//                            ch1.setChecked(false);
//                        }
//                    });
//                    ad.setCancelable(false);
//                    ad.show();
//                }
//            }
//        });
         ch1.setEnabled(false);

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
