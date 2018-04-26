package by.minskkniga.minskkniga.activity.Zakazy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
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

    DialogFragment dlg_zakaz;
    DialogFragment dlg_client;

    LinearLayout linear_select_client;
    LinearLayout linear_zametki;
    LinearLayout linear_ok;

    Button select_client;
    Button ok;

    boolean is_select_client = false;

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
        if (is_select_client && !dlg_client.isVisible()){
            dlg_client.dismiss();
            dlg_client.show(getFragmentManager(), "");
        }
    }

    public void return_zakaz_type(int type){

    }
}
