package by.minskkniga.minskkniga.activity.Zakazy;


import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy_Client extends AppCompatActivity {

    int id;
    static String name;
    ImageButton back;
    TextView caption;

    FloatingActionButton fab;
    DialogFragment dlg_zakaz;

    ArrayList<Zakazy> zakazy;
    ArrayList<Zakazy> zakazy_buf;

    ExpandableListView expListView;
    TabHost tabHost;
    TextView notfound_1;
    TextView notfound_2;

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

        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);

        expListView = findViewById(R.id.expandeblelistview);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
                Intent intent = new Intent(Zakazy_Client.this, Zakaz_info.class);
                intent.putExtra("name", name);
                intent.putExtra("id", zakazy.get(groupPosition).getId());
                startActivity(intent);
                return true;
            }
        });

        zakazy = new ArrayList<>();
        zakazy_buf = new ArrayList<>();

        expListView.setAdapter(new Zakazy_2(this, zakazy));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg_zakaz = new Add_Dialog(Zakazy_Client.this, "zakaz_type", String.valueOf(caption.getText()), String.valueOf(id),"");
                dlg_zakaz.show(getFragmentManager(), "");
            }
        });

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

    public void expanded(int id){
        if (expListView.isGroupExpanded(id)){
            expListView.collapseGroup(id);
        }else{
            expListView.expandGroup(id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tabHost.getCurrentTab()==0)
            reload_1();
        if (tabHost.getCurrentTab()==1)
            reload_2();
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

                if (!zakazy.isEmpty()) {
                    notfound_2.setVisibility(View.GONE);
                } else {
                    notfound_2.setVisibility(View.VISIBLE);
                }
                notfound_2.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<Zakazy>> call, Throwable t) {
                Toast.makeText(Zakazy_Client.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
