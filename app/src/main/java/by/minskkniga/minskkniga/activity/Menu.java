package by.minskkniga.minskkniga.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;

public class Menu extends AppCompatActivity {

    String[] list1 = {"Заказы", "Поставщики", "Касса", "Номенклатура", "Органайзер", "Инвентаризация"};
    String[] list2 = {"Клиенты", "Поставщики", "Курьеры"};

    ListView lv1, lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lv1 = findViewById(R.id.listView1);
        lv2 = findViewById(R.id.listView2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list2);
        // присваиваем адаптер списку
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(Menu.this, Zakazy.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        Intent intent2 = new Intent(Menu.this, Nomenklatura.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(Menu.this, Spravoch_Clients.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Menu.this, Spravoch_Providers.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(Menu.this, Spravoch_Couriers.class);
                        startActivity(intent3);
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
