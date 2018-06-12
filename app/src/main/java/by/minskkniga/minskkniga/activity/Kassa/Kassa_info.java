package by.minskkniga.minskkniga.activity.Kassa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.adapter.AdapterItogScheta;
import by.minskkniga.minskkniga.activity.providers.NewProviderZayavka;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.cassa.GeneralItog;
import by.minskkniga.minskkniga.api.Class.cassa.Scheta;
import by.minskkniga.minskkniga.api.Class.cassa.SchetaItog;
import by.minskkniga.minskkniga.api.Class.cassa.SchetaNoItog;
import by.minskkniga.minskkniga.api.Class.cassa.SchetaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Kassa_info extends AppCompatActivity {

    ListView lv_itog, lv_neitog;

    AdapterItogScheta itogScheta, neitogScheta;

    ProgressBar progressBar;

    ImageButton img_back;

    TextView tv_act, tv_ob, tv_balans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kassa_info);

        lv_itog = (ListView) findViewById(R.id.lv_itog);
        lv_neitog = (ListView) findViewById(R.id.lv_neitog);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        img_back = (ImageButton) findViewById(R.id.back);

        tv_act = (TextView) findViewById(R.id.scheta_activi);
        tv_ob = (TextView) findViewById(R.id.scheta_obyaz);
        tv_balans = (TextView) findViewById(R.id.scheta_balans);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadData();

    }

    private void loadData(){

        App.getApi().getAllScheta().enqueue(new Callback<List<SchetaResponse>>() {
            @Override
            public void onResponse(Call<List<SchetaResponse>> call, Response<List<SchetaResponse>> response) {

                progressBar.setVisibility(View.GONE);

                if(response.body() != null){

                    ArrayList<Scheta> listItog = new ArrayList<>();
                    ArrayList<Scheta> listNeItog = new ArrayList<>();

                    if(response.body().get(0).getSchetaItog() != null){

                        for (SchetaItog schetaItog: response.body().get(0).getSchetaItog()
                             ) {
                            Scheta scheta = new Scheta();
                            scheta.setName(schetaItog.getName());
                            scheta.setValue(schetaItog.getNachSum());
                            listItog.add(scheta);
                        }



                    }
                    if(response.body().get(0).getSchetaNoItog() != null){

                        for (SchetaNoItog schetaNoItog: response.body().get(0).getSchetaNoItog()
                                ) {
                            Scheta scheta = new Scheta();
                            scheta.setName(schetaNoItog.getName());
                            scheta.setValue(schetaNoItog.getNachSum());
                            listNeItog.add(scheta);
                        }
                    }

                    if(response.body().get(0).getGeneralItog() != null){

                        GeneralItog generalItog = response.body().get(0).getGeneralItog().get(0);

                        tv_act.setText(String.valueOf(generalItog.getActivy()));
                        tv_ob.setText(String.valueOf(generalItog.getObyazatelstva()));
                        tv_balans.setText(String.valueOf(generalItog.getBalans()));

                    }

                    setInfo(listItog, listNeItog);

                }

            }

            @Override
            public void onFailure(Call<List<SchetaResponse>> call, Throwable t) {

            }
        });




    }

    private void setInfo(ArrayList<Scheta> itogS, ArrayList<Scheta> neitog){

        itogScheta = new AdapterItogScheta(Kassa_info.this, itogS);
        neitogScheta = new AdapterItogScheta(Kassa_info.this, neitog);

        lv_neitog.setAdapter(neitogScheta);
        setListViewBase(lv_neitog);
        lv_itog.setAdapter(itogScheta);
        setListViewBase(lv_itog);


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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() -1));
        listView.setLayoutParams(params);
    }
}
