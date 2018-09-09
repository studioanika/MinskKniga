package by.minskkniga.minskkniga.activity.inventarizacia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.inventarizacia.adapter.RangsAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.inventarizacia.RangObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RangsActivity extends AppCompatActivity {


    TextView tv_name, tv_class, tv_zakazano, tv_otgruzheno;
    ListView lv;

    String id;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);

        init();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if(id != null) loadData(id);
    }

    private void loadData(String id) {

        App.getApi().getRangId(id).enqueue(new Callback<RangObject>() {
            @Override
            public void onResponse(Call<RangObject> call, Response<RangObject> response) {

                if(response.body() != null) {

                    RangObject rangObject = response.body();

                    tv_name.setText(rangObject.getName());
                    tv_class.setText(rangObject.getClas());
                    tv_otgruzheno.setText(String.valueOf(rangObject.getOtgruzeno()));
                    tv_zakazano.setText(String.valueOf(rangObject.getZakazano()));

                    if(rangObject.getWhat_zakaz() != null)lv.setAdapter(new RangsAdapter(RangsActivity.this, rangObject.getWhat_zakaz()));

                }

            }

            @Override
            public void onFailure(Call<RangObject> call, Throwable t) {

            }
        });

    }

    private void init() {

        tv_name = findViewById(R.id.tv_name);
        tv_class = findViewById(R.id.tv_class);
        tv_zakazano = findViewById(R.id.tv_zakazano);
        tv_otgruzheno = findViewById(R.id.tv_otgruzheno);
        lv = findViewById(R.id.lv);

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
