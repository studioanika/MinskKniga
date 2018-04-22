package by.minskkniga.minskkniga.activity.Organizer;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Organizer_info;
import by.minskkniga.minskkniga.api.Class.Organizer_info_mas;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends AppCompatActivity {

    ImageButton back;
    String id;
    String tab;
    String contragent_id;
    String autor_name;
    String autor_id;
    String ispolnitel;
    String date;
    String status;
    String text;

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    EditText ed_autor;
    TextView autor_lab;
    EditText ed_date;
    EditText ed_text;

    ArrayList<Organizer_info_mas> clients;
    ArrayList<Organizer_info_mas> couriers;

    ArrayAdapter<String> adapter;
    ArrayList<String> clients_buf;
    ArrayList<String> couriers_buf;
    String[] stat = {"Новое","Выполнено"};

    Button save;
    Button cancel;

    Calendar dateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_add);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ed_autor = findViewById(R.id.autor);
        autor_lab = findViewById(R.id.autor_lab);
        ed_date = findViewById(R.id.date);
        ed_text = findViewById(R.id.text);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);


        dateAndTime = Calendar.getInstance();
        ed_date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        ed_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Add.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });


        tab = getIntent().getStringExtra("tab");
        id = getIntent().getStringExtra("id");
        contragent_id = getIntent().getStringExtra("contragent_id");
        autor_name = getIntent().getStringExtra("autor_name");
        autor_id = getIntent().getStringExtra("autor_id");
        ispolnitel = getIntent().getStringExtra("ispolnitel");
        date = getIntent().getStringExtra("date");
        status = getIntent().getStringExtra("status");
        text = getIntent().getStringExtra("text");

        ed_autor.setText(autor_name);
        if (!date.equals("null")) ed_date.setText(date);
        ed_text.setText(text);
        adapter = new ArrayAdapter<String>(Add.this, R.layout.adapter_nomenklatura_filter, stat);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        if (!tab.equals("null")) {
            if (tab.equals("1")) {
                spinner1.setEnabled(false);
                spinner2.setEnabled(false);
                spinner3.setEnabled(false);
                ed_date.setEnabled(false);
                ed_autor.setEnabled(false);
                ed_text.setEnabled(false);
            } else {
                spinner3.setEnabled(false);
            }
        }

        if (status.equals("new"))
            spinner3.setSelection(0);
        else
            spinner3.setSelection(1);


        if (id.equals("null")) {
            ed_autor.setVisibility(View.GONE);
            autor_lab.setVisibility(View.GONE);
        }
        clients = new ArrayList<>();
        couriers = new ArrayList<>();
        clients_buf = new ArrayList<>();
        couriers_buf = new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contragent_id = clients.get(spinner1.getSelectedItemPosition()).getId().toString();
                ispolnitel = couriers.get(spinner2.getSelectedItemPosition()).getId().toString();
                date = DateUtils.formatDateTime(Add.this,
                        dateAndTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
                if (spinner3.getSelectedItemPosition() == 0)
                    status = "new";
                else
                    status = "ok";
                text = ed_text.getText().toString();


                App.getApi().addOrganizer(id, contragent_id, autor_id, ispolnitel, date, status, text).enqueue(new Callback<ResultBody>() {
                    @Override
                    public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ResultBody> call, Throwable t) {
                        Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    private void setInitialDateTime() {
        ed_date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        reload();
    }

    public void reload(){
        App.getApi().getOrganizer_info().enqueue(new Callback<Organizer_info>() {
            @Override
            public void onResponse(Call<Organizer_info> call, Response<Organizer_info> response) {
                clients.clear();
                couriers.clear();
                clients_buf.clear();
                couriers_buf.clear();
                clients.addAll(response.body().getClients());
                couriers.addAll(response.body().getCouriers());

                for (int i = 0; i < response.body().getClients().size(); i++) {
                    clients_buf.add(response.body().getClients().get(i).getName().toString());
                }

                for (int i = 0; i < response.body().getCouriers().size(); i++) {
                    couriers_buf.add(response.body().getCouriers().get(i).getName().toString());
                }

                adapter = new ArrayAdapter<>(Add.this, R.layout.adapter_nomenklatura_filter, clients_buf);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter);

                if (!contragent_id.equals("null")) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (clients.get(i).getId().equals(contragent_id)) {
                            spinner1.setSelection(i);
                        }
                    }
                }

                adapter = new ArrayAdapter<String>(Add.this, R.layout.adapter_nomenklatura_filter, couriers_buf);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter);

                if (!ispolnitel.equals("null")) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (couriers.get(i).getId().equals(ispolnitel)) {
                            spinner2.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Organizer_info> call, Throwable t) {

            }
        });
    }
}
