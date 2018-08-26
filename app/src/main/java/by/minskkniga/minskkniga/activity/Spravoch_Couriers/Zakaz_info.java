package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Zakazy.adapter.ZakazzyObrazacAdapet;
import by.minskkniga.minskkniga.adapter.Zakazy.ReturnToSclad;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.WhatZakazal;
import by.minskkniga.minskkniga.api.Class.Zakaz;
import by.minskkniga.minskkniga.api.Class.nomenclatura.ObjectZakazyObrazci;
import by.minskkniga.minskkniga.api.Class.zakazy.ObrazciComplect;
import by.minskkniga.minskkniga.api.Class.zakazy.ObrazciProduct;
import by.minskkniga.minskkniga.api.Class.zakazy.ZakazyObrazec;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_info extends AppCompatActivity {

    String status_general = "0";

    String id_cur = "";
    String name;
    String id, obr;
    TextView caption;
    ImageButton back;
    ImageButton menu;
    Zakaz zakaz;
    LinearLayout lin_itog, lin_head1, lin_head2;

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
    ExpandableListView exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_couriers_zakaz_info);

        back = findViewById(R.id.back);
        menu = findViewById(R.id.menu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id != null){

                    Intent intent = new Intent(Zakaz_info.this, ReturnToSclad.class);
                    intent.putExtra("id", id);
                    if(obr.contains("0")){
                        intent.putExtra("obrazec", "0");
                    }else{
                        intent.putExtra("obrazec", "1");
                    }
                    startActivity(intent);
                }
            }
        });

        caption = findViewById(R.id.caption);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        obr = getIntent().getStringExtra("o");
        caption.setText(name);
        lin_itog = (LinearLayout) findViewById(R.id.lin_itog);
        lin_head1 =  findViewById(R.id.lin_head1);
        lin_head2 =  findViewById(R.id.lin_head2);
        exp = findViewById(R.id.lv2);

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

                switch (status_general){
                    case "1":
                        if(obr.contains("0")) courier_zakazy_vzyac();
                        else courier_obrazcy_vzyac();
                        break;
                    case "3":
                        if(obr.contains("0")) courier_zakazy_vzyac();
                        else courier_obrazcy_vzyac();
                        break;
                    case "4":
                        if(obr.contains("0"))courier_zakazy_vipolneno();
                        else courier_obrazcy_vipolneno();
                        break;
                    case "5" :
                        if(obr.contains("0")){}
                        else {}
                        break;
                }

