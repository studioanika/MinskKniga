package by.minskkniga.minskkniga.activity.providers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import by.minskkniga.minskkniga.activity.providers.adapter.AdapterProviderRaschet;
import by.minskkniga.minskkniga.activity.providers.adapter.AdapterProviderZakazano;
import by.minskkniga.minskkniga.activity.providers.adapter.ZayavkiProductsAdapter;
import by.minskkniga.minskkniga.adapter.Nomenklatura.Nav_zakaz;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import by.minskkniga.minskkniga.api.Class.inventarizacia.InventarizaciaObject;
import by.minskkniga.minskkniga.api.Class.inventarizacia.Kniga;
import by.minskkniga.minskkniga.api.Class.providers.GetOzhidaemResponse;
import by.minskkniga.minskkniga.api.Class.providers.GetZakK;
import by.minskkniga.minskkniga.api.Class.providers.GetZakKObjectList;
import by.minskkniga.minskkniga.api.Class.providers.OzhidaemObject;
import by.minskkniga.minskkniga.api.Class.providers.ZayavkaInfo;
import by.minskkniga.minskkniga.api.RestApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProvidersZayavkiRaschetActivity extends AppCompatActivity {
    ProgressBar progressBar;

    List<ZayavkaInfo> list = new ArrayList<>();

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

    ArrayList<ZayavkaInfo> products;
    ArrayList<ZayavkaInfo> products_buf;

    Spinner spinner1, spinner2, spinner3, spinner4;

    ArrayList<String> yesno;

    String avtor = "Автор";
    String izdatel = "Издатель";
    String obraz = "Образец";
    String class_ = "Класс";
    String filter_ = "";

    boolean zakaz = false;


    TextView nav_notfound;
    ListView nav_lv;
    String id_client;
    ArrayList<Zakaz_product> nav_product;
    AlertDialog.Builder ad;

    TextView tv_name, tv_class, tv_izdatel, tv_artikul, tv_ostatok;
    ImageView img;

    Kniga _kniga;

    String id = "0";
    String url = "/api/inv_ed.php?";


    String id_provider = "";


    // TODO нужно обновление заявки

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



        nav_notfound = findViewById(R.id.nav_notfound);
        nav_lv = findViewById(R.id.nav_lv);


        nav_product = new ArrayList<>();


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

        //FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setVisibility(View.GONE);



    }

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_zayavki_raschet);

        progressBar = (ProgressBar) findViewById(R.id.prgrs);
        initialize();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                addToList(position);

            }
        });

        Intent intent = getIntent();
        //izdatel = intent.getStringExtra("izdatel");
        izdatel = "Издатель";
        id_provider = intent.getStringExtra("id");

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
                new IntentIntegrator(ProvidersZayavkiRaschetActivity.this)
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
                lv.setAdapter(new ZayavkiProductsAdapter(ProvidersZayavkiRaschetActivity.this, products));
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
                lv.setAdapter(new ZayavkiProductsAdapter(ProvidersZayavkiRaschetActivity.this, products));
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

