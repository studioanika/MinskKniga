package by.minskkniga.minskkniga.activity.Kassa;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.adapter.AdapterSchetaInfo;
import by.minskkniga.minskkniga.activity.Kassa.calculator.CalculatorGB;
import by.minskkniga.minskkniga.activity.Kassa.calculator.CalculatorL;
import by.minskkniga.minskkniga.activity.Kassa.calculator.Utils;
import by.minskkniga.minskkniga.activity.category.CategoryActivity;
import by.minskkniga.minskkniga.activity.category.SchetaListActivity;
import by.minskkniga.minskkniga.activity.prefs.Prefs;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.cassa.DescInfoSchet;
import by.minskkniga.minskkniga.api.Class.cassa.GetDohodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.GetPerevodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.GetRashodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaItog;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaResponse;
import by.minskkniga.minskkniga.api.Class.cassa.ObjectTransaction;
import by.minskkniga.minskkniga.api.RestApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity {

    TextView tv_prihod, tv_rashod, tv_title, tv_balans, tv_nach;
    ListView lv;
    ImageButton back;

    String id, name;

    AdapterSchetaInfo adapterSchetaInfo;

    FloatingActionButton fab;

    private static final int REQUEST_CODE = 201;
    private static final int REQUEST_CODE_1 = 202;

    List<ObjectTransaction> list = new ArrayList<>();
    ArrayList<String> providers = new ArrayList<>();
    ArrayList<String> providersID = new ArrayList<>();

    String cat, cat_id, podcat, podcat_id;
    String schet, schetid, schet2, schetid2;
    String provider_id;

    boolean isPr2 = false;

    TextView cat_tv, schet_tv, pol_tv, iz_tv;

    TextView tv_date, tv_time, tv_summa;
    ImageView img_left, img_right, img_money;

    Button btn_save;

    EditText et_comment;

    String url_prihod = "/api/update_prihod.php?";
    String url_rashod = "/api/update_rashod.php?";
    String url_perevod = "/api/update_perevod.php?";

    GetDohodResponse getDohodResponse;
    GetRashodResponse getRashodResponse;
    GetPerevodResponse getPerevodResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kassa_main);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");

        lv = (ListView) findViewById(R.id.cassa_lv);
        tv_prihod = (TextView) findViewById(R.id.cassa_prihod);
        tv_rashod = (TextView) findViewById(R.id.cassa_rashod);
        tv_nach = (TextView) findViewById(R.id.cassa_nach);
        tv_balans = (TextView) findViewById(R.id.cassa_balans);

        tv_title = (TextView) findViewById(R.id.caption);
        tv_title.setText(name);
        back = (ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.hasLollipop()) {
                    Prefs prefs = new Prefs(Main.this);
                    prefs.setSessionIdSchet(id);
                    prefs.setSchet(name);
                    Intent calL = new Intent(Main.this, CalculatorL.class);

                    startActivity(calL);
                } else {
                    Intent calB = new Intent(Main.this, CalculatorGB.class);
                    startActivity(calB);
                }
                //finish();
            }
        });

        loadData();
    }

    public void startInfoOperation(String id_oper, int type){

       if(type == 0) {
           showDialogDohod(id_oper);
           

       }else if(type == 1){
        showDialogRashod(id_oper);

       }else {

        showDialogPervod(id_oper);
       }

    }

    public void startLongInfoOperation(String id_oper, int type){

        if(type == 0) {
            showDialogEditDohod(id_oper);
        }else if(type == 1){
            showDialogEditRashod(id_oper);
        }else {
            showDialogEditPerevod(id_oper);
        }

    }

    private void showDialogDohod(final String id){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_dohod);

        Button btn = (Button) dialogEdit.findViewById(R.id.r_o_edit);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
                showDialogEditDohod(id);
            }
        });

        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);
        img_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);
        et_comment.setEnabled(false);
        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);
        btn_save.setVisibility(View.GONE);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        App.getApi().getDohodID(id).enqueue(new Callback<List<GetDohodResponse>>() {
            @Override
            public void onResponse(Call<List<GetDohodResponse>> call, Response<List<GetDohodResponse>> response) {
                if(response.body() != null){
                    GetDohodResponse dohodResponse = response.body().get(0);

                    tv_date.setText(dohodResponse.getDate());
                    tv_summa.setText(dohodResponse.getPrihod());
                    pol_tv.setText(dohodResponse.getClient());
                    schet_tv.setText(dohodResponse.getSchet());
                    et_comment.setText(dohodResponse.getKom());
                    cat_tv.setText(dohodResponse.getCategory());

                }
            }

            @Override
            public void onFailure(Call<List<GetDohodResponse>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void showDialogRashod(final String id){


        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_rashod);

        Button btn = (Button) dialogEdit.findViewById(R.id.r_o_edit);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
                showDialogEditRashod(id);
            }
        });
        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);
        img_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);
        et_comment.setEnabled(false);

        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);
        btn_save.setVisibility(View.GONE);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        App.getApi().getRashodID(id).enqueue(new Callback<List<GetRashodResponse>>() {
            @Override
            public void onResponse(Call<List<GetRashodResponse>> call, Response<List<GetRashodResponse>> response) {
                if(response.body() != null) {
                    GetRashodResponse dohodResponse = response.body().get(0);

                    tv_date.setText(dohodResponse.getDate());
                    tv_summa.setText(dohodResponse.getRashod());
                    pol_tv.setText(dohodResponse.getClient());
                    schet_tv.setText(dohodResponse.getSchet());
                    et_comment.setText(dohodResponse.getKom());
                    cat_tv.setText(dohodResponse.getCategory());
                }
            }

            @Override
            public void onFailure(Call<List<GetRashodResponse>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);


    }

    private void showDialogPervod(final String id){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_perevod);

        Button btn = (Button) dialogEdit.findViewById(R.id.r_o_edit);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
                showDialogEditPerevod(id);
            }
        });

        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);
        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);

        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);
        et_comment.setEnabled(false);
        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);

        btn_save.setVisibility(View.GONE);
        iz_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        App.getApi().getPerevodID(id).enqueue(new Callback<List<GetPerevodResponse>>() {
            @Override
            public void onResponse(Call<List<GetPerevodResponse>> call, Response<List<GetPerevodResponse>> response) {

                if(response.body() != null){

                    GetPerevodResponse perevodResponse = response.body().get(0);

                    tv_date.setText(perevodResponse.getDate());
                    et_comment.setText(perevodResponse.getKom());
                    tv_summa.setText(perevodResponse.getPerevod());
                    iz_tv.setText(perevodResponse.getIz());
                    schet_tv.setText(perevodResponse.getV());
                    cat_tv.setText(perevodResponse.getCategory());

                }

            }

            @Override
            public void onFailure(Call<List<GetPerevodResponse>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void showDialogEditDohod(String id){


        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_dohod);

        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);
        img_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);
        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // id , prov_id, summa, schet_id, cat_id, pod_cat_id, com

                if(getDohodResponse != null )url_prihod = url_prihod + "id=" + getDohodResponse.getId()+"&";
                else return;

                url_prihod = url_prihod + "prov_id=" + provider_id+"&";
                url_prihod = url_prihod + "summa=" + tv_summa.getText().toString()+"&";
                url_prihod = url_prihod + "schet_id=" + schetid +"&";
                url_prihod = url_prihod + "cat_id=" + cat_id +"&";
                url_prihod = url_prihod + "pod_cat_id=" + podcat_id +"&";
                url_prihod = url_prihod + "com=" + et_comment.getText().toString() +"&";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cc96297.tmweb.ru/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi api = retrofit.create(RestApi.class);

                api.setUpdatePrihod(url_prihod).enqueue(new Callback<ResponseBody>() {
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
                        Toast.makeText(Main.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tv_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditMony(tv_summa);
            }
        });

        ImageView btn_del = (ImageView) dialogEdit.findViewById(R.id.dohod_delete);
        btn_del.setVisibility(View.VISIBLE);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getApi().getDeleteOperationId(getDohodResponse.getId(), "1").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.body() != null){
                            String b = response.body().toString();
                            if(b.contains("ok")){
                                Toast.makeText(Main.this,
                                        "Удалено!",
                                        Toast.LENGTH_SHORT).show();
                                dialogEdit.dismiss();
                                loadData();
                            }else {
                                Toast.makeText(Main.this,
                                        "Проверьте подключение к интернету....",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Main.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        cat_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCat("1");
            }
        });

        pol_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectProvider("1");
            }
        });

        schet_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScheta();
            }
        });

        tv_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditMony(tv_summa);
            }
        });;

        App.getApi().getDohodID(id).enqueue(new Callback<List<GetDohodResponse>>() {
            @Override
            public void onResponse(Call<List<GetDohodResponse>> call, Response<List<GetDohodResponse>> response) {
                if(response.body() != null){

                    GetDohodResponse dohodResponse = response.body().get(0);
                    getDohodResponse = dohodResponse;
                    schetid = getDohodResponse.getSchet_id();
                    provider_id = getDohodResponse.getClient_id();
                    cat_id = getDohodResponse.getCat_id();
                    podcat_id = getDohodResponse.getPod_cat_id();
                    tv_date.setText(dohodResponse.getDate());
                    tv_summa.setText(dohodResponse.getPrihod());
                    pol_tv.setText(dohodResponse.getClient());
                    schet_tv.setText(dohodResponse.getSchet());
                    et_comment.setText(dohodResponse.getKom());
                    cat_tv.setText(dohodResponse.getCategory());

                }
            }

            @Override
            public void onFailure(Call<List<GetDohodResponse>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void showDialogEditRashod(String id){
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_rashod);

        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);
        img_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);
        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getRashodResponse != null )url_rashod = url_rashod + "id=" + getRashodResponse.getId()+"&";
                else return;

                url_rashod = url_rashod + "prov_id=" + provider_id+"&";
                url_rashod = url_rashod + "summa=" + tv_summa.getText().toString()+"&";
                url_rashod = url_rashod + "schet_id=" + schetid +"&";
                url_rashod = url_rashod + "cat_id=" + cat_id +"&";
                url_rashod = url_rashod + "pod_cat_id=" + podcat_id +"&";
                url_rashod = url_rashod + "com=" + et_comment.getText().toString() +"&";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cc96297.tmweb.ru/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi api = retrofit.create(RestApi.class);

                api.setUpdateRashod(url_rashod).enqueue(new Callback<ResponseBody>() {
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
                        Toast.makeText(Main.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tv_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditMony(tv_summa);
            }
        });

        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        ImageView btn_del = (ImageView) dialogEdit.findViewById(R.id.dohod_delete);
        btn_del.setVisibility(View.VISIBLE);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getApi().getDeleteOperationId(getRashodResponse.getId(), "2").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.body() != null){
                            String b = response.body().toString();
                            if(b.contains("ok")){
                                Toast.makeText(Main.this,
                                        "Удалено!",
                                        Toast.LENGTH_SHORT).show();
                                dialogEdit.dismiss();
                                loadData();
                            }else {
                                Toast.makeText(Main.this,
                                        "Проверьте подключение к интернету....",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Main.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        cat_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCat("2");
            }
        });

        pol_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectProvider("2");
            }
        });

        schet_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScheta();
            }
        });

        App.getApi().getRashodID(id).enqueue(new Callback<List<GetRashodResponse>>() {
            @Override
            public void onResponse(Call<List<GetRashodResponse>> call, Response<List<GetRashodResponse>> response) {
                if(response.body() != null) {
                    GetRashodResponse dohodResponse = response.body().get(0);
                    getRashodResponse = dohodResponse;
                    schetid = getRashodResponse.getSchet_id();
                    provider_id = getRashodResponse.getClient_id();
                    cat_id = getRashodResponse.getCat_id();
                    podcat_id = getRashodResponse.getPod_cat_id();
                    tv_date.setText(dohodResponse.getDate());
                    tv_summa.setText(dohodResponse.getRashod());
                    pol_tv.setText(dohodResponse.getClient());
                    schet_tv.setText(dohodResponse.getSchet());
                    et_comment.setText(dohodResponse.getKom());
                    cat_tv.setText(dohodResponse.getCategory());
                }
            }

            @Override
            public void onFailure(Call<List<GetRashodResponse>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);
    }

    private void showDialogEditPerevod(String id){
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_perevod);
        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);
        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);

        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);

        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getPerevodResponse != null )url_perevod = url_perevod + "id=" + getPerevodResponse.getId()+"&";
                else return;

                url_perevod = url_perevod + "schet_perevoda_id=" + provider_id+"&";
                url_perevod = url_perevod + "summa=" + tv_summa.getText().toString()+"&";
                url_perevod = url_perevod + "schet_id=" + schetid +"&";
                url_perevod = url_perevod + "cat_id=" + cat_id +"&";
                url_perevod = url_perevod + "pod_cat_id=" + podcat_id +"&";
                url_perevod = url_perevod + "com=" + et_comment.getText().toString() +"&";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cc96297.tmweb.ru/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi api = retrofit.create(RestApi.class);

                api.setUpdatePerevod(url_perevod).enqueue(new Callback<ResponseBody>() {
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
                        Toast.makeText(Main.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        tv_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditMony(tv_summa);
            }
        });

        ImageView btn_del = (ImageView) dialogEdit.findViewById(R.id.dohod_delete);
        btn_del.setVisibility(View.VISIBLE);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getApi().getDeleteOperationId(getPerevodResponse.getId(), "3").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.body() != null){
                            String b = response.body().toString();
                            if(b.contains("ok")){
                                Toast.makeText(Main.this,
                                        "Удалено!",
                                        Toast.LENGTH_SHORT).show();
                                dialogEdit.dismiss();
                                loadData();
                            }else {
                                Toast.makeText(Main.this,
                                        "Проверьте подключение к интернету....",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Main.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        iz_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        cat_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCat("3");
            }
        });

        schet_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPr2 = true;
                startScheta();
            }
        });
        iz_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPr2 = false;
                startScheta();
            }
        });


        App.getApi().getPerevodID(id).enqueue(new Callback<List<GetPerevodResponse>>() {
            @Override
            public void onResponse(Call<List<GetPerevodResponse>> call, Response<List<GetPerevodResponse>> response) {

                if(response.body() != null){

                    GetPerevodResponse perevodResponse = response.body().get(0);
                    getPerevodResponse = perevodResponse;

                    schetid = getPerevodResponse.getIz_id();
                    provider_id = getPerevodResponse.getV_id();
                    cat_id = getPerevodResponse.getCat_id();
                    podcat_id = getPerevodResponse.getPod_cat_id();
                    tv_date.setText(perevodResponse.getDate());
                    et_comment.setText(perevodResponse.getKom());
                    tv_summa.setText(perevodResponse.getPerevod());
                    iz_tv.setText(perevodResponse.getIz());
                    schet_tv.setText(perevodResponse.getV());
                    cat_tv.setText(perevodResponse.getCategory());

                }

            }

            @Override
            public void onFailure(Call<List<GetPerevodResponse>> call, Throwable t) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void loadData() {

        App.getApi().getSchetaInfo(id).enqueue(new Callback<InfoSchetaResponse>() {
            @Override
            public void onResponse(Call<InfoSchetaResponse> call, Response<InfoSchetaResponse> response) {

                if(response.body() != null) {
                    if(response.body().getGeneral_itog() != null) setInfo(response.body().getGeneral_itog());
                    if(response.body().getSchet_info() != null) setList(response.body().getSchet_info());
                }

            }

            @Override
            public void onFailure(Call<InfoSchetaResponse> call, Throwable t) {

            }
        });

    }

    private void setList(List<DescInfoSchet> schet_info) {

        adapterSchetaInfo = new AdapterSchetaInfo(Main.this, (ArrayList<DescInfoSchet>) schet_info);
        lv.setAdapter(adapterSchetaInfo);

    }

    private void setInfo(InfoSchetaItog general_itog) {

        tv_balans.setText(String.valueOf(general_itog.getNach_sum()));
        tv_rashod.setText(String.valueOf(general_itog.getRashod()));
        tv_prihod.setText(String.valueOf(general_itog.getDohod()));
        tv_nach.setText(String.valueOf(general_itog.getStart_seson_sum()));


        try{

            String a = tv_balans.getText().toString();
            String b = tv_rashod.getText().toString();
            String c = tv_prihod.getText().toString();

            if(Double.parseDouble(a) < 0) tv_balans.setTextColor(getResources().getColor(R.color.red));
            if(Double.parseDouble(b) < 0) tv_rashod.setTextColor(getResources().getColor(R.color.red));
            if(Double.parseDouble(c) < 0) tv_prihod.setTextColor(getResources().getColor(R.color.red));

        }
        catch (Exception e){}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {


            if (resultCode == RESULT_OK) {

                cat = data.getStringExtra("cat");
                cat_id = data.getStringExtra("cat_id");
                podcat = data.getStringExtra("podcat");
                podcat_id = data.getStringExtra("podcat_id");

                if(cat.isEmpty()) return;
                try {
                    if(podcat!=null)cat_tv.setText(cat+"-"+podcat);
                    else cat_tv.setText(cat);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {

                try {

                    if(!isPr2) {
                        schet = data.getStringExtra("schet");
                        schetid = data.getStringExtra("schetid");


                        if(schet.isEmpty()) return;
                        else {
                            //schet_tv.setText(schet);
                            iz_tv.setText(schet);

                        }
                    }else {
                        schet2 = data.getStringExtra("schet");
                        schetid2 = data.getStringExtra("schetid");
                        provider_id = schetid2;

                        if(schet2.isEmpty()) return;
                        else {
                            schet_tv.setText(schet2);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startCat(String type){

        Intent intent = new Intent(Main.this, CategoryActivity.class);
        intent.putExtra("type", type);
        startActivityForResult(intent, REQUEST_CODE);

    }

    public void startScheta(){

        Intent intent = new Intent(Main.this, SchetaListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_1);

    }

    public void showSelectProvider(final String type){
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_select_provider);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.cansel_dlg);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
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
                providers.clear();
                providersID.clear();
                for (ObjectTransaction obj: list
                        ) {
                    if(obj.getName().toLowerCase().contains(s.toLowerCase()) || obj.getName().contains(s.toUpperCase()) || obj.getName().contains(s))
                    {
                        providers.add(obj.getName());
                        providersID.add(obj.getId());
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main.this,
                        android.R.layout.simple_list_item_1, providers);
                lv.setAdapter(adapter);

                return false;
            }
        });



        App.getApi().getKontragent(type).enqueue(new Callback<List<ObjectTransaction>>() {
            @Override
            public void onResponse(Call<List<ObjectTransaction>> call, Response<List<ObjectTransaction>> response) {
                progressBar.setVisibility(View.GONE);

                providers.clear();
                if(response.body() != null) {
                    list = response.body();
                    for (ObjectTransaction object: response.body()
                            ) {
                        providers.add(object.getName());
                        providersID.add(object.getId());

                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main.this,
                        android.R.layout.simple_list_item_1, providers);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ObjectTransaction>> call, Throwable t) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list != null){
                    //ObjectTransaction objectTransaction = (ObjectTransaction) lv.getAdapter().getItem(i);
                    try{
                        if(pol_tv != null)pol_tv.setText(list.get(i).getName());
                        provider_id = list.get(i).getId();
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

    private void showDialogEditMony(final TextView tv){

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
    protected void onPostResume() {
        loadData();
        super.onPostResume();
    }
}
