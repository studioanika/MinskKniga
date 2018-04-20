package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {

    ImageButton back;
    ArrayList<by.minskkniga.minskkniga.api.Class.Couriers> couriers;
    ArrayList<by.minskkniga.minskkniga.api.Class.Couriers> couriers_buf;
    ListView lv;
    TextView notfound;

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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Main.this, Zakazy.class);
                intent.putExtra("id", Integer.parseInt(couriers.get(position).getId()));
                intent.putExtra("name", couriers.get(position).getName());
                startActivity(intent);

            }
        });

        reload();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload(){
        App.getApi().getCouriers().enqueue(new Callback<List<by.minskkniga.minskkniga.api.Class.Couriers>>() {
            @Override
            public void onResponse(Call<List<by.minskkniga.minskkniga.api.Class.Couriers>> call, Response<List<by.minskkniga.minskkniga.api.Class.Couriers>> response) {
                couriers.clear();
                couriers_buf.clear();
                couriers.addAll(response.body());
                couriers_buf.addAll(response.body());
                lv.setAdapter(new by.minskkniga.minskkniga.adapter.Spravoch_Couriers.Main(Main.this, couriers));
                notfound.setVisibility(View.GONE);
                notfound.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<by.minskkniga.minskkniga.api.Class.Couriers>> call, Throwable t) {
                Toast.makeText(Main.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

