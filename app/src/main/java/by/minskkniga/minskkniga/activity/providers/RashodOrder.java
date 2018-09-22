package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.category.Category;
import by.minskkniga.minskkniga.api.Class.category.PodCat;
import by.minskkniga.minskkniga.api.Class.category.ResponseProvScheta;
import by.minskkniga.minskkniga.api.Class.category.Schetum;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RashodOrder extends AppCompatActivity {


    String phone = "";
    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    Date currentDate = new Date();
    TextView tv_name;
    Spinner sp_chet, sp_cat, sp_podcat;
    EditText summa, comment;
    Button btn_cancel, btn_save;

    List<Category> categoryListFinal = null;
    List<Category> categoryListFinal2 = null;
    List<Schetum> schetumListFinal = null;
    List<Schetum> schetumListFinal2 = null;

    String id = "";

    RelativeLayout drawer, rel_send_email, rel_call;

    boolean isOk = false;
    ProgressBar progressBar;

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

        drawer = (RelativeLayout) findViewById(R.id.rel_dr_right);
        rel_send_email = (RelativeLayout) findViewById(R.id.rel_1);
        rel_call = (RelativeLayout) findViewById(R.id.rel_2);

        rel_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOk) Toast.makeText(RashodOrder.this, "Требуется создать ордер", Toast.LENGTH_SHORT).show();
                else {
                   // TODO здесь нужно отправку сделать
                }
            }
        });

        rel_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone != null && !phone.isEmpty()){

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);

                }else Toast.makeText(RashodOrder.this, "В базе нет номера телефона", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.order_progress);

        loadData();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        App.getApi().getProvScheta(id).enqueue(new Callback<List<ResponseProvScheta>>() {
            @Override
            public void onResponse(Call<List<ResponseProvScheta>> call, Response<List<ResponseProvScheta>> response) {

                if(response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    final ResponseProvScheta responseProvScheta = response.body().get(0);

                    if(responseProvScheta.getCategory() != null){

                        ArrayList<String> arr = new ArrayList<>();
                        categoryListFinal = responseProvScheta.getCategory();
                        categoryListFinal2 = responseProvScheta.getCategory();
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
                        phone = responseProvScheta.getPhone();
                        tv_name.setText(responseProvScheta.getProviders());
                    }

                    if(responseProvScheta.getScheta() != null) {

                        ArrayList<String> arrayList = new ArrayList<>();
                        schetumListFinal = responseProvScheta.getScheta();
                        schetumListFinal2 = responseProvScheta.getScheta();
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
                progressBar.setVisibility(View.GONE);

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


        String schet = null;
        String sum = null;
        String date = null;
        String com = null;
        try {
            int cat_pos = sp_cat.getSelectedItemPosition();
            int pod_cat_pos = sp_podcat.getSelectedItemPosition();

            if(categoryListFinal.get(cat_pos).getId() == null
                    || categoryListFinal.get(cat_pos).getId().isEmpty()) {
                Toast.makeText(RashodOrder.this, "Не выбрана категория", Toast.LENGTH_SHORT).show();
                return;
            }

            if(categoryListFinal.get(cat_pos).getList().get(sp_podcat.getSelectedItemPosition()).getId() == null ||
                    categoryListFinal.get(cat_pos).getList().get(sp_podcat.getSelectedItemPosition()).getId().isEmpty()) {
                Toast.makeText(RashodOrder.this, "Не выбрана подкатегория", Toast.LENGTH_SHORT).show();
                return;
            }

            if(schetumListFinal == null) {
                    Toast.makeText(RashodOrder.this, "Не выбран счет", Toast.LENGTH_SHORT).show();
                    return;
            }

            schet = schetumListFinal.get(sp_chet.getSelectedItemPosition()).getId();

            sum = summa.getText().toString();

            if(sum.isEmpty()){
                Toast.makeText(RashodOrder.this, "Введите сумму", Toast.LENGTH_SHORT).show();
                return;
            }

            date = df.format(currentDate);

            com = comment.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            btn_save.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        App.getApi().addOperationCassa(categoryListFinal.get(sp_cat.getSelectedItemPosition()).getId(),
                categoryListFinal.get(sp_cat.getSelectedItemPosition()).getList().get(sp_podcat.getSelectedItemPosition()).getId(),
                schet, sum, date, id, com, "2", "0", "").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                isOk = false;
                btn_save.setEnabled(true);
                if(response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    try {
                        if(response.body().string().contains("ok")){
                            isOk = true;
                            //showDrawer();
                            Toast.makeText(RashodOrder.this, "Расходный ордер успешно создан", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else Toast.makeText(RashodOrder.this, "Ошибка создания ордера...", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(RashodOrder.this, "Ошибка создания ордера...", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String d = t.toString();
                btn_save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_provider, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.more) {
            if(drawer.getVisibility() == View.GONE) showDrawer();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDrawer(){

        drawer.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(1000)
                .playOn(drawer);

    }

    private void hideDrawer(){
        YoYo.with(Techniques.FadeOutRight)
                .duration(1000)
                .playOn(drawer);
        drawer.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(drawer.getVisibility() == View.VISIBLE) {
            hideDrawer();
            return;
        }
        super.onBackPressed();
    }
}
