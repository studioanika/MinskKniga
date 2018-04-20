package by.minskkniga.minskkniga.activity.Spravoch_Couriers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import by.minskkniga.minskkniga.R;

public class Zakaz_info extends AppCompatActivity {

    int id;
    String name;
    TextView caption;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravoch_couriers_zakaz_info);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        caption = findViewById(R.id.caption);
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        caption.setText(name);

    }
}
