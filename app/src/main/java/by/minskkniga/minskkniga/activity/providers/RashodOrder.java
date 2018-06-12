package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.category.Category;
import by.minskkniga.minskkniga.api.Class.category.PodCat;
import by.minskkniga.minskkniga.api.Class.category.ResponseProvScheta;
import by.minskkniga.minskkniga.api.Class.category.Schetum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RashodOrder extends AppCompatActivity {

    TextView tv_name;
    Spinner sp_chet, sp_cat, sp_podcat;
    EditText summa, comment;
    Button btn_cancel, btn_save;

    List<Category> categoryListFinal = null;
    List<Schetum> schetumListFinal = null;

    String id = "";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rashod_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        tv_name = (TextView) findViewById(R.id.r_o_pr);
        sp_chet = (Spinner) findViewById(R.id.r_o_chet);
        sp_cat = (Spinner) findViewById(R.id.r_o_cat);
        sp_podcat = (Spinner) findViewById(R.id.r_o_pod_cat);
        summa = (EditText) findViewById(R.id.r_o_wumm);
        comment = (EditText) findViewById(R.id.r_o_comment);
        btn_cancel = (Button) findViewById(R.id.r_o_cancel);
        btn_save = (Button) findViewById(R.id.r_o_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadData();
    }

    private void loadData() {

        App.getApi().getProvScheta(id).enqueue(new Callback<List<ResponseProvScheta>>() {
            @Override
            public void onResponse(Call<List<ResponseProvScheta>> call, Response<List<ResponseProvScheta>> response) {

                if(response.body() != null) {

                    final ResponseProvScheta responseProvScheta = response.body().get(0);

                    if(responseProvScheta.getCategory() != null){

                        ArrayList<String> arr = new ArrayList<>();
                        categoryListFinal = responseProvScheta.getCategory();
                        for (Category category :responseProvScheta.getCategory()
                             ) {
                            arr.add(category.getName());
                        }


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(RashodOrder.this, R.layout.adapter_nomenklatura_filter, arr);
                        sp_cat.setAdapter(spinnerArrayAdapter);
                        if(sp_cat.getAdapter().getCount() != 0) sp_cat.setSelection(0);
                        setPodCat(responseProvScheta.getCategory().get(0).getId(), responseProvScheta.getCategory());

                        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                setPodCat(responseProvScheta.getCategory().get(i).getId(), responseProvScheta.getCategory());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    if(responseProvScheta.getProviders() != null) {

                        tv_name.setText(responseProvScheta.getProviders());
                    }

                    if(responseProvScheta.getScheta() != null) {

                        ArrayList<String> arrayList = new ArrayList<>();
                        schetumListFinal = responseProvScheta.getScheta();
                        for (Schetum schetum:responseProvScheta.getScheta()
                             ) {
                            arrayList.add(schetum.getName());
                        }
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(RashodOrder.this, R.layout.adapter_nomenklatura_filter, arrayList);
                        sp_chet.setAdapter(spinnerArrayAdapter);
                    }

                }

            }

            @Override
            public void onFailure(Call<List<ResponseProvScheta>> call, Throwable t) {

            }
        });

    }

    private void setPodCat(String id, List<Category> category) {

        ArrayList<String> arrayList = new ArrayList<>();

        for (Category categoryItem :category
                ) {
            if(categoryItem.getId().contains(id)){

                for (PodCat podCat: categoryItem.getList()
                     ) {
                    arrayList.add(podCat.getName());
                }

            }
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(RashodOrder.this, R.layout.adapter_nomenklatura_filter, arrayList);
        sp_podcat.setAdapter(spinnerArrayAdapter);

    }


    private void sendData() {


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

}
