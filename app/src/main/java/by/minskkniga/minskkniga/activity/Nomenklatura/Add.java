package by.minskkniga.minskkniga.activity.Nomenklatura;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Barcode;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.Product_client;
import by.minskkniga.minskkniga.api.Class.Products;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import by.minskkniga.minskkniga.api.Class.Zakaz_product;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends AppCompatActivity {

    ImageButton back;
    ScrollView sv;

    EditText name;
    EditText clas;
    Spinner obrazec_spinner;
    EditText artikul;
    EditText sokr_name;
    EditText izdatel;
    EditText autor;
    EditText barcode;
    ImageButton barcode_button;
    EditText zakup_cena;
    EditText prod_cena;
    EditText standart;
    EditText ves;

    TextView zakazano;
    TextView dostupno;
    TextView vozvrat;
    TextView ogidanie;
    TextView upakovok;
    TextView ostatok;

    Button cancel;
    Button ok;


    IntentIntegrator qrScan;
    ImageView image;
    Bitmap bitmap;

    private final int TAKE_PICTURE = 1;
    private final int FILE_CHOOSER_RESULT = 2;
    private Uri outputFileUri;

    int isimage = 0;
    boolean zakaz = false;
    String id = "";

    LinearLayout linear_add;
    LinearLayout linear_add_zakaz;

    EditText cena_zakaz;
    EditText col_zakaz;
    TextView summa_zakaz;

    EditText cena_podar;
    EditText col_podar;
    TextView summa_podar;
    TextView summa;

    String id_client;

    public void initialize() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok();
            }
        });

        sv = findViewById(R.id.sv);

        barcode_button = findViewById(R.id.barcode_button);

        name = findViewById(R.id.name);
        clas = findViewById(R.id.clas);
        obrazec_spinner = findViewById(R.id.obrazec_spinner);
        artikul = findViewById(R.id.artikul);
        sokr_name = findViewById(R.id.sokr_name);
        izdatel = findViewById(R.id.izdatel);
        autor = findViewById(R.id.autor);
        barcode = findViewById(R.id.barcode);
        barcode_button = findViewById(R.id.barcode_button);
        zakup_cena = findViewById(R.id.zakup_cena);
        prod_cena = findViewById(R.id.prod_cena);
        standart = findViewById(R.id.standart);
        ves = findViewById(R.id.ves);

        zakazano = findViewById(R.id.zakazano);
        dostupno = findViewById(R.id.dostupno);
        vozvrat = findViewById(R.id.vozvrat);
        ogidanie = findViewById(R.id.ogidanie);
        upakovok = findViewById(R.id.upakovok);
        ostatok = findViewById(R.id.ostatok);

        qrScan = new IntentIntegrator(this);
        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(Add.this)
                        .setOrientationLocked(true)
                        .setCaptureActivity(Barcode.class)
                        .initiateScan();
            }
        });

        image = findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        Glide.with(this).load(R.drawable.ic_launcher_foreground).into(image);

        id = getIntent().getStringExtra("id");
        zakaz = getIntent().getBooleanExtra("zakaz", false);


        linear_add = findViewById(R.id.linear_add);
        linear_add_zakaz = findViewById(R.id.linear_add_zakaz);

        cena_zakaz = findViewById(R.id.cena_zakaz);
        col_zakaz = findViewById(R.id.col_zakaz);
        summa_zakaz = findViewById(R.id.summa_zakaz);

        cena_podar = findViewById(R.id.cena_podar);
        col_podar = findViewById(R.id.col_podar);
        summa_podar = findViewById(R.id.summa_podar);

        summa = findViewById(R.id.summa);

        if (zakaz) {
            id_client = getIntent().getStringExtra("id_client");
            linear_add.setVisibility(View.GONE);
            linear_add_zakaz.setVisibility(View.VISIBLE);

            cena_zakaz.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        summa_zakaz.setText(String.valueOf(Double.parseDouble(String.valueOf(cena_zakaz.getText())) * Double.parseDouble(String.valueOf(col_zakaz.getText()))));
                        summa.setText(String.valueOf(Double.parseDouble(String.valueOf(summa_zakaz.getText()))+Double.parseDouble(String.valueOf(summa_podar.getText()))));
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            col_zakaz.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        summa_zakaz.setText(String.valueOf(Double.parseDouble(String.valueOf(cena_zakaz.getText())) * Double.parseDouble(String.valueOf(col_zakaz.getText()))));
                        summa.setText(String.valueOf(Double.parseDouble(String.valueOf(summa_zakaz.getText()))+Double.parseDouble(String.valueOf(summa_podar.getText()))));
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            cena_podar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        summa_podar.setText("-"+String.valueOf(Double.parseDouble(String.valueOf(cena_podar.getText())) * Double.parseDouble(String.valueOf(col_podar.getText()))));
                        summa.setText(String.valueOf(Double.parseDouble(String.valueOf(summa_zakaz.getText()))+Double.parseDouble(String.valueOf(summa_podar.getText()))));
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            col_podar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        summa_podar.setText("-"+String.valueOf(Double.parseDouble(String.valueOf(cena_podar.getText())) * Double.parseDouble(String.valueOf(col_podar.getText()))));
                        summa.setText(String.valueOf(Double.parseDouble(String.valueOf(summa_zakaz.getText()))+Double.parseDouble(String.valueOf(summa_podar.getText()))));
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else {
            linear_add.setVisibility(View.VISIBLE);
            linear_add_zakaz.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nomenklatura);
        initialize();


        if (!id.equals("null")) {
            if (zakaz) {
                Toast.makeText(this, id+" "+id_client, Toast.LENGTH_SHORT).show();
                App.getApi().getProduct_client(id, id_client).enqueue(new Callback<Product_client>() {
                    @Override
                    public void onResponse(Call<Product_client> call, Response<Product_client> response) {

                        name.setText(response.body().getName());
                        clas.setText(response.body().getClas());
                        obrazec_spinner.setSelection(response.body().getArtikul().equals("Есть") ? 1 : 0);
                        artikul.setText(response.body().getArtikul());
                        sokr_name.setText(response.body().getSokrName());
                        izdatel.setText(response.body().getIzdatel());
                        autor.setText(response.body().getAutor());
                        barcode.setText(response.body().getBarcode());
                        ves.setText(response.body().getVes());

                        cena_zakaz.setText(response.body().getCena());
                        cena_podar.setText(response.body().getCena());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Glide.with(Add.this).load("http://query.pe.hu/admin/img/nomen/" + response.body().getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(image);
                        } else {
                            Glide.with(Add.this).load("http://query.pe.hu/admin/img/nomen/" + response.body().getImage()).into(image);
                        }

                    }

                    @Override
                    public void onFailure(Call<Product_client> call, Throwable t) {

                    }
                });
            }else {
                App.getApi().getProduct(id).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {

                        name.setText(response.body().getName());
                        clas.setText(response.body().getClas());
                        obrazec_spinner.setSelection(response.body().getArtikul().equals("Есть") ? 1 : 0);
                        artikul.setText(response.body().getArtikul());
                        sokr_name.setText(response.body().getSokrName());
                        izdatel.setText(response.body().getIzdatel());
                        autor.setText(response.body().getAutor());
                        barcode.setText(response.body().getBarcode());
                        zakup_cena.setText(response.body().getZakupCena());
                        prod_cena.setText(response.body().getProdCena());
                        standart.setText(response.body().getStandart());
                        ves.setText(response.body().getVes());

                        zakazano.setText(response.body().getZakazano());
                        dostupno.setText(response.body().getDostupno());
                        vozvrat.setText(response.body().getVozvrat());
                        ogidanie.setText(response.body().getOgidanie());
                        upakovok.setText(response.body().getUpakovok());
                        ostatok.setText(response.body().getOstatok());


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Glide.with(Add.this).load("http://query.pe.hu/admin/img/nomen/" + response.body().getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(image);
                        } else {
                            Glide.with(Add.this).load("http://query.pe.hu/admin/img/nomen/" + response.body().getImage()).into(image);
                        }

                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(Add.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Add.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            }, 199);
        }
    }

    public void ok() {
        if (zakaz) {
            Intent intent = new Intent();
            Zakaz_product product = new Zakaz_product(
                    id,
                    String.valueOf(name.getText()),
                    String.valueOf(artikul.getText()),
                    String.valueOf(cena_zakaz.getText()),
                    String.valueOf(col_zakaz.getText()),
                    String.valueOf(col_podar.getText()),
                    String.valueOf(summa.getText()),
                    "",
                    String.valueOf(ves.getText()));

            intent.putExtra(Zakaz_product.class.getCanonicalName(), product);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            if (name.getText().toString().isEmpty()) {
                Toast.makeText(this, "Поле 'Наименование' не заполнено", Toast.LENGTH_SHORT).show();
                sv.smoothScrollTo(0, name.getTop());
                name.requestFocus();
                return;
            }

            if (isimage != 0) {
                final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file());
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file().getName(), reqFile);

                App.getApi().addProduct(body,
                        id,
                        name.getText().toString(),
                        clas.getText().toString().isEmpty() ? "" : clas.getText().toString(),
                        obrazec_spinner.getSelectedItemPosition() == 0 ? "" : "Есть",
                        artikul.getText().toString().isEmpty() ? "" : artikul.getText().toString(),
                        sokr_name.getText().toString().isEmpty() ? "" : sokr_name.getText().toString(),
                        izdatel.getText().toString().isEmpty() ? "" : izdatel.getText().toString(),
                        autor.getText().toString().isEmpty() ? "" : autor.getText().toString(),
                        barcode.getText().toString().isEmpty() ? "" : barcode.getText().toString(),
                        zakup_cena.getText().toString().isEmpty() ? "" : zakup_cena.getText().toString(),
                        prod_cena.getText().toString().isEmpty() ? "" : prod_cena.getText().toString(),
                        standart.getText().toString().isEmpty() ? "" : standart.getText().toString(),
                        ves.getText().toString().isEmpty() ? "" : ves.getText().toString()
                ).enqueue(new Callback<ResultBody>() {
                    @Override
                    public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                        Toast.makeText(Add.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResultBody> call, Throwable t) {
                        Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                App.getApi().addProduct(id,
                        name.getText().toString(),
                        clas.getText().toString().isEmpty() ? "" : clas.getText().toString(),
                        obrazec_spinner.getSelectedItemPosition() == 0 ? "" : "Есть",
                        artikul.getText().toString().isEmpty() ? "" : artikul.getText().toString(),
                        sokr_name.getText().toString().isEmpty() ? "" : sokr_name.getText().toString(),
                        izdatel.getText().toString().isEmpty() ? "" : izdatel.getText().toString(),
                        autor.getText().toString().isEmpty() ? "" : autor.getText().toString(),
                        barcode.getText().toString().isEmpty() ? "" : barcode.getText().toString(),
                        zakup_cena.getText().toString().isEmpty() ? "" : zakup_cena.getText().toString(),
                        prod_cena.getText().toString().isEmpty() ? "" : prod_cena.getText().toString(),
                        standart.getText().toString().isEmpty() ? "" : standart.getText().toString(),
                        ves.getText().toString().isEmpty() ? "" : ves.getText().toString()
                ).enqueue(new Callback<ResultBody>() {
                    @Override
                    public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                        Toast.makeText(Add.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResultBody> call, Throwable t) {
                        Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public File file() {
        String path = "";
        if (isimage == 1) {
            path = outputFileUri.getPath();
        } else if (isimage == 2) {
            path = getPath(outputFileUri);
        }

        File f = new File(getCacheDir(), "buffer.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            barcode.setText(result.getContents());
        }

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case TAKE_PICTURE:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), outputFileUri);
                        bitmap = decodeSampledBitmapFromUri(outputFileUri.getPath(), 480, 320);

                        Glide.with(this).load(bitmap).into(image);
                        isimage = 1;
                    } catch (IOException e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FILE_CHOOSER_RESULT:
                    try {
                        outputFileUri = data.getData();

                        bitmap = decodeSampledBitmapFromUri(getPath(outputFileUri), 480, 320);

                        Glide.with(this).load(bitmap).into(image);
                        isimage = 2;
                    } catch (Exception e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


    }

    public static Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void showFileChooser() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, FILE_CHOOSER_RESULT);
    }

    public void takePhoto() {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory(),
                    "minskkniga.jpg");
            outputFileUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, TAKE_PICTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Выбор изображения")
                        .setCancelable(true)
                        .setPositiveButton("Открыть фото",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        showFileChooser();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Сделать фото",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        takePhoto();
                                        dialog.cancel();
                                    }
                                });

                return builder.create();
            default:
                return null;
        }
    }
}