package by.minskkniga.minskkniga.activity.Zakazy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Barcode;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sborka_tab extends AppCompatActivity {

    ImageButton back;
    TextView caption;
    ArrayList<Zakaz_product> products;
    int id;

    ImageView image;
    TextView name;
    TextView artikul;
    TextView clas;
    TextView izdatel;
    TextView obrazec;
    TextView autor;
    TextView sokr_name;

    EditText zakazano;
    EditText otgruzeno;
    Button check_otgruzeno;
    Button prev;
    Button next;
    Button barcode;
    FloatingActionButton fab;

    LinearLayout ll;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Zakaz_product.class.getCanonicalName(), products);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void initialize() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        caption = findViewById(R.id.caption);
        products = new ArrayList<>();

        ll = findViewById(R.id.ll);

        zakazano = findViewById(R.id.zakazano);
        otgruzeno = findViewById(R.id.otgruzeno);
        check_otgruzeno = findViewById(R.id.check_otgruzeno);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        zakazano.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (zakazano.getText().equals(""))
                    products.get(id).col_zakaz = "0";
                else
                    products.get(id).col_zakaz = String.valueOf(zakazano.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otgruzeno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otgruzeno.getText().equals(""))
                    products.get(id).otgruzeno = "0";
                else
                    products.get(id).otgruzeno = String.valueOf(otgruzeno.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        check_otgruzeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otgruzeno.setText(zakazano.getText());
                products.get(id).otgruzeno = products.get(id).col_zakaz;
            }
        });

        name = findViewById(R.id.name);
        artikul = findViewById(R.id.artikul);
        clas = findViewById(R.id.clas);
        izdatel = findViewById(R.id.izdatel);
        obrazec = findViewById(R.id.obrazec);
        autor = findViewById(R.id.autor);
        sokr_name = findViewById(R.id.sokr_name);
        image = findViewById(R.id.image);


        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        barcode = findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(Sborka_tab.this)
                        .setOrientationLocked(true)
                        .setCaptureActivity(Barcode.class)
                        .initiateScan();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id--;
                reload(id);

                if (id == 0) prev.setEnabled(false);
                else prev.setEnabled(true);
                if (id == products.size() - 1) next.setEnabled(false);
                else next.setEnabled(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id++;
                reload(id);

                if (id == 0) prev.setEnabled(false);
                else prev.setEnabled(true);
                if (id == products.size() - 1) next.setEnabled(false);
                else next.setEnabled(true);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sborka_tab);
        initialize();

        id = getIntent().getIntExtra("id", 0);

        caption.setText(getIntent().getStringExtra("name"));
        products.addAll(getIntent().<Zakaz_product>getParcelableArrayListExtra(Zakaz_product.class.getCanonicalName()));

        if (id == 0) prev.setEnabled(false);
        else prev.setEnabled(true);
        if (id == products.size() - 1) next.setEnabled(false);
        else next.setEnabled(true);

        reload(id);
    }

    public void reload(int id) {
        zakazano.setText(products.get(id).col_zakaz);
        otgruzeno.setText(products.get(id).otgruzeno);
        if (products.get(id).barcode_status.equals("1")) {
            ll.setBackgroundColor(Color.LTGRAY);
        } else {
            ll.setBackgroundColor(Color.WHITE);
        }
        App.getApi().getProduct(products.get(id).id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                name.setText(response.body().getName());
                artikul.setText(response.body().getArtikul());
                clas.setText(response.body().getClas());
                izdatel.setText(response.body().getIzdatel());
                obrazec.setText(response.body().getObrazec().equals("1") ? "Есть" : "Нет");
                autor.setText(response.body().getAutor());
                sokr_name.setText(response.body().getSokrName());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Glide.with(Sborka_tab.this).load("http://cc96297.tmweb.ru/admin/img/nomen/" + response.body().getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(image);
                } else {
                    Glide.with(Sborka_tab.this).load("http://cc96297.tmweb.ru/admin/img/nomen/" + response.body().getImage()).into(image);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String res;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            try {
                res = result.getContents().isEmpty() ? "" : result.getContents();
            }catch (Exception e){
                res = "null";
            }
            if (products.get(id).barcode.contains(res)) {
                products.get(id).barcode_status = "1";
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                ll.setBackgroundColor(Color.LTGRAY);
            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                ll.setBackgroundColor(Color.WHITE);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
