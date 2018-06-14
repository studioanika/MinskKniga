package by.minskkniga.minskkniga.activity.Kassa;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.adapter.AdapterSchetaInfo;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.cassa.DescInfoSchet;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaItog;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaResponse;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kassa_main);

        Intent intent = getIntent();
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
                startActivity(new Intent(Main.this, SchetOperation.class));
            }
        });

        loadData();
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
}
