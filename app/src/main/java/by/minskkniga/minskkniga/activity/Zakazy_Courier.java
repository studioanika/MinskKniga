package by.minskkniga.minskkniga.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Zakazy_Client_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Zakazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy_Courier extends AppCompatActivity {

    ImageButton back;

    int id;
    String name;
    TextView caption;

    ListView lv;
    ListView lv2;

    ArrayList<by.minskkniga.minskkniga.api.Zakazy> zakazy;
    ArrayList<by.minskkniga.minskkniga.api.Zakazy> zakazy_buf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy_courier);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lv2 = findViewById(R.id.lv2);

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);
        Toast.makeText(this, id+" ", Toast.LENGTH_SHORT).show();


        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("по книгам");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("по клиентам");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                reload_1();
                reload_2();
            }
        });

        reload_1();
        reload_2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload_1();
        reload_2();

    }

    public void reload_1(){
        //reload
    }

    public void reload_2(){
        App.getApi().getZakazy(id).enqueue(new Callback<List<Zakazy>>() {
            @Override
            public void onResponse(Call<List<Zakazy>> call, Response<List<Zakazy>> response) {
                zakazy.clear();
                zakazy_buf.clear();
                zakazy.addAll(response.body());
                zakazy_buf.addAll(response.body());
                lv2.setAdapter(new Zakazy_Client_2(Zakazy_Courier.this, zakazy));
                //search();
            }

            @Override
            public void onFailure(Call<List<by.minskkniga.minskkniga.api.Zakazy>> call, Throwable t) {
            //    Toast.makeText(Zakazy_Client.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
