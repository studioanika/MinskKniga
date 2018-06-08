package by.minskkniga.minskkniga.activity.providers.fragments;

/**
 * Created by root on 13.4.18.
 */

public interface IFragmentProvider {

    void initView();
    void loadData();
    void error(String text);
    void showProgress();
    void hideProgress();

}
