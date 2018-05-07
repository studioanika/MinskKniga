package by.minskkniga.minskkniga.activity.Zakazy;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zakaz_new extends AppCompatActivity {

    ImageButton back;
    ImageButton menu;
    TextView caption;

    DialogFragment dlg_zakaz;
    DialogFragment dlg_client;

    LinearLayout linear_select_client;
    LinearLayout linear_zametka;
    LinearLayout linear_ok;

    TextView blank;
    TextView date1;
    TextView autor;
    Spinner courier;
    TextView status;
    TextView date2;
    TextView zametka;

    Button select_client;
    Button ok;

    TextView tv1;
    TextView tv2;
    ListView lv;

    ArrayList<String> couriers;
    ArrayAdapter<String> adapter;

    SharedPreferences sp;
    boolean is_select_client = false;
    String id_client;

    Date currentDate;
    DateFormat df;

    final int REQUEST_CODE_PRODUCT = 1;

    public void initialize() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menu = findViewById(R.id.menu);
        caption = findViewById(R.id.caption);

        blank = findViewById(R.id.blank);
        date1 = findViewById(R.id.date1);
        autor = findViewById(R.id.autor);
        courier = findViewById(R.id.courier);
        status = findViewById(R.id.status);
        date2 = findViewById(R.id.date2);

        linear_select_client = findViewById(R.id.linear_select_client);
        select_client = findViewById(R.id.select_client);

        linear_zametka = findViewById(R.id.linear_zametka);
        linear_zametka.setVisibility(View.GONE);
        zametka = findViewById(R.id.zametka);

        linear_ok = findViewById(R.id.linear_ok);
        linear_ok.setVisibility(View.GONE);
        ok = findViewById(R.id.ok);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        lv = findViewById(R.id.lv);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        couriers = new ArrayList<>();


        dlg_zakaz = new Add_Dialog(this, "zakaz_type","");
        dlg_zakaz.setCancelable(false);
        dlg_client = new Add_Dialog(this, "zakaz_client","");
        dlg_client.setCancelable(false);

        currentDate = new Date();
        df = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Zakaz_new.this, by.minskkniga.minskkniga.activity.Nomenklatura.Main.class);
                intent.putExtra("zakaz", true);
                startActivityForResult(intent, REQUEST_CODE_PRODUCT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_PRODUCT:

                    String name = data.getStringExtra("name");
                    //data.getStringArrayExtra();
                    //tvName.setText("Your name is " + name);

                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_new);
        initialize();

        dlg_zakaz.show(getFragmentManager(), "");

        autor.setText(sp.getString("login", ""));
        status.setTextColor(Color.rgb(97, 184, 126));
        date1.setText(df.format(currentDate));
        date2.setText(df.format(currentDate));

        select_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_select_client = true;
                dlg_client.show(getFragmentManager(), "");
            }
        });

        load_couriers();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void load_couriers(){
        App.getApi().getCouriers().enqueue(new Callback<List<Couriers>>() {
            @Override
            public void onResponse(Call<List<Couriers>> call, Response<List<Couriers>> response) {
                couriers.clear();

                for(Couriers buffer : response.body()){
                    couriers.add(buffer.getName());
                }

                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, couriers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courier.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Couriers>> call, Throwable t) {
                Toast.makeText(Zakaz_new.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void return_zakaz_type(int type){

    }

    public void return_client(String id, String name){
        id_client = id;
        linear_select_client.setVisibility(View.GONE);
        linear_zametka.setVisibility(View.VISIBLE);
        linear_ok.setVisibility(View.VISIBLE);
        caption.setText(name);
    }
}
