package by.minskkniga.minskkniga.activity.providers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.fragments.FragmentMoney;
import by.minskkniga.minskkniga.activity.providers.fragments.FragmentProviderZayavki;

public class ProviderZayavkiListActivity extends AppCompatActivity {

    TabLayout tabLayout;
    String id, name, type;
    boolean auto;
    FloatingActionButton fab;

    PagerAdapter adapter;
    ViewPager viewPager;

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

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ЗАЯВКИ"));
        tabLayout.addTab(tabLayout.newTab().setText("ДЕНЬГИ"));

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

        //noinspection SimplifiableIfStatement
        if( id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startNextAct(String id){

        Intent intent = new Intent(ProviderZayavkiListActivity.this, DescriptionZayavkaActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
