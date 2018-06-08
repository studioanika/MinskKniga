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
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.adapter.FragmentPublishingAdapter;
import by.minskkniga.minskkniga.activity.providers.adapter.ProvidersZayavkiAdapter;
import by.minskkniga.minskkniga.api.App;
import by.minskkniga.minskkniga.api.Class.providers.WhatZakazal;
import by.minskkniga.minskkniga.api.Class.providers.ProvidersZayavkiId;
import by.minskkniga.minskkniga.api.Class.providers.Book;
import by.minskkniga.minskkniga.api.Class.providerzayavka.ProviderZayavkaI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 13.4.18.
 */

@SuppressLint("ValidFragment")
public class FragmentProviderZayavki extends Fragment implements IFragmentProvider {
    View v;
    ProgressBar progress;
    private RecyclerView recyclerView;
    private FragmentPublishingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<Provider> studentList;


    ProvidersZayavkiAdapter listAdapter;
    ExpandableListView expListView;
    List<ProviderZayavkaI> listDataHeader;
    HashMap<String, List<Book>> listDataChild;

    String id;

    @SuppressLint("ValidFragment")
    public FragmentProviderZayavki(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_providers_zayavki, container, false);
        initView();
        return v;
    }

    @Override
    public void initView() {

        progress = (ProgressBar) v.findViewById(R.id.progress);
        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);

    }

    @Override
    public void loadData() {

        showProgress();
        App.getApi().getProvidersZayavki(id).enqueue(new Callback<List<ProvidersZayavkiId>>() {
            @Override
            public void onResponse(Call<List<ProvidersZayavkiId>> call, Response<List<ProvidersZayavkiId>> response) {
                hideProgress();
                if(response.body()!=null){

                    List<ProvidersZayavkiId> list = response.body();
                    setRecycler(list);

                }else error("Ошибка сервера...");

            }

            @Override
            public void onFailure(Call<List<ProvidersZayavkiId>> call, Throwable t) {
                hideProgress();
                error("Проверьте подключение к интернету...");
            }
        });

    }

    private void setRecycler(List<ProvidersZayavkiId> list){


        listDataChild = new HashMap<String, List<Book>>();
        int i = 0;
        for(ProvidersZayavkiId providerZayavkaI : list){

                List<Book> book = new ArrayList<Book>();
                Book book1 = new Book();
                book1.setClasss("-1");
                book.add(0,book1);
                book.addAll(getListBooks(i, list));
                listDataChild.put(String.valueOf(i), book);


                i++;
        }
        int is = listDataChild.size();
        listAdapter = new ProvidersZayavkiAdapter(v.getContext(), list, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private List<Book> getListBooks(int i, List<ProvidersZayavkiId> list) {

        ArrayList<Book> listBook = new ArrayList<>();
        List<WhatZakazal> whatZakazal = list.get(i).getWhatZakazal();

        for(WhatZakazal wz : whatZakazal){
            Book book = new Book();
            book.setClasss(wz.getClas());
            book.setIzdatel(wz.getIzdatel());
            book.setName(wz.getName());
            book.setSokrName(wz.getSokrName());
            book.setZakaz(wz.getZav());
            listBook.add(book);
        }

        return listBook;
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
        Snackbar snackbar = Snackbar.make(v, text, Snackbar.LENGTH_LONG);
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
