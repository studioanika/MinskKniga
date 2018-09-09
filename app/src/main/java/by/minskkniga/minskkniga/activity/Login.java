package by.minskkniga.minskkniga.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.prefs.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText login;
    EditText pass, host;
    Button button;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    Prefs prefs;

    ProgressDialog pd;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = new Prefs(this);

        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);
        host = findViewById(R.id.host);
        button = findViewById(R.id.button);


        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        ed = sp.edit();

        login.setText(sp.getString("login", ""));
        pass.setText(sp.getString("pass", ""));

        if (!login.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !prefs.getHost().isEmpty()) {
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

            ed.putString("login", login.getText().toString());
            ed.putString("pass", pass.getText().toString());
            ed.apply();

            pd = new ProgressDialog(this);
            pd.setTitle("Авторизация");
            pd.setMessage("Пожалуйста подождите");
            pd.setCancelable(false);
            pd.show();

            String host_s = "";

            if(prefs.getHost().isEmpty()) host_s = host.getText().toString();
            else host_s = prefs.getHost();

            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(host_s)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
            by.minskkniga.minskkniga.api.RestApi api = retrofit.create(by.minskkniga.minskkniga.api.RestApi.class);

            final String finalHost_s = host_s;
            final String finalHost_s1 = host_s;
            api.login(login.getText().toString(), pass.getText().toString()).enqueue(new Callback<by.minskkniga.minskkniga.api.Class.Login>() {
                @Override
                public void onResponse(Call<by.minskkniga.minskkniga.api.Class.Login> call, Response<by.minskkniga.minskkniga.api.Class.Login> response) {
                    //if(prefs.getHost().isEmpty()) prefs.setHost(host.getText().toString());
                    if (!response.body().getMessage().equals("error")) {

                        pd.cancel();
                        Toast.makeText(Login.this, response.body().getRank()+" "+response.body().getUser_id(), Toast.LENGTH_SHORT).show();
                        ed.putString("id", response.body().getId());
                        ed.putString("login", login.getText().toString());
                        ed.putString("pass", pass.getText().toString());
                        ed.putString("rank", response.body().getRank());
                        ed.putString("user_id", response.body().getUser_id());
                        ed.putString("name", response.body().getName());
                        ed.apply();
                        if(prefs.getHost().isEmpty()){
                            prefs.setHost(finalHost_s);
                            getApplication().onCreate();

                        }

                        Intent intent = new Intent(Login.this, Menu.class);
                        startActivity(intent);


                    } else {
                        //if(prefs.getHost().isEmpty()) prefs.setHost(finalHost_s1);
                        pd.cancel();
                        Toast.makeText(Login.this, "Не верный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<by.minskkniga.minskkniga.api.Class.Login> call, Throwable t) {
                    String d = t.toString();
                    pd.cancel();
                    Toast.makeText(Login.this, "Ошибка соединения с сервером...", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(Login.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }
}
