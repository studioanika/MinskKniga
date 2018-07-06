package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.ResultBody;
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
    TextView tv1;
    TextView tv2;
    EditText zametka;
    Button ok;
    Spinner courier;
    ArrayAdapter<String> adapter;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_couriers_zakaz_info);

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
        zametka = findViewById(R.id.zametka);
        zametka.setEnabled(false);

        ok = findViewById(R.id.ok);
        ok.setVisibility(View.GONE);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Zakaz_info.this, id, Toast.LENGTH_SHORT).show();
                App.getApi().setComplete_zakaz(id).enqueue(new Callback<ResultBody>() {
                    @Override
                    public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ResultBody> call, Throwable t) {
                        Toast.makeText(Zakaz_info.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        zakaz = new Zakaz();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload() {
        App.getApi().getZakaz_info(id).enqueue(new Callback<Zakaz>() {
            @Override
            public void onResponse(Call<Zakaz> call, Response<Zakaz> response) {
                zakaz = response.body();
                blank.setText(zakaz.getId());
                date1.setText(zakaz.getDate());
                date2.setText(zakaz.getDate());
                autor.setText(zakaz.getAutor());
                zametka.setText(zakaz.getZametka());

                double summa = 0;
                double ves = 0;
//                for(int i = 0;i<zakaz.getWhatZakazal().size();i++){
//                    summa += Double.parseDouble(zakaz.getWhatZakazal().get(i).getStoim());
//                    ves+=Double.parseDouble(zakaz.getWhatZakazal().get(i).getVes())* Double.parseDouble(zakaz.getWhatZakazal().get(i).getZak());
//                }

                tv1.setText("Итого " + zakaz.getWhatZakazal().size() + " позиция на " + summa + " BYN");
                tv2.setText("Вес: " + ves + " кг");


                switch (zakaz.getStatus()) {
                    case "0"://chernovik новый  green
                        status.setText("Новый");
                        status.setTextColor(Color.rgb(97, 184, 126));
                        break;
                    case "1"://новый  green
                        status.setText("Новый");
                        status.setTextColor(Color.rgb(97, 184, 126));
                        break;
                    case "2"://в сборке  yellow
                        status.setText("В сборке");
                        status.setTextColor(Color.rgb(242, 201, 76));
                        break;
                    case "3"://собран  blue
                        status.setText("Собран");
                        status.setTextColor(Color.BLUE);
                        break;
                    case "4"://в доставке   lightred
                        status.setText("В доставке");
                        status.setTextColor(Color.rgb(242, 0, 86));
                        ok.setVisibility(View.VISIBLE);
                        break;
                    case "5"://отгружен   darkred
                        status.setText("Отгружен");
                        status.setTextColor(Color.rgb(139, 0, 0));
                        break;

                }
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, (ArrayList<WhatZakazal>) zakaz.getWhatZakazal()));
                setListViewBase(lv);
                load_couriers();
            }

            @Override
            public void onFailure(Call<Zakaz> call, Throwable t) {

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

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, mass);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

//    public  void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }

    private void setListViewBase(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();

        if(listAdapter == null) return;

        int totalHeight = 0;

        for ( int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0 ,0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() -1));
        listView.setLayoutParams(params);
    }
}
