package by.minskkniga.minskkniga.activity.Zakazy;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import by.minskkniga.minskkniga.adapter.Zakazy.Main_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ImageButton back;
    ListView lv2;

    ArrayList<Clients> clien;
    ArrayList<Clients> clien_buf;

    TabHost tabHost;

    TextView notfound_1;
    TextView notfound_2;

    DialogFragment dlg_zakaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);

        clien = new ArrayList<Clients>();
        clien_buf = new ArrayList<Clients>();
        lv2 = findViewById(R.id.lv2);
        lv2.setAdapter(new Main_2(this, clien));

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Main.this, Zakazy_Client.class);
                intent.putExtra("id", Integer.parseInt(clien.get(position).getId()));
                intent.putExtra("name", clien.get(position).getName());
                startActivity(intent);

            }
        });


        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("по городам");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("по клиентам");
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg_zakaz = new Add_Dialog(Main.this, "zakaz_type", "null", "null","");
                dlg_zakaz.show(getFragmentManager(), "");
                //Intent intent = new Intent(Main.this, Zakaz_new.class);
                //startActivity(intent);
            }
        });
    }

    public void close(){
        finish();
    }

    @Override
    protected void onResume() {
        if (tabHost.getCurrentTab()==0)
            reload_1();
        if (tabHost.getCurrentTab()==1)
            reload_2();
        super.onResume();
    }

    public void reload_1(){

    }

    public void reload_2(){
        App.getApi().getClients().enqueue(new Callback<List<Clients>>() {
            @Override
            public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                clien.clear();
                clien_buf.clear();
                clien.addAll(response.body());
                clien_buf.addAll(response.body());

                if (!clien.isEmpty()) {
                    notfound_2.setVisibility(View.GONE);
                } else {
                    notfound_2.setVisibility(View.VISIBLE);
                }
                notfound_2.setText("Ничего не найдено");

                lv2.setAdapter(new Main_2(Main.this, clien));
                //search();
            }

            @Override
            public void onFailure(Call<List<Clients>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
