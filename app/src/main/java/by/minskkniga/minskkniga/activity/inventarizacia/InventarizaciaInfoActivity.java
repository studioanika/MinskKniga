package by.minskkniga.minskkniga.activity.inventarizacia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.inventarizacia.adapter.AdapterInventarizacia;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.inventarizacia.InventarizaciaObject;
import by.minskkniga.minskkniga.api.Class.inventarizacia.Kniga;
import by.minskkniga.minskkniga.api.Class.inventarizacia.Kont;
import by.minskkniga.minskkniga.api.RestApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventarizaciaInfoActivity extends AppCompatActivity {

    TextView tv_name, tv_class, tv_izdatel, tv_artikul, tv_ostatok;
    ImageView img;
    ListView lv;

    List<Kont> list = new ArrayList<>();
    Kniga _kniga;

    String id = "0";
    String url = "/api/inv_ed.php?";

    SharedPreferences sp;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventarizacia_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(_kniga != null) showAlertInfo();
            }
        });


        initView();
    }

    private void showAlertInfo() {

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_inventarizacia);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.a_i_cancel);
        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.a_i_done);
        TextView tv_obr = (TextView) dialogEdit.findViewById(R.id.a_i_obr);
        final TextView tv_ost_b = (TextView) dialogEdit.findViewById(R.id.a_i_ost_b);
        TextView tv_cour = (TextView) dialogEdit.findViewById(R.id.a_i_cour);
        final EditText tv_sclad = (EditText) dialogEdit.findViewById(R.id.a_i_sklad);
        TextView tv_fact = (TextView) dialogEdit.findViewById(R.id.a_i_fact);
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
                String o_b = tv_ost_b.getText().toString();
                String sk = tv_sclad.getText().toString();
                if(o_b.equals(sk)) return;

                url = "/api/inv_ed.php?";
                url = url +"id="+id;
                try{

                    // id - id knigi
                    // izl - izlishek
                    // ned - nodostacha
                    // aut - user
                    // ost - na sklade
                    // pod_s = ost + izlishek

                    Double d = Double.parseDouble(tv_nedos.getText().toString());
                    Double fact = Double.parseDouble(String.valueOf(_kniga.getFakt()));

                    Double result_d =Double.parseDouble(tv_nedos.getText().toString());
                    int result = result_d.intValue();
                    if(tv_nedos_i.getText().toString().contains("Излишек")) url = url + "&ned=0&izl="+String.valueOf(result);
                    else url = url + "&izl=0&ned="+String.valueOf(result);
                    //else url = url + "&izl=0&ned=0";

                    url = url + "&aut="+sp.getString("user_id", "");
                    url = url + "&ost=" +String.valueOf(_kniga.getNasklade());

                    int pod_s = 0;

                    try{

                        int ds = _kniga.getNasklade();
                        Double iz = Double.parseDouble(tv_nedos.getText().toString());

                        int izii = iz.intValue();
                        pod_s = ds + izii;

                    }catch (Exception e){}

                    //url = url + "&pod_s=" +String.valueOf(pod_s);

                }
                catch (Exception e){
                    String sd = e.toString();
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cc96297.tmweb.ru/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi api = retrofit.create(RestApi.class);

                //Response{protocol=http/1.1, code=200, message=OK, url=http://cc96297.tmweb.ru/api/inv_ed.php?id=2&izl=0&ned=0&aut=1&ost=2113id=2&ned=0&izl=80&aut=1&ost=2113}

                api.setInvEd(url).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body() != null) {
                            String d = response.body().toString();
                            dialogEdit.dismiss();
                            loadData();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(InventarizaciaInfoActivity.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        if(_kniga.getObr() != null) tv_obr.setText(String.valueOf(_kniga.getObr()));
        else tv_obr.setText("0");
        if(_kniga.getFakt() != null) tv_fact.setText(String.valueOf(_kniga.getFakt()));
        else tv_fact.setText("0");
        if(_kniga.getCour() != null) tv_cour.setText(String.valueOf(_kniga.getCour()));
        else tv_cour.setText("0");
        if(_kniga.getOst() != null) tv_ost_b.setText(String.valueOf(_kniga.getOst()));
        else tv_ost_b.setText("0");

        tv_sclad.setText(String.valueOf(_kniga.getNasklade()));
        getResult(tv_sclad, tv_nedos, tv_nedos_i);
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


            Double fact = Double.parseDouble(String.valueOf(_kniga.getOst()));

            double result = ( fact - d);
            String r = String.valueOf(result);
            r = r.replace("-", "");
            tv_nedostacha.setText(r);

            if((fact - d) >0) {
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

    private void initView(){

        tv_name = (TextView) findViewById(R.id.i_info_name);
        tv_class = (TextView) findViewById(R.id.i_info_class);
        tv_artikul = (TextView) findViewById(R.id.i_info_articul);
        tv_ostatok = (TextView) findViewById(R.id.i_info_ostatok);
        tv_izdatel = (TextView) findViewById(R.id.i_info_izdatel);
        img = (ImageView) findViewById(R.id.i_info_img);
        lv = (ListView) findViewById(R.id.i_info_lv);

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InventarizaciaInfoActivity.this, RangsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        loadData();
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    public void clickItemLv(){

    }

    private void loadData(){



        App.getApi().getInventarizacia(id).enqueue(new Callback<InventarizaciaObject>() {
            @Override
            public void onResponse(Call<InventarizaciaObject> call, Response<InventarizaciaObject> response) {
                if(response.body() != null){
                    InventarizaciaObject object = response.body();
                    if(object.getResult() != null &&
                            object.getResult().contains("1")){

                        if(object.getKont() != null ){

                            list = object.getKont();
                            lv.setAdapter(new AdapterInventarizacia(InventarizaciaInfoActivity.this, list));
                            //setListViewBase(lv);

                        }

                        if(object.getKniga() != null){
                            Kniga kniga = object.getKniga();
                            _kniga = kniga;

                            tv_name.setText(kniga.getName());
                            tv_artikul.setText(kniga.getArtikul());
                            tv_class.setText(kniga.getClas());
                            tv_ostatok.setText(String.valueOf(kniga.getOst()));
                            tv_izdatel.setText(kniga.getIzdatel());
                            getSupportActionBar().setTitle(kniga.getName());

                            Glide.with(img.getContext())
                                 .load("http://cc96297.tmweb.ru/admin/img/nomen/"+kniga.getImg())
                                 .into(img);

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<InventarizaciaObject> call, Throwable t) {
                Toast.makeText(InventarizaciaInfoActivity.this,
                        "Проверьте подключение к интернету....",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
