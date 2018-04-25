package by.minskkniga.minskkniga.api;

import java.util.List;

import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Courier_filter_1;
import by.minskkniga.minskkniga.api.Class.Courier_filter_2;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.Login;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Notif_count;
import by.minskkniga.minskkniga.api.Class.Organizer;
import by.minskkniga.minskkniga.api.Class.Organizer_filter;
import by.minskkniga.minskkniga.api.Class.Organizer_info;
import by.minskkniga.minskkniga.api.Class.Providers;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.Gorod;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_clients;
import by.minskkniga.minskkniga.api.Class.Zakazy_courier_knigi;
import okhttp3.MultipartBody;
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


    @GET("/api/get_clients.php")
    Call<List<Clients>> getClients();

    @GET("/api/add_client.php")
    Call<ResultBody> addClient(@Query("name") String name,
                               @Query("sokr_name") String sokr_name,
                               @Query("zametka") String zametka,
                               @Query("print") String print,
                               @Query("type_c") String type_c,
                               @Query("nacenka") String nacenka,
                               @Query("podarki") String podarki,
                               @Query("skidka") String skidka,
                               @Query("dolg") String dolg,
                               @Query("napravl") String napravl,
                               @Query("gorod_id") int gorod_id,
                               @Query("school") String school,
                               @Query("smena") String smena,
                               @Query("contacts") String contacts);

    @GET("/api/get_providers.php")
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
                                 @Query("contacts") String contacts);

    @GET("/api/get_goroda.php")
    Call<List<Gorod>> getGorod();

    @GET("/api/get_products_filter.php")
    Call<Products_filter> getProducts_filter();

    @GET("/api/get_products.php")
    Call<List<Products>> getProducts(@Query("autor") String avtor,
                                         @Query("izdatel") String izdatel,
                                         @Query("obrazec") String obraz,
                                         @Query("clas") String clas);

    @GET("/api/get_product.php")
    Call<Products> getProduct(@Query("id") String id);

    @Multipart
    @POST("/api/add_product.php")
    Call<ResultBody> addProduct(@Part MultipartBody.Part image,
                                     @Query("id") String id,
                                     @Query("name") String name,
                                     @Query("clas") String clas,
                                     @Query("obrazec") String obrazec,
                                     @Query("artikul") String artikul,
                                     @Query("sokr_name") String sokr_name,
                                     @Query("izdatel") String izdatel,
                                     @Query("autor") String autor,
                                     @Query("barcode") String barcode,
                                     @Query("zakup_cena") String zakup_cena,
                                     @Query("prod_cena") String prod_cena,
                                     @Query("standart") String standart,
                                     @Query("ves") String ves);

    @GET("/api/add_product.php")
    Call<ResultBody> addProduct(@Query("id") String id,
                                     @Query("name") String name,
                                     @Query("clas") String clas,
                                     @Query("obrazec") String obrazec,
                                     @Query("artikul") String artikul,
                                     @Query("sokr_name") String sokr_name,
                                     @Query("izdatel") String izdatel,
                                     @Query("autor") String autor,
                                     @Query("barcode") String barcode,
                                     @Query("zakup_cena") String zakup_cena,
                                     @Query("prod_cena") String prod_cena,
                                     @Query("standart") String standart,
                                     @Query("ves") String ves);


    @GET("/api/artikyl.php")
    Call<ResultBody> artikyl(@Query("name") String name);

    @GET("/api/get_zakazy.php")
    Call<List<Zakazy>> getZakazy(@Query("id") int id);

    @GET("/api/get_couriers.php")
    Call<List<Couriers>> getCouriers();

    @GET("/api/set_oplata.php")
    Call<ResultBody> setOplata(@Query("id") String id);

    @GET("/api/get_zakaz_info.php")
    Call<by.minskkniga.minskkniga.api.Class.Zakaz_info> getZakaz_info(@Query("id") String id);

    @GET("/api/get_courier_zakazy.php")
    Call<List<Zakazy_courier_clients>> getCourier_zakazy(@Query("id") String id,
                                                         @Query("napravl") String napravl,
                                                         @Query("sity") String sity,
                                                         @Query("school") String school,
                                                         @Query("smena") String smena);

    @GET("/api/get_courier_knigi.php")
    Call<List<Zakazy_courier_knigi>> getCourier_knigi(@Query("id") String id,
                                                      @Query("izdanie") String izdanie,
                                                      @Query("class") String _class);

    @GET("/api/get_couriers_filter_1.php")
    Call<Courier_filter_1> getCourier_filter_1(@Query("id") String id);

    @GET("/api/get_couriers_filter_2.php")
    Call<Courier_filter_2> getCourier_filter_2(@Query("id") String id);

    @GET("/api/set_complete_zakaz.php")
    Call<ResultBody> setComplete_zakaz(@Query("id") String id);

    @GET("/api/get_organizer.php")
    Call<List<Organizer>> getOrganizer(@Query("autor") String autor,
                                  @Query("komy") String komy,
                                  @Query("kontragent") String kontragent,
                                  @Query("status") String status);

    @GET("/api/get_organizer_filter.php")
    Call<Organizer_filter> getOrganizer_filter();

    @GET("/api/set_organizer_ok.php")
    Call<ResultBody> setOrganizer_ok(@Query("id") String id);

    @GET("/api/get_organizer_info.php")
    Call<Organizer_info> getOrganizer_info();

    @GET("/api/add_organizer.php")
    Call<ResultBody> addOrganizer(@Query("id") String id,
                                  @Query("countragent_id") String countragent_id,
                                  @Query("autor_id") String autor_id,
                                  @Query("ispolnitel_id") String ispolnitel_id,
                                  @Query("date") String date,
                                  @Query("status") String status,
                                  @Query("text") String text);

    @GET("/api/get_notif.php")
    Call<Notif_count> getNotif(@Query("id") String id);

    @GET("/api/set_courier_add_knigi.php")
    Call<ResultBody> addKnigi(@Query("id") String id);

    @GET("/api/add_courier.php")
    Call<ResultBody> addCourier(@Query("name") String name,
                                @Query("login") String login,
                                @Query("pass") String pass,
                                @Query("desc") String desc,
                                @Query("contacts") String contacts);
}