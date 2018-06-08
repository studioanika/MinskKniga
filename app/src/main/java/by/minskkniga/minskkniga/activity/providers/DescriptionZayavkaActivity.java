package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.adapter.ZayavkiBoobIAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.ZavInfo;
import by.minskkniga.minskkniga.api.Class.providerzayavka.WhatZayavkaI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionZayavkaActivity extends AppCompatActivity {

    private static final String TAG = "DescriptionZayavkaActivity";
    ProgressBar progress;
    TextView tva_d_z_id, tva_d_z_date,tva_d_z_author,tva_d_z_tv_result_summ,tva_d_z_tv_result_weight;
    Spinner spinner;
    CheckBox a_d_z_note, a_d_z_oplata;

    private RecyclerView recyclerView;
    private ZayavkiBoobIAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<WhatZayavkaI> studentList;

    String[] arr = new String[]{"Черновик","Не обработан","Оприходован","Ожидаем","Отменен","Не обработан"};
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_zayavka);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        initView();
        loadData(id);
    }

    private void initView() {

        progress = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.a_d_z_tv_result_recycler);


        studentList = new ArrayList<WhatZayavkaI>();

        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        tva_d_z_id = (TextView) findViewById(R.id.a_d_z_id);
        tva_d_z_date = (TextView) findViewById(R.id.a_d_z_date);
        tva_d_z_author = (TextView) findViewById(R.id.a_d_z_author);
        tva_d_z_tv_result_summ = (TextView) findViewById(R.id.a_d_z_tv_result_summ);
        tva_d_z_tv_result_weight = (TextView) findViewById(R.id.a_d_z_tv_result_weight);

        spinner = (Spinner) findViewById(R.id.a_d_z_status);

        a_d_z_note = (CheckBox) findViewById(R.id.a_d_z_note);
        a_d_z_oplata = (CheckBox) findViewById(R.id.a_d_z_oplata);
    }

    private void loadData(String id) {
        progress.setVisibility(View.VISIBLE);
        App.getApi().getZavInfo(id).enqueue(new Callback<ZavInfo>() {
            @Override
            public void onResponse(Call<ZavInfo> call, Response<ZavInfo> response) {

                progress.setVisibility(View.GONE);
                ZavInfo info = response.body();
                if(info != null) setInfo(info);
                String sd = "";


            }

            @Override
            public void onFailure(Call<ZavInfo> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void setInfo(ZavInfo description) {

        tva_d_z_id.setText("№"+description.getId());
        tva_d_z_date.setText(description.getDate());
        tva_d_z_author.setText(description.getAutor());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_nomenklatura_filter, arr);
        spinner.setAdapter(spinnerArrayAdapter);
        if(description.getStatus()!=null){
            try{
                int select = Integer.parseInt(description.getStatus());
                spinner.setSelection(select);

                if(select ==0) a_d_z_note.setChecked(true);
            }
            catch (Exception e){}
        }

        if(Integer.parseInt(description.getOplacheno()) == 0) a_d_z_oplata.setChecked(false);
        else a_d_z_oplata.setChecked(true);

        setRelResult(description);

        mAdapter = new ZayavkiBoobIAdapter(description.getWhat_zakazal(),recyclerView,DescriptionZayavkaActivity.this);
        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("LongLogTag")
    private void setRelResult(ZavInfo description) {

        int col = 0;
        if(description.getWhat_zakazal() != null) col = description.getWhat_zakazal().size();
        double summ = 0;
        double weight = 0;

        try{

            summ = description.getSumma();
            weight = description.getVes();

        }
        catch (Exception e){}

        Log.e(TAG, "col_zayavki = "+col);
        Log.e(TAG, "summ_zayavki = "+summ);
        Log.e(TAG, "weight_zayavki = "+weight);

        tva_d_z_tv_result_summ.setText("Итого "+col+" поз. на "+summ+" BYN");
        tva_d_z_tv_result_weight.setText("Вес: "+weight+" кг");
    }

    public void startInfoDescr(String id, String zak){

        Intent intent = new Intent(DescriptionZayavkaActivity.this, DescriptionZayavkaBookActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("zak", zak);
        startActivity(intent);

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
