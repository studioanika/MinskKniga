package by.minskkniga.minskkniga.activity.Zakazy;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Zakazy.Filter;
import by.minskkniga.minskkniga.adapter.Zakazy.Main_1;
import by.minskkniga.minskkniga.adapter.Zakazy.Main_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Spinner_filter;
import by.minskkniga.minskkniga.api.Class.Zakaz_filter;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import by.minskkniga.minskkniga.api.Class.Zakazy_short;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ImageButton back;
    ImageButton filter_button;
    LinearLayout filter_layout;
    ListView lv1;
    ListView lv2;

    ArrayList<Clients> clien = new ArrayList<>();
    ArrayList<Clients> clien_buf  = new ArrayList<>();

    ArrayList<Zakazy_short> zakazy_short = new ArrayList<>();
    ArrayList<Zakazy_short> zakazy_short_buf = new ArrayList<>();

    TabHost tabHost;

    TextView notfound_1;
    TextView notfound_2;

    DialogFragment dlg_zakaz;

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;

    Button clear1;
    Button clear2;
    Button clear3;

    ArrayList<Spinner_filter> buf1 = new ArrayList<>();
    ArrayList<Spinner_filter> buf2 = new ArrayList<>();
    ArrayList<Spinner_filter> buf3 = new ArrayList<>();

    public void initialize() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        filter_button = findViewById(R.id.filter_button);
        filter_layout = findViewById(R.id.filter_layout);


        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filter_layout.getVisibility() == View.GONE){
                    filter_layout.setVisibility(View.VISIBLE);
                }else{
                    filter_layout.setVisibility(View.GONE);
                }
            }
        });

        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);

        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);

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
                if (tabHost.getCurrentTab() == 0) {
                    reload_1();
                    filter_button.setVisibility(View.VISIBLE);
                }
                if (tabHost.getCurrentTab() == 1) {
                    reload_2();
                    filter_button.setVisibility(View.GONE);
                }
            }
        });

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        clear1 = findViewById(R.id.clear1);
        clear2 = findViewById(R.id.clear2);
        clear3 = findViewById(R.id.clear3);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg_zakaz = new Add_Dialog(Main.this, "zakaz_type", "null", "null", "");
                dlg_zakaz.show(getFragmentManager(), "");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy);
        initialize();

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main.this, Zakaz_info.class);
                intent.putExtra("name", zakazy_short.get(position).getClient());
                intent.putExtra("id", zakazy_short.get(position).getId());
                startActivity(intent);
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main.this, Zakazy_Client.class);
                intent.putExtra("id", Integer.parseInt(clien.get(position).getId()));
                intent.putExtra("name", clien.get(position).getName());
                startActivity(intent);

            }
        });

        clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Spinner_filter buf : buf1)
                    buf.setChecked("0");
                clear1.setText("X");
                filter();
            }
        });

        clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Spinner_filter buf : buf2)
                    buf.setChecked("0");
                clear2.setText("X");
                filter();
            }
        });

        clear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Spinner_filter buf : buf3)
                    buf.setChecked("0");
                clear3.setText("X");
                filter();
            }
        });

        filter_load();
        reload_2();
    }

    @SuppressLint("DefaultLocale")
    public void response(int status, ArrayList<Spinner_filter> buffer) {
        int m = colcheck(buffer);
        switch (status) {
            case 1:
                if (m == 0) {
                    clear1.setText("X");
                } else {
                    clear1.setText(String.format("%d X", m));
                }
                break;
            case 2:
                if (m == 0) {
                    clear2.setText("X");
                } else {
                    clear2.setText(String.format("%d X", m));
                }
                break;
            case 3:
                if (m == 0) {
                    clear3.setText("X");
                } else {
                    clear3.setText(String.format("%d X", m));
                }
                break;
        }
        filter();
    }

    public int colcheck(ArrayList<Spinner_filter> buffer){
        int i=0;
        for (Spinner_filter buf : buffer)
            if (buf.getChecked().equals("1")) i++;
        return i;
    }

    public void filter(){
        zakazy_short.clear();
        int col1,col2,col3;
        boolean ch1,ch2,ch3;
        for (Zakazy_short buffer : zakazy_short_buf) {
            col1 = 0;
            col2 = 0;
            col3 = 0;
            ch1 = false;
            ch2 = false;
            ch3 = false;

            for (Spinner_filter buf1 : buf1) {
                if (buf1.getChecked().equals("1")) col1++;
                if (buf1.getChecked().equals("1") && buf1.getName().contains(buffer.getStatus()))
                    ch1 = true;
            }

            for (Spinner_filter buf2 : buf2) {
                if (buf2.getChecked().equals("1")) col2++;
                if (buf2.getChecked().equals("1") && buf2.getName().contains(buffer.getGorod()))
                    ch2 = true;
            }

            for (Spinner_filter buf3 : buf3) {
                if (buf3.getChecked().equals("1")) col3++;
                if (buf3.getChecked().equals("1") && buf3.getName().contains(buffer.getSchool()))
                    ch3 = true;
            }

            if (col1==0) ch1=true;
            if (col2==0) ch2=true;
            if (col3==0) ch3=true;


            if (ch1 && ch2 && ch3) {
                zakazy_short.add(buffer);
            }
        }

        if (!zakazy_short.isEmpty()) {
            notfound_1.setVisibility(View.GONE);
        } else {
            notfound_1.setVisibility(View.VISIBLE);
        }
        notfound_1.setText("Ничего не найдено");

        lv1.setAdapter(new Main_1(Main.this, zakazy_short));

    }

    public void reload_1() {
        App.getApi().getZakazy().enqueue(new Callback<List<Zakazy_short>>() {
            @Override
            public void onResponse(Call<List<Zakazy_short>> call, Response<List<Zakazy_short>> response) {
                zakazy_short.clear();
                zakazy_short_buf.clear();
                zakazy_short.addAll(response.body());
                zakazy_short_buf.addAll(response.body());

                if (!zakazy_short.isEmpty()) {
                    notfound_1.setVisibility(View.GONE);
                } else {
                    notfound_1.setVisibility(View.VISIBLE);
                }
                notfound_1.setText("Ничего не найдено");

                lv1.setAdapter(new Main_1(Main.this, zakazy_short));
                filter();
            }

            @Override
            public void onFailure(Call<List<Zakazy_short>> call, Throwable t) {

            }
        });
    }

    public void filter_load(){
        App.getApi().getZakaz_filter().enqueue(new Callback<Zakaz_filter>() {
            @Override
            public void onResponse(Call<Zakaz_filter> call, Response<Zakaz_filter> response) {
                buf1.clear();
                for (String buffer : response.body().getStatus()) {
                    buf1.add(new Spinner_filter(String.valueOf(buffer), "0"));
                }
                spinner1.setAdapter(new Filter(Main.this, 1, buf1));

                buf2.clear();
                for (String buffer : response.body().getGorod()) {
                    buf2.add(new Spinner_filter(String.valueOf(buffer), "0"));
                }
                spinner2.setAdapter(new Filter(Main.this, 2, buf2));

                buf3.clear();
                for (String buffer : response.body().getSchool()) {
                    buf3.add(new Spinner_filter(String.valueOf(buffer), "0"));
                }
                spinner3.setAdapter(new Filter(Main.this, 3, buf3));
            }

            @Override
            public void onFailure(Call<Zakaz_filter> call, Throwable t) {

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
