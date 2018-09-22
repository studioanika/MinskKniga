package by.minskkniga.minskkniga.activity.Zakazy;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Add_Contacts;
import by.minskkniga.minskkniga.adapter.Zakazy.Zakazy_2;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import by.minskkniga.minskkniga.api.Class.clients.ClientInfo;
import by.minskkniga.minskkniga.api.Class.clients.Contact;
import by.minskkniga.minskkniga.api.Class.zakazy.ItemListZakMoney;
import by.minskkniga.minskkniga.api.Class.zakazy.MoneyZakResponse;
import by.minskkniga.minskkniga.api.Class.zakazy.ZakMoneyInfo;
import by.minskkniga.minskkniga.dialog.Add_Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Zakazy_Client extends AppCompatActivity {

    int id;
    int id_client = 0;
    static String name;
    ImageButton back;
    TextView caption;
    RecyclerView recyclerView;

    TextView zak_money_info_tovary, zak_money_info_vozvrat, zak_money_info_oplata,
            zak_money_info_podarki, zak_money_info_itogo, zak_money_info_skidka;

    FloatingActionButton fab;
    DialogFragment dlg_zakaz;

    ArrayList<Zakazy> zakazy;
    ArrayList<Zakazy> zakazy_buf;

    ExpandableListView expListView;
    TabHost tabHost;
    TextView notfound_1;
    TextView notfound_2;

    TextView tv_value_d, tv_type_d;
    ImageView more;
    DrawerLayout drawer;

    TextView nav_contacts, nav_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakazy_client);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        notfound_1 = findViewById(R.id.notfound_1);
        notfound_2 = findViewById(R.id.notfound_2);

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        id_client = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);
        drawer = findViewById(R.id.drawer);
        tv_type_d = findViewById(R.id.tv_type_d);
        tv_value_d = findViewById(R.id.tv_value_d);
        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(GravityCompat.END)) drawer.closeDrawer(GravityCompat.END);
                else drawer.openDrawer(GravityCompat.END);
            }
        });

        expListView = findViewById(R.id.expandeblelistview);
        zak_money_info_tovary = (TextView) findViewById(R.id.zak_money_info_tovary);
        zak_money_info_vozvrat = (TextView) findViewById(R.id.zak_money_info_vozvrat);
        zak_money_info_oplata = (TextView) findViewById(R.id.zak_money_info_oplata);
        zak_money_info_podarki = (TextView) findViewById(R.id.zak_money_info_podarki);
        zak_money_info_itogo = (TextView) findViewById(R.id.zak_money_info_itogo);
        zak_money_info_skidka = (TextView) findViewById(R.id.zak_money_info_skidka);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
                Intent intent = new Intent(Zakazy_Client.this, Zakaz_new.class);
                intent.putExtra("name", name);
                intent.putExtra("id_c", String.valueOf(id_client));
                intent.putExtra("id_z", zakazy.get(groupPosition).getId());
                if(zakazy.get(groupPosition).getObrazec().contains("1"))
                    intent.putExtra("obrazec", "1");
                startActivity(intent);
                return true;
            }
        });

        zakazy = new ArrayList<>();
        zakazy_buf = new ArrayList<>();

        expListView.setAdapter(new Zakazy_2(this, zakazy));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tabHost.getCurrentTab() == 1){
                    dlg_zakaz = new Add_Dialog(Zakazy_Client.this, "zakaz_type", String.valueOf(caption.getText()), String.valueOf(id),"");
                    dlg_zakaz.show(getFragmentManager(), "");
                }else {
                    Intent intent = new Intent(Zakazy_Client.this, PrihodOrder.class);
                    intent.putExtra("id", String.valueOf(id));
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });

        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("деньги");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("товары");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(1);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabHost.getCurrentTab()==0)
                {
                    fab.setAlpha((float) 0.3);
                    reload_1();
                }
                if (tabHost.getCurrentTab()==1)
                {
                    fab.setAlpha((float) 1.0);
                    reload_2();
                }
            }
        });

        nav_contacts = findViewById(R.id.nav_contacts);
        nav_show = findViewById(R.id.nav_show_otgr);

        nav_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                showDialogContacts(String.valueOf(id_client));
            }
        });

        nav_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                Intent intent = new Intent(Zakazy_Client.this, ShowOtgruzennoe.class);
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    private void showDialogContacts(String id_client) {
        final Dialog view = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        view.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.setContentView(R.layout.dialog_contacts);


        final ProgressBar progressBar = view.findViewById(R.id.progress);
        final ListView lv = view.findViewById(R.id.lv);
        final TextView name = view.findViewById(R.id.tv_name);
        final TextView close = view.findViewById(R.id.tv_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                view.dismiss();
            }
        });

        final ArrayList<String> contact_type = new ArrayList<>();
        final ArrayList<String> contact_text = new ArrayList<>();
        final ArrayList<String> contacts_id = new ArrayList<>();

        App.getApi().getClientId(id_client).enqueue(new Callback<List<ClientInfo>>()
        {
            @Override
            public void onResponse(Call<List<ClientInfo>> call, Response<List<ClientInfo>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    name.setText(response.body().get(0).getName());
                    ClientInfo clientInfo = response.body().get(0);

                    if(clientInfo.getContacts() != null && clientInfo.getContacts().size() != 0){
                        contact_text.clear();
                        contact_type.clear();
                        for (Contact ct: clientInfo.getContacts()
                                ) {
                            contacts_id.add(ct.getId());
                            contact_type.add(ct.getType());
                            contact_text.add(ct.getText());
                            lv.setAdapter(new Add_Contacts(Zakazy_Client.this, contact_type, contact_text));
//                            setListViewHeightBasedOnChildren(list_contact);

                        }
                    }else {
                        Toast.makeText(Zakazy_Client.this, "Контакты не найдены...", Toast.LENGTH_SHORT).show();
                        view.dismiss();
                    }

                } else {
                    Toast.makeText(Zakazy_Client.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ClientInfo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Zakazy_Client.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(view.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.show();
        view.getWindow().setAttributes(lp);


    }

    public void expanded(int id){
        if (expListView.isGroupExpanded(id)){
            expListView.collapseGroup(id);
        }else{
            expListView.expandGroup(id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tabHost.getCurrentTab()==0)
            reload_1();
        if (tabHost.getCurrentTab()==1)
            reload_2();
    }

    public void reload_1(){

        App.getApi().getZakDengi(String.valueOf(id)).enqueue(new Callback<List<MoneyZakResponse>>() {
            @Override
            public void onResponse(Call<List<MoneyZakResponse>> call, Response<List<MoneyZakResponse>> response) {

                if(response.body() != null){

                    if(response.body().get(0).getInfo() != null){

                        ZakMoneyInfo info = response.body().get(0).getInfo();

                        try{
                            zak_money_info_skidka.setText(String.valueOf(info.getSkidka())+" ("+String.valueOf(info.getSkidka_proc())+"%)");
                            zak_money_info_itogo.setText(String.valueOf(info.getDoljny()));
                            zak_money_info_oplata.setText(String.valueOf(info.getOplaty()));
                            zak_money_info_podarki.setText(String.valueOf(info.getPodarki()));
                            zak_money_info_vozvrat.setText(String.valueOf(info.getVozvrat()));
                            zak_money_info_tovary.setText(String.valueOf(info.getTovar()));

                            if(info.getItog() >= 0) {
                                tv_type_d.setText("Нам должны:");
                                tv_value_d.setText(String.valueOf(info.getItog()));
                            }else {
                                tv_type_d.setText("Мы должны:");
                                tv_value_d.setText(String.valueOf(info.getItog()));
                            }

                        }catch (Exception e){

                        }

                    }

                    if(response.body().get(0).getList() != null){

                        setRecycler(response.body().get(0).getList());

                    }
                }

            }

            @Override
            public void onFailure(Call<List<MoneyZakResponse>> call, Throwable t) {

            }
        });

    }

    private void setRecycler(List<ItemListZakMoney> list) {


        recyclerView = (RecyclerView) findViewById(R.id.fragment_monry_recycler);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        MoneyAdapterZak adapter = new MoneyAdapterZak(list, recyclerView, this) {
        };

        // set the adapter object to the Recyclerview
        recyclerView.setAdapter(adapter);

    }

    public void reload_2(){

        App.getApi().getZakazy_client(id).enqueue(new Callback<List<Zakazy>>() {
            @Override
            public void onResponse(Call<List<Zakazy>> call, Response<List<Zakazy>> response) {
                zakazy.clear();
                zakazy_buf.clear();
                zakazy.addAll(response.body());
                zakazy_buf.addAll(response.body());



                expListView.setAdapter(new Zakazy_2(Zakazy_Client.this, zakazy));

                if (!zakazy.isEmpty()) {
                    notfound_2.setVisibility(View.GONE);
                } else {
                    notfound_2.setVisibility(View.VISIBLE);
                }
                notfound_2.setText("Ничего не найдено");
            }

            @Override
            public void onFailure(Call<List<Zakazy>> call, Throwable t) {
                Toast.makeText(Zakazy_Client.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startNew(int groupPosition){
        Intent intent = new Intent(Zakazy_Client.this, Zakaz_new.class);
        intent.putExtra("name", name);
        intent.putExtra("id_c", String.valueOf(id_client));
        intent.putExtra("id_z", zakazy.get(groupPosition).getId());
        if(zakazy.get(groupPosition).getObrazec().contains("1"))
            intent.putExtra("obrazec", "1");
        startActivity(intent);
    }

    public void startNewFromLV(String id_zak){
        Intent intent = new Intent(Zakazy_Client.this, Zakaz_new.class);
        intent.putExtra("name", name);
        intent.putExtra("id_c", String.valueOf(id_client));
        intent.putExtra("id_z", id_zak);
        startActivity(intent);
    }

}
