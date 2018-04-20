package by.minskkniga.minskkniga.activity.Zakazy;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Zakazy.Zakazy_2;
import by.minskkniga.minskkniga.api.*;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy_Client extends AppCompatActivity {

    int id;
    static String name;
    ImageButton back;
    TextView caption;

    ArrayList<Zakazy> zakazy;
    ArrayList<Zakazy> zakazy_buf;

    ExpandableListView expListView;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy_client);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);
        Toast.makeText(this, id+" ", Toast.LENGTH_SHORT).show();

        expListView = findViewById(R.id.expandeblelistview);

        zakazy = new ArrayList<>();
        zakazy_buf = new ArrayList<>();

        expListView.setAdapter(new Zakazy_2(this, zakazy));


        tabHost = findViewById(R.id.tabHost);
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
                if (tabHost.getCurrentTab()==0)
                    reload_1();
                if (tabHost.getCurrentTab()==1)
                    reload_2();
            }
        });
    }

    @Override
    protected void onResume() {
        if (tabHost.getCurrentTab()==0)
            reload_1();
        if (tabHost.getCurrentTab()==1)
            reload_2();
        super.onResume();
    }

    public static String getName(){
            return name;
    }

    public void reload_1(){

    }

    public void reload_2(){


        App.getApi().getZakazy(id).enqueue(new Callback<List<Zakazy>>() {
            @Override
            public void onResponse(Call<List<Zakazy>> call, Response<List<Zakazy>> response) {
                zakazy.clear();
                zakazy_buf.clear();
                zakazy.addAll(response.body());
                zakazy_buf.addAll(response.body());






                expListView.setAdapter(new Zakazy_2(Zakazy_Client.this, zakazy));
                //search();
            }

            @Override
            public void onFailure(Call<List<Zakazy>> call, Throwable t) {
                Toast.makeText(Zakazy_Client.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
