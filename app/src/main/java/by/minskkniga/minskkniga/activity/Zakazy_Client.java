package by.minskkniga.minskkniga.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Zakazy_2;
import by.minskkniga.minskkniga.adapter.Zakazy_Client_2;
import by.minskkniga.minskkniga.api.*;
import by.minskkniga.minskkniga.api.Zakazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy_Client extends AppCompatActivity {

    int id;
    String name;
    TextView caption;

    Zakazy_2 adapter;
    ArrayList<by.minskkniga.minskkniga.api.Zakazy> zakazy;
    ArrayList<by.minskkniga.minskkniga.api.Zakazy> zakazy_buf;

    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy_client);

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);
        Toast.makeText(this, id+" ", Toast.LENGTH_SHORT).show();

        expListView = findViewById(R.id.expandeblelistview);

        zakazy = new ArrayList<>();
        zakazy_buf = new ArrayList<>();

        expListView.setAdapter(new Zakazy_Client_2(this, zakazy));


        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("деньги");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("товары");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(1);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {

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

    }

    public void reload_2(){


        App.getApi().getZakazy(id).enqueue(new Callback<List<by.minskkniga.minskkniga.api.Zakazy>>() {
            @Override
            public void onResponse(Call<List<by.minskkniga.minskkniga.api.Zakazy>> call, Response<List<by.minskkniga.minskkniga.api.Zakazy>> response) {
                zakazy.clear();
                zakazy_buf.clear();
                zakazy.addAll(response.body());
                zakazy_buf.addAll(response.body());






                expListView.setAdapter(new Zakazy_Client_2(Zakazy_Client.this, zakazy));
                //search();
            }

            @Override
            public void onFailure(Call<List<by.minskkniga.minskkniga.api.Zakazy>> call, Throwable t) {
                Toast.makeText(Zakazy_Client.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
