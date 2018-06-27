package by.minskkniga.minskkniga.activity.Spravoch_Clients;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends AppCompatActivity {

    // TODO исправить контакты, а то не добавляются

    ScrollView sv;
    ImageButton back;

    TextView price_caption;
    TextView vzaimo_caption;
    TextView region_caption;
    TextView contacts_caption;
    TextView nacenka_text;

    LinearLayout price_linear;
    LinearLayout vzaimo_linear;
    LinearLayout region_linear;
    LinearLayout contacts_linear;

    DialogFragment dlg_gorod;
    DialogFragment dlg_contact;

    EditText name;
    EditText sokr_name;
    EditText zametka;
    EditText print;
    EditText nacenka;
    EditText skidka;
    EditText dolg;
    EditText naprav;
    EditText school;

    Switch skidka_switch;
    Switch podarki_switch;

    Spinner type_ceni;
    Spinner type_dolg;
    Spinner smena;

    Button add_gorod;
    Button add_contact;
    Button cancel;
    Button ok;

    ListView list_contact;
    ArrayList<String> contact_type;
    ArrayList<String> contact_text;

    int gorod = -1;
    String contacts = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        back = findViewById(R.id.back);
        add_gorod = findViewById(R.id.add_gorod);
        add_contact = findViewById(R.id.add_contact);
        cancel = findViewById(R.id.cancel);
        ok = findViewById(R.id.ok);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sv = findViewById(R.id.scrollview);

        dlg_gorod = new Add_Dialog(this, "gorod_client");
        dlg_contact = new Add_Dialog(this, "contact_client");

        contact_type = new ArrayList<>();
        contact_text = new ArrayList<>();

        list_contact = findViewById(R.id.contacts_listview);

        name = findViewById(R.id.name);
        sokr_name = findViewById(R.id.sokr_name);
        zametka = findViewById(R.id.zametka);
        print = findViewById(R.id.print);
        nacenka = findViewById(R.id.nacenka);
        skidka = findViewById(R.id.skidka);
        dolg = findViewById(R.id.dolg);
        naprav = findViewById(R.id.naprav);
        school = findViewById(R.id.school);

        skidka_switch = findViewById(R.id.skidka_switch);
        podarki_switch = findViewById(R.id.podarki_switch);

        type_ceni = findViewById(R.id.type_ceni);
        type_dolg = findViewById(R.id.type_dolg);
        smena = findViewById(R.id.smena);

        nacenka_text = findViewById(R.id.nacenka_text);

        dolg.setVisibility(View.GONE);
        nacenka.setVisibility(View.GONE);
        skidka.setVisibility(View.GONE);
        nacenka_text.setVisibility(View.GONE);
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
//end открытие закрытие подпунктов

        skidka_switch = findViewById(R.id.skidka_switch);
        skidka_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    skidka.setVisibility(View.VISIBLE);
                } else {
                    skidka.setVisibility(View.GONE);
                    skidka.setText("");
                }
            }
        });

        type_ceni = findViewById(R.id.type_ceni);
        type_ceni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //закупочная
                        nacenka.setVisibility(View.GONE);
                        nacenka_text.setVisibility(View.GONE);
                        break;
                    case 1: //оптовая
                        nacenka.setVisibility(View.VISIBLE);
                        nacenka_text.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        type_dolg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //нет долга
                        dolg.setVisibility(View.GONE);
                        break;
                    case 1: //нам должны
                        dolg.setVisibility(View.VISIBLE);
                        break;
                    case 2: //мы должны
                        dolg.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_gorod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg_gorod.show(getFragmentManager(), "");
            }
        });

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg_contact.show(getFragmentManager(), "");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_client();
            }
        });
    }

    public void return_gorod(int id, String name){
        gorod = id;
        add_gorod.setText(name);
    }

    public void return_contact(String type, String text) {
        if (!text.isEmpty()) {
            contact_type.add(type);
            contact_text.add(text);
            list_contact.setAdapter(new Add_Contacts(this, contact_type, contact_text));
            setListViewHeightBasedOnChildren(list_contact);
        }
    }


    public void add_client() {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Наименование клиента' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, name.getTop());
            name.requestFocus();
            return;
        }

        if (type_ceni.getSelectedItemPosition()==2 && nacenka.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Наценка' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, price_linear.getTop()+nacenka.getTop());
            nacenka.requestFocus();
            return;
        }

        if (skidka_switch.isChecked() && skidka.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Скидка' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, price_linear.getTop()+skidka.getTop());
            skidka.requestFocus();
            return;
        }

