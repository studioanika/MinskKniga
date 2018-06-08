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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.InfoZayavkaBook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionZayavkaBookActivity extends AppCompatActivity {


    ImageView imBook;
    TextView name, clas, predmet, obrazec,articul, sokr_name, standart, updakovok,
             zakazano, ostatok, ozhidaet, cost, zayavok, summa;

    CheckBox okrugl;

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
        String id = intent.getStringExtra("id");
        String zak = intent.getStringExtra("zak");
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

    }

    private void loadData(String id, String zak) {

        App.getApi().getInfoBookZ(id, zak).enqueue(new Callback<List<InfoZayavkaBook>>() {
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
