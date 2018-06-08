package by.minskkniga.minskkniga.activity.providers.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.ProvidersListActivity;
import by.minskkniga.minskkniga.activity.providers.adapter.FragmentNewsAdapter;
import by.minskkniga.minskkniga.activity.providers.adapter.FragmentPublishingAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.ProviderNews;
import by.minskkniga.minskkniga.api.Class.providers.ProviderZayavkiIzdatelstva;
import by.minskkniga.minskkniga.api.Class.providers.ProviderZayavkiNews;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 13.4.18.
 */

@SuppressLint("ValidFragment")
public class FragmentNews extends Fragment implements IFragmentProvider {


    View v;
    ProgressBar progress;
    private RecyclerView recyclerView;
    private FragmentNewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private TextView tvResultWeight;

    private List<ProviderNews> studentList;

    ProvidersListActivity activity;

    @SuppressLint("ValidFragment")
    public FragmentNews(ProvidersListActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return v;
    }

    @Override
    public void initView() {
        tvResultWeight = (TextView) v.findViewById(R.id.fragment_news_result_value);
        progress = (ProgressBar) v.findViewById(R.id.progress);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);


        studentList = new ArrayList<ProviderNews>();

        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(v.getContext());

        // use a linear layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new FragmentNewsAdapter(studentList, recyclerView,activity) {
        };

        // set the adapter object to the Recyclerview
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void loadData() {

        showProgress();
        App.getApi().getAutoZAll().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body() != null) setData();
                else error("Ошибка сервера...");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                error("Проверьте подключение к интернету...");
            }
        });

    }

    private void setData(){
        App.getApi().getProvidersNewsZ().enqueue(new Callback<ProviderZayavkiNews>() {
            @Override
            public void onResponse(Call<ProviderZayavkiNews> call, Response<ProviderZayavkiNews> response) {
                hideProgress();
                if(response.body()!=null){
                    ProviderZayavkiNews pr = response.body();
                    List<ProviderNews> list = pr.getMas();
                    if(pr.getItogVes()!=null)tvResultWeight.setText(pr.getItogVes()+" кг");
                    mAdapter = new FragmentNewsAdapter(list,recyclerView,activity);
                    recyclerView.setAdapter(mAdapter);


                }else error("Ошибка сервера...");

            }

            @Override
            public void onFailure(Call<ProviderZayavkiNews> call, Throwable t) {
                hideProgress();
                error("Проверьте подключение к интернету...");
            }
        });
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void error(String text) {
        hideProgress();
        Snackbar snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG);
        snackbar.setDuration(15000);
        snackbar.setAction("Повторить", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

}
