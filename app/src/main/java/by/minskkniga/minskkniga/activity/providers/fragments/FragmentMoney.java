package by.minskkniga.minskkniga.activity.providers.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.RashodOrder;
import by.minskkniga.minskkniga.activity.providers.adapter.MoneyAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.ItemListProvMoney;
import by.minskkniga.minskkniga.api.Class.providers.MoneyProvResponse;
import by.minskkniga.minskkniga.api.Class.providers.ProvMoneyInfo;
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
    private List<ItemListProvMoney> studentList;
    private LinearLayoutManager mLayoutManager;
    Context activity;

    FloatingActionButton fab;


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

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RashodOrder.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }

    @Override
    public void loadData() {

        App.getApi().getProvD(this.id).enqueue(new Callback<List<MoneyProvResponse>>() {
            @Override
            public void onResponse(Call<List<MoneyProvResponse>> call, Response<List<MoneyProvResponse>> response) {

                if(response.body() != null){
                    if(response.body().get(0).getInfo() != null){

                        ProvMoneyInfo info = response.body().get(0).getInfo();

                        tv_tovari.setText(String.valueOf(info.getTovar()));
                        tv_vozvrat.setText(String.valueOf(info.getVozvrat()));
                        tv_oplacheno.setText(String.valueOf(info.getOplaty()));
                        tv_itogo.setText(String.valueOf(info.getItog()));

                    }

                    if(response.body().get(0).getList() != null) {
                        setRecycler(response.body().get(0).getList());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<MoneyProvResponse>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onResume() {
        if(id != null) loadData();
        super.onResume();
    }

    private void setRecycler(List<ItemListProvMoney> list) {

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
