package by.minskkniga.minskkniga.activity.inventarizacia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.inventarizacia.adapter.AdapterInventarizacia;
import by.minskkniga.minskkniga.api.Class.inventarizacia.InventarizaciaObject;

public class InventarizaciaInfoActivity extends AppCompatActivity {

    TextView tv_name, tv_class, tv_izdatel, tv_artikul, tv_ostatok;
    ImageView img;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventarizacia_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView(){

        tv_name = (TextView) findViewById(R.id.i_info_name);
        tv_class = (TextView) findViewById(R.id.i_info_class);
        tv_artikul = (TextView) findViewById(R.id.i_info_articul);
        tv_ostatok = (TextView) findViewById(R.id.i_info_ostatok);
        tv_izdatel = (TextView) findViewById(R.id.i_info_izdatel);
        img = (ImageView) findViewById(R.id.i_info_img);
        lv = (ListView) findViewById(R.id.i_info_lv);

        loadData();
    }

    private void setListViewBase(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();

        if(listAdapter == null) return;

        int totalHeight = 0;

        for ( int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0 ,0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() -1));
        listView.setLayoutParams(params);
    }

    public void clickItemLv(){

    }

    private void loadData(){

        List<InventarizaciaObject> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            InventarizaciaObject object = new InventarizaciaObject();

            object.setColvo(String.valueOf(i+5));
            object.setContragent("Контрагент "+i);
            object.setDate("20.0"+i);
            object.setOsnavanie("Продажа");
            list.add(object);
        }

        lv.setAdapter(new AdapterInventarizacia(InventarizaciaInfoActivity.this, list));
        setListViewBase(lv);

    }

}
