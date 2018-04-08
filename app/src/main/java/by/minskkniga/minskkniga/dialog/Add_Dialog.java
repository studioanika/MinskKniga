package by.minskkniga.minskkniga.dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Add_Client;
import by.minskkniga.minskkniga.activity.Add_Provider;
import by.minskkniga.minskkniga.activity.Spravoch_Clients;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Clients;
import by.minskkniga.minskkniga.api.Sity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class Add_Dialog extends DialogFragment {

    private EditText search_ed;
    private ListView search_lv;
    private ArrayList<String> names;
    private ArrayList<String> names_buf;
    private TextView search_tv;


    private Button datepicker;
    private Calendar dateAndTime;
    private Spinner otvetstv;
    private Spinner status;
    private String[] mas_otvetstv = {"Дима", "Вася", "Федя"};
    private String date;
    private String stat;
    private String otve;
    private ArrayAdapter<String> otvetstv_adapter;




    private View view;
    private LayoutInflater inflater;
    private Context context;
    private int id;

    private Spinner spinner;
    private EditText edittext;
    private String type;

    private EditText edit;


    public Add_Dialog(Context context, int id) {
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
            case 1:
                view = inflater.inflate(R.layout.dialog_add_sity, null);
                search_ed = view.findViewById(R.id.search_ed);
                search_lv = view.findViewById(R.id.search_lv);
                search_tv = view.findViewById(R.id.search_tv);
                names = new ArrayList<>();
                names_buf = new ArrayList<>();

                App.getApi().getSity().enqueue(new Callback<List<Sity>>() {
                    @Override
                    public void onResponse(Call<List<Sity>> call, Response<List<Sity>> response) {

                        for (Sity buffer : response.body()) {
                            names.add(buffer.getName());
                            names_buf.add(buffer.getName());
                        }
                        //dialog.cancel();
                        search_lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names));
                    }

                    @Override
                    public void onFailure(Call<List<Sity>> call, Throwable t) {
                        Toast.makeText(context, "An error occurred during networking", Toast.LENGTH_SHORT).show();
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
                        ((Add_Client)getActivity()).return_sity(position+1,names.get(position));
                        getDialog().dismiss();
                    }
                });

                builder.setTitle("Выбор города")
                       .setView(view);
                break;
            case 2:
                view = inflater.inflate(R.layout.dialog_add_dela, null);
                datepicker = view.findViewById(R.id.dialog_dela_datepicker);
                status = view.findViewById(R.id.dialog_dela_spinner1);
                status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String) parent.getItemAtPosition(position);
                        stat = item;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                otvetstv = (Spinner) view.findViewById(R.id.dialog_dela_spinner2);
                otvetstv_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mas_otvetstv);
                otvetstv_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                otvetstv.setAdapter(otvetstv_adapter);
                otvetstv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String) parent.getItemAtPosition(position);
                        otve = item;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                dateAndTime = Calendar.getInstance();
                datepicker.setText(DateUtils.formatDateTime(getActivity(),
                        dateAndTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
                datepicker.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(getActivity(), d,
                                dateAndTime.get(Calendar.YEAR),
                                dateAndTime.get(Calendar.MONTH),
                                dateAndTime.get(Calendar.DAY_OF_MONTH))
                                .show();
                    }
                });
                builder.setTitle("Добавить дело")
                        .setView(view)
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // код для передачи данных
                                date = DateUtils.formatDateTime(getActivity(),
                                        dateAndTime.getTimeInMillis(),
                                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
                                ((Add_Client) getActivity()).return_dela(date, stat, otve);

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
            case 3:
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
                                ((Add_Client) getActivity()).return_contact(type, String.valueOf(edittext.getText()));
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
            case 4:
                view = inflater.inflate(R.layout.dialog_add_contactfaces, null);
                edit = view.findViewById(R.id.faces_edittext);
                builder.setTitle("Добавить контактное лицо")
                        .setView(view)
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // код для передачи данных
                                ((Add_Client) getActivity()).return_contact_faces(String.valueOf(edit.getText()));
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
            case 5:
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
                                ((Add_Provider) getActivity()).return_contact(type, String.valueOf(edittext.getText()));
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
            case 6:
                view = inflater.inflate(R.layout.dialog_add_contactfaces, null);
                edit = view.findViewById(R.id.faces_edittext);
                builder.setTitle("Добавить контактное лицо")
                        .setView(view)
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // код для передачи данных
                                ((Add_Provider) getActivity()).return_contact_faces(String.valueOf(edit.getText()));
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
            case 7:
                view = inflater.inflate(R.layout.dialog_add_sity, null);
                search_ed = view.findViewById(R.id.search_ed);
                search_lv = view.findViewById(R.id.search_lv);
                search_tv = view.findViewById(R.id.search_tv);
                names = new ArrayList<>();
                names_buf = new ArrayList<>();

                App.getApi().getSity().enqueue(new Callback<List<Sity>>() {
                    @Override
                    public void onResponse(Call<List<Sity>> call, Response<List<Sity>> response) {

                        for (Sity buffer : response.body()) {
                            names.add(buffer.getName());
                            names_buf.add(buffer.getName());
                        }
                        //dialog.cancel();
                        search_lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, names));
                    }

                    @Override
                    public void onFailure(Call<List<Sity>> call, Throwable t) {
                        Toast.makeText(context, "An error occurred during networking", Toast.LENGTH_SHORT).show();
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
                        ((Add_Provider)getActivity()).return_sity(position+1,names.get(position));
                        getDialog().dismiss();
                    }
                });

                builder.setTitle("Выбор города")
                        .setView(view);
                break;
        }
        return builder.create();
    }

    private void setInitialDateTime() {
        datepicker.setText(DateUtils.formatDateTime(getActivity(),
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
}