//        switch (type_dolg.getSelectedItemPosition()) {
//            case 0:
//                type_dolg_summa = "0.0";
//                break;
//            case 1:
//                if (dolg.getText().toString().isEmpty()){
//                    Toast.makeText(this, "Поле 'Долг' не заполнено", Toast.LENGTH_SHORT).show();
//                    sv.smoothScrollTo(0, vzaimo_linear.getTop()+dolg.getTop());
//                    dolg.requestFocus();
//                    return;
//                }
//                type_dolg_summa = dolg.getText().toString();
//                break;
//            case 2:
//                if (dolg.getText().toString().isEmpty()){
//                    Toast.makeText(this, "Поле 'Долг' не заполнено", Toast.LENGTH_SHORT).show();
//                    sv.smoothScrollTo(0, vzaimo_linear.getTop()+dolg.getTop());
//                    dolg.requestFocus();
//                    return;
//                }
//                type_dolg_summa = "-"+dolg.getText().toString();
//                break;
//        }

        if (type_ceni.getSelectedItemPosition()!=0){
            if (nacenka.getText().toString().isEmpty()){
                Toast.makeText(this, "Поле 'Наценка' не заполнено", Toast.LENGTH_SHORT).show();
                sv.smoothScrollTo(0, price_linear.getTop()+nacenka.getTop());
                nacenka.requestFocus();
                return;
            }
        }

        if (type_dolg.getSelectedItemPosition()!=0){
            if (dolg.getText().toString().isEmpty()){
                Toast.makeText(this, "Поле 'Долг' не заполнено", Toast.LENGTH_SHORT).show();
                sv.smoothScrollTo(0, vzaimo_linear.getTop()+dolg.getTop());
                dolg.requestFocus();
                return;
            }
        }

        if (gorod==-1){
            Toast.makeText(this, "Выберите город", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, region_linear.getTop()+add_gorod.getTop());
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return;
        }

        if (contact_type.size() != 0) {
            contacts = contact_type.get(0) + "/~/" + contact_text.get(0);
            for (int i = 1; i < contact_type.size(); i++) {
                contacts += "/~~/" + contact_type.get(i) + "/~/" + contact_text.get(i);
            }
        }

        App.getApi().addClient(name.getText().toString(),
                sokr_name.getText().toString().isEmpty()?"":sokr_name.getText().toString(),
                zametka.getText().toString().isEmpty()?"":zametka.getText().toString(),
                print.getText().toString().isEmpty()?"":print.getText().toString(),
                type_ceni.getSelectedItemPosition()==0?"zakyp":"optov",
                type_ceni.getSelectedItemPosition()==0?"0.00":nacenka.getText().toString(),
                podarki_switch.isChecked()?"1":"0",
                skidka.getText().toString().isEmpty()?"0.00":skidka.getText().toString(),
                dolg.getText().toString().isEmpty()?"0.00":dolg.getText().toString(),
                naprav.getText().toString().isEmpty()?"":naprav.getText().toString(),
                gorod,
                school.getText().toString().isEmpty()?"":school.getText().toString(),
                String.valueOf(smena.getSelectedItemPosition()),
                contacts.isEmpty()?"null":contacts).enqueue(new Callback<ResultBody>() {

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