package by.minskkniga.minskkniga.activity.Spravoch_Providers;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Add_Contacts;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.clients.Contact;
import by.minskkniga.minskkniga.api.Class.provider_sp.ProviderInfo;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends AppCompatActivity {

    ScrollView sv;
    DialogFragment dlg_gorod;
    DialogFragment dlg_contact;
    Button add_contact;

    Spinner type_ceni;
    Spinner dolg_type;

    Button btn_ok;

    TextView price_caption;
    TextView vzaimo_caption;
    TextView region_caption;
    TextView contacts_caption;

    LinearLayout price_linear;
    LinearLayout vzaimo_linear;
    LinearLayout region_linear;
    LinearLayout contacts_linear;


    EditText name_ed;
    EditText short_name_ed;
    EditText zametka_ed;
    EditText info_ed;
    EditText nakrytka_ed;
    EditText dolg_size;
    EditText naprav_ed;
    Button add_city;

    ListView list_contact;

    ArrayList<String> contact_type;
    ArrayList<String> contact_text;
    ArrayList<String> contacts_id = new ArrayList<>();

    String price_type;
    int type_dolg;
    ImageButton back;
    int city = -1;

    String id_provider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_provider);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_ok = (Button) findViewById(R.id.add_client);

        dlg_gorod = new Add_Dialog(this, "gorod_provider");
        dlg_contact = new Add_Dialog(this, "contact_provider");

        contact_type = new ArrayList<String>();
        contact_text = new ArrayList<String>();

        list_contact = findViewById(R.id.contacts_listview);


        sv = findViewById(R.id.scrollview);


        name_ed = findViewById(R.id.name_ed);
        short_name_ed = findViewById(R.id.short_name_ed);
        zametka_ed = findViewById(R.id.zametka_ed);
        info_ed = findViewById(R.id.info_ed);
        nakrytka_ed = findViewById(R.id.nakrytka_ed);
        dolg_size = findViewById(R.id.dolg_size);
        naprav_ed = findViewById(R.id.naprav_ed);
        add_city = findViewById(R.id.add_city);

        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg_gorod.show(getFragmentManager(), "");
            }
        });

        dolg_size.setVisibility(View.INVISIBLE);


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




        type_ceni = findViewById(R.id.type_ceni);
        type_ceni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //продажная
                        price_type = "prod";
                        break;
                    case 1: //закупочная
                        price_type = "zakyp";
                        break;
                    case 2: //оптовая
                        price_type = "optov";
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
                        dolg_size.setVisibility(View.INVISIBLE);
                        break;
                    case 1: //нам должны
                        type_dolg = 1;
                        dolg_size.setVisibility(View.VISIBLE);
                        break;
                    case 2: //мы должны
                        type_dolg = 2;
                        dolg_size.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_contact = findViewById(R.id.contacts_add);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg_contact.show(getFragmentManager(), "");
            }
        });

        Intent intent = getIntent();
        id_provider = intent.getStringExtra("id");
        if(id_provider != null) {
            btn_ok.setText("Изменить");
            loadInfoProvider(id_provider);
        }

    }

    private void loadInfoProvider(String id_provider) {

        App.getApi().getProviderId(id_provider).enqueue(new Callback<List<ProviderInfo>>() {
            @Override
            public void onResponse(Call<List<ProviderInfo>> call, Response<List<ProviderInfo>> response) {
                if(response.body() != null){

                    setInfoProvider(response.body().get(0));

                }
                else Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ProviderInfo>> call, Throwable t) {
                Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setInfoProvider(ProviderInfo providerInfo) {

        city = Integer.parseInt(providerInfo.getCityId());

        name_ed.setText(providerInfo.getName());
        short_name_ed.setText(providerInfo.getShortName());
        zametka_ed.setText(providerInfo.getShortName());
        info_ed.setText(providerInfo.getInfo());

        add_city.setText(providerInfo.getCity());

        naprav_ed.setText(providerInfo.getNapravl());

        if(providerInfo.getPriceType().contains("zakyp")) type_ceni.setSelection(1);
        else if(providerInfo.getPriceType().contains("prod")) type_ceni.setSelection(0);
        else type_ceni.setSelection(2);

        nakrytka_ed.setText(providerInfo.getNakrytka());

        dolg_size.setText(providerInfo.getCreditSize());

        if(providerInfo.getType_creditsize()!= null && !providerInfo.getType_creditsize().isEmpty()){

            if(Integer.parseInt(providerInfo.getType_creditsize()) == 0) dolg_type.setSelection(1);
            else if(Integer.parseInt(providerInfo.getType_creditsize()) == 1) dolg_type.setSelection(2);
            else dolg_type.setSelection(0);
        }

        if(providerInfo.getContacts() != null){
            contact_text.clear();
            contact_type.clear();
            for (Contact ct: providerInfo.getContacts()
                    ) {
                contacts_id.add(ct.getId());
                contact_type.add(ct.getType());
                contact_text.add(ct.getText());
                list_contact.setAdapter(new Add_Contacts(this, contact_type, contact_text));
                setListViewHeightBasedOnChildren(list_contact);

            }
        }
    }

    public void return_gorod(int id, String name) {
        city = id;
        add_city.setText(name);
    }

    public void return_contact(String type, String text) {
        if (!text.isEmpty()) {
            contact_type.add(type);
            contact_text.add(text);
            contacts_id.add("-1");
            list_contact.setAdapter(new Add_Contacts(this, contact_type, contact_text));
            setListViewHeightBasedOnChildren(list_contact);
        }
    }

    Double creditsize;
    String contacts = "";
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


        if (short_name_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Сокращение' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, short_name_ed.getTop());
            short_name_ed.requestFocus();
            return;
        }

        if (zametka_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Заметка' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, zametka_ed.getTop());
            zametka_ed.requestFocus();
            return;
        }

        if (info_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Инфо' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, info_ed.getTop());
            info_ed.requestFocus();
            return;
        }

        if (nakrytka_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Накрутка' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, nakrytka_ed.getTop());
            nakrytka_ed.requestFocus();
            return;
        }

        switch (type_dolg) {
            case 0:
                creditsize = 0.00;
                break;
            case 1:
                if (dolg_size.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Поле 'Взаиморасчеты' не заполнено", Toast.LENGTH_SHORT).show();
                    sv.smoothScrollTo(0, dolg_size.getTop());
                    dolg_size.requestFocus();
                    return;
                }
                creditsize = Double.parseDouble(dolg_size.getText().toString());
                break;
            case 2:
                if (dolg_size.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Поле 'Взаиморасчеты' не заполнено", Toast.LENGTH_SHORT).show();
                    sv.smoothScrollTo(0, dolg_size.getTop());
                    dolg_size.requestFocus();
                    return;
                }
                creditsize = 0 - Double.parseDouble(dolg_size.getText().toString());
                break;
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

        if (naprav_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Направление' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, region_linear.getTop()+naprav_ed.getTop());
            naprav_ed.requestFocus();
            return;
        }

        if (contact_type.size() != 0) {
            contacts = contact_type.get(0) + "/~/" + contact_text.get(0);
            for (int i = 1; i < contact_type.size(); i++) {
                contacts += "/~~/" + contact_type.get(i) + "/~/" + contact_text.get(i);
            }
        }


        if (contacts.isEmpty()) contacts = "null";

        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < contact_type.size(); i++) {
            Contact contact = new Contact();
            contact.setRank("provider");
            contact.setUserId(id_provider);
            contact.setText(contact_text.get(i));
            contact.setType(contact_type.get(i));
            try{
                if(contacts_id.get(i) != null)contact.setId(contacts_id.get(i));
            }
            catch (Exception e){
                contact.setId("-1");
            }
            contacts.add(contact);
        }

        Gson gson = new Gson();
        String listJSON = gson.toJson(contacts);
        String d = "";

        String type_dolg = "0";
        if(dolg_type.getSelectedItemPosition() == 0) type_dolg = "2";
        else if(dolg_type.getSelectedItemPosition() == 1) type_dolg = "0";
        else type_dolg = "1";

        if(id_provider != null){



            App.getApi().updateProvider(id_provider,
                    name_ed.getText().toString(),
                    short_name_ed.getText().toString(),
                    zametka_ed.getText().toString(),
                    info_ed.getText().toString(),
                    price_type,
                    Double.parseDouble(nakrytka_ed.getText().toString()),
                    type_dolg,
                    creditsize,
                    String.valueOf(city),
                    naprav_ed.getText().toString(),
                    contacts !=null ? listJSON : "").enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.body() != null) {
                        try {
                            String d = response.body().string();
                            if(d.contains("error")) Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(Add.this, "Изменения сохранены...", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {

            App.getApi().addProvider(name_ed.getText().toString(),
                    short_name_ed.getText().toString(),
                    zametka_ed.getText().toString(),
                    info_ed.getText().toString(),
                    price_type,
                    Double.parseDouble(nakrytka_ed.getText().toString()),
                    type_dolg,
                    creditsize,
                    String.valueOf(city),
                    naprav_ed.getText().toString(),
                    contacts !=null ? listJSON : "").enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.body() != null) {
                        ResponseBody resultBody = response.body();

                        try {
                            String sd = resultBody.string();

                            if(sd.contains("error")) Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(Add.this, "Поставщик успешно создан", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                }
            });
        }

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