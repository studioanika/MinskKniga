package by.minskkniga.minskkniga.activity.Nomenklatura;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Barcode;
import by.minskkniga.minskkniga.activity.Zakazy.adapter.ZakazzyObrazacAdapet;
import by.minskkniga.minskkniga.adapter.Nomenklatura.Nav_zakaz;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import by.minskkniga.minskkniga.api.Class.nomenclatura.ObjectZakazyObrazci;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ImageButton filter;
    LinearLayout filter_layout;
    ImageButton back;
    ImageButton barcode;
    Button clear;
    Button ok;
    ListView lv;
    ExpandableListView exp;
    TextView notfound;
    EditText search;
    ImageView clear_q;

    ImageView imgmore;
    LinearLayout lin_header;
    LinearLayout lin_header1;

    ArrayList<Products> products;
    ArrayList<Products> products_buf;

    Spinner spinner1, spinner2, spinner3, spinner4;

    ArrayList<String> yesno;

    String avtor = "Автор";
    String izdatel = "Издатель";
    String obraz = "Образец";
    String class_ = "Класс";

    boolean zakaz = false;

    DialogFragment dlg_nomenclatura;

    DrawerLayout drawer;
    FloatingActionButton nav_fab;
    TextView nav_notfound;
    ListView nav_lv;
    String id_client;
    ArrayList<Zakaz_product> nav_product;
    List<ObjectZakazyObrazci> obrazciList = new ArrayList<>();
    AlertDialog.Builder ad;

    NavigationView nv;
    RelativeLayout rel;

    String filter_ = "";

    Animation animImgLeft, animImgRight;

    public void initialize(){
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgmore = (ImageView) findViewById(R.id.n_img_more);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        lin_header = (LinearLayout) findViewById(R.id.lin_header);
        lin_header1 = (LinearLayout) findViewById(R.id.lin_header1);

        rel = (RelativeLayout) findViewById(R.id.relll);
        clear = findViewById(R.id.clear);
        ok = findViewById(R.id.ok);
        search = findViewById(R.id.search);

        nv = (NavigationView) findViewById(R.id.nav_view);

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

        nav_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    vibrator.vibrate(20);
                }
                ad = new AlertDialog.Builder(Main.this);
                ad.setMessage("Удалить " + nav_product.get(i).name + "?");
                ad.setPositiveButton("ПОДТВЕРДИТЬ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        nav_product.remove(i);
                        nav_lv.setAdapter(new Nav_zakaz(Main.this, nav_product));
                        if (nav_product.size()!=0){
                            nav_notfound.setVisibility(View.GONE);
                        }else{
                            nav_notfound.setVisibility(View.VISIBLE);
                        }
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

        nav_product = new ArrayList<>();

        zakaz = getIntent().getBooleanExtra("zakaz", false);
        if (zakaz){
            id_client = getIntent().getStringExtra("id_client");
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        lv = findViewById(R.id.lv);
        exp = findViewById(R.id.exp);

        products = new ArrayList<>();
        products_buf = new ArrayList<>();


        filter_layout = findViewById(R.id.filter_layout);
        filter_layout.setVisibility(View.GONE);
        filter = findViewById(R.id.filter_button);

        notfound = findViewById(R.id.notfound);
        yesno = new ArrayList<>();
        yesno.add("Не выбран");
        yesno.add("Да");
        yesno.add("Нет");

        barcode = findViewById(R.id.barcode);
        search = findViewById(R.id.search);
        clear_q = findViewById(R.id.clear_q);
        clear_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
            }
        });

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

        Intent intent = getIntent();
        String sd  = intent.getStringExtra("menu");
        if(sd == null) startAnimation();

    }

    private void startAnimation() {

        YoYo.with(Techniques.Pulse)
                .delay(50)
                .repeat(1)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        imgmore.setVisibility(View.VISIBLE);
                    }
                })
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        imgmore.setVisibility(View.GONE);
                    }
                })
                .playOn(imgmore);

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
                    dlg_nomenclatura = new Add_Dialog(Main.this, "nomenclatura_info", String.valueOf(products.get(position).getId()), id_client);
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
                filter_ = "";
                avtor = "Автор";
                izdatel = "Издатель";
                obraz = "Образец";
                class_ = "Класс";
                reload();
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(Main.this, products));
                filter_layout.setVisibility(View.GONE);
                getNewFilter();
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

        //load_filter();
        getNewFilter();
    }

    public void return_product(Zakaz_product product){
//        Intent intent = new Intent(Main.this, Add.class);
//        intent.putExtra("zakaz", true);
//        intent.putExtra("id_client", id_client);
//        intent.putExtra("id", id);
//        startActivityForResult(intent, 1);


        nav_product.add(product);

        nav_lv.setAdapter(new Nav_zakaz(this, nav_product));
        startAnimation();
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
        startAnimation();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void search() {
        products.clear();
        for (Products buffer : products_buf) {
            if (buffer.getName().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    //buffer.getClas().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    //buffer.getIzdatel().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getArtikul().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    buffer.getSokrName().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
                    //buffer.getProdCena().toLowerCase().contains(search.getText().toString().toLowerCase()) ||
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

        if(spinner3.getSelectedItem().toString().contains("Да")) obraz = "Есть";
        if(spinner3.getSelectedItem().toString().contains("Нет")) obraz = "";



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

    private void setAdapter(final Spinner spinner, ArrayList<String> arr, final int id) {
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id_) {

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

    @Override
    public void onBackPressed() {

        Intent intent = getIntent();
        String sd  = intent.getStringExtra("menu");
        boolean b = intent.getBooleanExtra("type_z", false);
        if(sd == null) {
            if(!b) {
                Intent intent1 = new Intent();
                intent1.putExtra(Zakaz_product.class.getCanonicalName(), nav_product);
                setResult(RESULT_OK, intent1);
                finish();
            }else {
                try {
                    Intent intent1 = new Intent();
                    intent1.putExtra(ObjectZakazyObrazci.class.getCanonicalName(), (Serializable) obrazciList);
                    setResult(RESULT_OK, intent1);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }else super.onBackPressed();

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
                    if(!b)reload();
                    else {
                        lin_header.setVisibility(View.GONE);
                        loadComplects();
                    }
                }

            }

            @Override
            public void onFailure(Call<Products_filter> call, Throwable t) {

            }
        });

    }

    private void loadComplects() {

        App.getApi().getZakazyObrazci().enqueue(new Callback<List<ObjectZakazyObrazci>>() {
            @Override
            public void onResponse(Call<List<ObjectZakazyObrazci>> call, Response<List<ObjectZakazyObrazci>> response) {
                if(response.body() != null){

                    notfound.setVisibility(View.GONE);
                    lv.setVisibility(View.GONE);
                    exp.setVisibility(View.VISIBLE);
                    lin_header.setVisibility(View.GONE);
                    lin_header1.setVisibility(View.VISIBLE);

                    exp.setAdapter(new ZakazzyObrazacAdapet(response.body(), Main.this));

                }
            }

            @Override
            public void onFailure(Call<List<ObjectZakazyObrazci>> call, Throwable t) {

            }
        });

    }

    public void addToListComplect(final ObjectZakazyObrazci objectZakazyObrazci) {

        AlertDialog.Builder ad = new AlertDialog.Builder(Main.this);
        ad.setMessage("Добавить товар в список?");
        ad.setPositiveButton("ПОДТВЕРДИТЬ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if(obrazciList.contains(objectZakazyObrazci)) {
                    Toast.makeText(Main.this, "Уже имеется в комплекте...", Toast.LENGTH_SHORT).show();
                    return;
                }else obrazciList.add(objectZakazyObrazci);
            }
        });
        ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        ad.setCancelable(false);
        ad.show();



    }
}