package by.minskkniga.minskkniga.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Spravoch_Clients.Add;
import by.minskkniga.minskkniga.activity.Zakazy.Zakaz_new;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Gorod;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.Product_client;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class Add_Dialog extends DialogFragment {

    DialogFragment dlg_image;
    private View view;
    private LayoutInflater inflater;
    private AlertDialog.Builder builder;
    private Context context;
    private String type_dialog;
    private String id_product;
    private String url;


    private String type_client;
    private String type_provider;
    private String type_courier;
    private int type_zakaz = 1;

    private String image;
    private String name_client = "null";
    private String id_client = "null";
    private Zakaz_product product;
    private String const_podar;
    private String ispodarki;
    private String barcode;

    public Add_Dialog(Context context, String type_dialog) {
        this.context = context;
        this.type_dialog = type_dialog;
    }

    public Add_Dialog(Context context, String type_dialog, String url) {
        this.context = context;
        this.type_dialog = type_dialog;
        this.url = url;
    }

    public Add_Dialog(Context context, String type_dialog, String id_product, String id_client) {
        this.context = context;
        this.type_dialog = type_dialog;
        this.id_product = id_product;
        this.id_client = id_client;
    }

    public Add_Dialog(Context context, String type_dialog, String name_client, String id_client, String nul) {
        this.context = context;
        this.type_dialog = type_dialog;
        this.name_client = name_client;
        this.id_client = id_client;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(context);
        inflater = getActivity().getLayoutInflater();

        switch (type_dialog) {
            case "gorod_client":
                gorod_client();
                break;
            case "contact_client":
                contact_client();
                break;
            case "contact_provider":
                contact_provider();
                break;
            case "gorod_provider":
                gorod_provider();
                break;
            case "contact_courier":
                contact_courier();
                break;
            case "zakaz_type":
                zakaz_type();
                break;
            case "zakaz_client":
                zakaz_client();
                break;
            case "nomenclatura_info":
                nomenclatura_info();
                break;
            case "image":
                image();
                break;
        }
        return builder.create();
    }

    public void gorod_client() {
        view = inflater.inflate(R.layout.dialog_add_gorod, null);
        final EditText search_client = view.findViewById(R.id.search_gorod);
        final ListView lv_client = view.findViewById(R.id.lv_gorod);
        final TextView notfound_client = view.findViewById(R.id.notfound);
        final ArrayList<String> names_client = new ArrayList<>();
        final ArrayList<String> names_client_buf = new ArrayList<>();
        final ArrayList<Gorod> gorod_client = new ArrayList<>();

        App.getApi().getGoroda().enqueue(new Callback<List<Gorod>>() {
            @Override
            public void onResponse(Call<List<Gorod>> call, Response<List<Gorod>> response) {

                for (Gorod buffer : response.body()) {
                    names_client.add(buffer.getName());
                    names_client_buf.add(buffer.getName());
                }
                gorod_client.addAll(response.body());
                //dialog.cancel();
                lv_client.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names_client));
            }

            @Override
            public void onFailure(Call<List<Gorod>> call, Throwable t) {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

        search_client.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                names_client.clear();
                if (!search_client.getText().toString().isEmpty()) {
                    for (int i = 0; i < names_client_buf.size(); i++) {
                        if (names_client_buf.get(i).toLowerCase().contains(search_client.getText().toString().toLowerCase())) {
                            names_client.add(names_client_buf.get(i));
                        }
                    }
                } else {
                    names_client.addAll(names_client_buf);
                }
                lv_client.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names_client));

                if (names_client.size() == 0) {
                    notfound_client.setVisibility(View.VISIBLE);
                } else {
                    notfound_client.setVisibility(View.GONE);
                }
            }
        });

        lv_client.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                for (Gorod buf : gorod_client) {
                    if (buf.getName().equals(names_client.get(position)))
                        ((by.minskkniga.minskkniga.activity.Spravoch_Clients.Add) getActivity()).return_gorod(Integer.parseInt(buf.getId()), names_client.get(position));
                    getDialog().dismiss();
                }
            }
        });

        builder.setTitle("Выбор города")
                .setView(view);
    }

    public void contact_client() {
        view = inflater.inflate(R.layout.dialog_add_contacts, null);
        final Spinner spinner_client = view.findViewById(R.id.spinner);
        final EditText edittext_client = view.findViewById(R.id.edittext);

        spinner_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        edittext_client.setHint("Номер телефона");
                        type_client = "tel";
                        break;
                    case 1:
                        edittext_client.setHint("Электронная почта");
                        type_client = "mail";
                        break;
                    case 2:
                        edittext_client.setHint("Контактное лицо");
                        type_client = "contact";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle("Добавить контакт")
                .setView(view)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((by.minskkniga.minskkniga.activity.Spravoch_Clients.Add) getActivity()).return_contact(type_client, String.valueOf(edittext_client.getText()));
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public void gorod_provider() {
        view = inflater.inflate(R.layout.dialog_add_gorod, null);
        final EditText search_provider = view.findViewById(R.id.search_gorod);
        final ListView lv_provider = view.findViewById(R.id.lv_gorod);
        final TextView notfound_provider = view.findViewById(R.id.notfound);
        final ArrayList<String> names_provider = new ArrayList<>();
        final ArrayList<String> names_provider_buf = new ArrayList<>();
        final ArrayList<Gorod> gorod_provider = new ArrayList<>();

        App.getApi().getGoroda().enqueue(new Callback<List<Gorod>>() {
            @Override
            public void onResponse(Call<List<Gorod>> call, Response<List<Gorod>> response) {

                for (Gorod buffer : response.body()) {
                    names_provider.add(buffer.getName());
                    names_provider_buf.add(buffer.getName());
                }
                gorod_provider.addAll(response.body());

                lv_provider.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names_provider));
            }

            @Override
            public void onFailure(Call<List<Gorod>> call, Throwable t) {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

        search_provider.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                names_provider.clear();
                if (!search_provider.getText().toString().isEmpty()) {
                    for (int i = 0; i < names_provider_buf.size(); i++) {
                        if (names_provider_buf.get(i).toLowerCase().contains(search_provider.getText().toString().toLowerCase())) {
                            names_provider.add(names_provider_buf.get(i));
                        }
                    }
                } else {
                    names_provider.addAll(names_provider_buf);
                }
                lv_provider.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names_provider));

                if (names_provider.size() == 0) {
                    notfound_provider.setVisibility(View.VISIBLE);
                } else {
                    notfound_provider.setVisibility(View.GONE);
                }
            }
        });

        lv_provider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                for (Gorod buf : gorod_provider) {
                    if (buf.getName().equals(names_provider.get(position)))
                        ((by.minskkniga.minskkniga.activity.Spravoch_Providers.Add) getActivity()).return_gorod(Integer.parseInt(buf.getId()), names_provider.get(position));
                    getDialog().dismiss();
                }

            }
        });
        builder.setTitle("Выбор города").setView(view);
    }

    public void contact_provider() {
        view = inflater.inflate(R.layout.dialog_add_contacts, null);
        final Spinner spinner_provider = view.findViewById(R.id.spinner);
        final EditText edittext_provider = view.findViewById(R.id.edittext);

        spinner_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        edittext_provider.setHint("Номер телефона");
                        type_provider = "tel";
                        break;
                    case 1:
                        edittext_provider.setHint("Электронная почта");
                        type_provider = "mail";
                        break;
                    case 2:
                        edittext_provider.setHint("Контактное лицо");
                        type_provider = "contact";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((by.minskkniga.minskkniga.activity.Spravoch_Providers.Add) getActivity()).return_contact(type_provider, String.valueOf(edittext_provider.getText()));
                dialog.cancel();
            }
        })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Добавить контакт").setView(view);
    }

    public void contact_courier() {
        view = inflater.inflate(R.layout.dialog_add_contacts, null);
        final Spinner spinner_courier = view.findViewById(R.id.spinner);
        final EditText edittext_courier = view.findViewById(R.id.edittext);

        spinner_courier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        edittext_courier.setHint("Номер телефона");
                        type_courier = "tel";
                        break;
                    case 1:
                        edittext_courier.setHint("Электронная почта");
                        type_courier = "mail";
                        break;
                    case 2:
                        edittext_courier.setHint("Контактное лицо");
                        type_courier = "contact";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle("Добавить контакт")
                .setView(view)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((by.minskkniga.minskkniga.activity.Spravoch_Couriers.Add) getActivity()).return_contact(type_courier, String.valueOf(edittext_courier.getText()));
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public void zakaz_type() {
        view = inflater.inflate(R.layout.dialog_zakaz_type, null);

        final TextView tv1 = view.findViewById(R.id.tv1);
        final TextView tv2 = view.findViewById(R.id.tv2);
        final TextView tv3 = view.findViewById(R.id.tv3);
        final TextView tv4 = view.findViewById(R.id.tv4);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_zakaz = 1;
                tv1.setBackgroundColor(Color.rgb(221, 221, 221));
                tv2.setBackgroundColor(Color.WHITE);
                tv3.setBackgroundColor(Color.WHITE);
                tv4.setBackgroundColor(Color.WHITE);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_zakaz = 2;
                tv1.setBackgroundColor(Color.WHITE);
                tv2.setBackgroundColor(Color.rgb(221, 221, 221));
                tv3.setBackgroundColor(Color.WHITE);
                tv4.setBackgroundColor(Color.WHITE);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_zakaz = 3;
                tv1.setBackgroundColor(Color.WHITE);
                tv2.setBackgroundColor(Color.WHITE);
                tv3.setBackgroundColor(Color.rgb(221, 221, 221));
                tv4.setBackgroundColor(Color.WHITE);
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_zakaz = 4;
                tv1.setBackgroundColor(Color.WHITE);
                tv2.setBackgroundColor(Color.WHITE);
                tv3.setBackgroundColor(Color.WHITE);
                tv4.setBackgroundColor(Color.rgb(221, 221, 221));
            }
        });


        builder.setTitle("Выбор заявки")
                .setView(view)
                .setPositiveButton("Создать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //((Zakaz_new) getActivity()).return_zakaz_type(type_zakaz);
                        Intent intent = new Intent(context, Zakaz_new.class);
                        intent.putExtra("type", type_zakaz);
                        intent.putExtra("id_client", id_client);
                        intent.putExtra("name_client", name_client);
                        context.startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public void zakaz_client() {
        view = inflater.inflate(R.layout.dialog_zakaz_client, null);

        final EditText search = view.findViewById(R.id.search);
        final ListView lv = view.findViewById(R.id.lv);
        final TextView notfound = view.findViewById(R.id.notfound);
        final ArrayList<String> clients = new ArrayList<>();
        final ArrayList<String> clients_buf = new ArrayList<>();
        final ArrayList<Clients> clients_zak = new ArrayList<>();

        final EditText search_gorod = view.findViewById(R.id.search_gorod);
        final Spinner spinner = view.findViewById(R.id.spinner);
        final LinearLayout filter = view.findViewById(R.id.filter);
        final Button select_gorod = view.findViewById(R.id.select_gorod);
        final Button clear_filter = view.findViewById(R.id.clear_filter);
        final TextView notfound_gorod = view.findViewById(R.id.notfound_gorod);
        final ArrayList<String> goroda = new ArrayList<>();
        final ArrayList<String> goroda_buf = new ArrayList<>();

        App.getApi().getGoroda().enqueue(new Callback<List<Gorod>>() {
            @Override
            public void onResponse(Call<List<Gorod>> call, Response<List<Gorod>> response) {
                goroda.clear();
                goroda_buf.clear();
                for (Gorod buffer : response.body()) {
                    goroda.add(buffer.getName());
                    goroda_buf.add(buffer.getName());
                }

                if (!goroda.isEmpty()) {
                    notfound_gorod.setVisibility(View.GONE);
                    filter.setVisibility(View.VISIBLE);
                } else {
                    notfound_gorod.setVisibility(View.VISIBLE);
                    filter.setVisibility(View.GONE);
                }
                notfound_gorod.setText("Ничего не найдено");

                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, goroda));
            }

            @Override
            public void onFailure(Call<List<Gorod>> call, Throwable t) {

            }
        });

        search_gorod.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goroda.clear();
                if (!search_gorod.getText().toString().isEmpty()) {
                    for (int i = 0; i < goroda_buf.size(); i++) {
                        if (goroda_buf.get(i).toLowerCase().contains(search_gorod.getText().toString().toLowerCase())) {
                            goroda.add(goroda_buf.get(i));
                        }
                    }
                } else {
                    goroda.addAll(goroda_buf);
                }
                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, goroda));

                if (goroda.size() == 0) {
                    notfound_gorod.setVisibility(View.VISIBLE);
                    filter.setVisibility(View.GONE);
                } else {
                    notfound_gorod.setVisibility(View.GONE);
                    filter.setVisibility(View.VISIBLE);
                }
            }
        });

        select_gorod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clients.clear();
                for (Clients buffer : clients_zak) {
                    if (buffer.getGorod().equals(spinner.getSelectedItem().toString())) {
                        clients.add(buffer.getName());
                        clients_buf.add(buffer.getName());
                    }
                }

                if (!clients.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");

                lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, clients));

                clients_buf.clear();
                clients_buf.addAll(clients);
            }
        });

        clear_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_gorod.setText("");

                clients.clear();
                clients_buf.clear();

                for (Clients buffer : clients_zak) {
                    clients.add(buffer.getName());
                    clients_buf.add(buffer.getName());
                }

                filter.setVisibility(View.VISIBLE);
                notfound.setVisibility(View.GONE);

                notfound.setText("Ничего не найдено");

                lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, clients));

                goroda.addAll(goroda_buf);

                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, goroda));
            }
        });

        App.getApi().getClients().enqueue(new Callback<List<Clients>>() {
            @Override
            public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                clients.clear();
                clients_buf.clear();
                for (Clients buffer : response.body()) {
                    clients.add(buffer.getName());
                    clients_buf.add(buffer.getName());
                }
                clients_zak.addAll(response.body());

                if (!clients.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");


                lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, clients));
            }

            @Override
            public void onFailure(Call<List<Clients>> call, Throwable t) {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clients.clear();
                if (!search.getText().toString().isEmpty()) {
                    for (int i = 0; i < clients_buf.size(); i++) {
                        if (clients_buf.get(i).toLowerCase().contains(search.getText().toString().toLowerCase())) {
                            clients.add(clients_buf.get(i));
                        }
                    }
                } else {
                    clients.addAll(clients_buf);
                }
                lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, clients));

                if (clients.size() == 0) {
                    notfound.setVisibility(View.VISIBLE);
                } else {
                    notfound.setVisibility(View.GONE);
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                for (Clients buf : clients_zak) {
                    if (buf.getName().equals(clients.get(position)))
                        ((Zakaz_new) getActivity()).return_client(buf.getId(), clients.get(position));
                    getDialog().dismiss();
                }

            }
        });

        builder.setTitle("Выбор клиента")
                .setView(view)
                .setPositiveButton("Создать клиента", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, Add.class);
                        context.startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
    }

    public void nomenclatura_info(){
        view = inflater.inflate(R.layout.dialog_nomenklatura, null);

        final Button button_image = view.findViewById(R.id.button_image);
        final TextView name = view.findViewById(R.id.name);
        final TextView artikul = view.findViewById(R.id.artikul);
        final TextView clas = view.findViewById(R.id.clas);
        //final TextView predmet = view.findViewById(R.id.predmet);
        final TextView izdatel = view.findViewById(R.id.izdatel);
        final TextView autor = view.findViewById(R.id.autor);
        final TextView sokr_name = view.findViewById(R.id.sokr_name);
        final TextView ves = view.findViewById(R.id.ves);
        final TextView ostatok = view.findViewById(R.id.ostatok);
        final TextView dostupno = view.findViewById(R.id.dostupno);
        final TextView obrazec = view.findViewById(R.id.obrazec);
        final TextView zakup_cena = view.findViewById(R.id.zakup_cena);
        final TextView prod_cena = view.findViewById(R.id.prod_cena);

        final LinearLayout linear_podarki = view.findViewById(R.id.linear_podarki);
        final EditText cena_zakaz = view.findViewById(R.id.cena_zakaz);
        final TextView cena_podar = view.findViewById(R.id.cena_podar);
        final EditText col_zakaz = view.findViewById(R.id.col_zakaz);
        final EditText col_podar = view.findViewById(R.id.col_podar);

        final TextView summa_zakaz = view.findViewById(R.id.summa_zakaz);
        final TextView summa_podar = view.findViewById(R.id.summa_podar);
        final TextView summa = view.findViewById(R.id.summa);

        Toast.makeText(context, id_product+" "+id_client, Toast.LENGTH_SHORT).show();
        App.getApi().getProduct_client(id_product, id_client).enqueue(new Callback<Product_client>() {
            @Override
            public void onResponse(Call<Product_client> call, Response<Product_client> response) {
                name.setText(response.body().getName());
                artikul.setText(response.body().getArtikul());
                clas.setText(response.body().getClas());
                //predmet.setText(response.body().get);
                //predmet.setText("Предмет");
                izdatel.setText(response.body().getIzdatel());
                autor.setText(response.body().getAutor());
                sokr_name.setText(response.body().getSokrName());
                ves.setText(response.body().getVes());
                ostatok.setText(response.body().getOstatok());
                dostupno.setText(response.body().getDostupno());
                obrazec.setText(response.body().getObrazecCol());
                zakup_cena.setText(String.format("Закупочная: %s", response.body().getZakupCena()));
                prod_cena.setText(String.format("Продажная: %s", response.body().getProdCena()));
                cena_zakaz.setText(response.body().getCena());
                cena_podar.setText(response.body().getCena());

                ispodarki = response.body().getPodarki();

                if (ispodarki.equals("0")){
                    linear_podarki.setVisibility(View.GONE);
                }else{
                    linear_podarki.setVisibility(View.VISIBLE);
                }
                const_podar = response.body().getConstPodar();
                image = response.body().getImage();
                barcode = response.body().getBarcode();
            }

            @Override
            public void onFailure(Call<Product_client> call, Throwable t) {

            }
        });

        cena_zakaz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(cena_zakaz.getText()).isEmpty()) cena_zakaz.setText("0");
                cena_podar.setText(cena_zakaz.getText());
                summa(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        col_zakaz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (ispodarki.equals("1")) {
                        col_podar.setText(String.valueOf((int)Math.floor(Integer.parseInt(String.valueOf(col_zakaz.getText()))/Integer.parseInt(const_podar))));
                    } else {
                        col_podar.setText("0");
                    }
                }catch (Exception e){
                    col_podar.setText("0");
                }
                summa(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        col_podar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                summa(cena_zakaz, col_zakaz, col_podar, summa_zakaz, summa_podar, summa);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg_image = new Add_Dialog(context, "image", image);
                dlg_image.show(getFragmentManager(), "");
            }
        });

        builder.setView(view)
                .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        product = new Zakaz_product(
                                id_product,
                                String.valueOf(name.getText()),
                                String.valueOf(artikul.getText()),
                                String.valueOf(cena_zakaz.getText()).equals("")?"0":String.valueOf(cena_zakaz.getText()),
                                String.valueOf(col_zakaz.getText()).equals("")?"0":String.valueOf(col_zakaz.getText()),
                                String.valueOf(col_podar.getText()).equals("")?"0":String.valueOf(col_podar.getText()),
                                String.valueOf(summa.getText()),
                                "0",
                                String.valueOf(ves.getText()),
                                image,
                                String.valueOf(clas.getText()),
                                barcode,
                                "0");
                        ((by.minskkniga.minskkniga.activity.Nomenklatura.Main)getActivity()).return_product(product);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public void image(){
        view = inflater.inflate(R.layout.dialog_image, null);
        final ImageView image = view.findViewById(R.id.image);

        Glide.with(context).load(R.drawable.ic_launcher_foreground).into(image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Glide.with(context).load("http://query.pe.hu/admin/img/nomen/" + url).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(image);
        }else{
            Glide.with(context).load("http://query.pe.hu/admin/img/nomen/" + url).into(image);
        }

        builder.setView(view)
                .setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
    }

    public void summa(EditText cena, EditText col_zakaz, EditText col_podar, TextView summa_z, TextView summa_p, TextView summa) {

        double this_cena;
        try{
            this_cena = Double.parseDouble(cena.getText().toString());
        }catch (Exception e) {
            this_cena = 0;
        }

        int this_col_z;
        try {
             this_col_z = Integer.parseInt(col_zakaz.getText().toString());
        }catch (Exception e){
            this_col_z = 0;
        }

        int this_col_p;
        try {
            this_col_p = Integer.parseInt(col_podar.getText().toString());
        }catch (Exception e){
            this_col_p = 0;
        }


        double this_summa_z = this_cena * this_col_z;
        double this_summa_p = this_cena * this_col_p;
        double this_summa = this_summa_z - this_summa_p;

        summa_z.setText(String.valueOf(Math.round(this_summa_z * 100.0) / 100.0));
        summa_p.setText(String.valueOf(Math.round(this_summa_p * 100.0) / 100.0));
        summa.setText(String.valueOf(Math.round(this_summa * 100.0) / 100.0));
    }

}