//        if(list != null) {
//
//            if(isList(products_buf.get(position).getId())) {
//                Toast.makeText(ProvidersZayavkiRaschetActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
//                list.remove(products_buf.get(position));
//            } else {
//                Toast.makeText(ProvidersZayavkiRaschetActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
//                list.add(products_buf.get(position));
//            }
//
//        }

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
        for (ZayavkaInfo buffer : products_buf) {
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
        lv.setAdapter(new ZayavkiProductsAdapter(ProvidersZayavkiRaschetActivity.this, products));
    }

    public void reload() {
        progressBar.setVisibility(View.VISIBLE);

        if (spinner1.getSelectedItemPosition() == 0) avtor = "null";
        if (spinner2.getSelectedItemPosition() == 0) {
            if(izdatel.isEmpty() || izdatel.contains("Издатель")) izdatel = "null";
        }
        if (spinner3.getSelectedItemPosition() == 0) obraz = "null";
        if (spinner4.getSelectedItemPosition() == 0) class_ = "null";

        App.getApi().getProductsZayavka(avtor, izdatel, obraz, class_).enqueue(new Callback<List<ZayavkaInfo>>() {
            @Override
            public void onResponse(Call<List<ZayavkaInfo>> call, Response<List<ZayavkaInfo>> response) {
                products.clear();
                products_buf.clear();
                products.addAll(response.body());
                products_buf.addAll(response.body());
                lv.setAdapter(new ZayavkiProductsAdapter(ProvidersZayavkiRaschetActivity.this, products));

                if (!products.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");

                //spinner2.setEnabled(false);
                //spinner2.setClickable(false);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<ZayavkaInfo>> call, Throwable t) {
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
                Toast.makeText(ProvidersZayavkiRaschetActivity.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
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


    public void showAlertInfo(String id_) {

        final Dialog dialogEdit = new Dialog(this);

        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_inventarizacia);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.a_i_cancel);
        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.a_i_done);
        final TextView tv_obr = (TextView) dialogEdit.findViewById(R.id.a_i_obr);
        final TextView tv_ost_b = (TextView) dialogEdit.findViewById(R.id.a_i_ost_b);
        final TextView tv_cour = (TextView) dialogEdit.findViewById(R.id.a_i_cour);
        final EditText tv_sclad = (EditText) dialogEdit.findViewById(R.id.a_i_sklad);
        final TextView tv_fact = (TextView) dialogEdit.findViewById(R.id.a_i_fact);
        final TextView tv_nedos = (TextView) dialogEdit.findViewById(R.id.a_i_nedostacha);
        final TextView tv_nedos_i = (TextView) dialogEdit.findViewById(R.id.a_i_nedostacha_i);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getResult(tv_sclad, tv_nedos, tv_nedos_i);
                url = url +"id="+id;
                try{
                    Double d = Double.parseDouble(tv_nedos.getText().toString());
                    Double fact = Double.parseDouble(String.valueOf(_kniga.getFakt()));

                    double result = Double.parseDouble(tv_nedos.getText().toString());
                    if(result >0) url = url + "&ned=0&izl="+String.valueOf(result);
                    else if(result < 0)url = url + "&izl=0&ned="+String.valueOf(result);
                    else url = url + "&izl=0&ned=0";
                }
                catch (Exception e){}

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cc96297.tmweb.ru/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi api = retrofit.create(RestApi.class);

                api.setInvEd(url).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body() != null) {
                            String d = response.body().toString();
                            dialogEdit.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(ProvidersZayavkiRaschetActivity.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });



        //fact - то, что ввел из руки = недостача/излишек

        tv_sclad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getResult(tv_sclad, tv_nedos, tv_nedos_i);
            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });

        App.getApi().getInventarizacia(id_).enqueue(new Callback<InventarizaciaObject>() {
            @Override
            public void onResponse(Call<InventarizaciaObject> call, Response<InventarizaciaObject> response) {
                if(response.body() != null){
                    InventarizaciaObject object = response.body();
                    if(object.getResult() != null &&
                            object.getResult().contains("1")){

                        if(object.getKniga() != null){
                            Kniga kniga = object.getKniga();
                            _kniga = kniga;

                            if(_kniga.getObr() != null) tv_obr.setText(String.valueOf(_kniga.getObr()));
                            else tv_obr.setText("0");
                            if(_kniga.getFakt() != null) tv_fact.setText(String.valueOf(_kniga.getFakt()));
                            else tv_fact.setText("0");
                            if(_kniga.getCour() != null) tv_cour.setText(String.valueOf(_kniga.getFakt()));
                            else tv_cour.setText("0");
                            if(_kniga.getOst() != null) tv_ost_b.setText(String.valueOf(_kniga.getOst()));
                            else tv_ost_b.setText("0");

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<InventarizaciaObject> call, Throwable t) {
                Toast.makeText(ProvidersZayavkiRaschetActivity.this,
                        "Проверьте подключение к интернету....",
                        Toast.LENGTH_SHORT).show();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void getResult(EditText tv_sclad, TextView tv_nedostacha, TextView tv_nedostacha_i) {

        try{
            String text = tv_sclad.getText().toString();
            Double d = 0.0;

            try {
                d = Double.parseDouble(text);
            } catch (Exception e){

            }

            if(text.contains("+")) {
                d = 0.0;
                String[] arr_t = text.split("\\+");

                for (int i = 0; i< arr_t.length; i++){

                    Double res_d = Double.parseDouble(arr_t[i]);
                    d = res_d +d;
                }

            }


            Double fact = Double.parseDouble(String.valueOf(_kniga.getFakt()));

            double result = (fact - d);
            tv_nedostacha.setText(String.valueOf(result));

            if((fact - d) <0) {
                tv_nedostacha.setTextColor(getResources().getColor(R.color.red));
                tv_nedostacha.setTextColor(getResources().getColor(R.color.red));
                tv_nedostacha_i.setTextColor(getResources().getColor(R.color.red));
                tv_nedostacha_i.setText("Недостача");


            }else {
                tv_nedostacha.setTextColor(getResources().getColor(R.color.green));
                tv_nedostacha.setTextColor(getResources().getColor(R.color.green));
                tv_nedostacha_i.setTextColor(getResources().getColor(R.color.green));
                tv_nedostacha_i.setText("Излишек");
            }
        }
        catch (Exception e){
            Log.e("INVENTARIZACIA", e.toString());
        }

    }

    public void showAlertOzhidaem(String id_){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_pr_ozhidaem);

        final TextView name_kniga = (TextView) dialogEdit.findViewById(R.id.apro_name);
        final TextView apro_izdatel = (TextView) dialogEdit.findViewById(R.id.apro_izdatel);
        final TextView apro_sokr = (TextView) dialogEdit.findViewById(R.id.apro_sokr);
        final TextView apro_articul = (TextView) dialogEdit.findViewById(R.id.apro_articul);
        final TextView apro_class = (TextView) dialogEdit.findViewById(R.id.apro_class);
        final TextView apro_result = (TextView) dialogEdit.findViewById(R.id.apro_result);
        TextView apro_ok = (TextView) dialogEdit.findViewById(R.id.apro_ok);

        apro_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        final ListView apro_lv = (ListView) dialogEdit.findViewById(R.id.apro_lv);


        App.getApi().getOjidaem(id_).enqueue(new Callback<List<GetOzhidaemResponse>>() {
            @Override
            public void onResponse(Call<List<GetOzhidaemResponse>> call, Response<List<GetOzhidaemResponse>> response) {
                if(response.body() != null){

                    GetOzhidaemResponse getZakK = response.body().get(0);

                    apro_articul.setText(getZakK.getArt());
                    apro_class.setText(getZakK.getClas());
                    apro_izdatel.setText(getZakK.getIzd());
                    name_kniga.setText(getZakK.getName());
                    apro_sokr.setText(getZakK.getSokr_name());
                    apro_result.setText("Итого "+getZakK.getCount_client()+" позиции. "+getZakK.getZak_no()+ " шт.");

                    if(getZakK.getList() != null){
                        apro_lv.setAdapter(new AdapterProviderRaschet(ProvidersZayavkiRaschetActivity.this, (ArrayList<OzhidaemObject>) getZakK.getList()));
                        setListViewBase(apro_lv);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<GetOzhidaemResponse>> call, Throwable t) {
                Toast.makeText(ProvidersZayavkiRaschetActivity.this,
                        "Проверьте подключение к интернету....",
                        Toast.LENGTH_SHORT).show();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    public void showAlertZakazano(String id){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_pr_zakazano);

        final TextView name_kniga = (TextView) dialogEdit.findViewById(R.id.apro_name);
        final TextView apro_izdatel = (TextView) dialogEdit.findViewById(R.id.apro_izdatel);
        final TextView apro_sokr = (TextView) dialogEdit.findViewById(R.id.apro_sokr);
        final TextView apro_articul = (TextView) dialogEdit.findViewById(R.id.apro_articul);
        final TextView apro_class = (TextView) dialogEdit.findViewById(R.id.apro_class);
        final TextView apro_result = (TextView) dialogEdit.findViewById(R.id.apro_result);
        TextView apro_ok = (TextView) dialogEdit.findViewById(R.id.apro_ok);

        apro_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        final ListView apro_lv = (ListView) dialogEdit.findViewById(R.id.apro_lv);

        App.getApi().getZakK(id).enqueue(new Callback<List<GetZakK>>() {
            @Override
            public void onResponse(Call<List<GetZakK>> call, Response<List<GetZakK>> response) {
                if(response.body() != null){
                    GetZakK getZakK = response.body().get(0);

                    apro_articul.setText(getZakK.getArt());
                    apro_class.setText(getZakK.getClas());
                    apro_izdatel.setText(getZakK.getIzd());
                    name_kniga.setText(getZakK.getName());
                    apro_sokr.setText(getZakK.getSokr_name());
                    apro_result.setText("Итого "+getZakK.getCount_client()+" позиции. "+getZakK.getZak_no()+ " шт.");

                    if(getZakK.getList() != null){
                        apro_lv.setAdapter(new AdapterProviderZakazano(ProvidersZayavkiRaschetActivity.this, (ArrayList<GetZakKObjectList>) getZakK.getList()));
                        setListViewBase(apro_lv);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<GetZakK>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

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
