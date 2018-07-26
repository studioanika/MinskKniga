package by.minskkniga.minskkniga.activity.Zakazy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import by.minskkniga.minskkniga.adapter.Dialog.Main;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.Dialog_clients;
import by.minskkniga.minskkniga.api.Class.Gorod;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.WhatZakazal;
import by.minskkniga.minskkniga.api.Class.Zakaz;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_new extends AppCompatActivity {

    private String ispodarki;
    private String const_podar;

    ImageButton back;
    ImageButton menu;
    TextView caption;
    FloatingActionButton fab;

    DrawerLayout drawer;

    DialogFragment dlg_client;
    DialogFragment dlg_product;

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
    TextView notfound;

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

    double summa = 0;
    double ves = 0;
    int col = 0;

    Zakaz zakaz;

    // TODO дабавить изменение статуса


    public void initialize() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer);


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

        notfound = findViewById(R.id.notfound);
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


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                dlg_client = new Add_Dialog(Zakaz_new.this, "zakaz_product", products.get(i-1), String.valueOf(i-1));
//                dlg_client.setCancelable(true);
//                dlg_client.show(getFragmentManager(), "");
                showDialogZakazProducts(products.get(i-1), String.valueOf(i-1));
            }
        });

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
                        summa();
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
                return true;
            }
        });

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        couriers = new ArrayList<>();
        couriers_id = new ArrayList<>();


