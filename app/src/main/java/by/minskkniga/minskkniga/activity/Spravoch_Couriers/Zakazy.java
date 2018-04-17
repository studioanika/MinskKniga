package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Spravoch_Couriers.Zakazy_1;
import by.minskkniga.minskkniga.adapter.Spravoch_Couriers.Zakazy_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_clients;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_knigi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy extends AppCompatActivity {

    int id;
    String name;
    TextView caption;
    ImageButton back;
    ImageButton filter;
    TabHost tabHost;
    RelativeLayout filter_layout_1;
    RelativeLayout filter_layout_2;
    TextView notfound_1;
    TextView notfound_2;

    ExpandableListView lv1;
    ListView lv2;
    TextView checkbox;
    int check = 0;

    ArrayList<Zakazy_courier_knigi> zakazy_1;
    ArrayList<Zakazy_courier_knigi> zakazy_1_buf;

    ArrayList<Zakazy_courier_clients> zakazy_2;
    ArrayList<Zakazy_courier_clients> zakazy_2_buf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_zakazy);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        filter_layout_1 = findViewById(R.id.filter_layout_1);
        filter_layout_1.setVisibility(View.GONE);
        filter_layout_2 = findViewById(R.id.filter_layout_2);
        filter_layout_2.setVisibility(View.GONE);
        filter = findViewById(R.id.filter_button);

        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tabHost.getCurrentTab()){
                    case 0:
                        if (filter_layout_1.getVisibility()==View.GONE){
                            filter_layout_1.setVisibility(View.VISIBLE);
                        }else{
                            filter_layout_1.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        if (filter_layout_2.getVisibility()==View.GONE){
                            filter_layout_2.setVisibility(View.VISIBLE);
                        }else{
                            filter_layout_2.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });


        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);

        zakazy_1 = new ArrayList<>();
        zakazy_1_buf = new ArrayList<>();

        zakazy_2 = new ArrayList<>();
        zakazy_2_buf = new ArrayList<>();

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


        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("по книгам");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("по клиентам");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(1);

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
        App.getApi().getCourier_knigi(String.valueOf(id)).enqueue(new Callback<List<Zakazy_courier_knigi>>() {
            @Override
            public void onResponse(Call<List<Zakazy_courier_knigi>> call, Response<List<Zakazy_courier_knigi>> response) {
                zakazy_1.clear();
                zakazy_1_buf.clear();
                zakazy_1.addAll(response.body());
                zakazy_1_buf.addAll(response.body());
                lv1.setAdapter(new Zakazy_1(Zakazy.this, zakazy_1));
                notfound_1.setVisibility(View.GONE);
                notfound_1.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<Zakazy_courier_knigi>> call, Throwable t) {

            }
        });
    }

    public void reload_2(){
        App.getApi().getCourier_zakazy(String.valueOf(id)).enqueue(new Callback<List<Zakazy_courier_clients>>() {
            @Override
            public void onResponse(Call<List<Zakazy_courier_clients>> call, Response<List<Zakazy_courier_clients>> response) {
                zakazy_2.clear();
                zakazy_2_buf.clear();
                zakazy_2.addAll(response.body());
                zakazy_2_buf.addAll(response.body());
                lv2.setAdapter(new Zakazy_2(Zakazy.this, zakazy_2));
                notfound_2.setVisibility(View.GONE);
                notfound_2.setText("Ничего не найдено");
                //search();
            }

            @Override
            public void onFailure(Call<List<Zakazy_courier_clients>> call, Throwable t) {
                Toast.makeText(Zakazy.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
