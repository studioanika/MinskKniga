package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.fragments.FragmentMoney;
import by.minskkniga.minskkniga.activity.providers.fragments.FragmentProviderZayavki;
import by.minskkniga.minskkniga.adapter.Add_Contacts;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.clients.Contact;
import by.minskkniga.minskkniga.api.Class.provider_sp.ProviderInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderZayavkiListActivity extends AppCompatActivity {

    TabLayout tabLayout;
    String id, name, type;
    boolean auto;
    FloatingActionButton fab;

    PagerAdapter adapter;
    ViewPager viewPager;

    DrawerLayout drawer;
    TextView nav_return, nav_contacts;

    String ret;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        auto = intent.getBooleanExtra("new", false);
        if(auto) type = "auto";
        else type = "";

        getSupportActionBar().setTitle(name);

        drawer = findViewById(R.id.drawer);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ЗАЯВКИ"));
        tabLayout.addTab(tabLayout.newTab().setText("ДЕНЬГИ"));

        nav_contacts = findViewById(R.id.nav_contacts);
        nav_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                showDialogContacts(id);
            }
        });
        nav_return = findViewById(R.id.nav_return);


        nav_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                Intent intent1 = new Intent(ProviderZayavkiListActivity.this, NewProviderZayavka.class);
                intent1.putExtra("return", "w");
                intent1.putExtra("id", id);
                intent1.putExtra("name", name);
                startActivity(intent1);
            }
        });

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();

        initViewPager();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 0) {
                   Intent intent1 =  new Intent(ProviderZayavkiListActivity.this, NewProviderZayavka.class);
                   intent1.putExtra("id", id);
                   intent1.putExtra("name", name);
                   startActivity(intent1);
                }
            }
        });
        //fab.setVisibility(View.GONE);

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

        App.getApi().getProviderId(id_client).enqueue(new Callback<List<ProviderInfo>>() {
            @Override
            public void onResponse(Call<List<ProviderInfo>> call, Response<List<ProviderInfo>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.body() != null){

                    ProviderInfo providerInfo = response.body().get(0);
                    name.setText(providerInfo.getName());
                    if(providerInfo.getContacts() != null && providerInfo.getContacts().size() != 0){
                        contact_text.clear();
                        contact_type.clear();
                        for (Contact ct: providerInfo.getContacts()
                                ) {
                            contacts_id.add(ct.getId());
                            contact_type.add(ct.getType());
                            contact_text.add(ct.getText());
                            lv.setAdapter(new Add_Contacts(ProviderZayavkiListActivity.this, contact_type, contact_text));
                            //setListViewHeightBasedOnChildren(list_contact);

                        }
                    }else {
                        Toast.makeText(ProviderZayavkiListActivity.this, "E поставщика нету контактов...", Toast.LENGTH_SHORT).show();
                        view.dismiss();
                    }

                }
                else Toast.makeText(ProviderZayavkiListActivity.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ProviderInfo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProviderZayavkiListActivity.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(view.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.show();
        view.getWindow().setAttributes(lp);


    }

    public void initViewPager(){



        try {
            viewPager = (ViewPager) findViewById(R.id.pager);
            adapter = new PagerAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    if(viewPager.getCurrentItem() == 0) fab.setVisibility(View.VISIBLE);
                    else fab.setVisibility(View.GONE);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        } return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_provider, menu);
        return true;
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FragmentProviderZayavki tab2 = new FragmentProviderZayavki(id, type);
                    return tab2;
                case 1:
                    FragmentMoney tab1 = new FragmentMoney(id);
                    return tab1;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.more) {
            more();
        }

        //noinspection SimplifiableIfStatement
        if( id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void more() {

        if(drawer.isDrawerOpen(GravityCompat.END)) drawer.closeDrawer(GravityCompat.END);
        else drawer.openDrawer(GravityCompat.END);

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
            return;
        }
        super.onBackPressed();
    }

    public void startNextAct(String id_){

        Intent intent = new Intent(ProviderZayavkiListActivity.this, DescriptionZayavkaActivity.class);
        intent.putExtra("id", id_);
        intent.putExtra("id_p", id);
        startActivity(intent);
    }

    public void startNextActReturn(String id){

        Intent intent = new Intent(ProviderZayavkiListActivity.this, DescriptionZayavkaActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("return", "w");
        startActivity(intent);
    }

}
