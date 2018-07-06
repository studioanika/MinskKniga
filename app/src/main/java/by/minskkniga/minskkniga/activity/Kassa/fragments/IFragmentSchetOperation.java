package by.minskkniga.minskkniga.activity.Kassa.fragments;

public interface IFragmentSchetOperation {

    void initView();
    void setTimeAndDate();
    void clickNextTime();
    void clickPrevTime();
    void showDateDialog();
    void updateDateTime(int y, int m, int d);
    void showCalculator();
    void setResultTV(String text);

}
