package by.minskkniga.minskkniga.api;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static RestApi Api;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        by.minskkniga.minskkniga.activity.prefs.Prefs pref = new by.minskkniga.minskkniga.activity.prefs.Prefs(this);


        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(pref.getHost())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api = retrofit.create(RestApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RestApi getApi() {
        return Api;
    }
}