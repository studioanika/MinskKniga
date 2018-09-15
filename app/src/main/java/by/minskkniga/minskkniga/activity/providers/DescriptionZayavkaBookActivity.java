package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.InfoZayavkaBook;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionZayavkaBookActivity extends AppCompatActivity {


    ImageView imBook;
    TextView name, clas, predmet, obrazec,articul, sokr_name, standart, updakovok,
             zakazano, ostatok, ozhidaet, cost, zayavok, summa;

    CheckBox okrugl;
    String zak;
    String id, id_z;

    CompoundButton.OnCheckedChangeListener listener;
    CompoundButton.OnCheckedChangeListener listenerNull;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_zayavka_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        id_z = intent.getStringExtra("id_zak");
        zak = intent.getStringExtra("zak");

        uiInitialization();
        loadData(id,zak);
    }

    private void uiInitialization() {

        name = (TextView) findViewById(R.id.infobook_name);
        clas = (TextView) findViewById(R.id.infobook_class);
        predmet = (TextView) findViewById(R.id.infobook_predmet);
        obrazec = (TextView) findViewById(R.id.infobook_obrazec);
        articul = (TextView) findViewById(R.id.infobook_articul);
        sokr_name = (TextView) findViewById(R.id.infobook_sokr_name);
        standart = (TextView) findViewById(R.id.infobook_standart);
        updakovok = (TextView) findViewById(R.id.infobook_upakovok);
        zakazano = (TextView) findViewById(R.id.infobook_zakazano);
        ostatok = (TextView) findViewById(R.id.infobook_ostatok);
        ozhidaet = (TextView) findViewById(R.id.infobook_ozhidaet);
        cost = (TextView) findViewById(R.id.infobook_cost1);
        zayavok = (TextView) findViewById(R.id.infobook_zayavak);
        summa = (TextView) findViewById(R.id.infobook_summa);
        imBook = (ImageView) findViewById(R.id.infobook_img);
        okrugl = (CheckBox) findViewById(R.id.infobook_okrgl);

        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                swowDialog(b);
            }
        };

        listenerNull = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        };

    }

    private void swowDialog(final boolean b) {

        AlertDialog.Builder ad = new AlertDialog.Builder(DescriptionZayavkaBookActivity.this);
        ad.setMessage("Вы действительно хотите изменить значение переменной?");
        ad.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

                int r = 0;
                if(b) r = 1;

                App.getApi().setRoundProduct(id, zak, r, id_z).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {

                            if(response.body().string().contains("error")) {
                                Toast.makeText(DescriptionZayavkaBookActivity.this,
                                        "Ошибка редактирования....",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(DescriptionZayavkaBookActivity.this,
                                        "Отредактировано.",
                                        Toast.LENGTH_SHORT).show();
                                okrugl.setOnCheckedChangeListener(listenerNull);
                                loadData(id, zak);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(DescriptionZayavkaBookActivity.this,
                                "Проверьте подключение к интернету....",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ad.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        ad.setCancelable(false);
        ad.show();

    }

    private void loadData(String id, String zak) {

        App.getApi().getInfoBookZ(id, zak, id_z).enqueue(new Callback<List<InfoZayavkaBook>>() {
            @Override
            public void onResponse(Call<List<InfoZayavkaBook>> call, Response<List<InfoZayavkaBook>> response) {

                InfoZayavkaBook infoZayavkaBook = response.body().get(0);
                if(infoZayavkaBook != null) setInfo(infoZayavkaBook);
                String sd = "";

            }

            @Override
            public void onFailure(Call<List<InfoZayavkaBook>> call, Throwable t) {

            }
        });

    }


    private void setInfo(InfoZayavkaBook infoZayavkaBook) {

        name.setText(infoZayavkaBook.getName());
        clas.setText(infoZayavkaBook.getClas());
        predmet.setText("predmet");
        obrazec.setText(infoZayavkaBook.getObr());
        articul.setText(infoZayavkaBook.getArt());
        sokr_name.setText(infoZayavkaBook.getSokr_name());
        standart.setText(infoZayavkaBook.getStandart());
        updakovok.setText(infoZayavkaBook.getUpak());
        zakazano.setText(String.valueOf(infoZayavkaBook.getZakazano()));
        ostatok.setText(String.valueOf(infoZayavkaBook.getOstatok()));
        ozhidaet.setText(String.valueOf(infoZayavkaBook.getOjidaem()));
        cost.setText(infoZayavkaBook.getCost());
        zayavok.setText(infoZayavkaBook.getZayavka());
        summa.setText(String.valueOf(infoZayavkaBook.getSum()));
        okrugl.setChecked(infoZayavkaBook.isRound());

        Glide.with(DescriptionZayavkaBookActivity.this).load("http://cc96297.tmweb.ru/admin/img/nomen/" + infoZayavkaBook.getImage()).into(imBook);

        okrugl.setOnCheckedChangeListener(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if( id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
