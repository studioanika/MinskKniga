package by.minskkniga.minskkniga.activity.Nomenklatura;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Nomenklatura_filter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Main extends AppCompatActivity {

    ImageButton filter;
    RelativeLayout filter_layout;
    ImageButton back;
    Button nomen_barcode;
    Button nomen_clear;
    Button nomen_ok;
    ListView nomen_lv;
    TextView notfound;
    EditText nomen_search;

    ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> lv;
    ArrayList<by.minskkniga.minskkniga.api.Class.Nomenklatura> lv_buf;
    by.minskkniga.minskkniga.adapter.Nomenklatura.Main nomen;

    Spinner spinner1, spinner2, spinner3, spinner4;

    ArrayList<String> yesno;
    IntentIntegrator qrScan;

    String avtor = "Автор";
    String izdatel = "Издатель";
    String obraz = "Образец";
    String class_ = "Класс";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomenklatura);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);

        nomen_clear = findViewById(R.id.nomen_clear);
        nomen_ok = findViewById(R.id.nomen_ok);
        nomen_search = findViewById(R.id.nomen_search);

        nomen_lv = findViewById(R.id.nomen_lv);

        lv = new ArrayList<>();
        lv_buf = new ArrayList<>();
        nomen = new by.minskkniga.minskkniga.adapter.Nomenklatura.Main(this, lv);


        filter_layout = findViewById(R.id.filter_layout);
        filter_layout.setVisibility(View.GONE);
        filter = findViewById(R.id.filter_button);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter_layout.getVisibility() == View.VISIBLE)
                    filter_layout.setVisibility(View.GONE);
                else
                    filter_layout.setVisibility(View.VISIBLE);
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

        notfound = findViewById(R.id.notfound);
        yesno = new ArrayList<>();
        yesno.add("Да");
        yesno.add("Нет");

        qrScan = new IntentIntegrator(this);
        nomen_barcode = findViewById(R.id.nomen_barcode);
        nomen_search = findViewById(R.id.nomen_search);
        nomen_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

//start onclick filter
        nomen_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                spinner4.setSelection(0);
                avtor = "Автор";
                izdatel = "Издатель";
                obraz = "Образец";
                class_ = "Класс";
                load_nomen();
                nomen_lv.setAdapter(nomen);
                filter_layout.setVisibility(View.GONE);
            }
        });

        nomen_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avtor = spinner1.getSelectedItem().toString();
                izdatel = spinner2.getSelectedItem().toString();
                obraz = spinner3.getSelectedItem().toString();
                class_ = spinner4.getSelectedItem().toString();
                load_nomen();
                nomen_lv.setAdapter(nomen);
                filter_layout.setVisibility(View.GONE);
            }
        });

        nomen_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//end onclick filter

        load_nomen_filter();
        load_nomen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            nomen_search.setText(result.getContents());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void filter() {
        lv.clear();
        for (by.minskkniga.minskkniga.api.Class.Nomenklatura buffer : lv_buf) {
            if (buffer.getName().contains(nomen_search.getText().toString()) ||
                    buffer.getPredmet().contains(nomen_search.getText().toString()) ||
                    buffer.getClass_().contains(nomen_search.getText().toString()) ||
                    buffer.getIzdatel().contains(nomen_search.getText().toString()) ||
                    buffer.getArtikul().contains(nomen_search.getText().toString()) ||
                    buffer.getSokrName().contains(nomen_search.getText().toString()) ||
                    buffer.getProdCena().contains(nomen_search.getText().toString()) ||
                    buffer.getBarcode().contains(nomen_search.getText().toString())) {
                lv.add(buffer);
            }
        }
        if (!lv.isEmpty()) {
            notfound.setVisibility(View.GONE);
        } else {
            notfound.setVisibility(View.VISIBLE);
        }
        nomen_lv.setAdapter(nomen);
    }

    public void load_nomen() {
        if (avtor == "Автор") avtor = "0";
        if (izdatel == "Издатель") izdatel = "0";
        if (obraz == "Образец") obraz = "0";
        if (class_ == "Класс") class_ = "0";

        App.getApi().getNomenclatura(avtor, izdatel, obraz, class_).enqueue(new Callback<List<by.minskkniga.minskkniga.api.Class.Nomenklatura>>() {
            @Override
            public void onResponse(Call<List<by.minskkniga.minskkniga.api.Class.Nomenklatura>> call, Response<List<by.minskkniga.minskkniga.api.Class.Nomenklatura>> response) {
                lv.clear();
                lv_buf.clear();
                lv.addAll(response.body());
                lv_buf.addAll(response.body());
                nomen_lv.setAdapter(nomen);

                if (!lv.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<by.minskkniga.minskkniga.api.Class.Nomenklatura>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nomen_search.getText().equals("")) {
            load_nomen_filter();
            load_nomen();
        }
        filter();
    }

    public void load_nomen_filter(){
        App.getApi().getNomenclatura_filter().enqueue(new Callback<Nomenklatura_filter>() {

            @Override
            public void onResponse(Call<Nomenklatura_filter> call, Response<Nomenklatura_filter> response) {
                setAdapter(spinner1, response.body().getAutor(), 1);
                setAdapter(spinner2, response.body().getIzdatel(), 2);
                setAdapter(spinner4, response.body().getClass_(), 4);
                setAdapter(spinner3, yesno, 3);
            }

            @Override
            public void onFailure(Call<Nomenklatura_filter> call, Throwable t) {
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
                arr.add(0, "Издатель");
                break;
            case 3:
                if (!arr.get(0).toString().equals("Образец"))
                    arr.add(0, "Образец");
                break;
            case 4:
                arr.add(0, "Класс");
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
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}