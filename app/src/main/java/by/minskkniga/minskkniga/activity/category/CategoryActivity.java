package by.minskkniga.minskkniga.activity.category;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.category.adapter.CategoryAdapter;
import by.minskkniga.minskkniga.activity.category.adapter.PodCategoryAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.category.Category;
import by.minskkniga.minskkniga.api.Class.category.PodCat;
import by.minskkniga.minskkniga.api.Class.category.ResponseCategory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {


    ListView lv_cat, lv_podcat;
    CategoryAdapter categoryAdapter;
    PodCategoryAdapter podCategoryAdapter;

    List<Category> list;
    List<PodCat> podCatList;

    String categoryFinal = "";
    String categoryFinal_ID = "";
    String podCategoryFinal = "";
    String podCategoryFinal_ID = "";

    String type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        lv_cat = (ListView) findViewById(R.id.lv_cat);
        lv_podcat = (ListView) findViewById(R.id.lv_podcat);

        lv_cat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickLongCategory(list.get(i).getId(), list.get(i).getName());
                return false;
            }
        });
        lv_cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickCategory(i);
            }
        });
        lv_podcat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickLongPodCategory(podCatList.get(i).getId(), podCatList.get(i).getName());
                return true;
            }
        });
        lv_podcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickPodCategory(i);
                done();
            }
        });

        loadData();

    }

    public void clickPodCategory(int i) {

        try {
            if( i != podCatList.size()-1){
                podCategoryFinal = podCatList.get(i).getName();
                podCategoryFinal_ID = podCatList.get(i).getId();
                podCategoryAdapter.setSelected(i);
                //done();
            }else showDialogAdd(TypeDialogCategory.AddPodcategory, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clickLongPodCategory(String id, String name) {
        showDialogAdd(TypeDialogCategory.EditPodcategory, id, name);

    }

    public void clickCategory(int i) {

        if(i == list.size()-1) {
            showDialogAddCategory();

        }else {
            categoryFinal = list.get(i).getName();
            categoryFinal_ID = list.get(i).getId();
            podCatList = list.get(i).getList();

            PodCat podCat = new PodCat();
            podCat.setId("999");
            podCat.setName("Добавить подкатегорию");

            if(podCatList.size() != 0)
            {
                if(!podCatList.get(podCatList.size()-1).getName().contains(podCat.getName())) podCatList.add(podCat);
            }else podCatList.add(podCat);
            podCategoryAdapter = new PodCategoryAdapter(CategoryActivity.this, podCatList);

            lv_podcat.setAdapter(podCategoryAdapter);

            categoryAdapter.setSelected(i);

            if(podCatList.size() != 1) clickPodCategory(0);
        }

    }

    private void showDialogAddCategory() {

        showDialogAdd(TypeDialogCategory.AddCategory, null, null);

    }

    public void clickLongCategory(String i, String name) {

        showDialogAdd(TypeDialogCategory.EditCategory, i, name);

    }

    private void loadData(){

        list = new ArrayList<>();
        podCatList = new ArrayList<>();

        App.getApi().getCategory(type).enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {
                if(response.body() != null){
                    list = response.body().getCategory();
                    if(response.body().getCategory().get(0).getList()!=null)podCatList = response.body().getCategory().get(0).getList();


                    Category category = new Category();
                    category.setId("9999");
                    category.setName("Добавить категорию");
                    list.add(category);

                    PodCat podCat = new PodCat();
                    podCat.setId("999");
                    podCat.setName("Добавить подкатегорию");
                    podCatList.add(podCat);

                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, list);
                    podCategoryAdapter = new PodCategoryAdapter(CategoryActivity.this, podCatList);

                    lv_cat.setAdapter(categoryAdapter);
                    lv_podcat.setAdapter(podCategoryAdapter);

                    categoryAdapter.setSelected(0);
                    //else categoryAdapter.setSelected(categoryAdapter.getSelectedPosition());
                    podCategoryAdapter.setSelected(0);

                    categoryFinal = list.get(0).getName();
                    categoryFinal_ID = list.get(0).getId();

                    podCategoryFinal = podCatList.get(0).getName();
                    podCategoryFinal_ID = podCatList.get(0).getId();
                }
            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable t) {

            }
        });


    }

    public void done(){
        Intent intent = new Intent();
        intent.putExtra("cat", categoryFinal);
        intent.putExtra("cat_id", categoryFinal_ID);
        intent.putExtra("podcat", podCategoryFinal);
        intent.putExtra("podcat_id", podCategoryFinal_ID);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.apply) {
            done();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogAdd(final TypeDialogCategory type, final String id, String name){

        final Dialog dialogEdit = new Dialog(this);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_add_category);

        final TextView tv_title = (TextView) dialogEdit.findViewById(R.id.add_cat_title);
        TextView tv_save = (TextView) dialogEdit.findViewById(R.id.add_cat_save);
        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.add_cat_cancel);
        final EditText et = (EditText) dialogEdit.findViewById(R.id.add_cat_et);

        if(name != null) et.setText(name);

        switch (type){
            case AddCategory:
                tv_title.setText("Введите название категории");
                break;
            case EditCategory:
                tv_title.setText("Измените название категории");
                break;
            case AddPodcategory:
                tv_title.setText("Введите название подкатегории");
                break;
            case EditPodcategory:
                tv_title.setText("Измените название подкатегории");
                break;
        }



        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case AddCategory:
                        addCatApi(dialogEdit, et.getText().toString());
                        break;
                    case EditCategory:
                        editCatApi(dialogEdit, et.getText().toString(), id);
                        break;
                    case AddPodcategory:
                        addPodCatApi(dialogEdit, et.getText().toString());
                        break;
                    case EditPodcategory:
                        editPodCatApi(dialogEdit, et.getText().toString(), id);
                        break;
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

    private void addCatApi(final Dialog dialog, String text){

        App.getApi().addCategory(type, text).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body() != null) {
                    ResponseBody responseBody = response.body();
                    dialog.dismiss();
                    loadData();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void addPodCatApi(final Dialog dialog, String text){

        App.getApi().addPodCategory(categoryFinal_ID, text).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){
                    ResponseBody responseBody = response.body();
                    dialog.dismiss();
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void editCatApi(final Dialog dialog, String text, String id){

        App.getApi().editCategory(id,text).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){
                    ResponseBody responseBody = response.body();
                    dialog.dismiss();
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void editPodCatApi(final Dialog dialog, String text, String id){

        App.getApi().editPodCategory(id,text).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){
                    ResponseBody responseBody = response.body();
                    dialog.dismiss();
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
