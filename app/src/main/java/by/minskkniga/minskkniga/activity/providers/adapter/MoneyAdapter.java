package by.minskkniga.minskkniga.activity.providers.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.ProviderZayavkiListActivity;
import by.minskkniga.minskkniga.activity.providers.ProvidersListActivity;
import by.minskkniga.minskkniga.api.Class.providers.MoneyTovar;
import by.minskkniga.minskkniga.api.Class.providers.ProviderNews;


public class MoneyAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<MoneyTovar> list;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public ProviderZayavkiListActivity activity;

    public MoneyAdapter(List<MoneyTovar> _list, RecyclerView recyclerView, Context activity) {
        list = _list;
        this.activity = (ProviderZayavkiListActivity) activity;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_fragment_money, parent, false);

        vh = new StudentViewHolder(v, activity);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            final MoneyTovar item = (MoneyTovar) list.get(position);

            ((StudentViewHolder) holder).tvVozvrat.setText(item.getVoz());
            ((StudentViewHolder) holder).tvDate.setText(item.getDate());
            ((StudentViewHolder) holder).tvOplata.setText(item.getOplata());
            ((StudentViewHolder) holder).tvTovar.setText(item.getTovar());


        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTovar, tvDate, tvOplata, tvVozvrat;
        LinearLayout lin;

        public StudentViewHolder(View v, final ProviderZayavkiListActivity activity) {
            super(v);
            tvTovar  = (TextView) v.findViewById(R.id.item_fragment_money_product);
            tvDate  = (TextView) v.findViewById(R.id.item_fragment_money_date);
            tvOplata  = (TextView) v.findViewById(R.id.item_fragment_money_oplaty);
            tvVozvrat  = (TextView) v.findViewById(R.id.item_fragment_money_vozvrat);
        }
    }
}

