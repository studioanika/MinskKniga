package by.minskkniga.minskkniga.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RestApi {

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

    @GET("/api/show_header_nomenklatura.php")
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
    Call<List<by.minskkniga.minskkniga.api.Zakazy>> getZakazy(@Query("id") int id);

    @GET("/api/show_couriers.php")
    Call<List<Couriers>> getCouriers();
}