package by.minskkniga.minskkniga.activity.Kassa.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.calculator.Calculator;
import by.minskkniga.minskkniga.api.App;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class FragmentRashod extends Fragment implements IFragmentSchetOperation, View.OnClickListener {

    View v;

    private static final int REQUEST_CODE = 201;
    private static final int REQUEST_CODE_1 = 202;

    TextView cat_tv, schet_tv, pol_tv;

    TextView tv_date, tv_time, tv_summa;
    ImageView img_left, img_right;

    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
    Date currentDate = new Date();
    long currentTimeMillis = currentDate.getTime();

    Context context;

    String schet_ID ="0";
    String cat_ID = "0";
    String podcat_ID ="0";
    String kontragent = "0";

    Button btn_save;

    EditText et_comment;

    ImageView img_money;

    Calculator operation;

    String id = "";

    public FragmentRashod(Context context,String _id) {
        this.context = context;
        this.id = _id;
        operation = (Calculator) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_dohod, container, false);

        initView();
        setTimeAndDate();

        if(id != null && !id.isEmpty()) getInfoScheet(id);

        return v;
    }

    private void getInfoScheet(String id) {
        // TODO pдесь нужно получить счет по ид
    }


    @Override
    public void initView() {
        tv_summa = (TextView) v.findViewById(R.id.dohod_summa);
        img_money = (ImageView) v.findViewById(R.id.dohod_img_money);
        img_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tv_summa != null)operation.showMonyDialog(tv_summa);
            }
        });
        et_comment = (EditText) v.findViewById(R.id.dohod_note);
        btn_save = (Button) v.findViewById(R.id.r_o_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        cat_tv = (TextView) v.findViewById(R.id.r_o_cat);
        schet_tv = (TextView) v.findViewById(R.id.r_o_chet);
        pol_tv = (TextView) v.findViewById(R.id.r_o_pod_cat);

        tv_date = (TextView) v.findViewById(R.id.dohod_date);
        tv_time = (TextView) v.findViewById(R.id.dohod_time);
        img_left = (ImageView) v.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) v.findViewById(R.id.dohod_img_right);

        img_right.setOnClickListener(this);
        img_left.setOnClickListener(this);

        tv_date.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_summa.setOnClickListener(this);

        cat_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCat();
            }
        });

        schet_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScheta();
            }
        });

        pol_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                operation.showSelectProvider("2");
            }
        });
    }

    @Override
    public void setTimeAndDate() {

        tv_date.setText(df.format(currentDate));
        tv_time.setText(dft.format(currentDate));


    }

    @Override
    public void clickNextTime() {


        currentTimeMillis = currentTimeMillis + 86400000;
        tv_date.setText(df.format(currentTimeMillis));
        tv_time.setText(dft.format(currentTimeMillis));

    }

    @Override
    public void clickPrevTime() {
        currentTimeMillis = currentTimeMillis - 86400000;
        tv_date.setText(df.format(currentTimeMillis));
        tv_time.setText(dft.format(currentTimeMillis));
    }

    @Override
    public void showDateDialog() {

        operation.showDialogSelect(1);
    }

    @Override
    public void updateDateTime(int y, int m, int d) {
        String m_;
        String d_;

        if(m < 10) m_ = String.valueOf("0"+m);
        else m_ = String.valueOf(m);

        if(d < 10) d_ = String.valueOf("0"+d);
        else d_ = String.valueOf(d);

        tv_date.setText(d_+":"+m_+":"+String.valueOf(y));

    }

    @Override
    public void showCalculator() {
        Calculator operation = (Calculator) context;
        operation.showCalculator(tv_summa);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dohod_img_left:
                clickPrevTime();
                break;
            case R.id.dohod_img_right:
                clickNextTime();
                break;
            case R.id.dohod_date:
                showDateDialog();
                break;
            case R.id.dohod_time:
                showDateDialog();
                break;
            case R.id.dohod_summa:
                showCalculator();
                break;
        }
    }

    @Override
    public void onResume() {
        setTimeAndDate();
        super.onResume();
    }

    private void startCat(){

        operation.startCat("2");

    }

    private void startScheta(){


        operation.startScheta();

    }

    public void setCategory(String category, String cat_ID, String podcat_ID){
        if(category != null) cat_tv.setText(category);
        if(cat_ID != null) this.cat_ID = cat_ID;
        if(podcat_ID != null) this.podcat_ID = podcat_ID;
    }

    public void setScheta(String id, String schet){
        if(schet != null) schet_tv.setText(schet);
        if(id != null) schet_ID = id;
    }

    public void setKontragent(String name, String id){
        if(name != null) pol_tv.setText(name);
        if(id != null) kontragent = id;
    }

    private void send(){

        if(cat_ID.isEmpty()) {
            Toast.makeText(getContext(), "Выберите категорию", Toast.LENGTH_SHORT).show();
            return;
        }

        if(podcat_ID.isEmpty()) {
            Toast.makeText(getContext(), "Выберите подкатегорию", Toast.LENGTH_SHORT).show();
            return;
        }

        if(schet_ID.isEmpty()) {
            Toast.makeText(getContext(), "Выберите счет", Toast.LENGTH_SHORT).show();
            return;
        }


        String summ = tv_summa.getText().toString();
        if(summ.isEmpty()) {
            Toast.makeText(getContext(), "Заполните сумму", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = tv_date.getText().toString();

        if(kontragent.isEmpty()){
            Toast.makeText(getContext(), "Выберите контрагента", Toast.LENGTH_SHORT).show();
            return;
        }

        String com = et_comment.getText().toString();

        String type = "2";

        String schet_perevoda = "0";

        App.getApi().addOperationCassa(cat_ID, podcat_ID,schet_ID, summ, date,
                kontragent, com, type, schet_perevoda).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body() != null){

                    Toast.makeText(getContext(), "Операция одобрена", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
