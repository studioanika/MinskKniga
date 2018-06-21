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
import by.minskkniga.minskkniga.api.Class.cassa.GetRashodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaItog;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaResponse;
import by.minskkniga.minskkniga.api.Class.cassa.ObjectTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



        }else if(type == 1){


        }else {


        }

    }

    private void showDialogDohod(String id){

        final TextView cat_tv, schet_tv, pol_tv;

        final TextView tv_date, tv_time, tv_summa;
        ImageView img_left, img_right, img_money;

        Button btn_save;

        final EditText et_comment;

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

    private void showDialogRashod(String id){
        final TextView cat_tv, schet_tv, pol_tv;

        final TextView tv_date, tv_time, tv_summa;
        ImageView img_left, img_right, img_money;

        Button btn_save;

        final EditText et_comment;

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

    private void showDialogPervod(String id){

        TextView iz_tv, schet_tv, pol_tv;

        TextView tv_date, tv_time, tv_summa;
        ImageView img_left, img_right;

        Button btn_save;

        EditText et_comment;

        ImageView img_money;

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.fragment_perevod);

        tv_summa = (TextView) dialogEdit.findViewById(R.id.dohod_summa);
        img_money = (ImageView) dialogEdit.findViewById(R.id.dohod_img_money);

        et_comment = (EditText) dialogEdit.findViewById(R.id.dohod_note);
        btn_save = (Button) dialogEdit.findViewById(R.id.r_o_save);


        iz_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void showDialogEditDohod(String id){

        TextView cat_tv, schet_tv, pol_tv;

        TextView tv_date, tv_time, tv_summa;
        ImageView img_left, img_right, img_money;

        Button btn_save;

        EditText et_comment;

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

            }
        });
        cat_tv = (TextView) dialogEdit.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) dialogEdit.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) dialogEdit.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) dialogEdit.findViewById(R.id.dohod_date);
        tv_time = (TextView) dialogEdit.findViewById(R.id.dohod_time);
        img_left = (ImageView) dialogEdit.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) dialogEdit.findViewById(R.id.dohod_img_right);



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.setCancelable(false);
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {


            if (resultCode == RESULT_OK) {

                String cat = data.getStringExtra("cat");
                String cat_id = data.getStringExtra("cat_id");
                String podcat = data.getStringExtra("podcat");
                String podcat_id = data.getStringExtra("podcat_id");

                if(cat.isEmpty()) return;
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {

                try {
                    String schet = data.getStringExtra("schet");
                    String schetid = data.getStringExtra("schetid");

                    if(schet.isEmpty()) return;
                    else {

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

                    // TODO здесь нужно записать в переменную
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
}
