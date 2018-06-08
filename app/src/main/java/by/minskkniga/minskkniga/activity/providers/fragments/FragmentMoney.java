package by.minskkniga.minskkniga.activity.providers.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.ProviderZayavkiListActivity;
import by.minskkniga.minskkniga.activity.providers.ProvidersListActivity;
import by.minskkniga.minskkniga.activity.providers.adapter.FragmentNewsAdapter;
import by.minskkniga.minskkniga.activity.providers.adapter.MoneyAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.Money;
import by.minskkniga.minskkniga.api.Class.providers.MoneyTovar;
import by.minskkniga.minskkniga.api.Class.providers.ProviderNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 13.4.18.
 */

@SuppressLint("ValidFragment")
public class FragmentMoney extends Fragment implements IFragmentProvider {

    View v;

    String id;

    TextView tv_tovari, tv_vozvrat, tv_oplacheno, tv_itogo, tv_dolg;
    RecyclerView recyclerView;
    MoneyAdapter adapter;
    private List<MoneyTovar> studentList;
    private LinearLayoutManager mLayoutManager;
    Context activity;


    @SuppressLint("ValidFragment")
    public FragmentMoney(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_money, container, false);
        activity = getContext();
        initView();
        loadData();

        return v;
    }

    @Override
    public void initView() {

        tv_tovari = (TextView) v.findViewById(R.id.fragment_money_tovari);
        tv_vozvrat = (TextView) v.findViewById(R.id.fragment_money_vozvrat);
        tv_oplacheno = (TextView) v.findViewById(R.id.fragment_money_oplacheno);
        tv_itogo = (TextView) v.findViewById(R.id.fragment_money_result);
        tv_dolg = (TextView) v.findViewById(R.id.fragment_money_nam_dolzhni);

    }

    @Override
    public void loadData() {

        App.getApi().getProviderMoney(this.id).enqueue(new Callback<List<Money>>() {
            @Override
            public void onResponse(Call<List<Money>> call, Response<List<Money>> response) {

                List<Money> lis = response.body();
                if(lis != null) {

                    if(lis.get(0).getVisible() == null){
                        tv_tovari.setText(String.valueOf(lis.get(0).getTovari()));
                        tv_vozvrat.setText(String.valueOf(lis.get(0).getVozvrat()));
                        tv_oplacheno.setText(String.valueOf(lis.get(0).getOpl()));
                        tv_itogo.setText(String.valueOf(lis.get(0).getItog()));

                        setRecycler(lis.get(0).getList());
                    }else Toast.makeText(v.getContext(), "У поставщика нет записей в кассе", Toast.LENGTH_SHORT).show();

                }
                else {}

            }

            @Override
            public void onFailure(Call<List<Money>> call, Throwable t) {

            }
        });

    }

    private void setRecycler(List<MoneyTovar> list) {

        recyclerView = (RecyclerView) v.findViewById(R.id.fragment_monry_recycler);


        studentList = list;

        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(v.getContext());

        // use a linear layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        adapter = new MoneyAdapter(studentList, recyclerView,activity) {
        };

        // set the adapter object to the Recyclerview
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void error(String text) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
