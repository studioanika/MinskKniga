package by.minskkniga.minskkniga.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Spravoch_Clients.Add;
import by.minskkniga.minskkniga.activity.Zakazy.Main;
import by.minskkniga.minskkniga.activity.Zakazy.Zakaz_new;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Gorod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class Add_Dialog extends DialogFragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private String id;

    private String type_client;
    private String type_provider;
    private String type_courier;
    private int type_zakaz;

    public Add_Dialog(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        inflater = getActivity().getLayoutInflater();

        switch (id) {
            case "gorod_client":
                view = inflater.inflate(R.layout.dialog_add_gorod, null);
                final EditText search_client = view.findViewById(R.id.search_gorod);
                final ListView lv_client = view.findViewById(R.id.lv_gorod);
                final TextView notfound_client = view.findViewById(R.id.notfound);
                final ArrayList<String> names_client = new ArrayList<>();
                final ArrayList<String> names_client_buf = new ArrayList<>();
                final ArrayList<Gorod> gorod_client = new ArrayList<>();

                App.getApi().getGorod().enqueue(new Callback<List<Gorod>>() {
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
                break;
            case "contact_client":
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
                break;
            case "contact_provider":
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
                break;
            case "gorod_provider":
                view = inflater.inflate(R.layout.dialog_add_gorod, null);
                final EditText search_provider = view.findViewById(R.id.search_gorod);
                final ListView lv_provider = view.findViewById(R.id.lv_gorod);
                final TextView notfound_provider = view.findViewById(R.id.notfound);
                final ArrayList<String> names_provider = new ArrayList<>();
                final ArrayList<String> names_provider_buf = new ArrayList<>();
                final ArrayList<Gorod> gorod_provider = new ArrayList<>();

                App.getApi().getGorod().enqueue(new Callback<List<Gorod>>() {
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
                break;
            case "contact_courier":
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
                break;
            case "zakaz_type":
                view = inflater.inflate(R.layout.dialog_zakaz_type, null);

                final TextView tv1 = view.findViewById(R.id.tv1);
                final TextView tv2 = view.findViewById(R.id.tv2);
                final TextView tv3 = view.findViewById(R.id.tv3);
                final TextView tv4 = view.findViewById(R.id.tv4);
                final TextView tv5 = view.findViewById(R.id.tv5);

                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type_zakaz = 1;
                        tv1.setBackgroundColor(Color.rgb(221, 221, 221));
                        tv2.setBackgroundColor(Color.WHITE);
                        tv3.setBackgroundColor(Color.WHITE);
                        tv4.setBackgroundColor(Color.WHITE);
                        tv5.setBackgroundColor(Color.WHITE);
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
                        tv5.setBackgroundColor(Color.WHITE);
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
                        tv5.setBackgroundColor(Color.WHITE);
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
                        tv5.setBackgroundColor(Color.WHITE);
                    }
                });

                tv5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type_zakaz = 5;
                        tv1.setBackgroundColor(Color.WHITE);
                        tv2.setBackgroundColor(Color.WHITE);
                        tv3.setBackgroundColor(Color.WHITE);
                        tv4.setBackgroundColor(Color.WHITE);
                        tv5.setBackgroundColor(Color.rgb(221, 221, 221));
                    }
                });

                builder.setTitle("Выбор заявки")
                        .setView(view)
                        .setPositiveButton("Создать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Zakaz_new)getActivity()).return_zakaz_type(type_zakaz);
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

                break;




            case "zakaz_client":
                view = inflater.inflate(R.layout.dialog_zakaz_client, null);


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
                            }
                        });

                break;
        }
        return builder.create();
    }
}
