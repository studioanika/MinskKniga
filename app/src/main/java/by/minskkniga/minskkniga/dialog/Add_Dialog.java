package by.minskkniga.minskkniga.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Gorod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class Add_Dialog extends DialogFragment {

    private EditText search_ed;
    private ListView search_lv;
    private ArrayList<Gorod> gorod;
    private ArrayList<String> names;
    private ArrayList<String> names_buf;
    private TextView search_tv;

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private String id;

    private Spinner spinner;
    private EditText edittext;
    private String type;

    private EditText edit;


    public Add_Dialog(Context context, String id) {
        this.context = context;
        this.id = id;
    }


    public void search(){
        names.clear();
        if (!search_ed.getText().toString().isEmpty()) {
            for (int i = 0; i < names_buf.size(); i++) {
                if (names_buf.get(i).toLowerCase().contains(search_ed.getText().toString().toLowerCase())) {
                    names.add(names_buf.get(i));
                }
            }
        }else{
            names.addAll(names_buf);
        }
        search_lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        inflater = getActivity().getLayoutInflater();
        switch (id) {
            case "gorod_client":
                view = inflater.inflate(R.layout.dialog_add_sity, null);
                search_ed = view.findViewById(R.id.search_ed);
                search_lv = view.findViewById(R.id.search_lv);
                search_tv = view.findViewById(R.id.search_tv);
                names = new ArrayList<>();
                names_buf = new ArrayList<>();
                gorod = new ArrayList<>();

                App.getApi().getGorod().enqueue(new Callback<List<Gorod>>() {
                    @Override
                    public void onResponse(Call<List<Gorod>> call, Response<List<Gorod>> response) {

                        for (Gorod buffer : response.body()) {
                            names.add(buffer.getName());
                            names_buf.add(buffer.getName());
                        }
                        gorod.addAll(response.body());
                        //dialog.cancel();
                        search_lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names));
                    }

                    @Override
                    public void onFailure(Call<List<Gorod>> call, Throwable t) {
                        Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });

                search_ed.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        search();
                        if (names.size()==0){
                            search_tv.setVisibility(View.VISIBLE);
                        }else{
                            search_tv.setVisibility(View.GONE);
                        }
                    }
                });

                search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                            long id) {
                        for(Gorod buf : gorod){
                            if (buf.getName().equals(names.get(position)))
                                ((by.minskkniga.minskkniga.activity.Spravoch_Clients.Add)getActivity()).return_gorod(Integer.parseInt(buf.getId()),names.get(position));
                            getDialog().dismiss();
                        }
                    }
                });

                builder.setTitle("Выбор города")
                       .setView(view);
                break;
            case "contact_client":
                view = inflater.inflate(R.layout.dialog_add_contacts, null);
                spinner = view.findViewById(R.id.dialog_contact_spinner);
                edittext = view.findViewById(R.id.dialog_contact_edittext);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                edittext.setHint("Номер телефона");
                                type = "tel";
                                break;
                            case 1:
                                edittext.setHint("Электронная почта");
                                type = "mail";
                                break;
                            case 2:
                                edittext.setHint("Место жительства");
                                type = "adress";
                                break;
                            case 3:
                                edittext.setHint("Web сайт");
                                type = "site";
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
                                // код для передачи данных
                                ((by.minskkniga.minskkniga.activity.Spravoch_Clients.Add)getActivity()).return_contact(type, String.valueOf(edittext.getText()));
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
                spinner = view.findViewById(R.id.dialog_contact_spinner);
                edittext = view.findViewById(R.id.dialog_contact_edittext);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                edittext.setHint("Номер телефона");
                                type = "tel";
                                break;
                            case 1:
                                edittext.setHint("Электронная почта");
                                type = "mail";
                                break;
                            case 2:
                                edittext.setHint("Контактное лицо");
                                type = "contact";
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
                                // код для передачи данных
                                ((by.minskkniga.minskkniga.activity.Spravoch_Providers.Add)getActivity()).return_contact(type, String.valueOf(edittext.getText()));
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
            case "gorod_provider":
                view = inflater.inflate(R.layout.dialog_add_sity, null);
                search_ed = view.findViewById(R.id.search_ed);
                search_lv = view.findViewById(R.id.search_lv);
                search_tv = view.findViewById(R.id.search_tv);
                names = new ArrayList<>();
                names_buf = new ArrayList<>();
                gorod = new ArrayList<>();

                App.getApi().getGorod().enqueue(new Callback<List<Gorod>>() {
                    @Override
                    public void onResponse(Call<List<Gorod>> call, Response<List<Gorod>> response) {

                        for (Gorod buffer : response.body()) {
                            names.add(buffer.getName());
                            names_buf.add(buffer.getName());
                        }
                        gorod.addAll(response.body());

                        search_lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names));
                    }

                    @Override
                    public void onFailure(Call<List<Gorod>> call, Throwable t) {
                        Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });

                search_ed.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        search();
                        if (names.size()==0){
                            search_tv.setVisibility(View.VISIBLE);
                        }else{
                            search_tv.setVisibility(View.GONE);
                        }
                    }
                });

                search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                            long id) {
                        for(Gorod buf : gorod){
                            if (buf.getName().equals(names.get(position)))
                                ((by.minskkniga.minskkniga.activity.Spravoch_Providers.Add)getActivity()).return_gorod(Integer.parseInt(buf.getId()),names.get(position));
                            getDialog().dismiss();
                        }

                    }
                });

                builder.setTitle("Выбор города")
                        .setView(view);
                break;
            case "contact_courier":
                view = inflater.inflate(R.layout.dialog_add_contacts, null);
                spinner = view.findViewById(R.id.dialog_contact_spinner);
                edittext = view.findViewById(R.id.dialog_contact_edittext);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                edittext.setHint("Номер телефона");
                                type = "tel";
                                break;
                            case 1:
                                edittext.setHint("Электронная почта");
                                type = "mail";
                                break;
                            case 2:
                                edittext.setHint("Контактное лицо");
                                type = "contact";
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
                                // код для передачи данных
                                ((by.minskkniga.minskkniga.activity.Spravoch_Couriers.Add) getActivity()).return_contact(type, String.valueOf(edittext.getText()));
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
        }
        return builder.create();
    }
}
