package by.minskkniga.minskkniga.activity.Zakazy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;

public class Sborka extends AppCompatActivity {

    ImageButton back;
    TextView caption;
    TextView sobrano;

    FloatingActionButton fab;
    ArrayList<Zakaz_product> products;
    ListView lv;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Zakaz_product.class.getCanonicalName(), products);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void initialize() {
        products = new ArrayList<>();
        lv = findViewById(R.id.lv);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        caption = findViewById(R.id.caption);

        sobrano = findViewById(R.id.sobrano);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    int i = 0;
    double ves;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sborka);
        initialize();

        caption.setText(getIntent().getStringExtra("name"));
        final ArrayList<Zakaz_product> product = getIntent().getParcelableArrayListExtra(Zakaz_product.class.getCanonicalName());
        products.addAll(product);
        lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_sborka(this, products));


        for (Zakaz_product buffer : products) {
            if (Integer.parseInt(buffer.otgruzeno) > 0) {
                i++;
                ves += Double.parseDouble(buffer.ves);
            }
        }

        sobrano.setText(String.format("%d поз./ %s кг.", i, ves));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Sborka.this, Sborka_tab.class);
                intent.putExtra("id", i);
                intent.putExtra("name", caption.getText());
                intent.putExtra(Zakaz_product.class.getCanonicalName(), products);
                startActivityForResult(intent, 1);
            }
        });

    }


    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ArrayList<Zakaz_product> product = data.getParcelableArrayListExtra(Zakaz_product.class.getCanonicalName());
                    products.clear();
                    products.addAll(product);
                    lv.setAdapter(new by.minskkniga.minskkniga.adapter.Zakazy.Zakaz_sborka(this, products));

                    for (Zakaz_product buffer : products) {
                        if (Integer.parseInt(buffer.otgruzeno) > 0) {
                            i++;
                            ves += Double.parseDouble(buffer.ves);
                        }
                    }

                    sobrano.setText(String.format("%d поз./ %s кг.", i, ves));
            }
        }
    }
}
