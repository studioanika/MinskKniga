package by.minskkniga.minskkniga.activity.Spravoch_Clients;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Add_Contacts;
import by.minskkniga.minskkniga.adapter.Add_Dela;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends AppCompatActivity {

    ScrollView sv;
    DialogFragment dlg1;
    DialogFragment dlg2;
    DialogFragment dlg3;
    DialogFragment dlg4;

    Button add_city;
    Button add_dela;
    Button add_contact;
    Button add_contactfaces;

    Spinner dolg_type;
    Spinner type_ceni;

    Switch podarki_switch;
    Switch skidka_switch;

    TextView price_caption;
    TextView vzaimo_caption;
    TextView region_caption;
    TextView dela_caption;
    TextView contacts_caption;
    TextView contactface_caption;

    LinearLayout price_linear;
    LinearLayout vzaimo_linear;
    LinearLayout region_linear;
    LinearLayout dela_linear;
    LinearLayout contacts_linear;
    LinearLayout contactface_linear;

    EditText name_ed;
    EditText sokr_name_ed;
    EditText zametka_ed;
    EditText info_ed;
    EditText skidka_ed;
    EditText dolg_ed;
    EditText naprav_ed;
    EditText school_ed;
    EditText smena_ed;

    ListView list_dela;
    ListView list_contact;
    ListView list_contactfaces;

    ArrayList<String> dela_date;
    ArrayList<String> dela_status;
    ArrayList<String> dela_otvetstv;
    ArrayList<String> dela_otvet_id;
    ArrayList<String> dela_text;

    ArrayList<String> contact_type;
    ArrayList<String> contact_text;

    ArrayList<String> array_contactface;

    SharedPreferences sp;

    String id;

    String podarki = "0";
    String ceni_type;
    int city = -1;
    int type_dolg;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sv = findViewById(R.id.scrollview);

        dlg1 = new Add_Dialog(this, 1);

        dlg2 = new Add_Dialog(this, 2);

        dela_date = new ArrayList<>();
        dela_status = new ArrayList<>();
        dela_otvetstv = new ArrayList<>();
        dela_otvet_id = new ArrayList<>();
        dela_text = new ArrayList<>();

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        id = sp.getString("id", "");

        list_dela = findViewById(R.id.dela_listview);

        list_dela.setAdapter(new Add_Dela(this, dela_date, dela_status, dela_otvetstv));

        dlg3 = new Add_Dialog(this, 3);

        contact_type = new ArrayList<String>();
        contact_text = new ArrayList<String>();

        list_contact = findViewById(R.id.contacts_listview);


        dlg4 = new Add_Dialog(this, 4);
        array_contactface = new ArrayList<String>();
        list_contactfaces = findViewById(R.id.contactface_listview);

        name_ed = findViewById(R.id.name_ed);
        sokr_name_ed = findViewById(R.id.sokr_name_ed);
        zametka_ed = findViewById(R.id.zametka_ed);
        info_ed = findViewById(R.id.info_ed);
        skidka_ed = findViewById(R.id.skidka_ed);
        dolg_ed = findViewById(R.id.dolg_ed);
        naprav_ed = findViewById(R.id.naprav_ed);
        school_ed = findViewById(R.id.school_ed);
        smena_ed = findViewById(R.id.smena_ed);


        dolg_ed.setVisibility(View.INVISIBLE);
//start открытие закрытие подпунктов

        price_caption = findViewById(R.id.price_caption);
        price_linear = findViewById(R.id.price_linear);
        price_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price_linear.getVisibility() == View.VISIBLE) {
                    price_linear.setVisibility(View.GONE);
                } else {
                    price_linear.setVisibility(View.VISIBLE);
                }
            }
        });

        vzaimo_caption = findViewById(R.id.vzaimo_caption);
        vzaimo_linear = findViewById(R.id.vzaimo_linear);
        vzaimo_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vzaimo_linear.getVisibility() == View.VISIBLE) {
                    vzaimo_linear.setVisibility(View.GONE);
                } else {
                    vzaimo_linear.setVisibility(View.VISIBLE);
                }
            }
        });

        region_caption = findViewById(R.id.region_caption);
        region_linear = findViewById(R.id.region_linear);
        region_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (region_linear.getVisibility() == View.VISIBLE) {
                    region_linear.setVisibility(View.GONE);
                } else {
                    region_linear.setVisibility(View.VISIBLE);
                }
            }
        });

        dela_caption = findViewById(R.id.dela_caption);
        dela_linear = findViewById(R.id.dela_linear);
        dela_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dela_linear.getVisibility() == View.VISIBLE) {
                    dela_linear.setVisibility(View.GONE);
                } else {
                    dela_linear.setVisibility(View.VISIBLE);
                }
            }
        });

        contacts_caption = findViewById(R.id.contacts_caption);
        contacts_linear = findViewById(R.id.contacts_linear);
        contacts_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contacts_linear.getVisibility() == View.VISIBLE) {
                    contacts_linear.setVisibility(View.GONE);
                } else {
                    contacts_linear.setVisibility(View.VISIBLE);
                }
            }
        });

        contactface_caption = findViewById(R.id.contactface_caption);
        contactface_linear = findViewById(R.id.contactface_linear);
        contactface_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactface_linear.getVisibility() == View.VISIBLE) {
                    contactface_linear.setVisibility(View.GONE);
                } else {
                    contactface_linear.setVisibility(View.VISIBLE);
                }
            }
        });

