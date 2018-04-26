package by.minskkniga.minskkniga.adapter.Spravoch_Couriers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Spravoch_Couriers.Zakaz_info;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_clients;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakazy_2 extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Zakazy_courier_clients> _objects;
    private AlertDialog.Builder ad;

    public Zakazy_2(Context context, ArrayList<Zakazy_courier_clients> objects) {
        this._context = context;
        this._objects = objects;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_spravoch_courier_2, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        final CheckBox ch1 = view.findViewById(R.id.ch1);
        final CheckBox ch2 = view.findViewById(R.id.ch2);
        LinearLayout iv1 = view.findViewById(R.id.icon);

        tv1.setText(_objects.get(position).getName());
        tv2.setText(_objects.get(position).getSumma());
        tv3.setText(_objects.get(position).getKoment());
        tv4.setText(_objects.get(position).getInfo());

        if (_objects.get(position).getStatus().equals("5")) {//выполнено книг на руках нет
            ch1.setChecked(true);
            ch2.setEnabled(false);
        } else {
            ch1.setChecked(false);
        }

        if (_objects.get(position).getStatus().equals("4")) {//собран книги на руках
            ch2.setChecked(true);
            ch2.setEnabled(false);
        } else {
            ch2.setChecked(false);
        }


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, Zakaz_info.class);
                intent.putExtra("id", _objects.get(position).getId());
                intent.putExtra("name", _objects.get(position).getName());
                _context.startActivity(intent);
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch2.isEnabled()) {
                    ad = new AlertDialog.Builder(_context);
                    ad.setTitle("Взять книги?");
                    ad.setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                            App.getApi().addKnigi(_objects.get(position).getId()).enqueue(new Callback<ResultBody>() {
                                @Override
                                public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                                    _objects.get(position).setStatus("ok");
                                    ch2.setEnabled(false);
                                }

                                @Override
                                public void onFailure(Call<ResultBody> call, Throwable t) {

                                }
                            });
                        }

                    });
                    ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            dialog.cancel();
                            ch2.setChecked(false);
                        }
                    });
                    ad.setCancelable(false);
                    ad.show();
                }
            }
        });
        return view;
    }
}
