package by.minskkniga.minskkniga.activity.Zakazy;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Barcode;
import by.minskkniga.minskkniga.adapter.Nomenklatura.AdapterShowOtgr;
import by.minskkniga.minskkniga.adapter.Nomenklatura.ItemShowOtgr;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import by.minskkniga.minskkniga.api.Class.zakazy.InfoShowOtgr;
import by.minskkniga.minskkniga.api.Class.zakazy.ShowOtgrResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowOtgruzennoe extends AppCompatActivity {
    List<Products> list = new ArrayList<>();

    ImageButton filter;
    LinearLayout filter_layout;
    ImageButton back;
    ImageButton barcode;
    Button clear;
    Button ok;
    ListView lv;
    TextView notfound;
    EditText search;
    TextView caption;

    ArrayList<ItemShowOtgr> products;
    ArrayList<ItemShowOtgr> products_buf;

    Spinner spinner1, spinner2, spinner3, spinner4;

    ArrayList<String> yesno;

    String avtor = "Автор";
    String izdatel = "Издатель";
    String obraz = "Образец";
    String class_ = "Класс";
    String filter_ = "";

    boolean zakaz = false;

    DialogFragment dlg_nomenclatura;

    DrawerLayout drawer;
    FloatingActionButton nav_fab;
    TextView nav_notfound;
    ListView nav_lv;
    String id_client;
    ArrayList<Zakaz_product> nav_product;
    AlertDialog.Builder ad;

    String id, name;


    TextView tv_skidka_proc, tv_price, tv_oplacheno,tv_skidka,tv_dolg;

    public void initialize(){
        try {
            back = findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  finish();
                }
            });


            tv_skidka_proc = findViewById(R.id.tv_skidka_proc);
            tv_price = findViewById(R.id.tv_price);
            tv_oplacheno = findViewById(R.id.tv_oplacheno);
            tv_skidka = findViewById(R.id.tv_skidka);
            tv_dolg = findViewById(R.id.tv_dolg);

            spinner1 = findViewById(R.id.spinner1);
            spinner2 = findViewById(R.id.spinner2);
            spinner3 = findViewById(R.id.spinner3);
            spinner4 = findViewById(R.id.spinner4);
            caption = findViewById(R.id.caption);

            clear = findViewById(R.id.clear);
            ok = findViewById(R.id.ok);
            search = findViewById(R.id.search);

            drawer = findViewById(R.id.drawer);
            nav_fab = findViewById(R.id.nav_fab);


            nav_notfound = findViewById(R.id.nav_notfound);
            nav_lv = findViewById(R.id.nav_lv);


            nav_product = new ArrayList<>();

            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");

            lv = findViewById(R.id.lv);

            products = new ArrayList<>();
            products_buf = new ArrayList<>();


            filter_layout = findViewById(R.id.filter_layout);
            filter_layout.setVisibility(View.GONE);
            filter = findViewById(R.id.filter_button);

            notfound = findViewById(R.id.notfound);
            yesno = new ArrayList<>();
            yesno.add("Да");
            yesno.add("Нет");

            barcode = findViewById(R.id.barcode);
            search = findViewById(R.id.search);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_otgruzennoe);
        initialize();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent = new Intent(InventarizaciaActivity.this, InventarizaciaInfoActivity.class);
//                if(products_buf != null) intent.putExtra("id", products_buf.get(position).getId());
//                startActivity(intent);

            }
        });

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        caption.setText(name);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter_layout.getVisibility() == View.VISIBLE)
                    filter_layout.setVisibility(View.GONE);
                else
                    filter_layout.setVisibility(View.VISIBLE);
            }
        });

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(ShowOtgruzennoe.this)
                        .setOrientationLocked(true)
                        .setCaptureActivity(Barcode.class)
                        .initiateScan();
            }
        });