//end открытие закрытие подпунктов

        podarki_switch = findViewById(R.id.podarki_switch);
        podarki_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    podarki = "1";
                } else {
                    podarki = "0";
                }
            }
        });


        skidka_switch = findViewById(R.id.skidka_switch);
        skidka_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    skidka_ed.setEnabled(true);
                } else {
                    skidka_ed.setEnabled(false);
                    skidka_ed.setText("");
                }
            }
        });


        type_ceni = findViewById(R.id.type_ceni);
        type_ceni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //продажная
                        ceni_type = "prod";
                        break;
                    case 1: //закупочная
                        ceni_type = "zakyp";
                        break;
                    case 2: //оптовая
                        ceni_type = "optov";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dolg_type = (Spinner) findViewById(R.id.dolg_type);
        dolg_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //нет долга
                        type_dolg = 0;
                        dolg_ed.setVisibility(View.INVISIBLE);
                        break;
                    case 1: //нам должны
                        type_dolg = 1;
                        dolg_ed.setVisibility(View.VISIBLE);
                        break;
                    case 2: //мы должны
                        type_dolg = 2;
                        dolg_ed.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_city = findViewById(R.id.city_add);
        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg1.show(getFragmentManager(), "");
            }
        });

        add_dela = findViewById(R.id.dela_add);
        add_dela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg2.show(getFragmentManager(), "");
            }
        });

        add_contact = findViewById(R.id.contacts_add);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg3.show(getFragmentManager(), "");
            }
        });

        add_contactfaces = findViewById(R.id.contactface_add);
        add_contactfaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg4.show(getFragmentManager(), "");
            }
        });
    }

    public void return_sity(int id, String name){
        city = id;
        add_city.setText(name);
    }

    public void return_dela(String date, String status, String otve_name, String otve, String text) {
        dela_date.add(date);
        dela_status.add(status);
        dela_otvetstv.add(otve_name);
        dela_otvet_id.add(otve);
        dela_text.add(text);
        list_dela.setAdapter(new Add_Dela(this, dela_date, dela_status, dela_otvetstv));
        setListViewHeightBasedOnChildren(list_dela);


        Toast.makeText(this, date+" "+status+" "+otve_name+" "+otve+" "+text, Toast.LENGTH_SHORT).show();
    }

    public void return_contact(String type, String text) {
        if (!text.isEmpty()) {
            contact_type.add(type);
            contact_text.add(text);
            list_contact.setAdapter(new Add_Contacts(this, contact_type, contact_text));
            setListViewHeightBasedOnChildren(list_contact);
        }
    }

    public void return_contact_faces(String text) {
        if (!text.isEmpty()) {
            array_contactface.add(text);
            list_contactfaces.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.adapter_add_contactfaces, array_contactface));
            setListViewHeightBasedOnChildren(list_contactfaces);
        }
    }

    Double creditsize = 0.00;
    String dela = "";
    String contacts = "";
    String contactfaces = "";
    String dolg = "0";

    int i = 0;

    public void add_back(View view){
        onBackPressed();
    }

    public void add_client_button(View view) {
        if (name_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Наименование клиента' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, name_ed.getTop());
            name_ed.requestFocus();
            return;
        }
        if (sokr_name_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Сокращение' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, sokr_name_ed.getTop());
            sokr_name_ed.requestFocus();
            return;
        }
        if (zametka_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Заметка' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, zametka_ed.getTop());
            zametka_ed.requestFocus();
            return;
        }
        if (info_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Инфо' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, info_ed.getTop());
            info_ed.requestFocus();
            return;
        }

        if (skidka_switch.isChecked() && skidka_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Скидка' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, price_linear.getTop()+skidka_ed.getTop());
            skidka_ed.requestFocus();
            return;
        }
        switch (type_dolg) {
            case 0:
                creditsize = 0.0;
                break;
            case 1:
                if (dolg_ed.getText().toString().isEmpty()){
                    Toast.makeText(this, "Поле 'Взаиморасчеты' не заполнено", Toast.LENGTH_SHORT).show();
                    sv.smoothScrollTo(0, vzaimo_linear.getTop()+dolg_ed.getTop());
                    dolg_ed.requestFocus();
                    return;
                }
                creditsize = Double.parseDouble(dolg_ed.getText().toString());
                break;
            case 2:
                if (dolg_ed.getText().toString().isEmpty()){
                    Toast.makeText(this, "Поле 'Взаиморасчеты' не заполнено", Toast.LENGTH_SHORT).show();
                    sv.smoothScrollTo(0, vzaimo_linear.getTop()+dolg_ed.getTop());
                    dolg_ed.requestFocus();
                    return;
                }
                creditsize = 0 - Double.parseDouble(dolg_ed.getText().toString());
                break;
        }

        if (naprav_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Направление' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, region_linear.getTop()+naprav_ed.getTop());
            naprav_ed.requestFocus();
            return;
        }

        if (city==-1){
            Toast.makeText(this, "Выберите город", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, region_linear.getTop()+add_city.getTop());
            View vieww = this.getCurrentFocus();
            if (vieww != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return;
        }

        if (school_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Школа' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, region_linear.getTop()+school_ed.getTop());
            school_ed.requestFocus();
            return;
        }
        if (smena_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Смена' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, region_linear.getTop()+smena_ed.getTop());
            smena_ed.requestFocus();
            return;
        }
        dolg = skidka_ed.getText().toString();
        if (dolg.isEmpty()) dolg = "0.0";

        
        if (dela_date.size() != 0) {
            dela = dela_date.get(0) + "/~/" + (dela_status.get(0).equals("Новое")?"new":"ok") + "/~/" + dela_otvet_id.get(0) + "/~/" + dela_text.get(0);
            for (int i = 1; i < dela_date.size(); i++) {
                dela += "/~~/" + dela_date.get(i) + "/~/" + (dela_status.get(i).equals("Новое")?"new":"ok") + "/~/" + dela_otvet_id.get(i) + "/~/" + dela_text.get(i);
            }
        }

        if (contact_type.size() != 0) {
            contacts = contact_type.get(0) + "/~/" + contact_text.get(0);
            for (int i = 1; i < contact_type.size(); i++) {
                contacts += "/~~/" + contact_type.get(i) + "/~/" + contact_text.get(i);
            }
        }

        if (array_contactface.size() != 0) {
            contactfaces = array_contactface.get(0);
            i = 0;
            for (String buf : array_contactface) {
                if (i != 0) {
                    contactfaces += "/~~/" + buf;
                }
                i++;
            }
        }
        if (dela.isEmpty()) dela = "null";
        if (contacts.isEmpty()) contacts = "null";
        if (contactfaces.isEmpty()) contactfaces = "null";

        App.getApi().addClient(id,
                name_ed.getText().toString(),
                sokr_name_ed.getText().toString(),
                info_ed.getText().toString(),
                zametka_ed.getText().toString(),
                naprav_ed.getText().toString(),
                city,
                school_ed.getText().toString(),
                smena_ed.getText().toString(),
                ceni_type,
                creditsize,
                podarki,
                Double.parseDouble(dolg),
                dela,
                contacts,
                contactfaces).enqueue(new Callback<ResultBody>() {

            @Override
            public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                finish();
            }

            @Override
            public void onFailure(Call<ResultBody> call, Throwable t) {
                Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}