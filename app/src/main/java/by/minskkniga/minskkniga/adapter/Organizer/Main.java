package by.minskkniga.minskkniga.adapter.Organizer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Organizer.Add;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Organizer;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Organizer> _objects;
    private AlertDialog.Builder ad;
    private int id;

    public Main(Context context, ArrayList<Organizer> objects, int id) {
        this.id = id;
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

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_organizer, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        final CheckBox ch1 = view.findViewById(R.id.ch1);
        LinearLayout ll1 = view.findViewById(R.id.ll1);

        tv1.setText(_objects.get(position).getDate());
        tv2.setText(_objects.get(position).getAutorName());
        tv3.setText(_objects.get(position).getCountragentName());

        ch1.setChecked(_objects.get(position).getStatus().equals("ok"));
        if (id==1)
            ch1.setEnabled(!_objects.get(position).getStatus().equals("ok"));
        if (id==2)
            ch1.setEnabled(false);


        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch1.isEnabled()) {
                    ad = new AlertDialog.Builder(_context);
                    ad.setTitle("Подтверждение выполнения поручения");
                    ad.setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                            App.getApi().setOrganizer_ok(_objects.get(position).getId()).enqueue(new Callback<ResultBody>() {
                                @Override
                                public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                                    _objects.get(position).setStatus("ok");
                                    ch1.setEnabled(false);
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
                            ch1.setChecked(false);
                        }
                    });
                    ad.setCancelable(false);
                    ad.show();
                }
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, Add.class);
                intent.putExtra("tab", id+"");
                intent.putExtra("id", _objects.get(position).getId());
                intent.putExtra("contragent_id", _objects.get(position).getCountragentId());
                intent.putExtra("autor_id", _objects.get(position).getAutorId());
                intent.putExtra("autor_name", _objects.get(position).getAutorName());
                intent.putExtra("ispolnitel", _objects.get(position).getIspolnitelId());
                intent.putExtra("date", _objects.get(position).getDate());
                intent.putExtra("status", _objects.get(position).getStatus());
                intent.putExtra("text", _objects.get(position).getText());
                _context.startActivity(intent);
            }
        });

        return view;
    }
}
