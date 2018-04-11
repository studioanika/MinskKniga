package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy extends AppCompatActivity {

    ImageButton back;

    int id;
    String name;
    TextView caption;

    ListView lv;
    ListView lv2;

    TextView checkbox;

    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy_courier);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lv2 = findViewById(R.id.lv2);

        checkbox = findViewById(R.id.checkbox);
        Drawable img = getResources().getDrawable(R.drawable.ic_check_0);
        img.setBounds(0, 0, 32, 32);
        checkbox.setCompoundDrawables(null, null, img, null);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (check){
                    case 0:
                        Drawable img1 = getResources().getDrawable(R.drawable.ic_check_1);
                        img1.setBounds(0, 0, 32, 32);
                        checkbox.setCompoundDrawables(null, null, img1, null);
                        check=1;
                        break;
                    case 1:
                        Drawable img2 = getResources().getDrawable(R.drawable.ic_check_2);
                        img2.setBounds(0, 0, 32, 32);
                        checkbox.setCompoundDrawables(null, null, img2, null);
                        check=2;
                        break;
                    case 2:
                        Drawable img0 = getResources().getDrawable(R.drawable.ic_check_0);
                        img0.setBounds(0, 0, 32, 32);
                        checkbox.setCompoundDrawables(null, null, img0, null);
                        check=0;
                        break;
                }
            }
        });

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);
        Toast.makeText(this, id+" ", Toast.LENGTH_SHORT).show();


        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("по книгам");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("по клиентам");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                reload_1();
                reload_2();
            }
        });

        reload_1();
        reload_2();
    }


    @Override
    protected void onResume() {
        super.onResume();
        reload_1();
        reload_2();

    }

    public void reload_1(){
        //reload
    }

    public void reload_2(){
        App.getApi().getZakazy(id).enqueue(new Callback<List<by.minskkniga.minskkniga.api.Class.Zakazy>>() {
            @Override
            public void onResponse(Call<List<by.minskkniga.minskkniga.api.Class.Zakazy>> call, Response<List<by.minskkniga.minskkniga.api.Class.Zakazy>> response) {
//                zakazy.clear();
//                zakazy_buf.clear();
//                zakazy.addAll(response.body());
//                zakazy_buf.addAll(response.body());
//                lv2.setAdapter(new Zakazy_Courier_2(Zakazy.this, zakazy));
                //search();
            }

            @Override
            public void onFailure(Call<List<by.minskkniga.minskkniga.api.Class.Zakazy>> call, Throwable t) {
                Toast.makeText(Zakazy.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
