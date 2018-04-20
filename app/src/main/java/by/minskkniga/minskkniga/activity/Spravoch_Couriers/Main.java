package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Couriers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ImageButton back;
    ArrayList<Couriers> couriers;
    ArrayList<Couriers> couriers_buf;
    ListView lv;
    TextView notfound;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_couriers);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        notfound = findViewById(R.id.notfound);

        couriers = new ArrayList<>();
        couriers_buf = new ArrayList<>();
        lv = findViewById(R.id.lv);

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Main.this, Zakazy.class);
                intent.putExtra("id", Integer.parseInt(couriers.get(position).getId()));
                intent.putExtra("name", couriers.get(position).getName());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        reload();
        super.onResume();
    }

    public void filter() {
        couriers.clear();
        for (Couriers buffer : couriers_buf) {
            if (buffer.getName().toLowerCase().contains(search.getText().toString().toLowerCase())) {
                couriers.add(buffer);
            }
        }
        if (!couriers.isEmpty()) {
            notfound.setVisibility(View.GONE);
        } else {
            notfound.setVisibility(View.VISIBLE);
        }
        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Spravoch_Couriers.Main(Main.this, couriers));
    }

    public void reload(){
        App.getApi().getCouriers().enqueue(new Callback<List<Couriers>>() {
            @Override
            public void onResponse(Call<List<Couriers>> call, Response<List<Couriers>> response) {
                couriers.clear();
                couriers_buf.clear();
                couriers.addAll(response.body());
                couriers_buf.addAll(response.body());
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Spravoch_Couriers.Main(Main.this, couriers));

                if (!couriers.isEmpty()) {
                    notfound.setVisibility(View.GONE);
                } else {
                    notfound.setVisibility(View.VISIBLE);
                }
                notfound.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<Couriers>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

