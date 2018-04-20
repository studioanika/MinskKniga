package by.minskkniga.minskkniga.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Spravoch_Couriers.Main;

public class Menu extends AppCompatActivity {

    String[] list1 = {"Заказы", "Поставщики", "Касса", "Номенклатура", "Органайзер", "Инвентаризация", "Справочники", "Клиенты", "Поставщики", "Курьеры"};

    ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lv1 = findViewById(R.id.lv1);

        lv1.setAdapter(new by.minskkniga.minskkniga.adapter.Main(this));



        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
            }
        });
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
                        Intent intent9 = new Intent(Menu.this, Main.class);
                        startActivity(intent9);
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

}
