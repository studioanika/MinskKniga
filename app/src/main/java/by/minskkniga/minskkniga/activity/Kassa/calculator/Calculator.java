/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package by.minskkniga.minskkniga.activity.Kassa.calculator;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.Kassa.Main;
import by.minskkniga.minskkniga.activity.Kassa.fragments.FragmentDohod;
import by.minskkniga.minskkniga.activity.Kassa.fragments.FragmentPerevod;
import by.minskkniga.minskkniga.activity.Kassa.fragments.FragmentRashod;
import by.minskkniga.minskkniga.activity.category.CategoryActivity;
import by.minskkniga.minskkniga.activity.category.SchetaListActivity;
import by.minskkniga.minskkniga.activity.prefs.Prefs;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.cassa.ObjectTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class Calculator extends AppCompatActivity
        implements CalculatorEditText.OnTextSizeChangeListener, CalculatorExpressionEvaluator.EvaluateCallback, OnLongClickListener {

    private static final String NAME = Calculator.class.getName();

    // instance state keys
    private static final String KEY_CURRENT_STATE = NAME + "_currentState";
    private static final String KEY_CURRENT_EXPRESSION = NAME + "_currentExpression";

    /**
     * Constant for an invalid resource id.
     */
    public static final int INVALID_RES_ID = -1;


    protected enum CalculatorState {
        INPUT, EVALUATE, RESULT, ERROR
    }
    String results = "0";
    public String money = "0";
    ViewPager viewPager;

    private final TextWatcher mFormulaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            setState(CalculatorState.INPUT);
            mEvaluator.evaluate(editable, Calculator.this);
            if(mCalculatorDisplay != null) {
                mCalculatorDisplay.setText(editable.toString());
            }
        }
    };

    private final OnKeyListener mFormulaOnKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_NUMPAD_ENTER:
                case KeyEvent.KEYCODE_ENTER:
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        onEquals();
                    }
                    // ignore all other actions
                    return true;
            }
            return false;
        }
    };

    private final Editable.Factory mFormulaEditableFactory = new Editable.Factory() {
        @Override
        public Editable newEditable(CharSequence source) {
            final boolean isEdited = mCurrentState == CalculatorState.INPUT
                    || mCurrentState == CalculatorState.ERROR;
            return new CalculatorExpressionBuilder(source, mTokenizer, isEdited);
        }
    };

    private CalculatorState mCurrentState;
    private CalculatorExpressionTokenizer mTokenizer;
    private CalculatorExpressionEvaluator mEvaluator;

    protected RelativeLayout mDisplayView;
    public CalculatorEditText mFormulaEditText;
    public CalculatorEditText mResultEditText;
    private NineOldViewPager mPadViewPager;
    private View mDeleteButton;
    private View mClearButton;
    private View mEqualButton;





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
    String schetid;
    String schet;

    private TextView mCalculatorDisplay, operationTXT;

    LinearLayout calculator;
    public String id = "";

    List<ObjectTransaction> list = new ArrayList<>();
    ArrayList<String> providers = new ArrayList<>();
    ArrayList<String> providersID = new ArrayList<>();

    public int type_perevod;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_port);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Приходы"));
        tabLayout.addTab(tabLayout.newTab().setText("Расходы"));
        tabLayout.addTab(tabLayout.newTab().setText("Переводы"));

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if(intent.hasExtra("id")) {

        }
//
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();

        initViewPager();
