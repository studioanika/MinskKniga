package by.minskkniga.minskkniga.activity.Spravoch_Clients;

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

import by.minskkniga.minskkniga.adapter.Spravoch_Clients.Main_1;
import by.minskkniga.minskkniga.adapter.Spravoch_Clients.Main_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ListView lv2;
    ArrayList<Clients> clien;
    ArrayList<Clients> clien_buf;

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
        setContentView(R.layout.activity_spravoch_clients);

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

        clien = new ArrayList<>();
        clien_buf = new ArrayList<>();

        lv2 = findViewById(R.id.lv2);

        lv2.setAdapter(new Main_2(this, clien));

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
        clien.clear();
        if (!search.getText().toString().isEmpty()) {
            for (int i = 0; i < clien_buf.size(); i++) {
                if (clien_buf.get(i).getName().toLowerCase().contains(search.getText().toString().toLowerCase())) {
                    clien.add(clien_buf.get(i));
                }
            }
        }else{
            clien.addAll(clien_buf);
        }
        lv2.setAdapter(new Main_2(this, clien));
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
        App.getApi().getClients().enqueue(new Callback<List<Clients>>() {
            @Override
            public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                //Подготавливаем список данных:
                listDataHeader = new ArrayList<String>();
                listDataChild = new ArrayList<ArrayList<String>>();
                
                List<Clients> cli = response.body();

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


                expListView.setAdapter(new Main_1(getApplicationContext(), listDataHeader, listDataChild));
                notfound_1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Clients>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reload_2(){
        App.getApi().getClients().enqueue(new Callback<List<Clients>>() {
            @Override
            public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                clien.clear();
                clien_buf.clear();
                clien.addAll(response.body());
                clien_buf.addAll(response.body());
                lv2.setAdapter(new Main_2(Main.this, clien));
                search();
                notfound_2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Clients>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }
}