package by.minskkniga.minskkniga.api;

import java.util.List;
import java.util.Map;

import by.minskkniga.minskkniga.api.Class.Clients;
import by.minskkniga.minskkniga.api.Class.Courier_filter_1;
import by.minskkniga.minskkniga.api.Class.Courier_filter_2;
import by.minskkniga.minskkniga.api.Class.Couriers;
import by.minskkniga.minskkniga.api.Class.Gorod;
import by.minskkniga.minskkniga.api.Class.Login;
import by.minskkniga.minskkniga.api.Class.Notif_count;
import by.minskkniga.minskkniga.api.Class.Organizer;
import by.minskkniga.minskkniga.api.Class.Organizer_filter;
import by.minskkniga.minskkniga.api.Class.Organizer_info;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.Product_client;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.Products_filter;
import by.minskkniga.minskkniga.api.Class.Providers;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.Zakaz;
import by.minskkniga.minskkniga.api.Class.Zakaz_filter;
import by.minskkniga.minskkniga.api.Class.Zakazy;
import by.minskkniga.minskkniga.api.Class.Zakazy_short;
import by.minskkniga.minskkniga.api.Class.cassa.GetDohodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.GetPerevodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.GetRashodResponse;
import by.minskkniga.minskkniga.api.Class.cassa.InfoSchetaResponse;
import by.minskkniga.minskkniga.api.Class.cassa.ObjectTransaction;
import by.minskkniga.minskkniga.api.Class.cassa.SchetaResponse;
import by.minskkniga.minskkniga.api.Class.category.ResponseCategory;
import by.minskkniga.minskkniga.api.Class.category.ResponseProvScheta;
import by.minskkniga.minskkniga.api.Class.category.Schetum;
import by.minskkniga.minskkniga.api.Class.clients.ClientInfo;
import by.minskkniga.minskkniga.api.Class.couriers.CourierClients;
import by.minskkniga.minskkniga.api.Class.couriers.CourierKnigi;
import by.minskkniga.minskkniga.api.Class.couriers.ObjectReturnObrazci;
import by.minskkniga.minskkniga.api.Class.couriers.ObjectReturnZakaz;
import by.minskkniga.minskkniga.api.Class.inventarizacia.InventarizaciaObject;
import by.minskkniga.minskkniga.api.Class.nomenclatura.ObjectZakazyObrazci;
import by.minskkniga.minskkniga.api.Class.provider_sp.ProviderInfo;
import by.minskkniga.minskkniga.api.Class.providers.GetOzhidaemResponse;
import by.minskkniga.minskkniga.api.Class.providers.GetZakK;
import by.minskkniga.minskkniga.api.Class.providers.InfoZayavkaBook;
import by.minskkniga.minskkniga.api.Class.providers.Money;
import by.minskkniga.minskkniga.api.Class.providers.MoneyProvResponse;
import by.minskkniga.minskkniga.api.Class.providers.ProviderObject;
import by.minskkniga.minskkniga.api.Class.providers.ProviderZayavkiIzdatelstva;
import by.minskkniga.minskkniga.api.Class.providers.ProviderZayavkiNews;
import by.minskkniga.minskkniga.api.Class.providers.ProvidersZayavkiId;
import by.minskkniga.minskkniga.api.Class.providers.ZavInfo;
import by.minskkniga.minskkniga.api.Class.providers.ZayavkaInfo;
import by.minskkniga.minskkniga.api.Class.zakazy.ClientsCity;
import by.minskkniga.minskkniga.api.Class.zakazy.MoneyZakResponse;
import by.minskkniga.minskkniga.api.Class.zakazy.ZakazyObrazec;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RestApi {


    @GET("login.php")
    Call<Login> login(@Query("login") String login,
                      @Query("pass") String pass);


    @GET("get_clients.php")
    Call<List<Clients>> getClients();

    @GET("add_client.php")
    Call<ResultBody> addClient(@Query("name") String name,
                               @Query("sokr_name") String sokr_name,
                               @Query("zametka") String zametka,
                               @Query("print") String print,
                               @Query("type_c") String type_c,
                               @Query("nacenka") String nacenka,
                               @Query("podarki") String podarki,
                               @Query("skidka") String skidka,
                               @Query("dolg") String dolg,
                               @Query("type_dolg") String type_dolg,
                               @Query("napravl") String napravl,
                               @Query("gorod_id") int gorod_id,
                               @Query("school") String school,
                               @Query("smena") String smena,
                               @Query("contacts") String contacts);

    @GET("get_providers.php")
    Call<List<Providers>> getProviders();

    @GET("add_provider.php")
    Call<ResponseBody> addProvider(@Query("name") String name,
                                 @Query("short_name") String short_name,
                                 @Query("zametka") String zametka,
                                 @Query("info") String info,
                                 @Query("price_type") String price_type,
                                 @Query("nakrytka") double price_sale,
                                 @Query("type_credit_size") String type_credit_size,
                                 @Query("credit_size") double credit_size,
                                 @Query("city") String city,
                                 @Query("napravl") String napravl,
                                 @Query("contacts") String contacts);

    @GET("get_goroda.php")
    Call<List<Gorod>> getGoroda();

    @GET("get_products_filter.php")
    Call<Products_filter> getProducts_filter();

    @GET("get_products.php")
    Call<List<Products>> getProducts(@Query("autor") String avtor,
                                     @Query("izdatel") String izdatel,
                                     @Query("obrazec") String obraz,
                                     @Query("clas") String clas);

    @GET("get_product.php")
    Call<Product> getProduct(@Query("id") String id);

    @GET("get_product_client.php")
    Call<Product_client> getProduct_client(@Query("id") String id,
                                           @Query("id_client") String id_client);

    @Multipart
    @POST("add_product.php")
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
                                @Query("ves") String ves,
                                @Query("rezerv") String rezerv);

    @GET("add_product.php")
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


    @GET("artikyl.php")
    Call<ResultBody> artikyl(@Query("name") String name);

    @GET("get_zakazy_client.php")
    Call<List<Zakazy>> getZakazy_client(@Query("id") int id);

    @GET("get_couriers.php")
    Call<List<Couriers>> getCouriers();

    @GET("set_oplata.php")
    Call<ResultBody> setOplata(@Query("id") String id);

    @GET("get_zakaz_info.php")
    Call<Zakaz> getZakaz_info(@Query("id") String id);

