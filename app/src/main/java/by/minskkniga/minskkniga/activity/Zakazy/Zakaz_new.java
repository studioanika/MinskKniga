package by.minskkniga.minskkniga.activity.Zakazy;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_new extends AppCompatActivity {

    ImageButton back;
    ImageButton menu;
    TextView caption;
    FloatingActionButton fab;

    DrawerLayout drawer;

    DialogFragment dlg_client;

    LinearLayout linear_zametka;
    LinearLayout linear_ok;

    TextView blank;
    TextView date1;
    TextView autor;
    Spinner courier;
    TextView status;
    int status_id;
    TextView date2;
    TextView zametka;

    Button ok;

    TextView tv1;
    TextView tv2;
    ListView lv;

    ArrayList<String> couriers;
    ArrayList<String> couriers_id;
    ArrayAdapter<String> adapter;

    SharedPreferences sp;
    String id_client;
    String admin_id;
    Date currentDate;
    SimpleDateFormat df;
    ArrayList<Zakaz_product> products;
    String product = "null";
    AlertDialog.Builder ad;
    TextView nav_sobrat;
    CheckBox chernovik;
    CheckBox oplacheno;

    public void initialize() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (products.isEmpty()) {
                    ad = new AlertDialog.Builder(Zakaz_new.this);
                    ad.setMessage("Не выбрано не одной позиции товара. Выйти без сохранения?");
                    ad.setPositiveButton("ПОДТВЕРДИТЬ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            onBackPressed();
                        }
                    });
                    ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            dialog.cancel();
                        }
                    });
                    ad.setCancelable(false);
                    ad.show();

                } else {
                    onBackPressed();
                }
            }
        });
        menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        caption = findViewById(R.id.caption);
        products = new ArrayList<>();
        lv = findViewById(R.id.lv);

        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(Zakaz_new.this, products));
