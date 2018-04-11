package by.minskkniga.minskkniga.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.adapter.Spravoch_Clients_1;
import by.minskkniga.minskkniga.adapter.Spravoch_Clients_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class_Clients;
import by.minskkniga.minskkniga.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Spravoch_Clients extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Class_Clients> clien;
    List<Class_Clients> clien_buf;

    Spravoch_Clients_1 listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    ArrayList<ArrayList<String>> listDataChild;
    Spravoch_Clients_2 adapter;
    EditText searchedit;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_clients);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("по городам");
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Spravoch_Clients.this, Add_Client.class);
                startActivity(intent);
            }
        });


//tab 1 start
        expListView = (ExpandableListView) findViewById(R.id.expandablelistview);

        /*if (android.os.Build.VERSION.SDK_INT <
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(0, 200);
        } else {
            expListView.setIndicatorBoundsRelative(0, 200);
        }*/

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(groupPosition).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
//tab 1 end

//tab 2 start
        clien = new ArrayList<>();
        clien_buf = new ArrayList<>();

        recyclerView = findViewById(R.id.rw2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Spravoch_Clients_2(clien);
        recyclerView.setAdapter(adapter);
//tab 2 end

//search start
        searchedit = findViewById(R.id.editsearchclients);
        searchedit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search();
            }
        });
//search end
    }

    public void search(){
        clien.clear();
        if (!searchedit.getText().toString().isEmpty()) {
            for (int i = 0; i < clien_buf.size(); i++) {
                if (clien_buf.get(i).getName().toLowerCase().contains(searchedit.getText().toString().toLowerCase())) {
                    clien.add(clien_buf.get(i));
                }
            }
        }else{
            clien.addAll(clien_buf);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload_1();
        reload_2();

    }

    public void reload_1(){
        App.getApi().getClients().enqueue(new Callback<List<Class_Clients>>() {
            @Override
            public void onResponse(Call<List<Class_Clients>> call, Response<List<Class_Clients>> response) {
                //Подготавливаем список данных:
                listDataHeader = new ArrayList<String>();
                listDataChild = new ArrayList<ArrayList<String>>();
                
                List<Class_Clients> cli = response.body();

                //Добавляем данные о пунктах списка:
                int col = cli.size();
                double dolg;
                int obraz = 0;
                boolean isset = true;

                for (int i=0;i<col;i++) {
                    dolg = 0;
                    isset = true;
                    obraz = 0;
                    for (int k = 0; k < i; k++) {
                        if (cli.get(i).getSity().equals(cli.get(k).getSity())) {
                            isset = false;
                        }
                    }

                    if (isset) {
                        ArrayList<String> temp = new ArrayList<String>();
                        for (int j = 0; j < col; j++) {
                            if (cli.get(j).getSity().equals(cli.get(i).getSity())) {
                                temp.add(cli.get(j).getName() + "@" + cli.get(j).getObraz() + "@" + cli.get(j).getDolg());
                                dolg += cli.get(j).getDolg();
                                if (cli.get(j).getObraz().equals("1")) obraz = 1;
                            }
                        }
                        listDataHeader.add(cli.get(i).getSity() + "@" + obraz + "@" + dolg);
                        listDataChild.add(temp);
                    }
                }


                listAdapter = new Spravoch_Clients_1(getApplicationContext(), listDataHeader, listDataChild);

                //Настраиваем listAdapter:
                expListView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<Class_Clients>> call, Throwable t) {
                Toast.makeText(Spravoch_Clients.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reload_2(){
        App.getApi().getClients().enqueue(new Callback<List<Class_Clients>>() {
            @Override
            public void onResponse(Call<List<Class_Clients>> call, Response<List<Class_Clients>> response) {
                clien.clear();
                clien_buf.clear();
                clien.addAll(response.body());
                clien_buf.addAll(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();
                search();
            }

            @Override
            public void onFailure(Call<List<Class_Clients>> call, Throwable t) {
                Toast.makeText(Spravoch_Clients.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }
}