//

        calculator = (LinearLayout)findViewById(R.id.operation_calculate);



        tv_done = (TextView)findViewById(R.id.tv_done);
        tv_cancel = (TextView)findViewById(R.id.tv_cancel);

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

        mDisplayView = (RelativeLayout) findViewById(R.id.display);
        mFormulaEditText = (CalculatorEditText) findViewById(R.id.formula);
        mResultEditText = (CalculatorEditText) findViewById(R.id.result);
        mPadViewPager = (NineOldViewPager) findViewById(R.id.pad_pager);
        mDeleteButton = findViewById(R.id.del);
        mClearButton = findViewById(R.id.clr);

        mEqualButton = findViewById(R.id.pad_numeric).findViewById(R.id.eq);
        if (mEqualButton == null || mEqualButton.getVisibility() != View.VISIBLE) {
            mEqualButton = findViewById(R.id.pad_operator).findViewById(R.id.eq);
        }

        mTokenizer = new CalculatorExpressionTokenizer(this);
        mEvaluator = new CalculatorExpressionEvaluator(mTokenizer);

        savedInstanceState = savedInstanceState == null ? Bundle.EMPTY : savedInstanceState;
        setState(CalculatorState.values()[
                savedInstanceState.getInt(KEY_CURRENT_STATE, CalculatorState.INPUT.ordinal())]);
        String keyCurrentExpr = savedInstanceState.getString(KEY_CURRENT_EXPRESSION);
        mFormulaEditText.setText(mTokenizer.getLocalizedExpression(
                keyCurrentExpr == null ? "" : keyCurrentExpr));
        mEvaluator.evaluate(mFormulaEditText.getText(), this);

        mFormulaEditText.setEditableFactory(mFormulaEditableFactory);
        mFormulaEditText.addTextChangedListener(mFormulaTextWatcher);
        mFormulaEditText.setOnKeyListener(mFormulaOnKeyListener);
        mFormulaEditText.setOnTextSizeChangeListener(this);
        mDeleteButton.setOnLongClickListener(this);

        Prefs prefs = new Prefs(Calculator.this);
        //if(prefs.getSessionIdOper() != null && !prefs.getSessionIdOper().isEmpty()) setTab(prefs.getSessionIdOper());

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // If there's an animation in progress, end it immediately to ensure the state is
        // up-to-date before it is serialized.
        cancelAnimation();

        super.onSaveInstanceState(outState);

        outState.putInt(KEY_CURRENT_STATE, mCurrentState.ordinal());
        outState.putString(KEY_CURRENT_EXPRESSION,
                mTokenizer.getNormalizedExpression(mFormulaEditText.getText().toString()));
    }

    protected void setState(CalculatorState state) {
        if (mCurrentState != state) {
            mCurrentState = state;

            if (state == CalculatorState.RESULT || state == CalculatorState.ERROR) {
                mDeleteButton.setVisibility(View.GONE);
                mClearButton.setVisibility(View.VISIBLE);
            } else {
                mDeleteButton.setVisibility(View.VISIBLE);
                mClearButton.setVisibility(View.GONE);
            }

            if (state == CalculatorState.ERROR) {
                final int errorColor = getResources().getColor(R.color.calculator_error_color);
                mFormulaEditText.setTextColor(errorColor);
                mResultEditText.setTextColor(errorColor);
                Utils.setStatusBarColorCompat(getWindow(), errorColor);
            } else {
                mFormulaEditText.setTextColor(
                        getResources().getColor(R.color.display_formula_text_color));
                mResultEditText.setTextColor(
                        getResources().getColor(R.color.display_result_text_color));
                Utils.setStatusBarColorCompat(getWindow(), getResources().getColor(R.color.calculator_accent_color));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(calculator != null && calculator.getVisibility() == View.VISIBLE){
            hideCalculator();
            return;
        }else if (mPadViewPager == null || mPadViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            cancelAnimation();
            mPadViewPager.setCurrentItem(mPadViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        // If there's an animation in progress, end it immediately to ensure the state is
        // up-to-date before the pending user interaction is handled.
        cancelAnimation();
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.eq:
                onEquals();
                break;
            case R.id.del:
                onDelete();
                break;
            case R.id.clr:
                onClear();
                break;
            case R.id.fun_cos:
            case R.id.fun_ln:
            case R.id.fun_log:
            case R.id.fun_sin:
            case R.id.fun_tan:
                // Add left parenthesis after functions.
                mFormulaEditText.append(((Button) view).getText() + "(");
                break;
            default:
                mFormulaEditText.append(((Button) view).getText());
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.del) {
            onClear();
            return true;
        }
        return false;
    }


    @Override
    public void onEvaluate(String expr, String result, int errorResourceId) {

        if (mCurrentState == CalculatorState.INPUT) {
            if(result != null){
                if(!mResultEditText.getText().toString().isEmpty()
                        && mResultEditText != null) {
                    mResultEditText.setText(result);
                    results = result;
                }
                else if(!mFormulaEditText.getText().toString().isEmpty()
                        && mFormulaEditText != null) {
                    results = mFormulaEditText.getText().toString();
                }

                if(mCalculatorDisplay != null) mCalculatorDisplay.setText(results);
            }
        } else if (errorResourceId != INVALID_RES_ID) {
            onError(errorResourceId);
        } else if (!TextUtils.isEmpty(result)) {
            onResult(result);
            if(!mResultEditText.getText().toString().isEmpty()
                    && mResultEditText != null) {
                mResultEditText.setText(result);
                results = result;
            }
            else if(!mFormulaEditText.getText().toString().isEmpty()
                    && mFormulaEditText != null) {
                results = mFormulaEditText.getText().toString();
            }

            if(mCalculatorDisplay != null) mCalculatorDisplay.setText(results);
        } else if (mCurrentState == CalculatorState.EVALUATE) {
            if(!mResultEditText.getText().toString().isEmpty()
                    && mResultEditText != null) {
                mResultEditText.setText(result);
                results = result;
            }
            else if(!mFormulaEditText.getText().toString().isEmpty()
                    && mFormulaEditText != null) {
                results = mFormulaEditText.getText().toString();
            }

            if(mCalculatorDisplay != null) mCalculatorDisplay.setText(results);
            // The current expression cannot be evaluated -> return to the input state.
            setState(CalculatorState.INPUT);
        }

        mFormulaEditText.requestFocus();
    }

    @Override
    public void onTextSizeChanged(final TextView textView, float oldSize) {
        if (mCurrentState != CalculatorState.INPUT) {
            // Only animate text changes that occur from user input.
            return;
        }

        // Calculate the values needed to perform the scale and translation animations,
        // maintaining the same apparent baseline for the displayed text.
        final float textScale = oldSize / textView.getTextSize();
        final float translationX = (1.0f - textScale) *
                (textView.getWidth() / 2.0f - ViewCompat.getPaddingEnd(textView));
        final float translationY = (1.0f - textScale) *
                (textView.getHeight() / 2.0f - textView.getPaddingBottom());

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(textView, "scaleX", textScale, 1.0f),
                ObjectAnimator.ofFloat(textView, "scaleY", textScale, 1.0f),
                ObjectAnimator.ofFloat(textView, "translationX", translationX, 0.0f),
                ObjectAnimator.ofFloat(textView, "translationY", translationY, 0.0f));
        animatorSet.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    private void onEquals() {
        if (mCurrentState == CalculatorState.INPUT) {
            setState(CalculatorState.EVALUATE);
            mEvaluator.evaluate(mFormulaEditText.getText(), this);
        }
    }

    private void onDelete() {
        // Delete works like backspace; remove the last character from the expression.
        final Editable formulaText = mFormulaEditText.getEditableText();
        final int formulaLength = formulaText.length();
        if (formulaLength > 0) {
            formulaText.delete(formulaLength - 1, formulaLength);
        }
    }

    abstract void cancelAnimation();

    abstract void reveal(View sourceView, int colorRes, AnimatorListenerWrapper listener);

    private void onClear() {
        if (TextUtils.isEmpty(mFormulaEditText.getText())) {
            return;
        }

        final View sourceView = mClearButton.getVisibility() == View.VISIBLE
                ? mClearButton : mDeleteButton;
        reveal(sourceView, R.color.calculator_accent_color, new AnimatorListenerWrapper() {
            @Override
            public void onAnimationStart() {
                mFormulaEditText.getEditableText().clear();
            }
        });
    }

    private void onError(final int errorResourceId) {
        if (mCurrentState != CalculatorState.EVALUATE) {
            // Only animate error on evaluate.
            mResultEditText.setText(errorResourceId);
            return;
        }

        reveal(mEqualButton, R.color.calculator_error_color, new AnimatorListenerWrapper() {
            @Override
            public void onAnimationStart() {
                setState(CalculatorState.ERROR);
                mResultEditText.setText(errorResourceId);
            }
        });
    }

    abstract void onResult(final String result);

    public void initViewPager(){
        try {
            viewPager = (ViewPager) findViewById(R.id.pager);
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
        if(mCalculatorDisplay.getText().toString().isEmpty()) mCalculatorDisplay.setText("0");
        if(calculator != null){

            YoYo.with(Techniques.SlideInDown)
                    .duration(1000)
                    .playOn(calculator);
            calculator.setVisibility(View.GONE);
        }
    }

    private void hideCalculator(){

        mEqualButton.performClick();

        try{
            if(!mFormulaEditText.getText().toString().isEmpty()) {
                mCalculatorDisplay.setText(mFormulaEditText.getText().toString());
                tab1.setResultTV(mFormulaEditText.getText().toString());
                tab2.setResultTV(mFormulaEditText.getText().toString());
                tab3.setResultTV(mFormulaEditText.getText().toString());
            }else  if(!results.isEmpty() && results != null) {
                mCalculatorDisplay.setText(results);
                tab1.setResultTV(results);
                tab2.setResultTV(results);
                tab3.setResultTV(results);
            }




        }
        catch (Exception e){

        }
        if(calculator != null){
            //onClear();
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
                    if(id != null && !id.isEmpty()) tab1 = new FragmentDohod(Calculator.this, id);
                    else tab1 = new FragmentDohod(Calculator.this, null);
                    return tab1;
                case 1:
                    if(id != null && !id.isEmpty()) tab2 = new FragmentRashod(Calculator.this, id);
                    else tab2 = new FragmentRashod(Calculator.this, null);
                    return tab2;
                case 2:
                    if(id != null && !id.isEmpty()) tab3 = new FragmentPerevod(Calculator.this, id);
                    else tab3 = new FragmentPerevod(Calculator.this, null);
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
            DatePickerDialog tpd = new DatePickerDialog(Calculator.this, myCallBack, myYear, myMonth, myDay);
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

    public void showCalculator(TextView tv, String value){
        mCalculatorDisplay = tv;
        if(mFormulaEditText != null) {
            if(!value.isEmpty() && !value.equals("0"))mFormulaEditText.setText(value);
            else mFormulaEditText.setText("");
        }
        if(tv != null && calculator != null){
            calculator.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp)
                    .duration(1000)
                    .playOn(calculator);

        }
    }

    public void startCat(String type){

        Intent intent = new Intent(Calculator.this, CategoryActivity.class);
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
                        if(viewPager.getCurrentItem() == 0) tab1.setCategory(cat, cat_id, podcat_id);
                        if(viewPager.getCurrentItem() == 1) tab2.setCategory(cat, cat_id, podcat_id);
                        if(viewPager.getCurrentItem() == 2) tab3.setCategory(cat, cat_id, podcat_id);
                    }else {
                        if(viewPager.getCurrentItem() == 0) tab1.setCategory(cat+">>"+podcat, cat_id, podcat_id);
                        if(viewPager.getCurrentItem() == 1) tab2.setCategory(cat+">>"+podcat, cat_id, podcat_id);
                        if(viewPager.getCurrentItem() == 2) tab3.setCategory(cat+">>"+podcat, cat_id, podcat_id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {

                try {
                    schet = data.getStringExtra("schet");
                    schetid = data.getStringExtra("schetid");

                    if(schet.isEmpty()) return;
                    else {
                        if(viewPager.getCurrentItem() == 0) tab1.setScheta(schetid, schet);
                        if(viewPager.getCurrentItem() == 1) tab2.setScheta(schetid, schet);
                        if(viewPager.getCurrentItem() == 2) tab3.setScheta(schetid, schet, type_perevod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startScheta(String schet_ids){

        Intent intent = new Intent(Calculator.this, SchetaListActivity.class);
        intent.putExtra("id", schet_ids);
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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculator.this,
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
                providersID.clear();
                if(response.body() != null) {
                    ObjectTransaction obj = new ObjectTransaction();
                    obj.setName("Не выбран");
                    obj.setId("-1");

                    list.clear();
                    list.add(obj);
                    list.addAll(response.body());

                    for (ObjectTransaction object: list
                            ) {
                        providers.add(object.getName());
                        providersID.add(object.getId());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculator.this,
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

    public void showMonyDialog(final TextView tv){
        mCalculatorDisplay = tv;
        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_money_calculator);
        final TextView tv_itog = (TextView) dialogEdit.findViewById(R.id.d_money_itog);
        tv_itog.setText("ИТОГО : "+tv.getText().toString());
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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                    updateItog(tv_500, tv_200, tv_100, tv_50, tv_20, tv_10, tv_5, tv_2,tv_itog, tv);

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
                            TextView itog, TextView  tv){

        int sum = 0;
        Double d_sum = 0.00;
        try{

            sum += Integer.parseInt(tv1.getText().toString());
            sum += Integer.parseInt(tv2.getText().toString());
            sum += Integer.parseInt(tv3.getText().toString());
            sum += Integer.parseInt(tv4.getText().toString());
            sum += Integer.parseInt(tv5.getText().toString());
            sum += Integer.parseInt(tv6.getText().toString());
            sum += Integer.parseInt(tv7.getText().toString());
            sum += Integer.parseInt(tv8.getText().toString());
            if(tv != null &&
                    !tv.getText().toString().isEmpty())
                d_sum = sum + Double.parseDouble(tv.getText().toString());
            else d_sum = (double) sum;
            itog.setText("Итог : "+String.valueOf(d_sum));
            if(mCalculatorDisplay != null) {
                mCalculatorDisplay.setText(String.valueOf(d_sum));
            }
            money = String.valueOf(d_sum);
            tab1.tv_summa.setText(money);
            tab2.tv_summa.setText(money);
            tab3.tv_summa.setText(money);

        }catch (Exception e){

            String sd = e.toString();

        }

    }

    public void end() {

        Intent intent = new Intent(Calculator.this, Main.class);
        if(schetid != null) {
            intent.putExtra("id", schetid);
            intent.putExtra("name", schet);
        }

        startActivity(intent);

    }


    public String id_cat_pr, cat_pr, id_podcat_pr;

    public void setCategoryPrihod(String id_cat, String cat, String id_podcat){

      id_cat_pr = id_cat;
      cat_pr = cat;
      id_podcat_pr = id_podcat;

    }

    public String id_cat_r, cat_r, id_podcat_r;

    public void setCategoryRashod(String id_cat, String cat, String id_podcat){

        id_cat_r = id_cat;
        cat_r = cat;
        id_podcat_r = id_podcat;

    }

    public String id_cat_pe, cat_pe, id_podcat_pe;

    public void setCategoryPerevod(String id_cat, String cat, String id_podcat){

        id_cat_pe = id_cat;
        cat_pe = cat;
        id_podcat_pe = id_podcat;

    }
}
