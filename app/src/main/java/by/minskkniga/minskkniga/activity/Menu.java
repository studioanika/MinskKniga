package by.minskkniga.minskkniga.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Notif_count;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity {

    ListView lv1;
    ImageButton notif;
    TextView notif_count;
    SharedPreferences sp;
    String user_id;
    String rank;
    String name;
    String col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lv1 = findViewById(R.id.lv1);
        lv1.setAdapter(new by.minskkniga.minskkniga.adapter.Menu(this));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
            }
        });

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        rank = sp.getString("rank", "");
        name = sp.getString("name", "");
        user_id = sp.getString("user_id", "");

        notif = findViewById(R.id.notif);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Organizer.Main.class);
                startActivity(intent);
            }
        });

        notif_count = findViewById(R.id.notif_count);
        notif_count.setVisibility(View.GONE);


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Zakazy.Main.class);
                        startActivity(intent0);
                        break;
                    case 3:
                        Intent intent3 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Nomenklatura.Main.class);
                        intent3.putExtra("zakaz", false);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Organizer.Main.class);
                        startActivity(intent4);
                        break;
                    case 7:
                        Intent intent7 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Spravoch_Clients.Main.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Spravoch_Providers.Main.class);
                        startActivity(intent8);
                        break;
                    case 9:
                        if (rank.equals("courier")) {
                            Intent intent9 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Spravoch_Couriers.Zakazy.class);
                            intent9.putExtra("name", name);
                            intent9.putExtra("user_id", user_id);
                            startActivity(intent9);
                        }
                        if (rank.equals("admin")) {
                            Intent intent9 = new Intent(Menu.this, by.minskkniga.minskkniga.activity.Spravoch_Couriers.Main.class);
                            startActivity(intent9);
                        }
                        break;
                }
            }
        });
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finishAffinity();
        } else {
            Toast.makeText(this, "Нажмите еще раз для выхода",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);

        }

    }

    @Override
    public void onResume(){
        super.onResume();
        reload();
    }

    public void reload(){
        App.getApi().getNotif(user_id).enqueue(new Callback<Notif_count>() {
            @Override
            public void onResponse(Call<Notif_count> call, Response<Notif_count> response) {
                col = response.body().getCol();
                notif_count.setText(col);
                if (!col.equals("0")){
                    notif_count.setVisibility(View.VISIBLE);
                }else{
                    notif_count.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Notif_count> call, Throwable t) {

            }
        });
    }

}
