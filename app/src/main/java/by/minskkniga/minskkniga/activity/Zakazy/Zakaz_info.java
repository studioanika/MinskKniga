package by.minskkniga.minskkniga.activity.Zakazy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.WhatZakazal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_info extends AppCompatActivity {

    String name;
    String id;
    TextView caption;
    ImageButton back;
    by.minskkniga.minskkniga.api.Class.Zakaz_info zakaz;

    TextView blank;
    TextView date1;
    TextView date2;
    TextView autor;
    TextView status;
    CheckBox chernovik;
    CheckBox oplacheno;
    TextView tv1;
    TextView tv2;

    Spinner courier;
    ArrayAdapter<String> adapter;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_info);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        caption = findViewById(R.id.caption);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        caption.setText(name);

        blank = findViewById(R.id.blank);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        autor = findViewById(R.id.autor);
        status = findViewById(R.id.status);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        lv = findViewById(R.id.lv);

        courier = findViewById(R.id.courier);

        chernovik = findViewById(R.id.chernovik);
        oplacheno = findViewById(R.id.oplacheno);

        zakaz = new by.minskkniga.minskkniga.api.Class.Zakaz_info();

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        reload();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload() {
        App.getApi().getZakaz_info(id).enqueue(new Callback<by.minskkniga.minskkniga.api.Class.Zakaz_info>() {
            @Override
            public void onResponse(Call<by.minskkniga.minskkniga.api.Class.Zakaz_info> call, Response<by.minskkniga.minskkniga.api.Class.Zakaz_info> response) {
                zakaz = response.body();
                blank.setText(zakaz.getId());
                date1.setText(zakaz.getDate());
                date2.setText(zakaz.getDate());
                autor.setText(zakaz.getAutor());

                double summa = 0;
                double ves = 0;
                for(int i = 0;i<zakaz.getWhatZakazal().size();i++){
                    summa += Double.parseDouble(zakaz.getWhatZakazal().get(i).getStoim());
                    ves+=Double.parseDouble(zakaz.getWhatZakazal().get(i).getVes());
                }

                tv1.setText("Итого "+zakaz.getWhatZakazal().size()+" позиция на "+summa+" BYN");
                tv2.setText("Вес: "+ves+" кг");

                oplacheno.setChecked(zakaz.getOplacheno().equals("1"));

                switch (zakaz.getStatus()) {
                    case "0"://chernovik новый  green
                        status.setText("Новый");
                        status.setBackgroundColor(Color.GREEN);
                        chernovik.setChecked(true);
                        break;
                    case "1"://новый  green
                        status.setText("Новый");
                        status.setBackgroundColor(Color.GREEN);
                        break;
                    case "2"://в сборке  yellow
                        status.setText("В сборке");
                        status.setBackgroundColor(Color.YELLOW);
                        break;
                    case "3"://собран  blue
                        status.setText("Собран");
                        status.setBackgroundColor(Color.BLUE);
                        break;
                    case "4"://в доставке   lightred
                        status.setText("В доставке");
                        status.setBackgroundColor(Color.rgb(242, 0, 86));
                        break;
                    case "5"://отгружен   darkred
                        status.setText("Отгружен");
                        status.setBackgroundColor(Color.rgb(255, 204, 203));
                        break;

                }
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, (ArrayList<WhatZakazal>) zakaz.getWhatZakazal()));
                load_couriers();
            }

            @Override
            public void onFailure(Call<by.minskkniga.minskkniga.api.Class.Zakaz_info> call, Throwable t) {

            }
        });
    }

    public void load_couriers(){
        App.getApi().getCouriers().enqueue(new Callback<List<Couriers>>() {
            @Override
            public void onResponse(Call<List<Couriers>> call, Response<List<Couriers>> response) {
                ArrayList<String> mass = new ArrayList();
                ArrayList<String> id = new ArrayList();
                for (int i = 0; i < response.body().size(); i++) {
                    mass.add(response.body().get(i).getName());
                    id.add(response.body().get(i).getId());
                }

                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, mass);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                courier.setAdapter(adapter);

                for (int i = 0; i < adapter.getCount(); i++) {
                    if (zakaz.getCourier().equals(id.get(i))) {
                        courier.setSelection(i);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Couriers>> call, Throwable t) {

            }
        });
    }
}