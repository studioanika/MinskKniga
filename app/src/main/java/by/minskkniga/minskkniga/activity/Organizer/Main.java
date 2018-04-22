package by.minskkniga.minskkniga.activity.Organizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Nomenklatura;
import by.minskkniga.minskkniga.api.Class.Nomenklatura_filter;
import by.minskkniga.minskkniga.api.Class.Organizer;
import by.minskkniga.minskkniga.api.Class.Organizer_filter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    TabHost tabHost;
    ImageButton back;
    ImageButton filter_button;
    RelativeLayout filter_layout;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;

    String autor = "Автор";
    String komy = "Кому";
    String kontragent = "Контрагент";
    String status = "Статус";
    TextView notfound_1;
    TextView notfound_2;

    ListView lv1;
    ListView lv2;

    Button ok;
    Button clear;

    ArrayList<Organizer> organ;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        ed = sp.edit();

        id = sp.getString("id", "");
        name = sp.getString("name","");

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);

        filter_button = findViewById(R.id.filter_button);
        filter_layout = findViewById(R.id.filter_layout);
        filter_layout.setVisibility(View.GONE);
        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);
        lv1 = findViewById(R.id.lv_1);
        lv2 = findViewById(R.id.lv_2);

        ok = findViewById(R.id.ok);
        clear = findViewById(R.id.clear);

        organ = new ArrayList<>();

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter_layout.getVisibility() == View.GONE)
                    filter_layout.setVisibility(View.VISIBLE);
                else
                    filter_layout.setVisibility(View.GONE);
            }
        });

        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("мне поручили");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("я поручил");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                load_filter();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                spinner4.setSelection(0);
                autor = "Автор";
                komy = "Кому";
                kontragent = "Контрагент";
                status = "Статус";
                reload();
                if (tabHost.getCurrentTab()==0)
                    lv1.setAdapter(new by.minskkniga.minskkniga.adapter.Organizer.Main(Main.this, organ,1));
                else
                    lv2.setAdapter(new by.minskkniga.minskkniga.adapter.Organizer.Main(Main.this, organ,2));
                filter_layout.setVisibility(View.GONE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autor = spinner1.getSelectedItem().toString();
                komy = spinner2.getSelectedItem().toString();
                kontragent = spinner3.getSelectedItem().toString();
                status = spinner4.getSelectedItem().toString();
                reload();
                if (tabHost.getCurrentTab()==0)
                    lv1.setAdapter(new by.minskkniga.minskkniga.adapter.Organizer.Main(Main.this, organ,1));
                else
                    lv2.setAdapter(new by.minskkniga.minskkniga.adapter.Organizer.Main(Main.this, organ,2));
                filter_layout.setVisibility(View.GONE);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Add.class);
                intent.putExtra("tab", "null");
                intent.putExtra("id", "null");
                intent.putExtra("contragent_id", "null");
                intent.putExtra("autor_id", id);
                intent.putExtra("autor_name", name);
                intent.putExtra("ispolnitel", "null");
                intent.putExtra("date", "null");
                intent.putExtra("status", "new");
                intent.putExtra("text", "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        load_filter();
    }

    public void reload() {
        if (spinner1.getSelectedItemPosition() == 0) autor = "null";
        if (spinner2.getSelectedItemPosition() == 0) komy = "null";
        if (spinner3.getSelectedItemPosition() == 0) kontragent = "null";
        if (spinner4.getSelectedItemPosition() == 0) status = "null";

        App.getApi().getOrganizer(autor, komy, kontragent, status).enqueue(new Callback<List<Organizer>>() {
            @Override
            public void onResponse(Call<List<Organizer>> call, Response<List<Organizer>> response) {
                organ.clear();

                if (tabHost.getCurrentTab()==0) {
                    for(Organizer buffer : response.body()) {
                        if (buffer.getIspolnitelId().equals(id)) organ.add(buffer);
                    }

                    lv1.setAdapter(new by.minskkniga.minskkniga.adapter.Organizer.Main(Main.this, organ,1));
                    if (!organ.isEmpty()) {
                        notfound_1.setVisibility(View.GONE);
                    } else {
                        notfound_1.setVisibility(View.VISIBLE);
                    }
                    notfound_1.setText("Ничего не найдено");
                }
                else {
                    for(Organizer buffer : response.body()) {
                        if (buffer.getAutorId().equals(id)) organ.add(buffer);
                    }

                    lv2.setAdapter(new by.minskkniga.minskkniga.adapter.Organizer.Main(Main.this, organ,2));
                    if (!organ.isEmpty()) {
                        notfound_2.setVisibility(View.GONE);
                    } else {
                        notfound_2.setVisibility(View.VISIBLE);
                    }
                    notfound_2.setText("Ничего не найдено");
                }
            }

            @Override
            public void onFailure(Call<List<Organizer>> call, Throwable t) {

            }
        });
    }

    public void load_filter(){
        App.getApi().getOrganizer_filter().enqueue(new Callback<Organizer_filter>() {

            @Override
            public void onResponse(Call<Organizer_filter> call, Response<Organizer_filter> response) {
                setAdapter(spinner1, response.body().getAutor(), 1);
                setAdapter(spinner2, response.body().getKomy(), 2);
                setAdapter(spinner3, response.body().getKontragent(), 3);
                setAdapter(spinner4, response.body().getStatus(), 4);
                reload();
            }

            @Override
            public void onFailure(Call<Organizer_filter> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(Spinner spinner, ArrayList<String> arr, int id) {
        switch (id) {
            case 1:
                arr.add(0, "Автор");
                break;
            case 2:
                arr.add(0, "Кому");
                break;
            case 3:
                arr.add(0, "Контрагент");
                break;
            case 4:
                arr.add(0, "Статус");
                break;
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_nomenklatura_filter, arr) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = view.findViewById(R.id.tv1);

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
