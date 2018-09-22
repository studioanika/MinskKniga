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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Zakazy.adapter.ZakazzyObrazacAdapet;
import by.minskkniga.minskkniga.adapter.Add_Contacts;
import by.minskkniga.minskkniga.adapter.Dialog.Main;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.Dialog_clients;
import by.minskkniga.minskkniga.api.Class.Gorod;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.WhatZakazal;
import by.minskkniga.minskkniga.api.Class.Zakaz;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import by.minskkniga.minskkniga.api.Class.clients.ClientInfo;
import by.minskkniga.minskkniga.api.Class.clients.Contact;
import by.minskkniga.minskkniga.api.Class.nomenclatura.OZOId;
import by.minskkniga.minskkniga.api.Class.nomenclatura.ObjectZakazyObrazci;
import by.minskkniga.minskkniga.api.Class.zakazy.ObrazciComplect;
import by.minskkniga.minskkniga.api.Class.zakazy.ObrazciProduct;
import by.minskkniga.minskkniga.api.Class.zakazy.ZakazyObrazec;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_new extends AppCompatActivity {

    private String ispodarki;
    private String const_podar;
    List<ObjectZakazyObrazci> obrazciList_general = null;

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
    int status_id = 0;
    TextView date2;
    TextView zametka;

    LinearLayout lin_h_1, lin_h_2;

    Button ok;

    TextView tv1;
    TextView tv2;
    ListView lv;
    ExpandableListView exp;
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
    CheckBox sobran;

    ArrayList<ObjectZakazyObrazci> obrazciList = new ArrayList<>();

    double summa = 0;
    double ves = 0;
    int col = 0;

    Zakaz zakaz;

    String id_z;
    View header;

    TextView nav_order, nav_otgruzit, nav_edit_otgruzhennoe, nav_prinat,
             nav_contacts;

    // TODO дабавить изменение статуса


    public void initialize() {
        try {
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

            header = getLayoutInflater().inflate(R.layout.include_zakaz_new, null);
            lv.addHeaderView(header);

            lin_h_1 = header.findViewById(R.id.ll_head);
            lin_h_2 = header.findViewById(R.id.lin_header1);

            sobran = header.findViewById(R.id.sobran);

            sobran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) status_id = 3;
                    else status_id = 2;
                }
            });

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
                    final Intent intent = getIntent();
                    int type_z = intent.getIntExtra("type", 1);
                    String obr = intent.getStringExtra("obrazec");
                    if(obr != null) type_z = 2;
                    if(type_z == 1)ok();
                    else addZakazObrazec();
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
                    if(status_id != 5)showDialogZakazProducts(products.get(i-1), String.valueOf(i-1));
                    else if(status_id == 5) editStatusOtgruxhen(products.get(i-1));
                }
            });

            exp = findViewById(R.id.exp);

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
            final Intent intent = getIntent();
            id_z = intent.getStringExtra("id_z");
            if(id_z != null){
                String cap = intent.getStringExtra("name");
                id_client = intent.getStringExtra("id_c");
                caption.setText(cap);
                reload(id_z);
            }else {
                if (getIntent().getStringExtra("name_client").equals("null")){
                    showDialogZakazClient();
                }else{
                    id_client = getIntent().getStringExtra("id_client");
                    caption.setText(getIntent().getStringExtra("name_client"));
                }
            }

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        drawer.openDrawer(GravityCompat.END, true);
                    } catch (Exception e) {
                        String d = e.toString();
                        e.printStackTrace();
                    }
                }
            });

            currentDate = new Date();

            df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());


            nav_sobrat = findViewById(R.id.nav_sobrat);

            nav_sobrat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(drawer.isDrawerOpen(GravityCompat.END)) drawer.closeDrawers();
                    //optimiz();
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
                    Intent intent1 = getIntent();
                    String obr = intent1.getStringExtra("obrazec");
                    int type_z = intent1.getIntExtra("type", 1);
                    if(obr != null) type_z = 2;
                    Intent intent = new Intent(Zakaz_new.this, by.minskkniga.minskkniga.activity.Nomenklatura.Main.class);
                    intent.putExtra("zakaz", true);
                    intent.putExtra("id_client", id_client);
                    if(type_z == 1){
                        startActivityForResult(intent, 1);
                    }else {
                        intent.putExtra("type_z", true);
                        startActivityForResult(intent, 3);
                    }
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

            Intent intent1 = getIntent();
            int type_z = intent1.getIntExtra("type", 1);
            if(type_z == 2) {
                chernovik.setVisibility(View.GONE);
                oplacheno.setVisibility(View.GONE);
            }

            nav_order = findViewById(R.id.nav_order);
            nav_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.closeDrawer(GravityCompat.END);
                    try {
                        if(id_z != null){
                            Intent intent = getIntent();
                            String i = intent.getStringExtra("obrazec");
                            if(i == null){
                                Intent prihod = new Intent(Zakaz_new.this, PrihodOrder.class);
                                prihod.putExtra("id", id_client);
                                prihod.putExtra("id_zak", id_z);
                                startActivity(prihod);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            nav_otgruzit = findViewById(R.id.nav_otgruz);
            nav_otgruzit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(courier.getSelectedItemPosition() != 0){
                        status.setText("В доставке");
                        status.setTextColor(Color.rgb(242, 0, 86));
                        status_id = 4;
                    }else {
                        status.setText("Отгружен");
                        status.setTextColor(Color.rgb(139, 0, 0));
                        status_id = 5;
                    }

                    drawer.closeDrawer(GravityCompat.END);

                }
            });

            nav_edit_otgruzhennoe = findViewById(R.id.nav_edit_otgruzhennoe);
            nav_edit_otgruzhennoe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editOtgruzhennoe();
                    drawer.closeDrawers();
                }
            });

            nav_prinat = findViewById(R.id.nav_prinat);
            nav_prinat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.closeDrawer(GravityCompat.END);
                    status.setText("Возвращение");
                    status.setTextColor(Color.rgb(100, 0, 0));
                    status_id = 6;
                }
            });

            nav_contacts = findViewById(R.id.nav_contacts);
            nav_contacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.closeDrawer(GravityCompat.END);
                    if(id_client != null) showDialogContacts(id_client);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showDialogContacts(String id_client) {
        final Dialog view = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        view.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.setContentView(R.layout.dialog_contacts);


        final ProgressBar progressBar = view.findViewById(R.id.progress);
        final ListView lv = view.findViewById(R.id.lv);
        final TextView name = view.findViewById(R.id.tv_name);
        final TextView close = view.findViewById(R.id.tv_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                view.dismiss();
            }
        });

        final ArrayList<String> contact_type = new ArrayList<>();
        final ArrayList<String> contact_text = new ArrayList<>();
        final ArrayList<String> contacts_id = new ArrayList<>();

        App.getApi().getClientId(id_client).enqueue(new Callback<List<ClientInfo>>()
        {
            @Override
            public void onResponse(Call<List<ClientInfo>> call, Response<List<ClientInfo>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    name.setText(response.body().get(0).getName());
                    ClientInfo clientInfo = response.body().get(0);

                    if(clientInfo.getContacts() != null && clientInfo.getContacts().size() != 0){
                        contact_text.clear();
                        contact_type.clear();
                        for (Contact ct: clientInfo.getContacts()
                                ) {
                            contacts_id.add(ct.getId());
                            contact_type.add(ct.getType());
                            contact_text.add(ct.getText());
                            lv.setAdapter(new Add_Contacts(Zakaz_new.this, contact_type, contact_text));
//                            setListViewHeightBasedOnChildren(list_contact);

                        }
                    }else {
                        Toast.makeText(Zakaz_new.this, "Контакты не найдены...", Toast.LENGTH_SHORT).show();
                        view.dismiss();
                    }

                } else {
                    Toast.makeText(Zakaz_new.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ClientInfo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Zakaz_new.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(view.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.show();
        view.getWindow().setAttributes(lp);


    }

    private void editStatusOtgruxhen(final Zakaz_product zakaz_product) {

        final Dialog view;
        try {
            view = new Dialog(this);
            //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            view.requestWindowFeature(Window.FEATURE_NO_TITLE);
            view.setContentView(R.layout.dialog_edit_zakaz_v_dostavke);

            TextView tv_done = view.findViewById(R.id.editmoney_done);
            TextView tv_cansel = view.findViewById(R.id.editmoney_cancel);

            final EditText col_zakaz = view.findViewById(R.id.tv_zakazano);
            final EditText col_otgr = view.findViewById(R.id.et_otguzheno);
            String otgr = zakaz_product.getOtgruzeno();
            col_otgr.setText(otgr);

            //zakaz_product.setCol_zakaz(col_zakaz.getText().toString());
            tv_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    try {
                        String et  = col_otgr.getText().toString();
                        if(!et.isEmpty()) zakaz_product.setOtgruzeno(col_otgr.getText().toString());
                        view.dismiss();
                        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(Zakaz_new.this, products));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            tv_cansel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        //col_zakaz.setText(zakaz_product.col_zakaz);




    }

    private void addZakazObrazec() {

        if(obrazciList != null && obrazciList.size() == 0) {
            Toast.makeText(Zakaz_new.this, "Не выбрано ниодной позиции комплектов", Toast.LENGTH_SHORT).show();
            return;
        }
        if(id_client == null) {
            showDialogZakazClient();
            return;
        }

        List<OZOId> list_nomer = new ArrayList<>();

        for (ObjectZakazyObrazci obr: obrazciList
             ) {
            OZOId ozoId = new OZOId();
            ozoId.setId(obr.getId());
            list_nomer.add(ozoId);
        }
        Gson gson = new Gson();
        String list = gson.toJson(list_nomer).toString();


        Map<String, String> map = new HashMap<>();
        map.put("autor", admin_id);
        if(courier.getSelectedItemPosition() == 0) {map.put("courier_zak", "-1");}
        else map.put("courier_zak", couriers_id.get(courier.getSelectedItemPosition()));

        map.put("client_zak", id_client);
        map.put("nomer_k", list);
        map.put("zak_kom", zametka.getText().toString());
        ok.setEnabled(false);
        if(id_z == null){
            App.getApi().addZakazObrazec(map).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.body() != null) Toast.makeText(Zakaz_new.this, "Заказ образцов добавлен.", Toast.LENGTH_SHORT).show();
                    ok.setEnabled(true);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ok.setEnabled(true);
                }
            });
        }else {
            map.put("komment", zametka.getText().toString());
            map.put("id", id_z);
            if(courier.getSelectedItemPosition() == 0) {map.put("courier", "-1");}
            else map.put("courier", couriers_id.get(courier.getSelectedItemPosition()));

            List<OZOId> list_nomer1 = new ArrayList<>();

            for (ObjectZakazyObrazci obr: obrazciList
                    ) {
                OZOId ozoId = new OZOId();
                ozoId.setKomplekt_id(obr.getId());
                list_nomer1.add(ozoId);
            }
            Gson gson1 = new Gson();
            String list1 = gson1.toJson(list_nomer1).toString();
            map.put("list", list1);

            App.getApi().updateZakazyObr(map).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.body() != null) Toast.makeText(Zakaz_new.this, "Заказ образцов обновлен.", Toast.LENGTH_SHORT).show();
                    ok.setEnabled(true);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ok.setEnabled(true);
                }
            });

        }



    }
    private void showDialogZakazClient(){

        final List<Gorod> listResponse = new ArrayList<>();
        final List<Gorod> listFilter = new ArrayList<>();

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

                if(response.body() != null) {
                    notfound_gorod.setVisibility(View.GONE);
                    List<Gorod> gorodList = response.body();
                    listFilter.addAll(gorodList);
                    listResponse.addAll(gorodList);

                    ArrayList<String> list = new ArrayList<>();

                    for (Gorod g: listResponse
                         ) {
                        list.add(g.getName());
                    }

                    spinner.setAdapter(new ArrayAdapter<>(Zakaz_new.this, android.R.layout.simple_list_item_1, list));
                    //listResponse.remove(0);
                }

            }

            @Override
            public void onFailure(Call<List<Gorod>> call, Throwable t) {

            }
        });

        search_gorod.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if(listResponse != null) {

                    ArrayList<String> l = new ArrayList<>();
                    listFilter.clear();
                    for (Gorod g: listResponse
                            ) {

                        if(g.getName().toLowerCase().contains(s.toString().toLowerCase()) &&
                                g.getName().toLowerCase().contains(s.toString().toLowerCase())){

                            listFilter.add(g);
                            l.add(g.getName());

                        }

                    }

                    if(l.size() == 0) notfound_gorod.setVisibility(View.VISIBLE);
                    else {
                        spinner.setAdapter(new ArrayAdapter<>(Zakaz_new.this, android.R.layout.simple_list_item_1, l));
                        notfound_gorod.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


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
        view.show();
        view.getWindow().setAttributes(lp);

    }
    private void showDialogZakazProducts(final Zakaz_product product, final String id_product){
        final Dialog view = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        view.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.setContentView(R.layout.dialog_zakaz_product);

        LinearLayout lin = view.findViewById(R.id.lin);
        final EditText col_otgr = view.findViewById(R.id.et_otguzheno);
        final EditText cena_zakaz = view.findViewById(R.id.cena_zakaz);
        final EditText col_zakaz = view.findViewById(R.id.col_zakaz);
        final TextView summa_zakaz = view.findViewById(R.id.summa_zakaz);
        final TextView tv_change = view.findViewById(R.id.btn_change);
        final TextView tv_close = view.findViewById(R.id.btn_close);


        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                view.dismiss();
            }
        });
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

        if(status_id == 4) lin.setVisibility(View.VISIBLE);
        col_otgr.setText(product.otgruzeno);

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
                        String org = col_otgr.getText().toString();
                        if(!org.isEmpty()) product.otgruzeno = org;
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
        try {
            initialize();

            autor.setText(sp.getString("login", ""));

            admin_id = sp.getString("user_id", "");
            status.setTextColor(Color.rgb(97, 184, 126));
            status.setText("Новый");
            status_id = 1;
            date1.setText(df.format(currentDate));
            //date2.setText(df.format(currentDate));

//        Intent intent = getIntent();
//        String id_z = intent.getStringExtra("id");
//        if(id_z != null) {
//            reload(id_z);
//        }

            load_couriers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (products != null && products.isEmpty() && obrazciList!=null && obrazciList.size() == 0) {
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
                if(drawer.isDrawerOpen(GravityCompat.END)) drawer.closeDrawers();
                else finish();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)   {
        boolean sobr;
        Toast.makeText(this, products.size()+"", Toast.LENGTH_SHORT).show();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    products.addAll(data.<Zakaz_product>getParcelableArrayListExtra(Zakaz_product.class.getCanonicalName()));

//                    for (int i=0;i<products.size();i++){
//                        products.get(i).summa = String.valueOf(Double.parseDouble(zakaz);
//                    }

                    lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(this, products));

                    sobr = false;
                    for (Zakaz_product buf: products){
                        if (Integer.parseInt(buf.otgruzeno)>0)
                            sobr = true;
                    }

                    if (sobr){
//                        status.setTextColor(Color.rgb(242, 201, 76));
//                        status.setText("В сборке");
//                        status_id = 2;
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
                        try {
                            Double d = Double.parseDouble(products.get(i).cena) * Double.parseDouble(products.get(i).col_zakaz)- Double.parseDouble(products.get(i).cena) * Double.parseDouble(products.get(i).col_podar);
                            products.get(i).summa = String.valueOf(String.format("%.2f", d));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
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
                        ok.setText("Сохранить");
                    }else{
                        status.setTextColor(Color.rgb(97, 184, 126));
                        status.setText("Новый");
                        status_id=1;
                    }

                    summa();


                    tv1.setText(String.format("Итого %s позиций на %sBYN", String.valueOf(Math.round(col)), String.valueOf(Math.round(summa * 100.0) / 100.0)));
                    tv2.setText(String.format("Вес: %sкг", String.valueOf(Math.round(ves * 100.0) / 100.0)));

                    break;
                case 3:
                    obrazciList.addAll((Collection) data.getSerializableExtra(ObjectZakazyObrazci.class.getCanonicalName()));
                    ArrayList<ObjectZakazyObrazci> l =  new ArrayList<>();


                    for (ObjectZakazyObrazci obj: obrazciList
                         ) {
                        if(!l.contains(obj)) l.add(obj);
                    }
                    String d = l.toString();
                    setAdapterKomplents(l);
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
            try {
                col++;
                summa += Double.parseDouble(buffer.summa.equals("")?"0":buffer.summa);
                ves += Double.parseDouble(buffer.ves.equals("")?"0":buffer.ves) * Double.parseDouble(buffer.col_zakaz);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (products.size()==0){
            notfound.setVisibility(View.VISIBLE);
        }else{
            notfound.setVisibility(View.GONE);
        }
    }

    public void load_couriers(){
        App.getApi().getCour().enqueue(new Callback<List<Couriers>>() {
            @Override
            public void onResponse(Call<List<Couriers>> call, Response<List<Couriers>> response) {
                couriers.clear();
                couriers_id.clear();

                couriers.add("Не выбран");
                couriers_id.add("-1");
                for(Couriers buffer : response.body()){
                    couriers.add(buffer.getName());
                    couriers_id.add(buffer.getId());
                }

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, couriers);
                //adapter.setDropDownViewResource(R.layout.spinner_item);
                courier.setAdapter(adapter);

                if(id_z != null){
                    Intent intent = getIntent();
                    String i = intent.getStringExtra("obrazec");
                    if(i == null)reload(id_z);
                    else loadObrazci(id_z);
                }
            }

            @Override
            public void onFailure(Call<List<Couriers>> call, Throwable t) {
                Toast.makeText(Zakaz_new.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadObrazci(String id_z) {

        chernovik.setVisibility(View.GONE);
        oplacheno.setVisibility(View.GONE);
        nav_order.setVisibility(View.GONE);
        App.getApi().getZakazObrazciId(id_z, "1").enqueue(new Callback<ZakazyObrazec>() {
            @Override
            public void onResponse(Call<ZakazyObrazec> call, Response<ZakazyObrazec> response) {

                if(response.body() != null) {

                    ZakazyObrazec obrazec = response.body();

                    blank.setText(obrazec.getId());
                    date1.setText(obrazec.getDate());
                    autor.setText(obrazec.getAutor());
                    setCourier(obrazec.getCourier());
                    zametka.setText(obrazec.getZametka());
                    switch (obrazec.getStatus()) {
                        case "0"://chernovik новый green
                            status.setText("Черновик");
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
                            sobran.setVisibility(View.VISIBLE);
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
                    tv1.setText(String.format("Итого %s позиций на %sBYN", obrazec.getKol(),obrazec.getSumma()));
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
                product.setCena(pr.getCena());
                product.setVes(pr.getVes());

                products.add(product);
            }

            objectZakazyObrazci.setVes(Double.valueOf(obr.getKomplect_ves()));
            objectZakazyObrazci.setList(products);

            list.add(objectZakazyObrazci);
        }
        obrazciList = (ArrayList<ObjectZakazyObrazci>) list;

        setAdapterKomplents(list);

    }
    private void setAdapterKomplents(List<ObjectZakazyObrazci> obrazciList_) {

        //fab.setEnabled(false);

        exp.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);

        lin_h_1.setVisibility(View.GONE);
        lin_h_2.setVisibility(View.VISIBLE);

        if(exp.getHeaderViewsCount() == 0) exp.addHeaderView(header);
        exp.setAdapter(new ZakazzyObrazacAdapet(obrazciList, this));


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

        if(id_client == null) {
            showDialogZakazClient();
            return;
        }

        Intent intent = getIntent();
        String id_z = intent.getStringExtra("id_z");

        if(id_z != null){

            Intent intent1 = getIntent();
            String i = intent1.getStringExtra("obrazec");
            if(i == null) {
                Map<String, String> map = new HashMap<>();
                map.put("id", id_z);

                if(oplacheno.isChecked()) map.put("oplacheno", "1");
                else map.put("oplacheno", "0");

                String com = zametka.getText().toString();


                map.put("komment", com);
                map.put("status", String.valueOf(status_id));

                if(chernovik.isChecked()) map.put("chern", "0");
                else map.put("chern", "1");

                Gson gson = new Gson();
                String list = gson.toJson(products).toString();

                map.put("list", list);
                if(courier.getSelectedItemPosition() == 0) {map.put("courier", "-1");}
                else map.put("courier", couriers_id.get(courier.getSelectedItemPosition()));

                App.getApi().updateZakay(map).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Toast.makeText(Zakaz_new.this, "Заказ обновлен.", Toast.LENGTH_SHORT).show();
                        if(sobran.isChecked()) finish();
                        load_couriers();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }



        }else {

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
        nav_prinat = findViewById(R.id.nav_prinat);
        App.getApi().getZakaz_info(id).enqueue(new Callback<Zakaz>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<Zakaz> call, Response<Zakaz> response) {
                try {
                    if(nav_otgruzit != null) nav_otgruzit.setVisibility(View.GONE);
                    Zakaz zakaz = response.body();
                    blank.setText(zakaz.getId());
                    date1.setText(zakaz.getDate());
                    //date2.setText(zakaz.getDateIzm());
                    autor.setText(zakaz.getAutor());
                    oplacheno.setChecked(zakaz.getOplacheno().equals("1"));
                    //courier.setText(response.body().getCourier());

                    setCourier(zakaz.getCourier());

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

                    tv1.setText(String.format("Итого %s позиций на %sBYN", col, zakaz.getSumma()));
                    tv2.setText(String.format("Вес: %sкг", zakaz.getVes()));

                    zametka.setText(zakaz.getZametka());

                    status_id = Integer.parseInt(zakaz.getStatus());
                    switch (zakaz.getStatus()) {
                        case "0"://chernovik новый green

                                status.setText("Новый");
                                status.setTextColor(Color.rgb(97, 184, 126));
                                chernovik.setChecked(true);

                            break;
                        case "1"://новый green
                            if(!zakaz.getType().contains("vozvrat")) {
                                status.setText("Новый");
                                status.setTextColor(Color.rgb(97, 184, 126));
                            }else {
                                status.setText("К возврату");
                                status.setTextColor(Color.rgb(97, 184, 126));
                                nav_otgruzit.setVisibility(View.GONE);
                                nav_order.setVisibility(View.GONE);
                                nav_sobrat.setVisibility(View.GONE);
                                nav_prinat.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "2"://в сборке yellow
                            status.setText("В сборке");
                            status.setTextColor(Color.rgb(242, 201, 76));
                            sobran.setVisibility(View.VISIBLE);
                            nav_otgruzit.setVisibility(View.VISIBLE);
                            break;
                        case "3"://собран blue
                            status.setText("Собран");
                            status.setTextColor(Color.BLUE);
                            nav_otgruzit.setVisibility(View.VISIBLE);
                            break;
                        case "4"://в доставке lightred
                            status.setText("В доставке");
                            status.setTextColor(Color.rgb(242, 0, 86));
                            fab.setVisibility(View.GONE);
                            nav_sobrat.setVisibility(View.GONE);
                            break;
                        case "5"://отгружен darkred
                            status.setText("Отгружен");
                            status.setTextColor(Color.rgb(139, 0, 0));
                            noEditOtgruzhennoe();
                            nav_edit_otgruzhennoe.setVisibility(View.VISIBLE);
                            fab.setVisibility(View.GONE);
                            nav_prinat.setVisibility(View.GONE);
                            nav_sobrat.setVisibility(View.GONE);
                            nav_otgruzit.setVisibility(View.GONE);
                            nav_order.setVisibility(View.GONE);
                            break;
                        case "6"://возвращение darkred
                            status.setText("Возвращение");
                            status.setTextColor(Color.rgb(100, 0, 0));
                            nav_prinat.setVisibility(View.GONE);
                            nav_sobrat.setVisibility(View.GONE);
                            nav_otgruzit.setVisibility(View.GONE);
                            nav_order.setVisibility(View.GONE);
                            //sobran.setVisibility(View.VISIBLE);
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
                                    wat
                                    .getId_poz(),
                                    wat.getId(), wat.getName(), wat.getArtikul(), wat.getCena(),
                                    wat.getZakazano(),wat.getPodarki(), "", "",
                                    String.valueOf(wat.getSumma()), wat.getOtgruzeno(), wat.getVes(), wat.getImage(),
                                    wat.getClas(),  "", ""
                            );
                            product.setSokr(wat.getSokrName());
                            products.add(product);
    //
                        }

                    }
                    lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_new(Zakaz_new.this, products));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, response.body().getWhatZakazal()));
                //lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_info(Zakaz_info.this, (ArrayList<WhatZakazal>) zakaz.getWhatZakazal()));
            }

            @Override
            public void onFailure(Call<Zakaz> call, Throwable t) {

            }
        });
    }

    private void setCourier(String courierr) {

        int i = 0;
        for (String d: couriers_id
             ) {
            if(d.equals(courierr)){
                try{
                    courier.setSelection(i);
                    return;
                }
                catch (Exception e){

                }

            }

            i++;
        }

    }

    private void editOtgruzhennoe(){
        lv.setEnabled(true);
        fab.setEnabled(true);
        chernovik.setEnabled(true);
        oplacheno.setEnabled(true);
        ok.setEnabled(true);
        zametka.setEnabled(true);
        courier.setEnabled(true);

    }

    private void noEditOtgruzhennoe(){

        lv.setEnabled(false);
        fab.setEnabled(false);
        chernovik.setEnabled(false);
        oplacheno.setEnabled(false);
        ok.setEnabled(false);
        zametka.setEnabled(false);
        courier.setEnabled(false);

    }


}