//        dlg_client = new Add_Dialog(this, "zakaz_client");
//        dlg_client.setCancelable(false);
        Intent intent = getIntent();
        String id_z = intent.getStringExtra("id_z");
        if(id_z != null){
            String cap = intent.getStringExtra("name");
            id_client = intent.getStringExtra("id_c");
            caption.setText(cap);
            menu.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            reload(id_z);
        }else {
            if (getIntent().getStringExtra("name_client").equals("null")){
                showDialogZakazClient();
            }else{
                id_client = getIntent().getStringExtra("id_client");
                caption.setText(getIntent().getStringExtra("name_client"));
            }
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.openDrawer(GravityCompat.END);
                }
            });
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


    private void showDialogZakazClient(){

        final Dialog view = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        view.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.setContentView(R.layout.dialog_zakaz_client);


        final EditText search = view.findViewById(R.id.search);
        final ListView lv = view.findViewById(R.id.lv);
        final TextView notfound = view.findViewById(R.id.notfound);
        final ArrayList<Dialog_clients> clients = new ArrayList<>();
        final ArrayList<Dialog_clients> clients_buf = new ArrayList<>();
        final ArrayList<Clients> clients_zak = new ArrayList<>();

        final EditText search_gorod = view.findViewById(R.id.search_gorod);
        final Spinner spinner = view.findViewById(R.id.spinner);
        final LinearLayout filter = view.findViewById(R.id.filter);
        final Button select_gorod = view.findViewById(R.id.select_gorod);
        final Button clear_filter = view.findViewById(R.id.clear_filter);
        final TextView notfound_gorod = view.findViewById(R.id.notfound_gorod);
        final ArrayList<String> goroda = new ArrayList<>();
        final ArrayList<String> goroda_buf = new ArrayList<>();

        App.getApi().getGoroda().enqueue(new Callback<List<Gorod>>() {
            @Override
            public void onResponse(Call<List<Gorod>> call, Response<List<Gorod>> response) {
                goroda.clear();
                goroda_buf.clear();
                for (Gorod buffer : response.body()) {
                    goroda.add(buffer.getName());
                    goroda_buf.add(buffer.getName());
                }

                if (!goroda.isEmpty()) {
                    notfound_gorod.setVisibility(View.GONE);
                    filter.setVisibility(View.VISIBLE);
                } else {
                    notfound_gorod.setVisibility(View.VISIBLE);
                    filter.setVisibility(View.GONE);
                }
                notfound_gorod.setText("Ничего не найдено");

                spinner.setAdapter(new ArrayAdapter<>(Zakaz_new.this, android.R.layout.simple_list_item_1, goroda));
            }

            @Override
            public void onFailure(Call<List<Gorod>> call, Throwable t) {

            }
        });

        search_gorod.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goroda.clear();
                if (!search_gorod.getText().toString().isEmpty()) {
                    for (int i = 0; i < goroda_buf.size(); i++) {
                        if (goroda_buf.get(i).toLowerCase().contains(search_gorod.getText().toString().toLowerCase())) {
                            goroda.add(goroda_buf.get(i));
                        }
                    }
                } else {
                    goroda.addAll(goroda_buf);
                }
                spinner.setAdapter(new ArrayAdapter<>(Zakaz_new.this, android.R.layout.simple_list_item_1, goroda));

                if (goroda.size() == 0) {
                    notfound_gorod.setVisibility(View.VISIBLE);
                    filter.setVisibility(View.GONE);
                } else {
                    notfound_gorod.setVisibility(View.GONE);
                    filter.setVisibility(View.VISIBLE);
                }
            }
        });

        select_gorod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clients.clear();
                for (Clients buffer : clients_zak) {
                    if (buffer.getGorod().equals(spinner.getSelectedItem().toString())) {
                        clients.add(new Dialog_clients(buffer.getName(), buffer.getPodarki()));
                        clients_buf.add(new Dialog_clients(buffer.getName(), buffer.getPodarki()));
                    }
                }

                if (!clients.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");

                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Dialog.Main(Zakaz_new.this, clients));

                clients_buf.clear();
                clients_buf.addAll(clients);
            }
        });

        clear_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_gorod.setText("");

                clients.clear();
                clients_buf.clear();

                for (Clients buffer : clients_zak) {
                    clients.add(new Dialog_clients(buffer.getName(), buffer.getPodarki()));
                    clients_buf.add(new Dialog_clients(buffer.getName(), buffer.getPodarki()));
                }

                filter.setVisibility(View.VISIBLE);
                notfound.setVisibility(View.GONE);

                notfound.setText("Ничего не найдено");

                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Dialog.Main(Zakaz_new.this, clients));

                goroda.addAll(goroda_buf);

                spinner.setAdapter(new ArrayAdapter<>(Zakaz_new.this, android.R.layout.simple_list_item_1, goroda));
            }
        });

        App.getApi().getClients().enqueue(new Callback<List<Clients>>() {
            @Override
            public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                clients.clear();
                clients_buf.clear();
                for (Clients buffer : response.body()) {
                    clients.add(new Dialog_clients(buffer.getName(), buffer.getPodarki()));
                    clients_buf.add(new Dialog_clients(buffer.getName(), buffer.getPodarki()));
                }
                clients_zak.addAll(response.body());

                if (!clients.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");


                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Dialog.Main(Zakaz_new.this, clients));
            }

            @Override
            public void onFailure(Call<List<Clients>> call, Throwable t) {
                Toast.makeText(Zakaz_new.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clients.clear();
                if (!search.getText().toString().isEmpty()) {
                    for (int i = 0; i < clients_buf.size(); i++) {
                        if (clients_buf.get(i).getName().toLowerCase().contains(search.getText().toString().toLowerCase())) {
                            clients.add(clients_buf.get(i));
                        }
                    }
                } else {
                    clients.addAll(clients_buf);
                }
                lv.setAdapter(new Main(Zakaz_new.this, clients));

                if (clients.size() == 0) {
                    notfound.setVisibility(View.VISIBLE);
                } else {
                    notfound.setVisibility(View.GONE);
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                for (Clients buf : clients_zak) {
                    if (buf.getName().equals(clients.get(position).getName()))
                        return_client(buf.getId(), clients.get(position).getName());
                    view.dismiss();
                }

            }
        });

//        builder.setTitle("Выбор клиента")
//                .setView(view)
//                .setPositiveButton("Создать клиента", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, int which) {
//                        Intent intent = new Intent(context, Add.class);
//                        context.startActivity(intent);
//                        dialog.cancel();
//                    }
//                })
//                .setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        getActivity().finish();
//                    }
//                });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(view.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setCancelable(false);
        view.show();
        view.getWindow().setAttributes(lp);

    }
    private void showDialogZakazProducts(final Zakaz_product product, final String id_product){
        final Dialog view = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        view.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.setContentView(R.layout.dialog_zakaz_product);


        final EditText cena_zakaz = view.findViewById(R.id.cena_zakaz);
        final EditText col_zakaz = view.findViewById(R.id.col_zakaz);
        final TextView summa_zakaz = view.findViewById(R.id.summa_zakaz);
        final TextView tv_change = view.findViewById(R.id.btn_change);
        final TextView tv_close = view.findViewById(R.id.btn_close);



        final LinearLayout linear_podarki = view.findViewById(R.id.linear_podarki);
        final TextView cena_podar = view.findViewById(R.id.cena_podar);
        final EditText col_podar = view.findViewById(R.id.col_podar);
        final TextView summa_podar = view.findViewById(R.id.summa_podar);

        final TextView summa = view.findViewById(R.id.summa);

        ispodarki = product.ispodar;
        const_podar = product.const_podar;

        if (ispodarki.equals("0")){
            linear_podarki.setVisibility(View.GONE);
        }else{
            linear_podarki.setVisibility(View.VISIBLE);
        }

        cena_zakaz.setText(product.cena);
        cena_podar.setText(product.cena);
        col_zakaz.setText(product.col_zakaz);
        col_podar.setText(product.col_podar);

        summas(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);

        cena_zakaz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(cena_zakaz.getText()).isEmpty()) cena_zakaz.setText("0");
                cena_podar.setText(cena_zakaz.getText());
                summas(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        col_zakaz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (ispodarki.equals("1")) {
                        col_podar.setText(String.valueOf((int)Math.floor(Integer.parseInt(String.valueOf(col_zakaz.getText()))/Integer.parseInt(const_podar))));
                    } else {
                        col_podar.setText("0");
                    }
                }catch (Exception e){
                    col_podar.setText("0");
                }
                summas(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        col_podar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                summas(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        view.setTitle(product.name);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                product.cena = String.valueOf(cena_zakaz.getText());
                        product.col_zakaz = String.valueOf(col_zakaz.getText());
                        product.col_podar = String.valueOf(col_podar.getText());
                        product.summa = String.valueOf(summa.getText());

                        return_product(product, id_product);
                        view.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(view.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setCancelable(false);
        view.show();
        view.getWindow().setAttributes(lp);
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

//        Intent intent = getIntent();
//        String id_z = intent.getStringExtra("id");
//        if(id_z != null) {
//            reload(id_z);
//        }

        load_couriers();
    }

    @Override
    public void onBackPressed() {
        if (products != null && products.isEmpty()) {
            ad = new AlertDialog.Builder(Zakaz_new.this);
            ad.setMessage("Не выбрано не одной позиции товара. Выйти без сохранения?");
            ad.setPositiveButton("ПОДТВЕРДИТЬ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    finish();
                }
            });
            ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                }
            });
            ad.setCancelable(false);
            ad.show();
            return;

        } else {
            try {
                finish();
            } catch (Exception e) {
                Log.e("ZAKAZ_NEW", e.toString());
                e.printStackTrace();
            }
        }
    }

    public void optimiz(){
        ArrayList<Zakaz_product> buffer = new ArrayList<>(products);
        products.clear();


        for (int i=0; i < buffer.size(); i++) {
            for (int j = i+1; j < buffer.size(); j++) {
                if (buffer.get(i).artukil.equals(buffer.get(j).artukil) && Integer.parseInt(buffer.get(i).col_zakaz)>=0 && Integer.parseInt(buffer.get(j).col_zakaz)>=0) {
                    buffer.get(i).col_zakaz = String.valueOf(Integer.parseInt(buffer.get(i).col_zakaz) + Integer.parseInt(buffer.get(j).col_zakaz));
                    buffer.get(i).col_podar = String.valueOf(Double.parseDouble(buffer.get(i).col_podar) + Double.parseDouble(buffer.get(j).col_podar));
                    buffer.get(i).otgruzeno = String.valueOf(Integer.parseInt(buffer.get(i).otgruzeno) + Integer.parseInt(buffer.get(j).otgruzeno));
                    buffer.get(j).artukil="";
                }

                if (buffer.get(i).artukil.equals(buffer.get(j).artukil) && Integer.parseInt(buffer.get(i).col_zakaz)<0 && Integer.parseInt(buffer.get(j).col_zakaz)<0) {
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

                    summa();

                    tv1.setText(String.format("Итого %s позиций на %sBYN", String.valueOf(Math.round(col)), String.valueOf(Math.round(summa * 100.0) / 100.0)));
                    tv2.setText(String.format("Вес: %sкг", String.valueOf(Math.round(ves * 100.0) / 100.0)));


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

                    summa();


                    tv1.setText(String.format("Итого %s позиций на %sBYN", String.valueOf(Math.round(col)), String.valueOf(Math.round(summa * 100.0) / 100.0)));
                    tv2.setText(String.format("Вес: %sкг", String.valueOf(Math.round(ves * 100.0) / 100.0)));

                    break;

            }
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }

    public void summa(){
        summa = 0;
        ves = 0;
        col = 0;
        for (Zakaz_product buffer : products) {
            col++;
            summa += Double.parseDouble(buffer.summa.equals("")?"0":buffer.summa);
            ves += Double.parseDouble(buffer.ves.equals("")?"0":buffer.ves) * Double.parseDouble(buffer.col_zakaz);
        }

        if (products.size()==0){
            notfound.setVisibility(View.VISIBLE);
        }else{
            notfound.setVisibility(View.GONE);
        }
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

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, couriers);
                //adapter.setDropDownViewResource(R.layout.spinner_item);
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

    public void return_product(Zakaz_product product, String id){
        products.get(Integer.parseInt(id)).cena = product.cena;
        products.get(Integer.parseInt(id)).col_zakaz = product.col_zakaz;
        products.get(Integer.parseInt(id)).col_podar = product.col_podar;
        products.get(Integer.parseInt(id)).summa = product.summa;

        summa();

        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(this, products));
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

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void summas(EditText cena, EditText col_zakaz, EditText col_podar, TextView summa_z, TextView summa_p, TextView summa) {

        double this_cena;
        try{
            this_cena = Double.parseDouble(cena.getText().toString());
        }catch (Exception e) {
            this_cena = 0;
        }

        int this_col_z;
        try {
            this_col_z = Integer.parseInt(col_zakaz.getText().toString());
        }catch (Exception e){
            this_col_z = 0;
        }

        int this_col_p;
        try {
            this_col_p = Integer.parseInt(col_podar.getText().toString());
        }catch (Exception e){
            this_col_p = 0;
        }

        double this_summa_z = this_cena * this_col_z;
        double this_summa_p = this_cena * this_col_p;
        double this_summa = this_summa_z - this_summa_p;

        summa_z.setText(String.valueOf(Math.round(this_summa_z * 100.0) / 100.0));
        summa_p.setText(String.valueOf(Math.round(this_summa_p * 100.0) / 100.0));
        summa.setText(String.valueOf(Math.round(this_summa * 100.0) / 100.0));
    }

    public void reload(final String id) {
        App.getApi().getZakaz_info(id).enqueue(new Callback<Zakaz>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<Zakaz> call, Response<Zakaz> response) {
                Zakaz zakaz = response.body();
                blank.setText(zakaz.getId());
                date1.setText(zakaz.getDate());
                date2.setText(zakaz.getDateIzm());
                autor.setText(zakaz.getAutor());
                oplacheno.setChecked(zakaz.getOplacheno().equals("1"));
                //courier.setText(response.body().getCourier());



                summa = 0;
                ves = 0;
                col = 0;
                for (WhatZakazal buffer : response.body().getWhatZakazal()) {
                    col++;
                    try {
                        summa += Double.parseDouble(buffer.getCena().equals("")?"0":buffer.getCena()) * Double.parseDouble(buffer.getZakazano());
                        ves += Double.parseDouble(buffer.getVes().equals("")?"0":buffer.getVes()) * Double.parseDouble(buffer.getZakazano());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

                if(response.body().getWhatZakazal() != null){
                    products.clear();
                    for (WhatZakazal wat: response.body().getWhatZakazal()
                         ) {

                        // TODO добавить сумму
                        String sums = "0";
                        try{

                            Double price = Double.parseDouble(wat.getCena());
                            int col = Integer.parseInt(wat.getZakazano());

                            Double sum = price * col;
                            sums = String.valueOf(sum);

                        }
                        catch (Exception e){}

                        if(wat.getOtgruzeno().isEmpty()){
                            wat.setOtgruzeno("0");
                        }
//
                        Zakaz_product product = new Zakaz_product(
                                wat.getId(), wat.getName(), wat.getArtikul(), wat.getCena(),
                                wat.getZakazano(),wat.getPodarki(), "", "",
                                sums, wat.getOtgruzeno(), wat.getVes(), "",
                                wat.getClas(),  "", ""
                        );

                        products.add(product);
//
                    }

                }
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(Zakaz_new.this, products));
                //lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, response.body().getWhatZakazal()));
                //lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, (ArrayList<WhatZakazal>) zakaz.getWhatZakazal()));
            }

            @Override
            public void onFailure(Call<Zakaz> call, Throwable t) {

            }
        });
    }
}
