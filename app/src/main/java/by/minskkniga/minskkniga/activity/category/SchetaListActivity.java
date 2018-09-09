package by.minskkniga.minskkniga.activity.category;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.category.adapter.SchetaAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.category.SchetInfo;
import by.minskkniga.minskkniga.api.Class.category.Schetum;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchetaListActivity extends AppCompatActivity {

    String categoryFinal = "";
    String categoryFinalID = "";
    List<Schetum> list = new ArrayList<>();

    ListView lv;
    SchetaAdapter adapter;

    String schet_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheta_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSchetDialog();
            }
        });
        lv = (ListView) findViewById(R.id.lv_scheta);

        Intent intent = getIntent();
        schet_id = intent.getStringExtra("id");


        loadData();
    }

    private void loadData(){

        App.getApi().getScheta().enqueue(new Callback<List<Schetum>>() {
            @Override
            public void onResponse(Call<List<Schetum>> call, final Response<List<Schetum>> response) {

                if(response.body() != null){
                    list = response.body();
                    adapter = new SchetaAdapter(SchetaListActivity.this, response.body());
                    lv.setAdapter(adapter);

                    adapter.setSelected(0);

                    categoryFinal = response.body().get(0).getName();
                    categoryFinalID = response.body().get(0).getId();

                    chechSchetId();
                }

            }

            @Override
            public void onFailure(Call<List<Schetum>> call, Throwable t) {

            }
        });

    }

    private void chechSchetId() {

        Intent intent = getIntent();
        String schet_id = intent.getStringExtra("id");

        if(schet_id != null || !schet_id.isEmpty()) {
            int i = 0;
            for (Schetum s: list
                 ) {
                if (s.getId().equals(schet_id)) {
                    adapter.setSelected(i);
                    setSchet(i);
                }
                i++;
            }

        }

    }

    public void setSchet(int i){
        if(adapter != null) {
            List<Schetum> list = adapter.getList();
            if(list != null) {
                categoryFinal = list.get(i).getName();
                categoryFinalID = list.get(i).getId();

                adapter.setSelected(i);
            }
        }

    }

    private void createSchetDialog() {

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_add_new_schet);


        final EditText et_name = (EditText) dialogEdit.findViewById(R.id.as_name);
        final EditText et_note = (EditText) dialogEdit.findViewById(R.id.as_note);
        final EditText et_summ = (EditText) dialogEdit.findViewById(R.id.as_summ);

        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.as_done);
        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.as_cancel);

        final CheckBox checkBox = (CheckBox) dialogEdit.findViewById(R.id.as_check);

        final Spinner spinner = (Spinner) dialogEdit.findViewById(R.id.as_spinner);

        ArrayList<String> arr = new ArrayList<>();arr.add("Наличный");arr.add("Безналичный");arr.add("Кредит");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SchetaListActivity.this, R.layout.adapter_nomenklatura_filter, arr);
        spinner.setAdapter(spinnerArrayAdapter);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String nach = et_summ.getText().toString();
                String kom = et_note.getText().toString();
                String itog = "0";
                if(checkBox.isChecked()) itog = "1";
                else itog = "0";

                if(name.isEmpty()) {
                    Toast.makeText(SchetaListActivity.this, "Введите название", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nach.isEmpty()) {
                    Toast.makeText(SchetaListActivity.this, "Введите начальную сумму", Toast.LENGTH_SHORT).show();
                    return;
                }

                addSchetApi(name, String.valueOf(spinner.getSelectedItemPosition()),
                        nach, itog, kom, dialogEdit);
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
    public void done(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        intent.putExtra("schet", categoryFinal);
        intent.putExtra("schetid", categoryFinalID);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.apply) {
            done();
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

    private void addSchetApi(String name, String type, String nach_sum,
                             String itog, String kom, final Dialog dialog){

        App.getApi().addNewSchet(name, type, nach_sum, itog, kom).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){
                    dialog.dismiss();
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void updateSchetApi(String id, String name, String type, String kom,
                                String nach, String itog, final Dialog dialog){

        App.getApi().updateSchet(id, name, type, kom, nach, itog).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){
                    dialog.dismiss();
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void setEditSchet(final int position) {

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_add_new_schet);



        final EditText et_name = (EditText) dialogEdit.findViewById(R.id.as_name);
        final EditText et_note = (EditText) dialogEdit.findViewById(R.id.as_note);
        final EditText et_summ = (EditText) dialogEdit.findViewById(R.id.as_summ);

        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.as_done);
        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.as_cancel);
        final TextView tv_title = (TextView) dialogEdit.findViewById(R.id.as_title);
        ImageView img_delete = (ImageView) dialogEdit.findViewById(R.id.img_delete);

        final CheckBox checkBox = (CheckBox) dialogEdit.findViewById(R.id.as_check);

        final Spinner spinner = (Spinner) dialogEdit.findViewById(R.id.as_spinner);

        ArrayList<String> arr = new ArrayList<>();arr.add("Наличный");arr.add("Безналичный");arr.add("Кредит");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SchetaListActivity.this, R.layout.adapter_nomenklatura_filter, arr);
        spinner.setAdapter(spinnerArrayAdapter);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String nach = et_summ.getText().toString();
                String kom = et_note.getText().toString();
                String itog = "0";
                if(checkBox.isChecked()) itog = "1";
                else itog = "0";

                if(name.isEmpty()) {
                    Toast.makeText(SchetaListActivity.this, "Введите название", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nach.isEmpty()) {
                    Toast.makeText(SchetaListActivity.this, "Введите начальную сумму", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateSchetApi(list.get(position).getId(),name, String.valueOf(spinner.getSelectedItemPosition()), kom, nach, itog,dialogEdit);
            }
        });

        App.getApi().getCassaSchetInfo(list.get(position).getId()).enqueue(new Callback<List<SchetInfo>>() {
            @Override
            public void onResponse(Call<List<SchetInfo>> call, Response<List<SchetInfo>> response) {

                if(response.body() != null){

                    try {
                        SchetInfo info = response.body().get(0);

                        tv_title.setText(info.getName());
                        et_name.setText(info.getName());
                        et_note.setText(info.getKoment());
                        et_summ.setText(info.getStart_seson_sum());

                        spinner.setSelection(info.getType());

                        if(Integer.parseInt(info.getItog()) == 0) checkBox.setChecked(false);
                        else checkBox.setChecked(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<SchetInfo>> call, Throwable t) {

            }
        });

        img_delete.setVisibility(View.VISIBLE);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getApi().deleteSchet(list.get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.body() != null){

                            try {

                                if(response.body().string().contains("ok")) {
                                    loadData();
                                    dialogEdit.dismiss();
                                }
                                else Toast.makeText(SchetaListActivity.this, "Ошибка подключения к серверу",Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
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
