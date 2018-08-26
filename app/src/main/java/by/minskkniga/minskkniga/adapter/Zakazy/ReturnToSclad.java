package by.minskkniga.minskkniga.adapter.Zakazy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Spravoch_Couriers.adapter.AdapterForReturn;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.couriers.ItemReturnForObrazci;
import by.minskkniga.minskkniga.api.Class.couriers.ItemReturnForZakaz;
import by.minskkniga.minskkniga.api.Class.couriers.ObjectReturnGeneral;
import by.minskkniga.minskkniga.api.Class.couriers.ObjectReturnObrazci;
import by.minskkniga.minskkniga.api.Class.couriers.ObjectReturnZakaz;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnToSclad extends AppCompatActivity {


    TextView blank, date, author, courier;
    CheckBox check_all;
    ListView lv;
    Button btn_n, btn_s;

    List<ObjectToReturn> list = new ArrayList<>();

    String id, obrazec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_return_to_sclad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        blank = findViewById(R.id.tv_blank_number);
        date = findViewById(R.id.tv_date_text);
        author = findViewById(R.id.tv_autor);
        courier = findViewById(R.id.tv_courier);
        check_all = findViewById(R.id.chech_all);
        lv = findViewById(R.id.lv);
        btn_n = findViewById(R.id.btn_n);
        btn_s = findViewById(R.id.btn_s);

        btn_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        obrazec = intent.getStringExtra("obrazec");

        check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) setAllItems();
                else clearAllItems();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialogEditColKnig(i);
            }
        });

        if(obrazec.contains("0")) loadZakaz();
        else loadObrazec();
    }

    private void save() {

        Map<String, String> map = new HashMap<>();
        map.put("zakaz_id", id);



        List<ObjectReturnGeneral> returnGenerals = new ArrayList<>();

        for (ObjectToReturn obj:list
             ) {
            ObjectReturnGeneral objectReturnGeneral = new ObjectReturnGeneral();
            objectReturnGeneral.setProd_id(obj.getId());
            objectReturnGeneral.setOtgruzeno(String.valueOf(obj.getReturn_kiniga()));
            returnGenerals.add(objectReturnGeneral);
        }

        Gson gson = new Gson();
        String mass = gson.toJson(returnGenerals).toString();

        map.put("mas", mass);

        if(obrazec.contains("0"))  map.put("obrazec", "0");
        else map.put("obrazec", "1");

        App.getApi().returnToSklad(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.body().string().contains("ok"))
                    {
                        Toast.makeText(ReturnToSclad.this, "Книги возвращены", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void clearAllItems() {

        if(list != null){

            for (ObjectToReturn o :list
                    ) {
                o.setReturn_kiniga(0);
            }

        }
        lv.setAdapter(new AdapterForReturn(list, this));

    }

    private void setAllItems() {

        if(list != null){

            for (ObjectToReturn o :list
                 ) {
                o.setReturn_kiniga(o.getU_cur());
            }

        }
        lv.setAdapter(new AdapterForReturn(list, this));

    }

    private void loadObrazec() {

        App.getApi().getInfoReturnObrazci(id, "1").enqueue(new Callback<ObjectReturnObrazci>() {
            @Override
            public void onResponse(Call<ObjectReturnObrazci> call, Response<ObjectReturnObrazci> response) {

                if(response.body() != null){

                    ObjectReturnObrazci  objectReturnZakaz = response.body();

                    blank.setText(objectReturnZakaz.getId());
                    date.setText(objectReturnZakaz.getDate());
                    author.setText(objectReturnZakaz.getAutor());
                    courier.setText(objectReturnZakaz.getCourier());

                    setForListOrazci(objectReturnZakaz.getWhat_zakazal());
                }

            }

            @Override
            public void onFailure(Call<ObjectReturnObrazci> call, Throwable t) {

            }
        });

    }

    private void setForListOrazci(List<ItemReturnForObrazci> what_zakazal) {

        list.clear();
        for (ItemReturnForObrazci forZakaz: what_zakazal
                ) {
            ObjectToReturn object = new ObjectToReturn(forZakaz.getKomplect_id(),
                    forZakaz.getKomplect_name(), Integer.parseInt(forZakaz.getKomplekt_kol()), 0);

            list.add(object);
        }

        lv.setAdapter(new AdapterForReturn(list, this));

    }

    private void loadZakaz() {

        App.getApi().getInfoReturnZakaz(id, "0").enqueue(new Callback<ObjectReturnZakaz>() {
            @Override
            public void onResponse(Call<ObjectReturnZakaz> call, Response<ObjectReturnZakaz> response) {

                if(response.body() != null){

                    ObjectReturnZakaz  objectReturnZakaz = response.body();

                    blank.setText(objectReturnZakaz.getId());
                    date.setText(objectReturnZakaz.getDate());
                    author.setText(objectReturnZakaz.getAutor());
                    courier.setText(objectReturnZakaz.getCourier());

                    setForList(objectReturnZakaz.getWhat_zakazal());
                }

            }

            @Override
            public void onFailure(Call<ObjectReturnZakaz> call, Throwable t) {

            }
        });

    }

    private void setForList(List<ItemReturnForZakaz> what_zakazal) {
        list.clear();
        for (ItemReturnForZakaz forZakaz: what_zakazal
             ) {
            ObjectToReturn object = new ObjectToReturn(forZakaz.getId(),
                    forZakaz.getName(), Integer.parseInt(forZakaz.getU_cur()), 0);

            list.add(object);
        }

        lv.setAdapter(new AdapterForReturn(list, this));
    }

    private void showDialogEditColKnig(final int position){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_edit_col);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.editmoney_cancel);
        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.editmoney_done);
        final EditText col = (EditText) dialogEdit.findViewById(R.id.editmoney_et);



        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int col_i = 0;
                try {
                    col_i = Integer.parseInt(col.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                final int finalCol_i = col_i;
                list.get(position).setReturn_kiniga(finalCol_i);
                lv.setAdapter(new AdapterForReturn(list, ReturnToSclad.this));
                dialogEdit.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

}
