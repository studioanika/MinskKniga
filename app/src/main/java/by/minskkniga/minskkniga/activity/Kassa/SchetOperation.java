package by.minskkniga.minskkniga.activity.Kassa;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.calculator.RevealColorView;
import by.minskkniga.minskkniga.activity.Kassa.fragments.FragmentDohod;
import by.minskkniga.minskkniga.activity.Kassa.fragments.FragmentPerevod;
import by.minskkniga.minskkniga.activity.Kassa.fragments.FragmentRashod;
import by.minskkniga.minskkniga.activity.category.CategoryActivity;
import by.minskkniga.minskkniga.activity.category.SchetaListActivity;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.cassa.ObjectTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchetOperation extends AppCompatActivity implements View.OnClickListener {
    private Animator mCurrentAnimator;
    private RevealColorView revealColorView;


    TabLayout tabLayout;
    FloatingActionButton fab;

    private static final int REQUEST_CODE = 201;
    private static final int REQUEST_CODE_1 = 202;

    int DIALOG_DATE = 1;
    int myYear = 2018;
    int myMonth = 06;
    int myDay = 31;

    FragmentDohod tab1;
    FragmentRashod tab2;
    FragmentPerevod tab3;

    ImageView img_clear;
    TextView tv_cancel, tv_done;

    private TextView mCalculatorDisplay, operationTXT;

    LinearLayout calculator;


    List<ObjectTransaction> list = new ArrayList<>();
    ArrayList<String> providers = new ArrayList<>();
    ArrayList<String> providersID = new ArrayList<>();

    public int type_perevod;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schet_operation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Приходы"));
        tabLayout.addTab(tabLayout.newTab().setText("Расходы"));
        tabLayout.addTab(tabLayout.newTab().setText("Переводы"));

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();

        initViewPager();
        img_clear = (ImageView) findViewById(R.id.imgClear);

        calculator = (LinearLayout)findViewById(R.id.operation_calculate);


        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCalculatorDisplay!=null) {
                    try{
                        if(mCalculatorDisplay.getText().toString().length() != 0){
                            mCalculatorDisplay.setText(mCalculatorDisplay.getText().toString().substring(0,mCalculatorDisplay.getText().toString().length()-1));
                            operationTXT.setText(operationTXT.getText().toString().substring(0,operationTXT.getText().toString().length()-1));

                        }

                    }
                    catch (Exception e){

                    }
                }
            }
        });

        tv_done = (TextView)findViewById(R.id.operation_done);
        tv_cancel = (TextView)findViewById(R.id.operation_cancel);

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideCalculator();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetCalculator();
            }
        });

    }

    public void initViewPager(){
        try {
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter adapter = new PagerAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetCalculator(){
        mCalculatorDisplay.setText("0");
        if(calculator != null){

            YoYo.with(Techniques.SlideInDown)
                    .duration(1000)
                    .playOn(calculator);
            calculator.setVisibility(View.GONE);
        }
    }

    private void hideCalculator(){

        if(calculator != null){

            YoYo.with(Techniques.SlideInDown)
                    .duration(1000)
                    .playOn(calculator);
            calculator.setVisibility(View.GONE);
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        } return super.onKeyDown(keyCode, event);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    tab1 = new FragmentDohod(SchetOperation.this, null);
                    return tab1;
                case 1:
                    tab2 = new FragmentRashod(SchetOperation.this, null);
                    return tab2;
                case 2:
                    tab3 = new FragmentPerevod(SchetOperation.this, null);
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_DATE){
            DatePickerDialog tpd = new DatePickerDialog(SchetOperation.this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            tab1.updateDateTime(year, month+1, day);
        }
    };

    public void showDialogSelect(int type){
        showDialog(DIALOG_DATE);
    }

    @Override
    public void onClick(View v) {


    }


    public void showCalculator(TextView tv){
        mCalculatorDisplay = tv;

        if(tv != null && calculator != null){
            calculator.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp)
                    .duration(1000)
                    .playOn(calculator);

        }
    }

    public void startCat(String type){

        Intent intent = new Intent(SchetOperation.this, CategoryActivity.class);
        intent.putExtra("type", type);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {


            if (resultCode == RESULT_OK) {

                String cat = data.getStringExtra("cat");
                String cat_id = data.getStringExtra("cat_id");
                String podcat = data.getStringExtra("podcat");
                String podcat_id = data.getStringExtra("podcat_id");

                if(cat.isEmpty()) return;
                try {
                    if(podcat.isEmpty()) {
                        tab1.setCategory(cat, cat_id, podcat_id);
                        tab2.setCategory(cat, cat_id, podcat_id);
                        tab3.setCategory(cat, cat_id, podcat_id);
                    }else {
                        tab1.setCategory(cat+">>"+podcat, cat_id, podcat_id);
                        tab2.setCategory(cat+">>"+podcat, cat_id, podcat_id);
                        tab3.setCategory(cat+">>"+podcat, cat_id, podcat_id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {

                try {
                    String schet = data.getStringExtra("schet");
                    String schetid = data.getStringExtra("schetid");

                    if(schet.isEmpty()) return;
                    else {
                        tab1.setScheta(schetid, schet);
                        tab2.setScheta(schetid, schet);
                        tab3.setScheta(schetid, schet, type_perevod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startScheta(){

        Intent intent = new Intent(SchetOperation.this, SchetaListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_1);

    }

    public void showSelectProvider(final String type){
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_select_provider);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.cansel_dlg);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ProgressBar progressBar = (ProgressBar) dialogEdit.findViewById(R.id.progressBar);
        final ListView lv = (ListView) dialogEdit.findViewById(R.id.recycler);

        SearchView search = (SearchView) dialogEdit.findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                providers.clear();
                providersID.clear();
                for (ObjectTransaction obj: list
                        ) {
                    if(obj.getName().toLowerCase().contains(s.toLowerCase()) || obj.getName().contains(s.toUpperCase()) || obj.getName().contains(s))
                    {
                        providers.add(obj.getName());
                        providersID.add(obj.getId());
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SchetOperation.this,
                        android.R.layout.simple_list_item_1, providers);
                lv.setAdapter(adapter);

                return false;
            }
        });



        App.getApi().getKontragent(type).enqueue(new Callback<List<ObjectTransaction>>() {
            @Override
            public void onResponse(Call<List<ObjectTransaction>> call, Response<List<ObjectTransaction>> response) {
                progressBar.setVisibility(View.GONE);

                providers.clear();
                if(response.body() != null) {
                    list = response.body();
                    for (ObjectTransaction object: response.body()
                            ) {
                        providers.add(object.getName());
                        providersID.add(object.getId());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SchetOperation.this,
                        android.R.layout.simple_list_item_1, providers);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ObjectTransaction>> call, Throwable t) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list != null){
                    //ObjectTransaction objectTransaction = (ObjectTransaction) lv.getAdapter().getItem(i);
                    try{


                        if(type.contains("1")) tab1.setKontragent(providers.get(i), providersID.get(i));
                        if(type.contains("2")) tab2.setKontragent(providers.get(i), providersID.get(i));
                        if(type.contains("3")) tab3.setKontragent(providers.get(i), providersID.get(i));
                        dialogEdit.dismiss();
                    }
                    catch (Exception e){}
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);
    }

    public void showMonyDialog(TextView tv){
        mCalculatorDisplay = tv;
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_money_calculator);
        final TextView tv_itog = (TextView) dialogEdit.findViewById(R.id.d_money_itog);
        final TextView tv_done = (TextView) dialogEdit.findViewById(R.id.d_money_done);
        final TextView tv_2 = (TextView) dialogEdit.findViewById(R.id.d_money_2_result);
        final TextView tv_5 = (TextView) dialogEdit.findViewById(R.id.d_money_5_result);
        final TextView tv_10 = (TextView) dialogEdit.findViewById(R.id.d_money_10_result);
        final TextView tv_20 = (TextView) dialogEdit.findViewById(R.id.d_money_20_result);
        final TextView tv_50 = (TextView) dialogEdit.findViewById(R.id.d_money_50_result);
        final TextView tv_100 = (TextView) dialogEdit.findViewById(R.id.d_money_100_result);
        final TextView tv_200 = (TextView) dialogEdit.findViewById(R.id.d_money_200_result);;
        final EditText et_500 = (EditText) dialogEdit.findViewById(R.id.d_money_500_et);
        final TextView tv_500 = (TextView) dialogEdit.findViewById(R.id.d_money_500_result);
        et_500.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_500.getText().toString()) * 500;
                    tv_500.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_200 = (EditText) dialogEdit.findViewById(R.id.d_money_200_et);

        et_200.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_200.getText().toString()) * 200;
                    tv_200.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_100 = (EditText) dialogEdit.findViewById(R.id.d_money_100_et);

        et_100.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_100.getText().toString()) * 100;
                    tv_100.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_50 = (EditText) dialogEdit.findViewById(R.id.d_money_50_et);

        et_50.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_50.getText().toString()) * 50;
                    tv_50.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_20 = (EditText) dialogEdit.findViewById(R.id.d_money_20_et);

        et_20.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_20.getText().toString()) * 20;
                    tv_20.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_10 = (EditText) dialogEdit.findViewById(R.id.d_money_10_et);

        et_10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_10.getText().toString()) * 10;
                    tv_10.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_5 = (EditText) dialogEdit.findViewById(R.id.d_money_5_et);

        et_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_5.getText().toString()) * 5;
                    tv_5.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });

        final EditText et_2 = (EditText) dialogEdit.findViewById(R.id.d_money_2_et);

        et_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    int res = Integer.parseInt(et_2.getText().toString()) * 2;
                    tv_2.setText(String.valueOf(res));
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog);

                }catch (Exception e){

                }
            }
        });


        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(mCalculatorDisplay != null) mCalculatorDisplay.setText("0");
                dialogEdit.dismiss();

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //dialogEdit.setCancelable(false);
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    private void updateItog(TextView tv1,TextView tv2,TextView tv3,TextView tv4,
                            TextView tv5, TextView tv6, TextView tv7, TextView tv8,
                            TextView itog){

        int sum = 0;

        try{

            sum += Integer.parseInt(tv1.getText().toString());
            sum += Integer.parseInt(tv2.getText().toString());
            sum += Integer.parseInt(tv3.getText().toString());
            sum += Integer.parseInt(tv4.getText().toString());
            sum += Integer.parseInt(tv5.getText().toString());
            sum += Integer.parseInt(tv6.getText().toString());
            sum += Integer.parseInt(tv7.getText().toString());
            sum += Integer.parseInt(tv8.getText().toString());

            itog.setText("Итог : "+String.valueOf(sum));
            if(mCalculatorDisplay != null) mCalculatorDisplay.setText(String.valueOf(sum));

        }catch (Exception e){

            String sd = e.toString();

        }

    }

}