//                Toast.makeText(Zakaz_info.this, id, Toast.LENGTH_SHORT).show();
//                App.getApi().setComplete_zakaz(id).enqueue(new Callback<ResultBody>() {
//                    @Override
//                    public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
//                        onBackPressed();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResultBody> call, Throwable t) {
//                        Toast.makeText(Zakaz_info.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

       if(obr.contains("0")){
           zakaz = new Zakaz();
           reload();
       }else{
           loadComplects();
       }

    }

    private void loadComplects() {

        App.getApi().getZakazObrazciId(id, "1").enqueue(new Callback<ZakazyObrazec>() {
            @Override
            public void onResponse(Call<ZakazyObrazec> call, Response<ZakazyObrazec> response) {

                if(response.body() != null) {

                    ZakazyObrazec obrazec = response.body();

                    blank.setText(obrazec.getId());
                    date1.setText(obrazec.getDate());
                    autor.setText(obrazec.getAutor());
                    id_cur = obrazec.getCourier();
                    zametka.setText(obrazec.getZametka());
                    status_general = obrazec.getStatus();
                    switch (obrazec.getStatus()) {
                        case "0"://chernovik новый green
                            status.setText("Черновик");
                            status.setTextColor(Color.rgb(97, 184, 126));

                            break;
                        case "1"://новый green
                            status.setText("Новый");
                            status.setTextColor(Color.rgb(97, 184, 126));
                            ok.setText("Взять книги");
                            ok.setVisibility(View.VISIBLE);
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
                            ok.setVisibility(View.VISIBLE);
                            ok.setText("Выполнено");
                            break;
                        case "5"://отгружен darkred
                            status.setText("Отгружен");
                            status.setTextColor(Color.rgb(139, 0, 0));
                            //ok.setText("Вернуть");
                            break;
                        case "6"://возвращение darkred
                            status.setText("Возвращение");
                            status.setTextColor(Color.rgb(100, 0, 0));
                            break;

                    }
                    tv1.setText(String.format("Итого %s позиций на %sBYN", obrazec.getKol(),"0"));
                    tv2.setText(String.format("Вес: %sкг", obrazec.getVes()));

                    forAdaperObrazci(obrazec.getWhat_zakazal());

                }

            }

            @Override
            public void onFailure(Call<ZakazyObrazec> call, Throwable t) {

            }
        });
    }
    private void forAdaperObrazci(List<ObrazciComplect> what_zakazal) {

        List<ObjectZakazyObrazci> list = new ArrayList<>();

        for (ObrazciComplect obr: what_zakazal
                ) {

            ObjectZakazyObrazci objectZakazyObrazci = new ObjectZakazyObrazci();
            objectZakazyObrazci.setAdmin_id("");
            objectZakazyObrazci.setName(obr.getKomplect_name());
            objectZakazyObrazci.setCena(Double.valueOf(obr.getKomplect_cena()));

            double ves = 0.0;
            List<Product> products = new ArrayList<>();

            for (ObrazciProduct pr: obr.getKomplect_list()
                    ) {
                try {
                    ves = Double.parseDouble(ves + pr.getVes());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                Product product = new Product();
                product.setName(pr.getName());
                product.setProdCena(pr.getProdCena());
                product.setVes(pr.getVes());

                products.add(product);
            }

            objectZakazyObrazci.setVes(ves);
            objectZakazyObrazci.setList(products);

            list.add(objectZakazyObrazci);
        }

        setAdapterKomplents(list);

    }
    private void setAdapterKomplents(List<ObjectZakazyObrazci> obrazciList) {

        exp.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);
        lin_itog.setVisibility(View.GONE);
        lin_head1.setVisibility(View.GONE);
        lin_head2.setVisibility(View.VISIBLE);

        exp.setAdapter(new ZakazzyObrazacAdapet(obrazciList, this));

        load_couriers();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if(obr.contains("0")){
                zakaz = new Zakaz();
                reload();
            }else{
                loadComplects();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                status_general = zakaz.getStatus();
                switch (zakaz.getStatus()) {
                    case "0"://chernovik новый  green
                        status.setText("Новый");
                        status.setTextColor(Color.rgb(97, 184, 126));

                        break;
                    case "1"://новый  green
                        status.setText("Новый");
                        status.setTextColor(Color.rgb(97, 184, 126));
                        ok.setText("Взять книги");
                        break;
                    case "2"://в сборке  yellow
                        status.setText("В сборке");
                        status.setTextColor(Color.rgb(242, 201, 76));
                        break;
                    case "3"://собран  blue
                        status.setText("Собран");
                        status.setTextColor(Color.BLUE);
                        ok.setText("Взять книги");
                        ok.setVisibility(View.VISIBLE);
                        break;
                    case "4"://в доставке   lightred
                        status.setText("В доставке");
                        status.setTextColor(Color.rgb(242, 0, 86));
                        ok.setVisibility(View.VISIBLE);
                        ok.setText("Выполнено");
                        break;
                    case "5"://отгружен   darkred
                        status.setText("Отгружен");
                        status.setTextColor(Color.rgb(139, 0, 0));
                        //ok.setText("Вернуть");
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
                    if(zakaz != null){
                        if (zakaz.getCourier().equals(id.get(i))) {
                            courier.setSelection(i);
                        }
                    }else {
                        if (id_cur.equals(id.get(i))) {
                            courier.setSelection(i);
                        }
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

    public void courier_obrazcy_vzyac(){

        Map<String,  String> map = new HashMap<>();
        map.put("id", id);

        App.getApi().courier_obrazcy_vzyac(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("ok"))
                        loadComplects();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Zakaz_info.this, "Ошибка....",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void courier_obrazcy_vipolneno(){
        Map<String,  String> map = new HashMap<>();
        map.put("id", id);

        App.getApi().courier_obrazcy_vipolneno(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("ok"))
                        onBackPressed();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Zakaz_info.this, "Ошибка....",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void courier_zakazy_vzyac(){

        Map<String,  String> map = new HashMap<>();
        map.put("id", id);

        App.getApi().courier_zakazy_vzyac(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("ok"))
                        reload();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Zakaz_info.this, "Ошибка....",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void courier_zakazy_vipolneno(){
        Map<String,  String> map = new HashMap<>();
        map.put("id", id);

        App.getApi().courier_zakazy_vipolneno(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("ok"))
                       onBackPressed();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Zakaz_info.this, "Ошибка....",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
