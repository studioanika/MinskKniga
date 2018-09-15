package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


    TextView nav_send;
    TextView nav_raschet, nav_add;

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
    LinearLayout linear;
    EditText et_nal;
    EditText et_beznal;

    String id_z = "";

    private RecyclerView recyclerView;
    private ZayavkiBoobIAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ZavInfoTovar> studentList;

    RelativeLayout rel_drawer;
    Button btn_cancel;
    Button btn_oprihodovat;


    DrawerLayout drawer;
    Double dengi;
    ZavInfo zavInfo;


    String providers = "null";
    TextWatcher text_nal;
    TextWatcher text_beznal;

    int finalColor;

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
        drawer = findViewById(R.id.drawer);
        studentList = new ArrayList<ZavInfoTovar>();
        nav_send = (TextView) findViewById(R.id.nav_otpravit);
        nav_add = (TextView) findViewById(R.id.nav_add);
        nav_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                showSendDialog();
            }
        });
        nav_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                startSelectN();
            }
        });
        recyclerView.setHasFixedSize(true);
        linear = findViewById(R.id.linear);

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
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_oprihodovat = findViewById(R.id.btn_oprihodovat);

        btn_oprihodovat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalColor = Color.CYAN;
                spinner.setSelection(2);

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalColor = Color.RED;
                spinner.setSelection(4);
            }
        });

        et_beznal = findViewById(R.id.et_beznal);
        et_nal = findViewById(R.id.et_nal);

        text_nal = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_beznal.removeTextChangedListener(text_beznal);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    if(dengi != null){
                        double d = Double.parseDouble(editable.toString());
                        if(dengi >= d){
                            et_beznal.setText(String.format("%.2f",dengi - d));
                        }//et_nal.setTextColor(Co);
                    }
                    et_beznal.addTextChangedListener(text_beznal);
                }
                catch (Exception e){}
            }
        };
        et_nal.addTextChangedListener(text_nal);
        text_beznal = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_nal.removeTextChangedListener(text_nal);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try{
                    if(dengi != null){
                        double d = Double.parseDouble(editable.toString());
                        if(dengi >= d){
                            et_nal.setText(String.format("%.2f",dengi - d));
                        }

                    }
                    et_nal.addTextChangedListener(text_nal);
                }

                catch (Exception e){}
            }
        };
        et_beznal.addTextChangedListener(text_beznal);
        nav_raschet = findViewById(R.id.nav_raschet);
        nav_raschet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                Intent intent = new Intent(DescriptionZayavkaActivity.this, ProvidersZayavkiRaschetActivity.class);
                intent.putExtra("izdatel", zavInfo.getProvedires_name());
                intent.putExtra("id", zavInfo.getProvedires());
                // TODO здесь походу еще ид будет
                startActivity(intent);
            }
        });
    }

    private void showSendDialog() {

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_show_send);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.editmoney_cancel);
        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.editmoney_done);
        RadioGroup rgp = dialogEdit.findViewById(R.id.rgrp);
        RadioButton rbtn1 = dialogEdit.findViewById(R.id.rbtn1);
        final RadioButton rbtn2 = dialogEdit.findViewById(R.id.rbtn2);
        rbtn1.setChecked(true);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbtn2.isChecked()) send();
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

    private void send() {

        App.getApi().sendAllSposob(id_z).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body().string().contains("ok"))
                    {
                        Toast.makeText(DescriptionZayavkaActivity.this,
                                "Заявка успешно отправлена", Toast.LENGTH_SHORT).show();
                        loadData(id_z);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void loadData(String id) {
        progress.setVisibility(View.VISIBLE);
        btn_oprihodovat.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        linear.setVisibility(View.GONE);
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
        zavInfo = description;
        et_beznal.removeTextChangedListener(text_beznal);
        et_nal.removeTextChangedListener(text_nal);
        try{
            Double d1 = Double.parseDouble(description.getNal());
            Double d2 = Double.parseDouble(description.getBeznal());

            dengi = description.getSumma();
        }
        catch (Exception e){}

        providers = description.getProvedires_name();
        id_z = description.getId();

        et_comment.setText(description.getKomment());
        tva_d_z_id.setText("№"+description.getId());
        tva_d_z_date.setText(description.getDate());
        tva_d_z_author.setText(description.getAutor());

        et_beznal.setText(description.getBeznal());
        et_nal.setText(description.getNal());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_nomenklatura_filter, arr);
        spinner.setAdapter(spinnerArrayAdapter);
        if(description.getStatus()!=null){
            try{

                int select = Integer.parseInt(description.getStatus());
                int color = (Color.rgb(97, 184, 126));
                switch (select){
                    case 0:
                        color = (Color.rgb(97, 184, 126));
                        break;
                    case 1:
                        color = (Color.rgb(97, 184, 126));
                        break;
                    case 2:
                        color = (Color.rgb(242, 201, 76));
                        linear.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        color = (Color.BLUE);
                        btn_oprihodovat.setVisibility(View.VISIBLE);
                        btn_cancel.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        color = (Color.rgb(242, 0, 86));
                        break;
                    case 5:
                        color = (Color.rgb(139, 0, 0));
                        break;
                    case 6:
                        color = (Color.rgb(100, 0, 0));
                        break;
                }

                finalColor = color;
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(finalColor);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spinner.setSelection(select);

                if(select ==0) a_d_z_note.setChecked(true);



            }
            catch (Exception e){}

            et_beznal.addTextChangedListener(text_beznal);
            et_nal.addTextChangedListener(text_nal);

            //spinner.setBackgroundColor();
        }
        spinner.setEnabled(false);
        if(Integer.parseInt(description.getOplacheno()) == 0) a_d_z_oplata.setChecked(false);
        else a_d_z_oplata.setChecked(true);


        setRelResult(description);
        studentList = description.getWhat_zakazal();
        mAdapter = new ZayavkiBoobIAdapter(description.getWhat_zakazal(),recyclerView,DescriptionZayavkaActivity.this, description.getId());
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

    public void startInfoDescr(String id, String zak, String id_zak){

        Intent intent = new Intent(DescriptionZayavkaActivity.this, DescriptionZayavkaBookActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("zak", zak);
        intent.putExtra("id_zak", id_zak);
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
        body.put("status", String.valueOf(spinner.getSelectedItemPosition()));
        //body.put("courier_id", courier);
        //body.put("auto", auto);
        Gson gson = new Gson();
        String listJSON = gson.toJson(studentList);
        String d = "";

        String nal = et_nal.getText().toString();
        String beznal = et_beznal.getText().toString();

        body.put("nal", nal);
        body.put("beznal", beznal);
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
                        if(d.contains("error")) {
                            Toast.makeText(DescriptionZayavkaActivity.this,
                                    "Ошибка редактирования....",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DescriptionZayavkaActivity.this,
                                    "Заявка отредактирована.",
                                    Toast.LENGTH_SHORT).show();
                            loadData(id_z);
                        }

                    } catch (Exception e) {
                         e.printStackTrace();
                        Toast.makeText(DescriptionZayavkaActivity.this,
                                "Ошибка редактирования....",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DescriptionZayavkaActivity.this,
                        "Проверьте подключение к интернету....",
                        Toast.LENGTH_SHORT).show();
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
            drawer.openDrawer(GravityCompat.END, true);

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

        String c = tv.getText().toString();

        if(!c.equals("0")) {
            et.setText(tv.getText().toString());
            et.setSelection(tv.getText().length());
        }
        else et.setText("");

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
                    infoTovar.setName(products.getName());
                    infoTovar.setSokr_name(products.getSokrName());
                    infoTovar.setVes(products.getVes());
                    infoTovar.setZayavka(products.getFor_providers_count());


                    studentList.add(infoTovar);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        mAdapter = new ZayavkiBoobIAdapter(studentList,recyclerView,DescriptionZayavkaActivity.this, id_z);
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

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
            return;
        }

        if(studentList != null && studentList.size() != 0) {
            showDialogExit();
            return;
        }
        super.onBackPressed();
    }

    private void showDialogExit() {
        AlertDialog.Builder ad = new AlertDialog.Builder(DescriptionZayavkaActivity.this);
        ad.setMessage("Сохранить?");
        ad.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                sendZayavka();
            }
        });
        ad.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                finish();
            }
        });
        ad.setCancelable(false);
        ad.show();

    }
}
