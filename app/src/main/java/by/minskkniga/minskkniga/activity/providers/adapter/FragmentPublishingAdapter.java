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
import by.minskkniga.minskkniga.api.Class.providers.ProviderZayavkiIzdatelstva;

/**
 * Created by root on 13.4.18.
 */

public class FragmentPublishingAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ProviderZayavkiIzdatelstva> list;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    ProvidersListActivity activity;

    public FragmentPublishingAdapter(List<ProviderZayavkiIzdatelstva> _list, RecyclerView recyclerView, ProvidersListActivity activity) {
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
                R.layout.item_fragment_publishing, parent, false);

        vh = new StudentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            final ProviderZayavkiIzdatelstva item = (ProviderZayavkiIzdatelstva) list.get(position);



            ((StudentViewHolder) holder).tvName.setText(item.getName());
            ((StudentViewHolder) holder).tvNal.setText(item.getNal());
            ((StudentViewHolder) holder).tvBeznal.setText(item.getBeznal());

            ((StudentViewHolder) holder).lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startPZLA(item.getId(), item.getName(), false);
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
        public TextView tvName, tvNal, tvBeznal;
        LinearLayout lin;

        public StudentViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.fragment_publishing_name);
            tvNal = (TextView) v.findViewById(R.id.fragment_publishing_nal);
            tvBeznal = (TextView) v.findViewById(R.id.fragment_publishing_beznal);
            lin = (LinearLayout) v.findViewById(R.id.lin);

        }
    }
}
