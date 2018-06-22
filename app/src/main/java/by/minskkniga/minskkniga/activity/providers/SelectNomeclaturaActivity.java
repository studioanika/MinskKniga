package by.minskkniga.minskkniga.activity.providers;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Barcode;
import by.minskkniga.minskkniga.adapter.Nomenklatura.Nav_zakaz;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectNomeclaturaActivity extends AppCompatActivity {

    ProgressBar progressBar;

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

    boolean load_filter_1 = false;

    ArrayList<Products> products;
    ArrayList<Products> products_buf;

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

    public void initialize(){
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                intent.putExtra("list",(Serializable) list);
                finish();
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
        nav_fab = findViewById(R.id.nav_fab);

        nav_fab.setOnClickListener(new View.OnClickListener() {
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

        barcode = findViewById(R.id.barcode);
        search = findViewById(R.id.search);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.prgrs);

    }

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomenklatura);
        intent = getIntent();
        izdatel = intent.getStringExtra("izdatel");
        filter_ = "izdatel";
        initialize();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                addToList(position);

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
                new IntentIntegrator(SelectNomeclaturaActivity.this)
                        .setOrientationLocked(true)
                        .setCaptureActivity(Barcode.class)
                        .initiateScan();
            }
        });

//start onclick filter
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_ = "";
                load_filter();
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                spinner4.setSelection(0);
                avtor = "Автор";
                izdatel = "Издатель";
                obraz = "Образец";
                class_ = "Класс";
                reload();
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(SelectNomeclaturaActivity.this, products));
                filter_layout.setVisibility(View.GONE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    avtor = spinner1.getSelectedItem().toString();
                    izdatel = spinner2.getSelectedItem().toString();
                    obraz = spinner3.getSelectedItem().toString();
                    class_ = spinner4.getSelectedItem().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                reload();
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(SelectNomeclaturaActivity.this, products));
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
        reload();
    }

    private void addToList(int position) {

        if(list != null) {

            if(isList(products_buf.get(position).getId())) {
                Toast.makeText(SelectNomeclaturaActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                list.remove(products_buf.get(position));
            } else {
                Toast.makeText(SelectNomeclaturaActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
                list.add(products_buf.get(position));
            }

        }

    }

    private boolean isList(String id){

        for (Products pr : list
                ) {

            if(pr.getId().contains(id)) return true;
            else return false;

        }

        return false;
    }

    public void return_product(Zakaz_product product){
//        Intent intent = new Intent(Main.this, Add.class);
//        intent.putExtra("zakaz", true);
//        intent.putExtra("id_client", id_client);
//        intent.putExtra("id", id);
//        startActivityForResult(intent, 1);


        nav_product.add(product);

        nav_lv.setAdapter(new Nav_zakaz(this, nav_product));

        if (nav_product.size()!=0){
            nav_notfound.setVisibility(View.GONE);
        }else{
            nav_notfound.setVisibility(View.VISIBLE);
        }
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
        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(SelectNomeclaturaActivity.this, products));
    }

    public void reload() {
        progressBar.setVisibility(View.VISIBLE);

        if (spinner1.getSelectedItemPosition() == 0) avtor = "null";
        if (spinner2.getSelectedItemPosition() == 0) {
            if(izdatel.isEmpty() || izdatel.contains("Издатель")) izdatel = "null";
        }
        if (spinner3.getSelectedItemPosition() == 0) obraz = "null";
        if (spinner4.getSelectedItemPosition() == 0) class_ = "null";

        App.getApi().getProducts(avtor, izdatel, obraz, class_).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                products.clear();
                products_buf.clear();
                products.addAll(response.body());
                products_buf.addAll(response.body());
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(SelectNomeclaturaActivity.this, products));

                if (!products.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");

                spinner2.setEnabled(false);
                spinner2.setClickable(false);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
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
                Toast.makeText(SelectNomeclaturaActivity.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(Spinner spinner, ArrayList<String> arr, final int id) {
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
        load_filter_1 = true;


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id_) {

                int i = position + (int)id_;
                if(id == 1) {
                    if(position != 0) {
                        avtor = spinner1.getSelectedItem().toString();
                        if(filter_.contains("clas") || filter_.contains("izdatel") ||
                                filter_.contains("obtazec")){
                            filter_ = filter_+",autor";
                        } else filter_ = "autor";

                        getNewFilter();
                    }

                }
                if(id == 2) {
                    if(position != 0) {
                        izdatel = spinner2.getSelectedItem().toString();
                        if(filter_.contains("clas") || filter_.contains("autor") ||
                                filter_.contains("obtazec")){
                            filter_ = filter_+",izdatel";
                        } else filter_ = "izdatel";

                        getNewFilter();
                    }
                }
                if(id == 3) {
                    if(position != 0) {
                        obraz = spinner3.getSelectedItem().toString();
                        if(filter_.contains("clas") || filter_.contains("izdatel") ||
                                filter_.contains("autor")){
                            filter_ = filter_+",obtazec";
                        } else filter_ = "obtazec";

                        getNewFilter();
                    }
                }
                if(id == 4) {
                    if(position != 0) {
                        class_ = spinner4.getSelectedItem().toString();
                        if(filter_.contains("autor") || filter_.contains("izdatel") ||
                                filter_.contains("obtazec")){
                            filter_ = filter_+",clas";
                        } else filter_ = "clas";

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

        String _autor = "";
        if(!avtor.contains("null")) _autor = avtor;
        if(avtor.contains("Автор")) _autor = "";

        String _obraz = "";
        if(!obraz.contains("null")) _obraz = obraz;
        if(obraz.contains("Образец")) _obraz = "";

        String _izdatel = "";
        if(!izdatel.contains("null")) _izdatel = izdatel;
        if(izdatel.contains("Издатель")) _izdatel = "";

        App.getApi().getProductsfilter(_class, _obraz, _autor, _izdatel,filter_).enqueue(new Callback<Products_filter>() {
            @Override
            public void onResponse(Call<Products_filter> call, Response<Products_filter> response) {

                if(response.body() != null){
                    if(!filter_.contains("autor"))setAdapter(spinner1, response.body().getAutor(), 1);
                    if(!filter_.contains("izdatel"))setAdapter(spinner2, response.body().getIzdatel(), 2);
                    if(!filter_.contains("obrazec"))setAdapter(spinner3, yesno, 3);
                    if(!filter_.contains("clas"))setAdapter(spinner4, response.body().getClas(), 4);
                    reload();
                }

            }

            @Override
            public void onFailure(Call<Products_filter> call, Throwable t) {

            }
        });

    }
}
