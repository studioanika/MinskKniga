package by.minskkniga.minskkniga.activity.Zakazy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.dialog.Add_Dialog;

public class Zakaz_new extends AppCompatActivity {

    ImageButton back;
    ImageButton menu;
    TextView caption;

    DialogFragment dlg_zakaz;
    DialogFragment dlg_client;

    LinearLayout linear_select_client;
    LinearLayout linear_zametki;
    LinearLayout linear_ok;

    Button select_client;
    Button ok;
    SharedPreferences sp;

    TextView autor;

    boolean is_select_client = false;

    String id_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_new);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menu = findViewById(R.id.menu);
        caption = findViewById(R.id.caption);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        autor = findViewById(R.id.autor);

        autor.setText(sp.getString("login", ""));

        linear_select_client = findViewById(R.id.linear_select_client);
        linear_zametki = findViewById(R.id.linear_zametki);
        linear_ok = findViewById(R.id.linear_ok);

        linear_zametki.setVisibility(View.GONE);
        linear_ok.setVisibility(View.GONE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Zakaz_new.this, Add.class);
                //startActivity(intent);
            }
        });

        dlg_zakaz = new Add_Dialog(this, "zakaz_type");
        dlg_zakaz.setCancelable(false);
        dlg_zakaz.show(getFragmentManager(), "");

        dlg_client = new Add_Dialog(this, "zakaz_client");

        select_client = findViewById(R.id.select_client);
        select_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_select_client = true;
                dlg_client.setCancelable(false);
                dlg_client.show(getFragmentManager(), "");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void return_zakaz_type(int type){

    }

    public void return_client(String id, String name){
        id_client = id;
        linear_select_client.setVisibility(View.GONE);
        linear_zametki.setVisibility(View.VISIBLE);
        linear_ok.setVisibility(View.VISIBLE);
        caption.setText(name);
    }
}
