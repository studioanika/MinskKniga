package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.adapter.ZayavkiBoobIAdapter;
import by.minskkniga.minskkniga.activity.providers.adapter.ZayavkiProvidersNewAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.providers.Couriers;
import by.minskkniga.minskkniga.api.Class.providers.ProductForZayackaProvider;
import by.minskkniga.minskkniga.api.Class.providers.ProviderObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewProviderZayavka extends AppCompatActivity {
    private static final int REQUEST_CODE = 201;

    List<ProviderObject> list = new ArrayList<>();
    List<Products> listPr = new ArrayList<>();
    List<ProductForZayackaProvider> productForZayackaProviderList = new ArrayList<>();
    ProviderObject providerObject;

    ZayavkiProvidersNewAdapter adapter;

    private LinearLayoutManager mLayoutManager;
    SharedPreferences sp;
    SimpleDateFormat df;
    String[] arr = new String[]{"Черновик","Не обработан","Оприходован","Ожидаем","Отменен","Не обработан"};

    TextView new_z_blank, tv_date, tv_author, tv_inf_1, tv_inf_2;

    Spinner couriers_sp, status_sp;
    RecyclerView recyclerView;
    Button btn;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_provider_zayavka);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(providerObject != null) {
                    Intent intent = new Intent(NewProviderZayavka.this, SelectNomeclaturaActivity.class);
                    intent.putExtra("izdatel", providerObject.getName());
                    startActivityForResult(intent, REQUEST_CODE);
                }else showSelectProvider();
            }
        });

        initView();
        getCouriers();
        showSelectProvider();
    }

    private void getCouriers() {

        App.getApi().getAllCouriers().enqueue(new Callback<List<Couriers>>() {
            @Override
            public void onResponse(Call<List<Couriers>> call, Response<List<Couriers>> response) {
                ArrayList<String> arr = new ArrayList<>();
                if(response.body() != null){
                    for (Couriers cour: response.body()
                         ) {
                        arr.add(cour.getName());
                    }
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NewProviderZayavka.this, R.layout.adapter_nomenklatura_filter, arr);
                couriers_sp.setAdapter(spinnerArrayAdapter);
                if(arr != null) couriers_sp.setSelection(0);
            }

            @Override
            public void onFailure(Call<List<Couriers>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        tv_inf_1 = (TextView) findViewById(R.id.new_z_p_inf_1);
        tv_inf_2 = (TextView) findViewById(R.id.new_z_p_inf_2);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_pr_z);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        btn = (Button) findViewById(R.id.new_z_btn);
        status_sp = (Spinner) findViewById(R.id.new_z_status);
        couriers_sp = (Spinner) findViewById(R.id.new_z_courer);
        new_z_blank = (TextView) findViewById(R.id.new_z_blank);
        tv_date = (TextView) findViewById(R.id.new_z_date);
        tv_author = (TextView) findViewById(R.id.new_z_author);
        df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date currentDate = new Date();
        tv_date.setText(df.format(currentDate));
        tv_author.setText(sp.getString("login", ""));
        new_z_blank.setText("№xxx");
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Новый");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NewProviderZayavka.this, R.layout.adapter_nomenklatura_filter, arr);
        status_sp.setAdapter(spinnerArrayAdapter);
        status_sp.setSelection(0);
        status_sp.setClickable(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productForZayackaProviderList.size() == 0) showDialogEmpty();
                else sendZayavka();
            }
        });

    }

    private void sendZayavka() {

        int i = productForZayackaProviderList.size();
        String ds = "";

    }

    private void showDialogEmpty() {

        final AlertDialog alertDialog = new AlertDialog.Builder(NewProviderZayavka.this).create();
        alertDialog.setTitle("Предупреждение");
        alertDialog.setMessage("Не выбрано ни одной позиции товара. Выйти без сохранения?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ПОДТВЕРДИТЬ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ОТМЕНА",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    private void showSelectProvider(){
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_select_provider);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.cansel_dlg);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ProgressBar progressBar = (ProgressBar) dialogEdit.findViewById(R.id.progressBar);
        final ListView lv = (ListView) dialogEdit.findViewById(R.id.recycler);

        SearchView search = (SearchView) dialogEdit.findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<String> providers = new ArrayList<>();
                for (ProviderObject obj: list
                     ) {
                    if(obj.getName().toLowerCase().contains(s.toLowerCase()) || obj.getName().contains(s.toUpperCase()) || obj.getName().contains(s))
                        providers.add(obj.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewProviderZayavka.this,
                        android.R.layout.simple_list_item_1, providers);
                lv.setAdapter(adapter);

                return false;
            }
        });



        App.getApi().getAllProviders().enqueue(new Callback<List<ProviderObject>>() {
            @Override
            public void onResponse(Call<List<ProviderObject>> call, Response<List<ProviderObject>> response) {
                progressBar.setVisibility(View.GONE);
                ArrayList<String> providers = new ArrayList<>();
                providers.clear();
                if(response.body() != null) {
                    list = response.body();
                    for (ProviderObject object: response.body()
                         ) {
                        providers.add(object.getName());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewProviderZayavka.this,
                        android.R.layout.simple_list_item_1, providers);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ProviderObject>> call, Throwable t) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list != null){

                    try{
                        providerObject = list.get(i);
                        dialogEdit.dismiss();
                    }
                    catch (Exception e){}
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Intent intent = data;
                listPr = (List<Products>) intent.getSerializableExtra("list");
                String sd = "";

                setRecycler(listPr);

            }
        }
    }

    private void setRecycler(List<Products> listPr) {

        //productForZayackaProviderList.clear();

        for (Products products: listPr
             ) {

            try {
                ProductForZayackaProvider pfzp = new ProductForZayackaProvider();
                pfzp.setProducts(products);
                pfzp.setZayavka("0");
                productForZayackaProviderList.add(pfzp);
                String sd = "";
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        adapter = new ZayavkiProvidersNewAdapter(productForZayackaProviderList,recyclerView,NewProviderZayavka.this);
        recyclerView.setAdapter(adapter);

        int i = productForZayackaProviderList.size();

        setInfoList();

    }

    private void setInfoList() {

        try {
            Double byn = 0.0;
            Double weight = 0.0;

            for (ProductForZayackaProvider pr: productForZayackaProviderList
                 ) {

                int colvo  = Integer.parseInt(pr.getZayavka());
                double pr_weight = colvo * Double.parseDouble(pr.getProducts().getVes());
                double pr_byn = colvo * Double.parseDouble(pr.getProducts().getProdCena());
                weight += pr_weight;
                byn += pr_byn;
            }

            tv_inf_1.setText("Итого "+productForZayackaProviderList.size()+ " поз. на "+
                            String.format("%.2f",byn)+ " BYN");
            tv_inf_2.setText("Вес: "+ String.format("%.2f",weight)+" кг");

        } catch (NumberFormatException e) {

            e.printStackTrace();
        }


    }

    public void setList(int position, String zayavka){

        if(productForZayackaProviderList != null) {
            try{
                productForZayackaProviderList.get(position).setZayavka(zayavka);
            }
            catch (Exception e){}
        }
        setInfoList();
    }

}