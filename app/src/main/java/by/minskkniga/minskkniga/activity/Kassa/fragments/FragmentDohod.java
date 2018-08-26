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
import by.minskkniga.minskkniga.activity.prefs.Prefs;
import by.minskkniga.minskkniga.api.App;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class FragmentDohod extends Fragment implements IFragmentSchetOperation, View.OnClickListener {

    View v;

    private static final int REQUEST_CODE = 201;
    private static final int REQUEST_CODE_1 = 202;

    public TextView cat_tv, schet_tv, pol_tv;

    public TextView tv_date, tv_time, tv_summa;
    ImageView img_left, img_right, img_money;

    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
    SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
    Date currentDate = new Date();
    long currentTimeMillis = currentDate.getTime();

    Context context;

    public String schet_ID ="";
    String cat_ID = "";
    String podcat_ID ="";
    String kontragent = "";

    Button btn_save;

    EditText et_comment;

    Calculator operation;

    String id = "";

    public boolean pause = false;
    public boolean isSchet = false;

    public FragmentDohod(Context context,String _id) {
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
        Prefs prefs = new Prefs(operation);
        update();

        if(!prefs.getSeshet().isEmpty()){
            schet_ID = prefs.getSessionIdSchet();
            schet_tv.setText(prefs.getSeshet());
        }
        return v;
    }


    @Override
    public void initView() {


        tv_summa = (TextView) v.findViewById(R.id.dohod_summa);
        img_money = (ImageView) v.findViewById(R.id.dohod_img_money);
        img_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calculator operation = (Calculator) context;
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
                Calculator operation = (Calculator) context;
                operation.showSelectProvider("1");
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
        Calculator operation = (Calculator) context;
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
        operation.showCalculator(tv_summa, tv_summa.getText().toString());
    }

    @Override
    public void setResultTV(String text) {
        if(tv_summa != null) tv_summa.setText(text);
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
        super.onResume();
        if(!pause) update();
        setTimeAndDate();
    }

    private void update() {
        Calculator calculator = (Calculator) context;
        String text = calculator.mFormulaEditText.getText().toString();
        String text2 = calculator.mResultEditText.getText().toString();

        if(calculator.id_cat_pr != null && !calculator.id_cat_pr.isEmpty()){
            setCategory(calculator.cat_pr, calculator.id_cat_pr, calculator.id_podcat_pr);
        }

        if(!text.isEmpty()){
            try{
                Double.parseDouble(text);
                setResultTV(text);
            }
            catch (Exception e){
                if(!text2.isEmpty()) setResultTV(text2);
                else setResultTV(calculator.money);
            }

        }else {
            if(!text2.isEmpty()) setResultTV(text2);
            else setResultTV(calculator.money);
        }


    }

    @Override
    public void onPause() {
        pause = true;
        super.onPause();
    }

    private void startCat(){

        Calculator schetOperation = (Calculator) context;
        schetOperation.startCat("1");

    }

    private void startScheta(){

        Calculator schetOperation = (Calculator) context;
        schetOperation.startScheta(schet_ID);

    }

    public void setCategory(String category, String cat_ID, String podcat_ID){
        if(category != null) cat_tv.setText(category);
        if(cat_ID != null) this.cat_ID = cat_ID;
        if(podcat_ID != null) this.podcat_ID = podcat_ID;

        Calculator schetOperation = (Calculator) context;
        schetOperation.setCategoryPrihod(cat_ID, category, podcat_ID);
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
        if(summ.isEmpty() || summ.equals("0")) {
            Toast.makeText(getContext(), "Заполните сумму", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = tv_date.getText().toString();

//        if(kontragent.isEmpty()){
//            Toast.makeText(getContext(), "Выберите контрагента", Toast.LENGTH_SHORT).show();
//            return;
//        }

        String com = et_comment.getText().toString();

        String type = "1";

        String schet_perevoda = "0";

        btn_save.setEnabled(false);
        App.getApi().addOperationCassa(cat_ID, podcat_ID,schet_ID, summ, date,
                kontragent, com, type, schet_perevoda).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body() != null){
                    btn_save.setEnabled(true);
                    Toast.makeText(getContext(), "Операция одобрена", Toast.LENGTH_SHORT).show();
                    Calculator calculator = (Calculator) context;
                    calculator.end();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                btn_save.setEnabled(true);
            }
        });
    }


}
