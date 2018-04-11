package by.minskkniga.minskkniga.adapter.Zakazy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;

public class Zakaz_info extends AppCompatActivity {

    String name;
    String id;
    TextView caption;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz_info);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        caption = findViewById(R.id.caption);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        caption.setText(name);


    }
}
