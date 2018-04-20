package by.minskkniga.minskkniga.activity.Spravoch_Providers;

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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Spravoch_Providers.Main_1;
import by.minskkniga.minskkniga.adapter.Spravoch_Providers.Main_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Providers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ListView lv2;
    ArrayList<Providers> prov;
    ArrayList<Providers> prov_buf;

    Main_1 listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    ArrayList<ArrayList<String>> listDataChild;
    EditText search;
    ImageButton back;

    TextView notfound_1;
    TextView notfound_2;

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_providers);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);

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

        tabHost.setCurrentTab(0);

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
                Intent intent = new Intent(Main.this, Add.class);
                startActivity(intent);
            }
        });

        expListView = findViewById(R.id.expandablelistview);



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


        prov = new ArrayList<>();
        prov_buf = new ArrayList<>();

        lv2 = findViewById(R.id.lv2);


        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {

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

    }

    public void search(){
        prov.clear();
        if (!search.getText().toString().isEmpty()) {
            for (int i = 0; i < prov_buf.size(); i++) {
                if (prov_buf.get(i).getName().toLowerCase().contains(search.getText().toString().toLowerCase())) {
                    prov.add(prov_buf.get(i));
                }
            }
        }else{
            prov.addAll(prov_buf);
        }
        if (!prov.isEmpty()) {
            notfound_2.setVisibility(View.GONE);
        } else {
            notfound_2.setVisibility(View.VISIBLE);
        }
        notfound_2.setText("Ничего не найдено");
        lv2.setAdapter(new Main_2(this, prov));
    }

    @Override
    protected void onResume() {
        if (tabHost.getCurrentTab()==0)
            reload_1();
        if (tabHost.getCurrentTab()==1 && search.getText().toString().isEmpty())
            reload_2();
        super.onResume();
    }

    public void reload_1(){
        App.getApi().getProviders().enqueue(new Callback<List<Providers>>() {
            @Override
            public void onResponse(Call<List<Providers>> call, Response<List<Providers>> response) {
                //Подготавливаем список данных:
                listDataHeader = new ArrayList<String>();
                listDataChild = new ArrayList<ArrayList<String>>();

                List<Providers> pro = response.body();

                //Добавляем данные о пунктах списка:
                int col = pro.size();
                double dolg;
                boolean isset = true;

                for (int i=0;i<col;i++) {
                    dolg = 0;
                    isset = true;

                    for (int k = 0; k < i; k++) {
                        if (pro.get(i).getCity().equals(pro.get(k).getCity())) {
                            isset = false;
                        }
                    }

                    if (isset) {
                        ArrayList<String> temp = new ArrayList<String>();
                        for (int j = 0; j < col; j++) {
                            if (pro.get(j).getCity().equals(pro.get(i).getCity())) {
                                temp.add(pro.get(j).getName() + "@" + pro.get(j).getCreditSize());
                                dolg += pro.get(j).getCreditSize();
                            }
                        }
                        listDataHeader.add(pro.get(i).getCity() + "@" + dolg);
                        listDataChild.add(temp);
                    }

                }

                listAdapter = new Main_1(getApplicationContext(), listDataHeader, listDataChild);

                //Настраиваем listAdapter:
                expListView.setAdapter(listAdapter);
                notfound_1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Providers>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void reload_2(){
        App.getApi().getProviders().enqueue(new Callback<List<Providers>>() {
            @Override
            public void onResponse(Call<List<Providers>> call, Response<List<Providers>> response) {
                prov.clear();
                prov_buf.clear();
                prov.addAll(response.body());
                prov_buf.addAll(response.body());
                lv2.setAdapter(new Main_2(Main.this, prov));

                if (!prov.isEmpty()) {
                    notfound_2.setVisibility(View.GONE);
                } else {
                    notfound_2.setVisibility(View.VISIBLE);
                }
                notfound_2.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<Providers>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
