package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Spravoch_Couriers.Zakazy_1;
import by.minskkniga.minskkniga.adapter.Spravoch_Couriers.adapter.CourierClientsAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Courier_filter_1;
import by.minskkniga.minskkniga.api.Class.Courier_filter_2;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_clients;
import by.minskkniga.minskkniga.api.Class.couriers.CourierClients;
import by.minskkniga.minskkniga.api.Class.couriers.CourierKnigi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy extends AppCompatActivity {

    String id;
    String name;

    TextView caption;
    ImageButton back;
    ImageButton filter;
    ImageButton menu;
    TabHost tabHost;
    RelativeLayout filter_layout_1;
    RelativeLayout filter_layout_2;
    TextView notfound_1;
    TextView notfound_2;
    DrawerLayout drawer;

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Spinner spinner6;

    Button clear_1;
    Button ok_1;

    Button clear_2;
    Button ok_2;

    ImageButton barcode;
    IntentIntegrator qrScan;

    ExpandableListView lv1;
    ExpandableListView lv2;
    TextView checkbox;
    int check = 0;

    EditText search_1;
    EditText search_2;

    ArrayList<CourierKnigi> zakazy_1;
    ArrayList<CourierKnigi> zakazy_1_buf;

    ArrayList<Zakazy_courier_clients> zakazy_2;
    ArrayList<Zakazy_courier_clients> zakazy_2_buf;

    String izdatel = "Издательство";
    String clas = "Класс";

    String napravl = "Направление";
    String sity = "Город";
    String school = "Школа";
    String smena = "Смена";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_courier_zakazy);

        try {
            back = findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            filter_layout_1 = findViewById(R.id.filter_layout_1);
            filter_layout_1.setVisibility(View.GONE);
            filter_layout_2 = findViewById(R.id.filter_layout_2);
            filter_layout_2.setVisibility(View.GONE);
            filter = findViewById(R.id.filter_button);

            notfound_1 = findViewById(R.id.notfound_1);
            notfound_2 = findViewById(R.id.notfound_2);

            spinner1 = findViewById(R.id.spinner1);
            spinner2 = findViewById(R.id.spinner2);
            spinner3 = findViewById(R.id.spinner3);
            spinner4 = findViewById(R.id.spinner4);
            spinner5 = findViewById(R.id.spinner5);
            spinner6 = findViewById(R.id.spinner6);

            search_1 = findViewById(R.id.search1);
            search_2 = findViewById(R.id.search2);

            barcode = findViewById(R.id.barcode);
            qrScan = new IntentIntegrator(this);
            barcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qrScan.initiateScan();
                }
            });

            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (tabHost.getCurrentTab()){
                        case 0:
                            if (filter_layout_1.getVisibility()==View.GONE){
                                filter_layout_1.setVisibility(View.VISIBLE);
                            }else{
                                filter_layout_1.setVisibility(View.GONE);
                            }
                            break;
                        case 1:
                            if (filter_layout_2.getVisibility()==View.GONE){
                                filter_layout_2.setVisibility(View.VISIBLE);
                            }else{
                                filter_layout_2.setVisibility(View.GONE);
                            }
                            break;
                    }
                }
            });


            lv1 = findViewById(R.id.lv1);
            lv2 = findViewById(R.id.lv2);

            zakazy_1 = new ArrayList<>();
            zakazy_1_buf = new ArrayList<>();

            zakazy_2 = new ArrayList<>();
            zakazy_2_buf = new ArrayList<>();

            checkbox = findViewById(R.id.checkbox);
            Drawable img = getResources().getDrawable(R.drawable.ic_check_0);
            img.setBounds(0, 0, 32, 32);
            checkbox.setCompoundDrawables(null, null, img, null);

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (check){
                        case 0:
                            Drawable img1 = getResources().getDrawable(R.drawable.ic_check_1);
                            img1.setBounds(0, 0, 32, 32);
                            checkbox.setCompoundDrawables(null, null, img1, null);
                            check=1;
                            break;
                        case 1:
                            Drawable img2 = getResources().getDrawable(R.drawable.ic_check_2);
                            img2.setBounds(0, 0, 32, 32);
                            checkbox.setCompoundDrawables(null, null, img2, null);
                            check=2;
                            break;
                        case 2:
                            Drawable img0 = getResources().getDrawable(R.drawable.ic_check_0);
                            img0.setBounds(0, 0, 32, 32);
                            checkbox.setCompoundDrawables(null, null, img0, null);
                            check=0;
                            break;
                    }
                    search_2();
                }
            });

            caption = findViewById(R.id.caption);
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            caption.setText(name);

            tabHost = findViewById(R.id.tabHost);
            tabHost.setup();

            TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

            tabSpec.setContent(R.id.linearLayout1);
            tabSpec.setIndicator("по книгам");
            tabHost.addTab(tabSpec);

            tabSpec = tabHost.newTabSpec("tag2");
            tabSpec.setContent(R.id.linearLayout2);
            tabSpec.setIndicator("по клиентам");
            tabHost.addTab(tabSpec);

            tabHost.setCurrentTab(1);

            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                public void onTabChanged(String tabId) {
                    if (tabHost.getCurrentTab()==0)
                        load_filter_1();
                    if (tabHost.getCurrentTab()==1)
                        load_filter_2();
                }
            });

            clear_1 = findViewById(R.id.clear_1);
            ok_1 = findViewById(R.id.ok_1);

            clear_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinner1.setSelection(0);
                    spinner2.setSelection(0);
                    izdatel = "Издательство";
                    clas = "Класс";
                    reload_1();
                    lv1.setAdapter(new Zakazy_1(Zakazy.this, zakazy_1));
                    filter_layout_1.setVisibility(View.GONE);
                }
            });

            ok_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    izdatel = spinner1.getSelectedItem().toString();
                    clas = spinner2.getSelectedItem().toString();
                    reload_1();
                    lv1.setAdapter(new Zakazy_1(Zakazy.this, zakazy_1));
                    filter_layout_1.setVisibility(View.GONE);
                }
            });

            clear_2 = findViewById(R.id.clear_2);
            ok_2 = findViewById(R.id.ok_2);


            clear_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinner3.setSelection(0);
                    spinner4.setSelection(0);
                    spinner5.setSelection(0);
                    spinner6.setSelection(0);
                    napravl = "Направление";
                    sity = "Город";
                    school = "Школа";
                    smena = "Смена";
                    reload_2();
                    //lv2.setAdapter(new Zakazy_2(Zakazy.this, zakazy_2));
                    //filter_layout_2.setVisibility(View.GONE);
                }
            });

            ok_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    napravl = spinner3.getSelectedItem().toString();
                    sity = spinner4.getSelectedItem().toString();
                    school = spinner5.getSelectedItem().toString();
                    smena = spinner6.getSelectedItem().toString();
                    reload_2();
                    //lv2.setAdapter(new Zakazy_2(Zakazy.this, zakazy_2));
                    //filter_layout_2.setVisibility(View.GONE);
                }
            });

            menu = findViewById(R.id.menu);
            drawer = findViewById(R.id.drawer);

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.END);
                }
            });

            search_1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search_1();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            search_2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search_2();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            load_filter_2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search_1() {

        // TODO здесь нужно подправить

//        zakazy_1.clear();
//        for (Zakazy_courier_knigi buffer : zakazy_1_buf) {
//            if (buffer.getName().toLowerCase().contains(search_1.getText().toString())||
//                    buffer.getBarcode().toLowerCase().contains(search_1.getText().toString())) {
//                zakazy_1.add(buffer);
//            }
//        }
//        if (!zakazy_1.isEmpty()) {
//            notfound_1.setVisibility(View.GONE);
//        } else {
//            notfound_1.setVisibility(View.VISIBLE);
//        }
//        lv1.setAdapter(new Zakazy_1(Zakazy.this, zakazy_1));
    }

    public void search_2() {
        zakazy_2.clear();
        for (Zakazy_courier_clients buffer : zakazy_2_buf) {
            if (buffer.getName().toLowerCase().contains(search_2.getText().toString())) {
                switch (check) {
                    case 0:
                        zakazy_2.add(buffer);
                        break;
                    case 1:
                        if (!buffer.getStatus().equals("3"))
                            zakazy_2.add(buffer);
                        break;
                    case 2:
                        if (buffer.getStatus().equals("3"))
                            zakazy_2.add(buffer);
                        break;
                }
            }
        }
        if (!zakazy_2.isEmpty()) {
            notfound_2.setVisibility(View.GONE);
        } else {
            notfound_2.setVisibility(View.VISIBLE);
        }
        //lv2.setAdapter(new Zakazy_2(Zakazy.this, zakazy_2));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            search_1.setText(result.getContents());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (tabHost.getCurrentTab() == 0 && search_1.getText().toString().isEmpty()) {
//            load_filter_1();
//            //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
//        }
//        if (tabHost.getCurrentTab() == 1 && search_2.getText().toString().isEmpty()) {
//            load_filter_2();
//            //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
//        }

        if (tabHost.getCurrentTab() == 0 ) {
            reload_1();
        }
        if (tabHost.getCurrentTab() == 1 ) {
            reload_2();
        }
    }

    public void reload_1(){
        if (spinner1.getSelectedItemPosition() == 0) izdatel = "null";
        if (spinner2.getSelectedItemPosition() == 0) clas = "null";

        //Toast.makeText(this, izdatel+" "+clas, Toast.LENGTH_SHORT).show();
        App.getApi().getCourier_knigi(String.valueOf(id),izdatel,clas).enqueue(new Callback<List<CourierKnigi>>() {
            @Override
            public void onResponse(Call<List<CourierKnigi>> call, Response<List<CourierKnigi>> response) {
                zakazy_1.clear();
                zakazy_1_buf.clear();
                zakazy_1.addAll(response.body());
                zakazy_1_buf.addAll(response.body());
                lv1.setAdapter(new Zakazy_1(Zakazy.this, zakazy_1));

                if (!zakazy_1.isEmpty()) {
                    notfound_1.setVisibility(View.GONE);
                } else {
                    notfound_1.setVisibility(View.VISIBLE);
                }
                notfound_1.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<CourierKnigi>> call, Throwable t) {

            }
        });


    }

    public void reload_2(){
        if (spinner3.getSelectedItemPosition() == 0) napravl = "null";
        if (spinner4.getSelectedItemPosition() == 0) sity = "null";
        if (spinner5.getSelectedItemPosition() == 0) school = "null";
        if (spinner6.getSelectedItemPosition() == 0) smena = "null";

        App.getApi().getCourier_zakazy(String.valueOf(id)).enqueue(new Callback<List<CourierClients>>() {
            @Override
            public void onResponse(Call<List<CourierClients>> call, Response<List<CourierClients>> response) {
//                zakazy_2.clear();
//                zakazy_2_buf.clear();
//                zakazy_2.addAll(response.body());
//                zakazy_2_buf.addAll(response.body());
//                lv2.setAdapter(new Zakazy_2(Zakazy.this, zakazy_2));
//
//

                if(response.body() != null){

                    if (response.body().size() != 0) {
                    notfound_2.setVisibility(View.GONE);
                    } else {
                        notfound_2.setVisibility(View.VISIBLE);
                    }
                    notfound_2.setText("Ничего не найдено");

                    lv2.setAdapter(new CourierClientsAdapter(Zakazy.this, response.body()));
                }


            }

            @Override
            public void onFailure(Call<List<CourierClients>> call, Throwable t) {

            }
        });
    }


    private void setAdapter_1(Spinner spinner, ArrayList<String> arr, int id) {
        switch (id) {
            case 1:
                arr.add(0, "Издательство");
                break;
            case 2:
                arr.add(0, "Класс");
                break;
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_nomenklatura_filter, arr) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = view.findViewById(R.id.tv1);

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinner.setAdapter(spinnerArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void load_filter_1(){
        App.getApi().getCourier_filter_1(String.valueOf(id)).enqueue(new Callback<Courier_filter_1>() {

            @Override
            public void onResponse(Call<Courier_filter_1> call, Response<Courier_filter_1> response) {
                setAdapter_1(spinner1, response.body().getIzdatel(), 1);
                setAdapter_1(spinner2, response.body().getClas(), 2);
                reload_1();
            }

            @Override
            public void onFailure(Call<Courier_filter_1> call, Throwable t) {
                Toast.makeText(Zakazy.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void load_filter_2(){
        App.getApi().getCourier_filter_2(String.valueOf(id)).enqueue(new Callback<Courier_filter_2>() {

            @Override
            public void onResponse(Call<Courier_filter_2> call, Response<Courier_filter_2> response) {
                setAdapter_2(spinner3, response.body().getNapravl(), 1);
                setAdapter_2(spinner4, response.body().getGorod(), 2);
                setAdapter_2(spinner5, response.body().getSchool(), 3);
                setAdapter_2(spinner6, response.body().getSmena(), 4);
                reload_2();
            }

            @Override
            public void onFailure(Call<Courier_filter_2> call, Throwable t) {
                Toast.makeText(Zakazy.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter_2(Spinner spinner, ArrayList<String> arr, int id) {
        switch (id) {
            case 1:
                arr.add(0, "Направление");
                break;
            case 2:
                arr.add(0, "Город");
                break;
            case 3:
                arr.add(0, "Школа");
                break;
            case 4:
                arr.add(0, "Смена");
                break;
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_nomenklatura_filter, arr) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = view.findViewById(R.id.tv1);

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinner.setAdapter(spinnerArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
