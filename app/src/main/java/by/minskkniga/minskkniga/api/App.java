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

        retrofit = new Retrofit.Builder()
                .baseUrl("http://cc96297.tmweb.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api = retrofit.create(RestApi.class);
    }

    public static RestApi getApi() {
        return Api;
    }
}