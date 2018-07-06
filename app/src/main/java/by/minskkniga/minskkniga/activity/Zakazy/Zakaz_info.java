package by.minskkniga.minskkniga.activity.Zakazy;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.WhatZakazal;
import by.minskkniga.minskkniga.api.Class.Zakaz;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_info extends AppCompatActivity {

    String name;
    String id;
    TextView caption;
    ImageButton back;
    Zakaz zakaz;

    TextView blank;
    TextView date1;
    TextView date2;
    TextView autor;
    TextView status;
    CheckBox chernovik;
    CheckBox oplacheno;
    TextView tv1;
    TextView tv2;
    TextView courier;

    ListView lv;

    // TODO добавить деньги в заказы

    public void initialize(){

        caption = findViewById(R.id.caption);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        caption.setText(name);

        lv = findViewById(R.id.lv);
        @SuppressLint("InflateParams") View header = getLayoutInflater().inflate(R.layout.include_zakaz_info, null);
        lv.addHeaderView(header);

        blank = header.findViewById(R.id.blank);
        date1 = header.findViewById(R.id.date1);
        date2 = header.findViewById(R.id.date2);
        autor = header.findViewById(R.id.autor);
        status = header.findViewById(R.id.status);
        tv1 = header.findViewById(R.id.tv1);
        tv2 = header.findViewById(R.id.tv2);


        courier = header.findViewById(R.id.courier);

        chernovik = header.findViewById(R.id.chernovik);
        oplacheno = header.findViewById(R.id.oplacheno);

        zakaz = new Zakaz();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_info);
        initialize();
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    double summa = 0;
    double ves = 0;
    int col = 0;

    public void reload() {
        App.getApi().getZakaz_info(id).enqueue(new Callback<Zakaz>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<Zakaz> call, Response<Zakaz> response) {
                zakaz = response.body();
                blank.setText(zakaz.getId());
                date1.setText(zakaz.getDate());
                date2.setText(zakaz.getDateIzm());
                autor.setText(zakaz.getAutor());
                oplacheno.setChecked(zakaz.getOplacheno().equals("1"));
                courier.setText(response.body().getCourier());


                summa = 0;
                ves = 0;
                col = 0;
                for (WhatZakazal buffer : response.body().getWhatZakazal()) {
                    col++;
                    summa += Double.parseDouble(buffer.getCena().equals("")?"0":buffer.getCena()) * Double.parseDouble(buffer.getZakazano());
                    ves += Double.parseDouble(buffer.getVes().equals("")?"0":buffer.getVes()) * Double.parseDouble(buffer.getZakazano());
                }

                tv1.setText(String.format("Итого %s позиций на %sBYN", col, Math.round(summa * 100.0) / 100.0));
                tv2.setText(String.format("Вес: %sкг", Math.round(ves * 100.0) / 100.0));



                switch (zakaz.getStatus()) {
                    case "0"://chernovik новый green
                        status.setText("Новый");
                        status.setTextColor(Color.rgb(97, 184, 126));
                        chernovik.setChecked(true);
                        break;
                    case "1"://новый green
                        status.setText("Новый");
                        status.setTextColor(Color.rgb(97, 184, 126));
                        break;
                    case "2"://в сборке yellow
                        status.setText("В сборке");
                        status.setTextColor(Color.rgb(242, 201, 76));
                        break;
                    case "3"://собран blue
                        status.setText("Собран");
                        status.setTextColor(Color.BLUE);
                        break;
                    case "4"://в доставке lightred
                        status.setText("В доставке");
                        status.setTextColor(Color.rgb(242, 0, 86));
                        break;
                    case "5"://отгружен darkred
                        status.setText("Отгружен");
                        status.setTextColor(Color.rgb(139, 0, 0));
                        break;
                    case "6"://возвращение darkred
                        status.setText("Возвращение");
                        status.setTextColor(Color.rgb(100, 0, 0));
                        break;

                }


                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, response.body().getWhatZakazal()));
                //lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, (ArrayList<WhatZakazal>) zakaz.getWhatZakazal()));
            }

            @Override
            public void onFailure(Call<Zakaz> call, Throwable t) {

            }
        });
    }

}