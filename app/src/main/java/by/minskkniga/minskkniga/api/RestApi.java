package by.minskkniga.minskkniga.api;

import java.util.List;

import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Courier_filter_1;
import by.minskkniga.minskkniga.api.Class.Courier_filter_2;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.Login;
import by.minskkniga.minskkniga.api.Class.Nomenklatura;
import by.minskkniga.minskkniga.api.Class.Nomenklatura_filter;
import by.minskkniga.minskkniga.api.Class.Notif_count;
import by.minskkniga.minskkniga.api.Class.Organizer;
import by.minskkniga.minskkniga.api.Class.Organizer_filter;
import by.minskkniga.minskkniga.api.Class.Organizer_info;
import by.minskkniga.minskkniga.api.Class.Providers;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.Sity;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_clients;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_knigi;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RestApi {


    @GET("/api/login.php")
    Call<Login> login(@Query("login") String login,
                      @Query("pass") String pass);


    @GET("/api/show_clients.php")
    Call<List<Clients>> getClients();

    @GET("/api/add_client.php")
    Call<ResultBody> addClient(@Query("name") String name,
                               @Query("sokr_name") String sokr_name,
                               @Query("info_for_print") String info_for_print,
                               @Query("zametka") String zametka,
                               @Query("napravl") String napravl,
                               @Query("sity") int sity,
                               @Query("school") String school,
                               @Query("smena") String smena,
                               @Query("type_ceni") String type_ceni,
                               @Query("dolg") Double dolg,
                               @Query("podarki") String podarki,
                               @Query("skidka") Double skidka,
                               @Query("dela") String dela,
                               @Query("contacts") String contacts,
                               @Query("contact_faces") String contact_faces);

    @GET("/api/show_providers.php")
    Call<List<Providers>> getProviders();

    @GET("/api/add_provider.php")
    Call<ResultBody> addProvider(@Query("name") String name,
                                 @Query("short_name") String short_name,
                                 @Query("zametka") String zametka,
                                 @Query("info") String info,
                                 @Query("price_type") String price_type,
                                 @Query("nakrytka") double price_sale,
                                 @Query("credit_size") double credit_size,
                                 @Query("city") String city,
                                 @Query("napravl") String napravl,
                                 @Query("contacts") String contacts,
                                 @Query("contact_faces") String contact_faces);

    @GET("/api/show_sity.php")
    Call<List<Sity>> getSity();

    @GET("/api/show_nomenklatura_filter.php")
    Call<Nomenklatura_filter> getNomenclatura_filter();

    @GET("/api/show_nomenklatura.php")
    Call<List<Nomenklatura>> getNomenclatura(@Query("autor") String avtor,
                                             @Query("izdatel") String izdatel,
                                             @Query("obrazec") String obraz,
                                             @Query("class") String _class);

    @Multipart
    @POST("/api/add_nomenklatura.php")
    Call<ResultBody> addNomenclatura(@Part MultipartBody.Part image,
                                     @Part("name") RequestBody name);
    /*,
                                     @Part("_class") RequestBody _class,
                                     @Part("predmet") RequestBody predmet,
                                     @Part("name") RequestBody obrazec,
                                     @Part("name") RequestBody artikul,
                                     @Part("name") RequestBody sokr_name,
                                     @Part("name") RequestBody izdatel,
                                     @Part("name") RequestBody autor,
                                     @Part("name") RequestBody barcode,
                                     @Query("name") RequestBody zakup_cena,
                                     @Query("name") RequestBody prod_cena);
*/
    @GET("/api/artikyl.php")
    Call<ResultBody> artikyl(@Query("name") String name);

    @GET("/api/show_zakazy.php")
    Call<List<Zakazy>> getZakazy(@Query("id") int id);

    @GET("/api/show_couriers.php")
    Call<List<Couriers>> getCouriers();

    @GET("/api/set_oplata.php")
    Call<ResultBody> setOplata(@Query("id") String id);

    @GET("/api/show_zakaz_info.php")
    Call<by.minskkniga.minskkniga.api.Class.Zakaz_info> getZakaz_info(@Query("id") String id);

    @GET("/api/show_courier_zakazy.php")
    Call<List<Zakazy_courier_clients>> getCourier_zakazy(@Query("id") String id,
                                                         @Query("napravl") String napravl,
                                                         @Query("sity") String sity,
                                                         @Query("school") String school,
                                                         @Query("smena") String smena);

    @GET("/api/show_courier_knigi.php")
    Call<List<Zakazy_courier_knigi>> getCourier_knigi(@Query("id") String id,
                                                      @Query("izdanie") String izdanie,
                                                      @Query("class") String _class);

    @GET("/api/show_couriers_filter_1.php")
    Call<Courier_filter_1> getCourier_filter_1(@Query("id") String id);

    @GET("/api/show_couriers_filter_2.php")
    Call<Courier_filter_2> getCourier_filter_2(@Query("id") String id);

    @GET("/api/set_complete_zakaz.php")
    Call<ResultBody> setComplete_zakaz(@Query("id") String id);

    @GET("/api/show_organizer.php")
    Call<List<Organizer>> getOrganizer(@Query("autor") String autor,
                                  @Query("komy") String komy,
                                  @Query("kontragent") String kontragent,
                                  @Query("status") String status);

    @GET("/api/show_organizer_filter.php")
    Call<Organizer_filter> getOrganizer_filter();

    @GET("/api/set_organizer_ok.php")
    Call<ResultBody> setOrganizer_ok(@Query("id") String id);

    @GET("/api/show_organizer_info.php")
    Call<Organizer_info> getOrganizer_info();

    @GET("/api/add_organizer.php")
    Call<ResultBody> addOrganizer(@Query("id") String id,
                                  @Query("countragent_id") String countragent_id,
                                  @Query("autor_id") String autor_id,
                                  @Query("ispolnitel_id") String ispolnitel_id,
                                  @Query("date") String date,
                                  @Query("status") String status,
                                  @Query("text") String text);

    @GET("/api/show_notif.php")
    Call<Notif_count> getNotif(@Query("id") String id);

}