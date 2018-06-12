package by.minskkniga.minskkniga.activity.Kassa.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.SchetOperation;

@SuppressLint("ValidFragment")
public class FragmentDohod extends Fragment implements IFragmentSchetOperation, View.OnClickListener {

    View v;

    TextView tv_date, tv_time, tv_summa;
    ImageView img_left, img_right;

    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
    SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
    Date currentDate = new Date();
    long currentTimeMillis = currentDate.getTime();

    Context context;

    public FragmentDohod(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_dohod, container, false);

        initView();
        setTimeAndDate();
        return v;
    }

    @Override
    public void initView() {

        tv_date = (TextView) v.findViewById(R.id.dohod_date);
        tv_time = (TextView) v.findViewById(R.id.dohod_time);
        img_left = (ImageView) v.findViewById(R.id.dohod_img_left);
        img_right = (ImageView) v.findViewById(R.id.dohod_img_right);

        tv_summa = (TextView) v.findViewById(R.id.dohod_summa);

        img_right.setOnClickListener(this);
        img_left.setOnClickListener(this);

        tv_date.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_summa.setOnClickListener(this);
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
        SchetOperation operation = (SchetOperation) context;
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
        SchetOperation operation = (SchetOperation) context;
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
}
