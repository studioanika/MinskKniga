package by.minskkniga.minskkniga.activity.Nomenklatura;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Barcode;
import by.minskkniga.minskkniga.adapter.Nomenklatura.Nav_zakaz;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Main extends AppCompatActivity {

    ImageButton filter;
    RelativeLayout filter_layout;
    ImageButton back;
    ImageButton barcode;
    Button clear;
    Button ok;
    ListView lv;
    TextView notfound;
    EditText search;

    ArrayList<Products> products;
    ArrayList<Products> products_buf;

    Spinner spinner1, spinner2, spinner3, spinner4;

    ArrayList<String> yesno;
    IntentIntegrator qrScan;

    String avtor = "Автор";
    String izdatel = "Издатель";
    String obraz = "Образец";
    String class_ = "Класс";

    boolean zakaz = false;

    DialogFragment dlg_nomenclatura;

    DrawerLayout drawer;
    Button nav_ok;
    TextView nav_notfound;
    ListView nav_lv;
    String id_client;
    ArrayList<Zakaz_product> nav_product;

    public void initialize(){
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);


        clear = findViewById(R.id.clear);
        ok = findViewById(R.id.ok);
        search = findViewById(R.id.search);

        drawer = findViewById(R.id.drawer);
        nav_ok = findViewById(R.id.nav_ok);

        nav_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Zakaz_product.class.getCanonicalName(), nav_product);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        nav_notfound = findViewById(R.id.nav_notfound);
        nav_lv = findViewById(R.id.nav_lv);

        nav_product = new ArrayList<>();

        zakaz = getIntent().getBooleanExtra("zakaz", false);
        if (zakaz){
            id_client = getIntent().getStringExtra("id_client");
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

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

        qrScan = new IntentIntegrator(this);
        barcode = findViewById(R.id.barcode);
        search = findViewById(R.id.search);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Add.class);
                intent.putExtra("zakaz", false);
                intent.putExtra("id", "null");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomenklatura);
        initialize();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (zakaz){
                    dlg_nomenclatura = new Add_Dialog(Main.this, "nomenclatura_info", String.valueOf(products.get(position).getName()), String.valueOf(products.get(position).getId()));
                    dlg_nomenclatura.show(getFragmentManager(), "");
                }else{
                    Intent intent = new Intent(Main.this, Add.class);
                    intent.putExtra("zakaz", false);
                    intent.putExtra("id", products.get(position).getId());
                    startActivity(intent);
                }
            }
        });

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
                new IntentIntegrator(Main.this)
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
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(Main.this, products));
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
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(Main.this, products));
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

        load_filter();
    }

    public void return_product(String id){
        Intent intent = new Intent(Main.this, Add.class);
        intent.putExtra("zakaz", true);
        intent.putExtra("id_client", id_client);
        intent.putExtra("id", id);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            search.setText(result.getContents());
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Zakaz_product product = data.getParcelableExtra(Zakaz_product.class.getCanonicalName());

                    nav_product.add(product);

                    nav_lv.setAdapter(new Nav_zakaz(this, nav_product));

                    if (nav_product.size()!=0){
                        nav_notfound.setVisibility(View.GONE);
                    }else{
                        nav_notfound.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void search() {
        products.clear();
        for (Products buffer : products_buf) {
            if (buffer.getName().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getClas().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getIzdatel().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getArtikul().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getSokrName().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getProdCena().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getBarcode().toLowerCase().contains(search.getText().toString().toLowerCase())) {
                products.add(buffer);
            }
        }
        if (!products.isEmpty()) {
            notfound.setVisibility(View.GONE);
        } else {
            notfound.setVisibility(View.VISIBLE);
        }
        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(Main.this, products));
    }

    public void reload() {
        if (spinner1.getSelectedItemPosition() == 0) avtor = "null";
        if (spinner2.getSelectedItemPosition() == 0) izdatel = "null";
        if (spinner3.getSelectedItemPosition() == 0) obraz = "null";
        if (spinner4.getSelectedItemPosition() == 0) class_ = "null";

        App.getApi().getProducts(avtor, izdatel, obraz, class_).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                products.clear();
                products_buf.clear();
                products.addAll(response.body());
                products_buf.addAll(response.body());
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(Main.this, products));

                if (!products.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

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
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(Spinner spinner, ArrayList<String> arr, int id) {
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