//    @GET("get_courier_zakazy.php")
//    Call<List<Zakazy_courier_clients>> getCourier_zakazy(@Query("id") String id,
//                                                         @Query("napravl") String napravl,
//                                                         @Query("gorod") String gorod,
//                                                         @Query("school") String school,
//                                                         @Query("smena") String smena);

    @GET("/api/get_couriers_list_knigi.php")
    Call<List<CourierKnigi>> getCourier_knigi(@Query("courier_id") String id,
                                                      @Query("izdanie") String izdanie,
                                                      @Query("class") String _class);

    @GET("get_couriers_filter_1.php")
    Call<Courier_filter_1> getCourier_filter_1(@Query("id") String id);

    @GET("get_couriers_filter_2.php")
    Call<Courier_filter_2> getCourier_filter_2(@Query("id") String id);

    @GET("set_complete_zakaz.php")
    Call<ResultBody> setComplete_zakaz(@Query("id") String id);

    @GET("get_organizer.php")
    Call<List<Organizer>> getOrganizer(@Query("autor") String autor,
                                       @Query("komy") String komy,
                                       @Query("kontragent") String kontragent,
                                       @Query("status") String status);

    @GET("get_organizer_filter.php")
    Call<Organizer_filter> getOrganizer_filter();

    @GET("set_organizer_ok.php")
    Call<ResultBody> setOrganizer_ok(@Query("id") String id);

    @GET("get_organizer_info.php")
    Call<Organizer_info> getOrganizer_info();

    @GET("add_organizer.php")
    Call<ResultBody> addOrganizer(@Query("id") String id,
                                  @Query("countragent_id") String countragent_id,
                                  @Query("autor_id") String autor_id,
                                  @Query("ispolnitel_id") String ispolnitel_id,
                                  @Query("date") String date,
                                  @Query("status") String status,
                                  @Query("text") String text);

    @GET("get_notif.php")
    Call<Notif_count> getNotif(@Query("id") String id);

    @GET("set_courier_add_knigi.php")
    Call<ResultBody> addKnigi(@Query("id") String id);

    @GET("add_courier.php")
    Call<ResultBody> addCourier(@Query("name") String name,
                                @Query("login") String login,
                                @Query("pass") String pass,
                                @Query("contacts") String contacts);

    @GET("add_zakaz.php")
    Call<ResultBody> addZakaz(@Query("date") String date,
                              @Query("client_id") String client_id,
                              @Query("admin_id") String admin_id,
                              @Query("koment") String koment,
                              @Query("courier_id") String courier_id,
                              @Query("status") String status,
                              @Query("oplacheno") String oplacheno,
                              @Query("mas") String mas);

    @GET("get_zakaz_filter.php")
    Call<Zakaz_filter> getZakaz_filter();

    @GET("get_zakazy.php")
    Call<List<Zakazy_short>> getZakazy();

    @GET("/api/get_providers_izd.php")
    Call<List<ProviderZayavkiIzdatelstva>> getProvidersZ();

    @GET("/admin/srcipts/auto_z_all.php")
    Call<ResponseBody> getAutoZAll();

    @GET("/api/get_providers_new.php")
    Call<ProviderZayavkiNews> getProvidersNewsZ();

    @GET("/api/get_zav_prov.php?")
    Call<List<ProvidersZayavkiId>> getProvidersZayavki(@Query("id") String id);

    @GET("/api/get_prov_d.php?")
    Call<List<Money>> getProviderMoney(@Query("id") String id);

    @GET("/api/get_zav_info.php?")
    Call<ZavInfo> getZavInfo(@Query("id") String id);

    @GET("/api/get_info_k.php?")
    Call<List<InfoZayavkaBook>> getInfoBookZ(@Query("id") String id, @Query("zak") String zak);

    @GET("/api/get_list_providers.php?")
    Call<List<ProviderObject>> getAllProviders();

    @GET("/api/get_couriers.php?")
    Call<List<by.minskkniga.minskkniga.api.Class.providers.Couriers>> getAllCouriers();

    @GET("/api/get_scheta.php?")
    Call<List<SchetaResponse>> getAllScheta();

    @GET("/api/get_prov_schet.php?")
    Call<List<ResponseProvScheta>> getProvScheta(@Query("id") String id);

    @GET("/api/get_scheta_cat.php?")
    Call<ResponseCategory> getCategory(@Query("type") String type);

    @GET("/api/get_scheta_cassa.php?")
    Call<List<Schetum>> getScheta();

    @GET("/api/get_scheta_kontragent.php?")
    Call<List<ObjectTransaction>> getKontragent(@Query("type") String type);

    @GET("/api/get_scheta_info.php?")
    Call<InfoSchetaResponse> getSchetaInfo(@Query("id") String id);

    @GET("/api/add_scheta_category.php?")
    Call<ResponseBody> addCategory(@Query("type") String type, @Query("name") String name);

    @GET("/api/add_scheta_pod_cat.php?")
    Call<ResponseBody> addPodCategory(@Query("cat_id") String cat_id, @Query("name") String name);

    @GET("/api/edit_scheta_category.php?")
    Call<ResponseBody> editCategory(@Query("id") String id, @Query("name") String name);

    @GET("/api/edit_scheta_pod_cat.php?")
    Call<ResponseBody> editPodCategory(@Query("id") String id, @Query("name") String name);

    @GET("/api/add_new_schet.php?")
    Call<ResponseBody> addNewSchet(@Query("name") String name, @Query("type") String type,
                                   @Query("nach_sum") String nach_sum, @Query("itog") String itog,
                                   @Query("kom") String kom);

    @GET("/api/update_schet.php?")
    Call<ResponseBody>updateSchet(@Query("name") String name, @Query("type") String type,
                                   @Query("kom") String kom);

    @GET("/api/set_kassa_order.php?")
    Call<ResponseBody> addOperationCassa(@Query("cat_id") String cat_id, @Query("pod_cat_id") String pod_cat_id,
                                   @Query("schet_id") String schet_id, @Query("summa") String summa,
                                   @Query("date") String date, @Query("prov_id") String prov_id,
                                   @Query("com") String com,
                                   @Query("type") String type,@Query("schet_perevoda") String schet_perevoda);

    @GET("get_providers_filter_products.php")
    Call<Products_filter> getProductsfilter(@Query("clas") String clas, @Query("obrazec") String obrazec,
                                            @Query("autor") String autor, @Query("izdatel") String izdatel,
                                            @Query("filter") String filter);
    @GET("/api/get_rashod_id.php?")
    Call<List<GetRashodResponse>> getRashodID(@Query("id") String id);

    @GET("/api/get_perevod_id.php?")
    Call<List<GetPerevodResponse>> getPerevodID(@Query("id") String id);

    @GET("/api/get_prihod_id.php?")
    Call<List<GetDohodResponse>> getDohodID(@Query("id") String id);

    @POST("/api/add_zakaz_provider.php?")
    @FormUrlEncoded
    Call<ResponseBody> addNewProviderZayavka(@FieldMap Map<String, String> map);

    @GET("/api/inv_kniga.php?")
    Call<InventarizaciaObject> getInventarizacia(@Query("id") String id);

    @GET
    Call<ResponseBody> setUpdatePrihod(@Url String url);

    @GET
    Call<ResponseBody> setUpdateRashod(@Url String url);

    @GET
    Call<ResponseBody> setUpdatePerevod(@Url String url);

    @GET
    Call<ResponseBody> setInvEd(@Url String url);

    @GET("/api/delete_scheta_info.php?")
    Call<ResponseBody> getDeleteOperationId(@Query("id") String id, @Query("type") String type);

    @GET("get_providers_products.php")
    Call<List<ZayavkaInfo>> getProductsZayavka(@Query("autor") String avtor,
                                               @Query("izdatel") String izdatel,
                                               @Query("obrazec") String obraz,
                                               @Query("clas") String clas);
    @GET("/api/get_zak_k.php?")
    Call<List<GetZakK>> getZakK(@Query("id") String id);

    @GET("/api/get_ojidaem_k.php?")
    Call<List<GetOzhidaemResponse>> getOjidaem(@Query("id") String id);

    @POST("/api/update_zav_providers.php?")
    @FormUrlEncoded
    Call<ResponseBody> updateProviderZayavka(@FieldMap Map<String, String> map);

    @GET("/api/get_client_id.php?")
    Call<List<ClientInfo>> getClientId(@Query("id") String id);

    @GET("/api/get_provider_id.php?")
    Call<List<ProviderInfo>> getProviderId(@Query("id") String id);

    @GET("update_client.php")
    Call<ResponseBody> updateClient(@Query("id") String id,
                                @Query("name") String name,
                               @Query("sokr_name") String sokr_name,
                               @Query("zametka") String zametka,
                               @Query("print") String print,
                               @Query("type_c") String type_c,
                               @Query("nacenka") String nacenka,
                               @Query("podarki") String podarki,
                               @Query("skidka") String skidka,
                               @Query("dolg") String dolg,
                               @Query("type_dolg") String type_dolg,
                               @Query("napravl") String napravl,
                               @Query("gorod_id") int gorod_id,
                               @Query("school") String school,
                               @Query("smena") String smena,
                               @Query("contacts") String contacts);
    @GET("update_provider.php")
    Call<ResponseBody> updateProvider(@Query("id") String id,
                                    @Query("name") String name,
                                 @Query("short_name") String short_name,
                                 @Query("zametka") String zametka,
                                 @Query("info") String info,
                                 @Query("price_type") String price_type,
                                 @Query("nakrytka") double price_sale,
                                 @Query("type_credit_size") String type_credit_size,
                                 @Query("credit_size") double credit_size,
                                 @Query("city_id") String city,
                                 @Query("napravl") String napravl,
                                 @Query("contacts") String contacts);

    @GET("get_courier_list_knigi.php")
    Call<List<CourierKnigi>> getCourierKnigi(@Query("id") String id);

    @GET("get_clients_city.php")
    Call<List<ClientsCity>> getClientsCity();

    @GET("get_prov_d.php")
    Call<List<MoneyProvResponse>> getProvD(@Query("id") String id);

    @GET("get_zak_dengi.php")
    Call<List<MoneyZakResponse>> getZakDengi(@Query("id") String id);

    @POST("update_zakazi.php")
    @FormUrlEncoded
    Call<ResponseBody> updateZakay(@FieldMap Map<String, String> map);

    @GET("get_list_couriers.php")
    Call<List<Couriers>> getCour();

    @GET("get_zakazy_obrazcy.php")
    Call<List<ObjectZakazyObrazci>> getZakazyObrazci();

    @POST("add_zakaz_obrazec.php")
    @FormUrlEncoded
    Call<ResponseBody> addZakazObrazec(@FieldMap Map<String, String> map);

    @GET("get_list_clients.php")
    Call<List<Clients>> getListClients();

    @GET("get_zakaz_info.php")
    Call<ZakazyObrazec> getZakazObrazciId(@Query("id") String id, @Query("obrazec") String obrazec);

    @GET("get_courier_zakazy.php")
    Call<List<CourierClients>> getCourier_zakazy(@Query("id") String id);

    @POST("courier_obrazcy_vzyac.php")
    @FormUrlEncoded
    Call<ResponseBody> courier_obrazcy_vzyac(@FieldMap Map<String, String> map);

    @POST("courier_obrazcy_vipolneno.php")
    @FormUrlEncoded
    Call<ResponseBody> courier_obrazcy_vipolneno(@FieldMap Map<String, String> map);

    @POST("courier_zakazy_vzyac.php")
    @FormUrlEncoded
    Call<ResponseBody> courier_zakazy_vzyac(@FieldMap Map<String, String> map);

    @POST("courier_zakazy_vipolneno.php")
    @FormUrlEncoded
    Call<ResponseBody> courier_zakazy_vipolneno(@FieldMap Map<String, String> map);

    @POST("add_courier_return_knigi_sklad.php")
    @FormUrlEncoded
    Call<ResponseBody> returnToSklad(@FieldMap Map<String, String> map);

    @GET("get_courier_return_knigi_sklad_info.php")
    Call<ObjectReturnZakaz> getInfoReturnZakaz(@Query("id") String id,
                                               @Query("obrazec") String obrazec);


    @GET("get_courier_return_knigi_sklad_info.php")
    Call<ObjectReturnObrazci> getInfoReturnObrazci(@Query("id") String id,
                                                   @Query("obrazec") String obrazec);
}