//start onclick filter
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                spinner4.setSelection(0);
                avtor = "Автор";
                izdatel = "Издатель";
                obraz = "Образец";
                class_ = "Класс";
                reload();
                lv.setAdapter(new AdapterShowOtgr(ShowOtgruzennoe.this, products));
                filter_layout.setVisibility(View.GONE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avtor = spinner1.getSelectedItem().toString();
                izdatel = spinner2.getSelectedItem().toString();
                obraz = spinner3.getSelectedItem().toString();
                class_ = spinner4.getSelectedItem().toString();
                reload();
                lv.setAdapter(new AdapterShowOtgr(ShowOtgruzennoe.this, products));
                filter_layout.setVisibility(View.GONE);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //spinner2.setEnabled(false);
        //spinner2.setClickable(false);
        //load_filter();


        getNewFilter();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            search.setText(result.getContents());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void search() {
        products.clear();
        for (ItemShowOtgr buffer : products_buf) {
            if (buffer.getName_zak().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getSokr_name().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getBarcode().toLowerCase().contains(search.getText().toString().toLowerCase())) {
                products.add(buffer);
            }
        }
        if (!products.isEmpty()) {
            notfound.setVisibility(View.GONE);
        } else {
            notfound.setVisibility(View.VISIBLE);
        }
        lv.setAdapter(new AdapterShowOtgr(ShowOtgruzennoe.this, products));
    }

    public void reload() {
        if (spinner1.getSelectedItemPosition() == 0) avtor = "null";
        if (spinner2.getSelectedItemPosition() == 0) izdatel = "null";
        if (spinner3.getSelectedItemPosition() == 0) obraz = "null";
        if (spinner4.getSelectedItemPosition() == 0) class_ = "null";

        if(spinner3.getSelectedItem().toString().contains("Да")) obraz = "Есть";
        if(spinner3.getSelectedItem().toString().contains("Нет")) obraz = "";

        App.getApi().getOtgruzki(avtor, izdatel, obraz, class_, id).enqueue(new Callback<List<ShowOtgrResponse>>() {
            @Override
            public void onResponse(Call<List<ShowOtgrResponse>> call, Response<List<ShowOtgrResponse>> response) {
                try {

                    if(response.body() != null){

                        ShowOtgrResponse otgrResponse = response.body().get(0);

                        InfoShowOtgr otgr = otgrResponse.getInfo();

                        tv_skidka_proc.setText("Скидка("+otgr.getSkidkaProc()+"%)");
                        tv_price.setText(otgr.getTovar());
                        tv_oplacheno.setText(String.valueOf(otgr.getOplaty()));
                        tv_skidka.setText(otgr.getSkidka());
                        tv_dolg.setText(otgr.getItog());

                        products.clear();
                        products_buf.clear();

                        if(otgrResponse.getList() != null && otgrResponse.getList().size() !=0){

                            products.addAll(otgrResponse.getList());
                            products_buf.addAll(otgrResponse.getList());
                            lv.setAdapter(new AdapterShowOtgr(ShowOtgruzennoe.this, products));

                            notfound.setVisibility(View.GONE);
                        }else {
                            notfound.setVisibility(View.VISIBLE);
                        }

                    }else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                products.clear();
//                products_buf.clear();
//                products.addAll(response.body());
//                products_buf.addAll(response.body());
//                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(ShowOtgruzennoe.this, products));
//
//                if (!products.isEmpty()) {
//
//                } else {
//
//                }
                notfound.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<ShowOtgrResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spinner1.getSelectedItemPosition() == 0 &&
                spinner2.getSelectedItemPosition() == 0 &&
                spinner3.getSelectedItemPosition() == 0 &&
                spinner4.getSelectedItemPosition() == 0 &&
                search.getText().toString().isEmpty() &&
                lv.getFirstVisiblePosition()==0) {
            load_filter();
        }
    }

    public void load_filter(){
        if (spinner1.getSelectedItemPosition() == 0) avtor = "null";
        if (spinner2.getSelectedItemPosition() == 0) izdatel = "null";
        if (spinner3.getSelectedItemPosition() == 0) obraz = "null";
        if (spinner4.getSelectedItemPosition() == 0) class_ = "null";

        App.getApi().getProducts_filter().enqueue(new Callback<Products_filter>() {

            @Override
            public void onResponse(Call<Products_filter> call, Response<Products_filter> response) {
                setAdapter(spinner1, response.body().getAutor(), 1);
                setAdapter(spinner2, response.body().getIzdatel(), 2);
                setAdapter(spinner3, yesno, 3);
                setAdapter(spinner4, response.body().getClas(), 4);
                reload();
            }

            @Override
            public void onFailure(Call<Products_filter> call, Throwable t) {
                Toast.makeText(ShowOtgruzennoe.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(final Spinner spinner, ArrayList<String> arr, int id) {
        switch (id) {
            case 1:
                arr.add(0, "Автор");
                break;
            case 2:
                arr.add(0, "Издатель");
                break;
            case 3:
                if (!arr.get(0).equals("Образец"))
                    arr.add(0, "Образец");
                break;
            case 4:
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

                long i1 = R.id.spinner1;
                long i2 = R.id.spinner2;
                long i3 = R.id.spinner3;
                long i4 = R.id.spinner4;
                long vi = spinner.getId();

                if(vi == i1) {
                    if(position != 0) {
                        if(!spinner1.getSelectedItem().toString().contains("Не выбран")){
                            avtor = spinner1.getSelectedItem().toString();
                            if(filter_.contains("clas") || filter_.contains("izdatel") ||
                                    filter_.contains("obrazec")){
                                filter_ = filter_+",autor";
                            } else filter_ = "autor";
                        }else {
                            if(filter_.contains("clas") || filter_.contains("izdatel") ||
                                    filter_.contains("obrazec")){
                                filter_ = filter_.replace(",autor", "");
                            } else filter_ = filter_.replace("autor", "");
                        }

                        getNewFilter();
                    }

                }
                if(vi == i2) {
                    if(position != 0) {
                        if(!spinner2.getSelectedItem().toString().contains("Не выбран")) {
                            izdatel = spinner2.getSelectedItem().toString();
                            if (filter_.contains("clas") || filter_.contains("autor") ||
                                    filter_.contains("obrazec")) {
                                filter_ = filter_ + ",izdatel";
                            } else filter_ = "izdatel";
                        }else {
                            if (filter_.contains("clas") || filter_.contains("autor") ||
                                    filter_.contains("obrazec")) {
                                filter_ = filter_.replace(",izdatel", "");
                            } else filter_ = filter_.replace("izdatel", "");
                        }

                        getNewFilter();
                    }
                }
                if(vi == i3) {
                    if(position != 0) {
                        if(!spinner3.getSelectedItem().toString().contains("Не выбран")) {
                            obraz = spinner3.getSelectedItem().toString();
                            if (filter_.contains("clas") || filter_.contains("izdatel") ||
                                    filter_.contains("autor")) {
                                filter_ = filter_ + ",obrazec";
                            } else filter_ = "obrazec";
                        }else {
                            if (filter_.contains("clas") || filter_.contains("izdatel") ||
                                    filter_.contains("autor")) {
                                filter_ = filter_.replace(",obrazec", "");
                            } else filter_ = filter_.replace("obrazec", "");
                        }
                        getNewFilter();
                    }
                }
                if(vi == i4) {
                    if(position != 0) {
                        if(!spinner4.getSelectedItem().toString().contains("Не выбран")) {
                            class_ = spinner4.getSelectedItem().toString();
                            if (filter_.contains("autor") || filter_.contains("izdatel") ||
                                    filter_.contains("obrazec")) {
                                filter_ = filter_ + ",clas";
                            } else filter_ = "clas";
                        }else {
                            if (filter_.contains("autor") || filter_.contains("izdatel") ||
                                    filter_.contains("obrazec")) {
                                filter_ = filter_.replace(",clas", "");
                            } else filter_ = filter_.replace("clas", "");
                        }

                        getNewFilter();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getNewFilter(){
        String _class = "";
        if(!class_.contains("null")) _class = class_;
        if(class_.contains("Класс")) _class = "";
        if(class_.contains("Не выбран")) _class = "";

        String _autor = "";
        if(!avtor.contains("null")) _autor = avtor;
        if(avtor.contains("Автор")) _autor = "";
        if(avtor.contains("Не выбран")) _autor = "";

        String _obraz = "";
        if(!obraz.contains("null")) _obraz = obraz;
        if(obraz.contains("Образец")) _obraz = "";
        else if(obraz.contains("Да")) _obraz = "Есть";
        else if(obraz.contains("Нет")) _obraz = "";
        else if(obraz.contains("Не выбран")) _obraz = "";

        String _izdatel = "";
        if(!izdatel.contains("null")) _izdatel = izdatel;
        if(izdatel.contains("Издатель")) _izdatel = "";
        if(izdatel.contains("Не выбран")) _izdatel = "";

        App.getApi().getProductsfilter(_class, _obraz, _autor, _izdatel,filter_).enqueue(new Callback<Products_filter>() {
            @Override
            public void onResponse(Call<Products_filter> call, Response<Products_filter> response) {

                if(response.body() != null){
                    if(!filter_.contains("autor"))setAdapter(spinner1, response.body().getAutor(), 1);
                    if(!filter_.contains("izdatel"))setAdapter(spinner2, response.body().getIzdatel(), 2);
                    if(!filter_.contains("obrazec"))setAdapter(spinner3, yesno, 3);
                    if(!filter_.contains("clas"))setAdapter(spinner4, response.body().getClas(), 4);
                    Intent intent = getIntent();
                    boolean b = intent.getBooleanExtra("type_z", false);
                    reload();
                }

            }

            @Override
            public void onFailure(Call<Products_filter> call, Throwable t) {

            }
        });

    }

}
