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
                .baseUrl("http://query.pe.hu/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        Api = retrofit.create(RestApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static RestApi getApi() {
        return Api;
    }
}