package by.minskkniga.minskkniga.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.ResultBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText login;
    EditText pass;
    Button button;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);
        button = findViewById(R.id.button);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        sp = getPreferences(MODE_PRIVATE);
        login.setText(sp.getString("login", ""));
        pass.setText(sp.getString("pass", ""));

        if (!login.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {
            login();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            }, 199);
        }
    }

    public void login() {
        if (!login.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {


            pd = new ProgressDialog(this);
            pd.setTitle("Авторизация");
            pd.setMessage("Пожалуйста подождите");
            pd.setCancelable(false);
            pd.show();


            App.getApi().login(login.getText().toString(), pass.getText().toString()).enqueue(new Callback<ResultBody>() {
                @Override
                public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                    if (!response.body().getMessage().equals("error")) {
                        pd.cancel();
                        sp = getPreferences(MODE_PRIVATE);
                        ed = sp.edit();
                        ed.putString("login", login.getText().toString());
                        ed.putString("pass", pass.getText().toString());
                        ed.putString("rank", response.body().getMessage().toString());
                        ed.apply();
                        Intent intent = new Intent(Login.this, Menu.class);
                        startActivity(intent);
                    } else {
                        pd.cancel();
                        Toast.makeText(Login.this, "Не верный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultBody> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(Login.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }
}