//        View header = findViewById(R.layout.include_zakaz_new);
//        lv.addHeaderView(header);

        @SuppressLint("InflateParams") View header = getLayoutInflater().inflate(R.layout.include_zakaz_new, null);
        lv.addHeaderView(header);

        blank = header.findViewById(R.id.blank);
        date1 = header.findViewById(R.id.date1);
        autor = header.findViewById(R.id.autor);
        courier = header.findViewById(R.id.courier);
        status = header.findViewById(R.id.status);
        date2 = header.findViewById(R.id.date2);


        linear_zametka = header.findViewById(R.id.linear_zametka);
        zametka = header.findViewById(R.id.zametka);

        linear_ok = header.findViewById(R.id.linear_ok);
        ok = header.findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok();
            }
        });

        tv1 = header.findViewById(R.id.tv1);
        tv2 = header.findViewById(R.id.tv2);




        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    vibrator.vibrate(20);
                }
                ad = new AlertDialog.Builder(Zakaz_new.this);
                ad.setMessage("Удалить " + products.get(i-1).name + "?");
                ad.setPositiveButton("ПОДТВЕРДИТЬ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        products.remove(i-1);
                        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(Zakaz_new.this, products));
                        dialog.cancel();
                    }
                });
                ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
                ad.setCancelable(true);
                ad.show();
                return false;
            }
        });

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        couriers = new ArrayList<>();
        couriers_id = new ArrayList<>();


        dlg_client = new Add_Dialog(this, "zakaz_client");
        dlg_client.setCancelable(false);
        if (getIntent().getStringExtra("name_client").equals("null")){
            dlg_client.show(getFragmentManager(), "");
        }else{
            id_client = getIntent().getStringExtra("id_client");
            caption.setText(getIntent().getStringExtra("name_client"));
        }


        currentDate = new Date();

        df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());




        nav_sobrat = findViewById(R.id.nav_sobrat);

        nav_sobrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optimiz();
                Intent intent = new Intent(Zakaz_new.this, Sborka.class);
                intent.putExtra(Zakaz_product.class.getCanonicalName(), products);
                intent.putExtra("name", caption.getText());
                startActivityForResult(intent, 2);
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Zakaz_new.this, by.minskkniga.minskkniga.activity.Nomenklatura.Main.class);
                intent.putExtra("zakaz", true);
                intent.putExtra("id_client", id_client);
                startActivityForResult(intent, 1);
            }
        });

        chernovik = header.findViewById(R.id.chernovik);
        oplacheno= header.findViewById(R.id.oplacheno);

        caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg_client.show(getFragmentManager(), "");
            }
        });
    }

    public void optimiz(){
        ArrayList<Zakaz_product> buffer = new ArrayList<>(products);
        products.clear();


        for (int i=0; i < buffer.size(); i++) {
            for (int j = i+1; j < buffer.size(); j++) {
                if (buffer.get(i).artukil.equals(buffer.get(j).artukil)) {
                    buffer.get(i).col_zakaz = String.valueOf(Integer.parseInt(buffer.get(i).col_zakaz) + Integer.parseInt(buffer.get(j).col_zakaz));
                    buffer.get(i).col_podar = String.valueOf(Double.parseDouble(buffer.get(i).col_podar) + Double.parseDouble(buffer.get(j).col_podar));
                    buffer.get(i).otgruzeno = String.valueOf(Integer.parseInt(buffer.get(i).otgruzeno) + Integer.parseInt(buffer.get(j).otgruzeno));
                    buffer.get(j).artukil="";
                }
            }

            if (!buffer.get(i).artukil.equals("")) {
                buffer.get(i).summa = String.valueOf((Double.parseDouble(buffer.get(i).cena) * Double.parseDouble(buffer.get(i).col_zakaz)) - (Double.parseDouble(buffer.get(i).cena) * Double.parseDouble(buffer.get(i).col_podar)));
                products.add(buffer.get(i));
            }
        }

        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(Zakaz_new.this, products));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean sobr;
        Toast.makeText(this, products.size()+"", Toast.LENGTH_SHORT).show();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    products.addAll(data.<Zakaz_product>getParcelableArrayListExtra(Zakaz_product.class.getCanonicalName()));

                    for (int i=0;i<products.size();i++){
                        products.get(i).summa = String.valueOf(Double.parseDouble(products.get(i).cena) * Double.parseDouble(products.get(i).col_zakaz) - Double.parseDouble(products.get(i).cena) * Double.parseDouble(products.get(i).col_podar));
                    }

                    lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(this, products));

                    sobr = false;
                    for (Zakaz_product buf: products){
                        if (Integer.parseInt(buf.otgruzeno)>0)
                            sobr = true;
                    }

                    if (sobr){
                        status.setTextColor(Color.rgb(242, 201, 76));
                        status.setText("В сборке");
                        status_id = 2;
                    }else{
                        status.setTextColor(Color.rgb(97, 184, 126));
                        status.setText("Новый");
                        status_id=1;
                    }

                    try {
                        double summa = 0;
                        double ves = 0;
                        int col = 0;
                        for (Zakaz_product buffer : products) {
                            col++;
                            summa += Double.parseDouble(buffer.summa);
                            ves += Double.parseDouble(buffer.ves) * Double.parseDouble(buffer.col_zakaz);
                        }

                        tv1.setText(String.format("Итого %s позиций на %sBYN", String.valueOf(Math.round(col)), String.valueOf(Math.round(summa * 100.0) / 100.0)));
                        tv2.setText(String.format("Вес: %sкг", String.valueOf(Math.round(ves * 100.0) / 100.0)));
                    }catch (Exception e){
                        tv1.setText(String.format("Итого %s позиций на %sBYN", 0,0));
                        tv2.setText(String.format("Вес: %sкг", 0));
                    }

                    break;

                case 2:
                    products.clear();
                    products.addAll(data.<Zakaz_product>getParcelableArrayListExtra(Zakaz_product.class.getCanonicalName()));

                    for (int i=0;i<products.size();i++){
                        products.get(i).summa = String.valueOf(Double.parseDouble(products.get(i).cena) * Double.parseDouble(products.get(i).col_zakaz) - Double.parseDouble(products.get(i).cena) * Double.parseDouble(products.get(i).col_podar));
                    }

                    lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(this, products));


                    sobr = false;
                    for (Zakaz_product buf: products){
                        if (Integer.parseInt(buf.otgruzeno)>0)
                            sobr = true;
                    }

                    if (sobr){
                        status.setTextColor(Color.rgb(242, 201, 76));
                        status.setText("В сборке");
                        status_id=2;
                    }else{
                        status.setTextColor(Color.rgb(97, 184, 126));
                        status.setText("Новый");
                        status_id=1;
                    }


                    try {
                        double summa = 0;
                        double ves = 0;
                        int col = 0;
                        for (Zakaz_product buffer : products) {
                            col++;
                            summa += Double.parseDouble(buffer.summa);
                            ves += Double.parseDouble(buffer.ves) * Double.parseDouble(buffer.col_zakaz);
                        }

                        tv1.setText(String.format("Итого %s позиций на %sBYN", String.valueOf(Math.round(col)), String.valueOf(Math.round(summa * 100.0) / 100.0)));
                        tv2.setText(String.format("Вес: %sкг", String.valueOf(Math.round(ves * 100.0) / 100.0)));
                    }catch (Exception e){
                        tv1.setText(String.format("Итого %s позиций на %sBYN", 0, 0));
                        tv2.setText(String.format("Вес: %sкг", 0));
                    }

                    break;

            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_new);
        initialize();

        autor.setText(sp.getString("login", ""));

        admin_id = sp.getString("user_id", "");
        status.setTextColor(Color.rgb(97, 184, 126));
        status.setText("Новый");
        status_id = 1;
        date1.setText(df.format(currentDate));
        date2.setText(df.format(currentDate));


        load_couriers();
    }


    public void load_couriers(){
        App.getApi().getCouriers().enqueue(new Callback<List<Couriers>>() {
            @Override
            public void onResponse(Call<List<Couriers>> call, Response<List<Couriers>> response) {
                couriers.clear();
                couriers_id.clear();

                for(Couriers buffer : response.body()){
                    couriers.add(buffer.getName());
                    couriers_id.add(buffer.getId());
                }

                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, couriers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courier.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Couriers>> call, Throwable t) {
                Toast.makeText(Zakaz_new.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void return_client(String id, String name){
        id_client = id;
        caption.setText(name);
    }

    public void ok() {
        if (products.size() != 0) {
            product = products.get(0).id + "/~/" + products.get(0).col_zakaz + "/~/" + products.get(0).otgruzeno + "/~/" + products.get(0).col_podar + "/~/" + products.get(0).cena;
            for (int i = 1; i < products.size(); i++) {
                product = String.format("%s%s", product, "/~~/" + products.get(i).id + "/~/" + products.get(i).col_zakaz + "/~/" + products.get(i).otgruzeno + "/~/" + products.get(i).col_podar + "/~/" + products.get(i).cena);
            }
        }

        if (chernovik.isChecked()) status_id = 0;

        App.getApi().addZakaz(String.valueOf(
                date1.getText()),
                id_client,
                admin_id,
                String.valueOf(zametka.getText()),
                couriers_id.get(courier.getSelectedItemPosition()),
                String.valueOf(status_id),
                oplacheno.isChecked()?"1":"0",
                product).enqueue(new Callback<ResultBody>() {
            @Override
            public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                Toast.makeText(Zakaz_new.this, "Номер заказа: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResultBody> call, Throwable t) {

            }
        });
    }

}
