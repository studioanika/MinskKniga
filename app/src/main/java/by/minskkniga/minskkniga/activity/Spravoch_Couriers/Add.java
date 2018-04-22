package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
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

    ScrollView sv;
    ImageButton back;
    Button add_contact;
    Button add_courier;
    Button add_back;
    DialogFragment dlg;
    ListView list_contact;
    EditText name_ed;
    EditText login_ed;
    EditText pass_ed;
    EditText desc_ed;

    ArrayList<String> contact_type;
    ArrayList<String> contact_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courier);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sv = findViewById(R.id.scrollview);
        dlg = new Add_Dialog(this, 8);
        add_contact = findViewById(R.id.contacts_add);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.show(getFragmentManager(), "");
            }
        });

        add_back = findViewById(R.id.add_back);
        add_courier = findViewById(R.id.add_courier);

        name_ed = findViewById(R.id.name_ed);
        login_ed = findViewById(R.id.login_ed);
        pass_ed = findViewById(R.id.pass_ed);
        desc_ed = findViewById(R.id.desc_ed);

        list_contact = findViewById(R.id.contacts_listview);


        contact_type = new ArrayList<>();
        contact_text = new ArrayList<>();

    }

    public void return_contact(String type, String text){
        if (!text.isEmpty()) {
            contact_type.add(type);
            contact_text.add(text);
            list_contact.setAdapter(new Add_Contacts(this, contact_type, contact_text));
            setListViewHeightBasedOnChildren(list_contact);
        }
    }

    public void add_back(View view){
        onBackPressed();
    }

    String contacts = "";

    public void add_courier_button(View view) {
        if (name_ed.getText().toString().isEmpty()) {
            Toast.makeText(this, "Поле 'Имя курьера' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, name_ed.getTop());
            name_ed.requestFocus();
            return;
        }
        if (login_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Логин' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, login_ed.getTop());
            login_ed.requestFocus();
            return;
        }
        if (pass_ed.getText().toString().isEmpty()){
            Toast.makeText(this, "Поле 'Пароль' не заполнено", Toast.LENGTH_SHORT).show();
            sv.smoothScrollTo(0, pass_ed.getTop());
            pass_ed.requestFocus();
            return;
        }

        if (contact_type.size() != 0) {
            contacts = contact_type.get(0) + "/~/" + contact_text.get(0);
            for (int i = 1; i < contact_type.size(); i++) {
                contacts += "/~~/" + contact_type.get(i) + "/~/" + contact_text.get(i);
            }
        }

        if (contacts.isEmpty()) contacts = "null";

        App.getApi().addCourier(name_ed.getText().toString(),
                login_ed.getText().toString(),
                pass_ed.getText().toString(),
                desc_ed.getText().toString(),
                contacts).enqueue(new Callback<ResultBody>() {

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
