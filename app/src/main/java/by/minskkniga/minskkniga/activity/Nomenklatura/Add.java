package by.minskkniga.minskkniga.activity.Nomenklatura;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.lang.reflect.Method;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.ResultBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add extends AppCompatActivity {

    ImageButton back;
    Button nomen_barcode_but, cancel, ok;
    EditText nomen_barcode;
    EditText nomen_artikyl;
    IntentIntegrator qrScan;
    ImageView imageView;
    Bitmap bitmap;

    private final int TAKE_PICTURE = 1;
    private final int FILE_CHOOSER_RESULT = 2;
    private Uri outputFileUri;

    int image = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nomenklatura);

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

        nomen_barcode_but = findViewById(R.id.nomen_barcode_but);
        nomen_barcode = findViewById(R.id.nomen_barcode);
        qrScan = new IntentIntegrator(this);
        nomen_barcode_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        nomen_artikyl = findViewById(R.id.nomen_artikyl);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
                final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file());
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file().getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "text");

                App.getApi().addNomenclatura(body, name).enqueue(new Callback<ResultBody>() {
                    @Override
                    public void onResponse(Call<ResultBody> call, Response<ResultBody> response) {
                        Toast.makeText(Add.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResultBody> call, Throwable t) {
                        Toast.makeText(Add.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(Add.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Add.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            }, 199);
        }
    }

    public File file() {
        String path = "";
        if (image == 1) {
            path = outputFileUri.getPath();
        } else if (image == 2) {
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

//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if (result != null) {
//            nomen_barcode.setText(result.getContents());
//        }
//

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case TAKE_PICTURE:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), outputFileUri);
                        bitmap = decodeSampledBitmapFromUri(outputFileUri.getPath(), 480, 320);
                        imageView.setImageBitmap(bitmap);
                        image=1;
                    } catch (IOException e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FILE_CHOOSER_RESULT:
                    try {
                        outputFileUri = data.getData();

                        bitmap = decodeSampledBitmapFromUri(getPath(outputFileUri), 480, 320);
                        imageView.setImageBitmap(bitmap);
                        image=2;
                        Toast.makeText(this, getPath(outputFileUri), Toast.LENGTH_SHORT).show();
//                        outputFileUri = data.getData();
//                        InputStream imageStream = getContentResolver().openInputStream(outputFileUri);
//
//                        bitmap = BitmapFactory.decodeStream(imageStream);
//                        bitmap = decodeSampledBitmapFromUri(outputFileUri, 480, 320);
//                        imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(this, "Ошибка получения пути к файлу, возможно данный файл находиться в облаке", Toast.LENGTH_SHORT).show();
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