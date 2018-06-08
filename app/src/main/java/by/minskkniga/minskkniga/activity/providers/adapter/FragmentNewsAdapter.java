package by.minskkniga.minskkniga.activity.providers.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.ProvidersListActivity;
import by.minskkniga.minskkniga.api.Class.providers.ProviderNews;

/**
 * Created by root on 13.4.18.
 */

public class FragmentNewsAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ProviderNews> list;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public ProvidersListActivity activity;

    public FragmentNewsAdapter(List<ProviderNews> _list, RecyclerView recyclerView, ProvidersListActivity activity) {
        list = _list;
        this.activity = activity;
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
                R.layout.item_fragment_news, parent, false);

        vh = new StudentViewHolder(v, activity);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            final ProviderNews item = (ProviderNews) list.get(position);


            ((StudentViewHolder) holder).tvName.setText(item.getName());
            ((StudentViewHolder) holder).tvWeight.setText(item.getVes());

            ((StudentViewHolder) holder).lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startPZLA(item.getId(), item.getName());
                }
            });

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
        public TextView tvName, tvWeight;
        LinearLayout lin;

        public StudentViewHolder(View v, final ProvidersListActivity activity) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.fragment_news_name);
            tvWeight = (TextView) v.findViewById(R.id.fragment_news_weight);
            lin = (LinearLayout) v.findViewById(R.id.lin);
        }
    }
}

