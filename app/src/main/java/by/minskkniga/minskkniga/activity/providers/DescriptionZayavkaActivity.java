package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.adapter.ZayavkiBoobIAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.providers.ProductForZayackaProvider;
import by.minskkniga.minskkniga.api.Class.providers.ProviderObject;
import by.minskkniga.minskkniga.api.Class.providers.ZavInfo;
import by.minskkniga.minskkniga.api.Class.providers.ZavInfoTovar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionZayavkaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 201;
    SimpleDateFormat df;

    List<ProviderObject> list = new ArrayList<>();
    List<ProviderObject> listF = new ArrayList<>();
    List<Products> listPr = new ArrayList<>();
    List<ProductForZayackaProvider> productForZayackaProviderList = new ArrayList<>();

    private static final String TAG = "DescriptionZayavkaActivity";
    ProgressBar progress;
    TextView tva_d_z_id, tva_d_z_date,tva_d_z_author,tva_d_z_tv_result_summ,tva_d_z_tv_result_weight;
    Spinner spinner;
    CheckBox a_d_z_note, a_d_z_oplata;
    EditText et_comment;

    String id_z = "";

    private RecyclerView recyclerView;
    private ZayavkiBoobIAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ZavInfoTovar> studentList;

    String providers = "null";

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


        studentList = new ArrayList<ZavInfoTovar>();

        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        tva_d_z_id = (TextView) findViewById(R.id.a_d_z_id);
        tva_d_z_date = (TextView) findViewById(R.id.a_d_z_date);
        tva_d_z_author = (TextView) findViewById(R.id.a_d_z_author);
        tva_d_z_tv_result_summ = (TextView) findViewById(R.id.a_d_z_tv_result_summ);
        tva_d_z_tv_result_weight = (TextView) findViewById(R.id.a_d_z_tv_result_weight);
        et_comment = (EditText) findViewById(R.id.a_d_z_comment);
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
        providers = description.getProvedires_name();
        id_z = description.getId();

        et_comment.setText(description.getKomment());
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
        spinner.setEnabled(false);
        if(Integer.parseInt(description.getOplacheno()) == 0) a_d_z_oplata.setChecked(false);
        else a_d_z_oplata.setChecked(true);


        setRelResult(description);
        studentList = description.getWhat_zakazal();
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

    public void longClickItem(ZavInfoTovar infoTovar, TextView tv, int pos){

        showDialogEditMony(tv, pos);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.desc_provider_zayavka, menu);
        return true;
    }

    private void sendZayavka(){

        String status = String.valueOf(spinner.getSelectedItemPosition());
        String comment = et_comment.getText().toString();
        df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date currentDate = new Date();
        String date = df.format(currentDate);
        String chernovik = "1";
        if(a_d_z_note.isChecked()) chernovik = "0";
        else chernovik = "1";

        String oplacheno = "0";
        if(a_d_z_oplata.isChecked()) oplacheno = "1";
        else oplacheno = "0";

        Map<String, String> body = new HashMap();
        //body.put("date", date);
        //body.put("provedires", providers);
        //body.put("autor", author);
        //body.put("status", status);
        body.put("komment", comment);
        //body.put("courier_id", courier);
        //body.put("auto", auto);
        Gson gson = new Gson();
        String listJSON = gson.toJson(studentList);
        String d = "";

        body.put("oplacheno", oplacheno);
        body.put("chern", chernovik);
        body.put("id", id_z);
        body.put("list", listJSON);

        App.getApi().updateProviderZayavka(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null) {
                    try {
                        String d = response.body().string();

                        // TODO yужно обработать ответ
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        //body.put("mas", mass);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            startSelectN();
        }
        if (id == R.id.apply) {
            sendZayavka();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showDialogEditMony(final TextView tv, final int position){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_edit_money);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.editmoney_cancel);
        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.editmoney_done);

        final EditText et = (EditText) dialogEdit.findViewById(R.id.editmoney_et);

        et.setText(tv.getText().toString());

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.get(position).setZayavka(et.getText().toString());
                tv.setText(et.getText().toString());
                dialogEdit.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
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

                if(isInToList(products.getArtikul())){

                }else {
                    ZavInfoTovar infoTovar = new ZavInfoTovar();
                    infoTovar.setArtikul(products.getArtikul());
                    infoTovar.setCena(products.getProdCena());
                    infoTovar.setClas(products.getClas());
                    infoTovar.setIzdatel(products.getIzdatel());
                    infoTovar.setName(products.getSokrName());
                    infoTovar.setVes(products.getVes());
                    infoTovar.setZayavka("0");

                    studentList.add(infoTovar);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        mAdapter = new ZayavkiBoobIAdapter(studentList,recyclerView,DescriptionZayavkaActivity.this);
        recyclerView.setAdapter(mAdapter);


//        adapter = new ZayavkiProvidersNewAdapter(productForZayackaProviderList,recyclerView,NewProviderZayavka.this);
//        recyclerView.setAdapter(adapter);
//
//        int i = productForZayackaProviderList.size();
//
//        setInfoList();

    }

    private boolean isInToList(String art){


        for (ZavInfoTovar zav: studentList
             ) {

            if (zav.getArtikul().contains(art)) return true;
            else return false;

        }
        return false;
    }

    private void startSelectN(){

        Intent intent = new Intent(DescriptionZayavkaActivity.this, SelectNomeclaturaActivity.class);
        intent.putExtra("izdatel", providers);

        startActivityForResult(intent, REQUEST_CODE);
    }
}
