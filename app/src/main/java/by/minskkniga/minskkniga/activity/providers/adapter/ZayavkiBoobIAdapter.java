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
import by.minskkniga.minskkniga.activity.providers.DescriptionZayavkaActivity;
import by.minskkniga.minskkniga.api.Class.providers.ZavInfoTovar;

/**
 * Created by root on 16.4.18.
 */

public class ZayavkiBoobIAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ZavInfoTovar> list;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

   public DescriptionZayavkaActivity activity;


    public ZayavkiBoobIAdapter(List<ZavInfoTovar> _list, RecyclerView recyclerView, DescriptionZayavkaActivity activity) {
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
                R.layout.item_zayavki_booki, parent, false);

        vh = new StudentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof StudentViewHolder) {

            final ZavInfoTovar bookI = (ZavInfoTovar) list.get(position);
            //final BookI bookI = (BookI) item.getLists().get(0);

            ((StudentViewHolder) holder).fullname.setText(bookI.getName());
            ((StudentViewHolder) holder).classs.setText(bookI.getClas());
            ((StudentViewHolder) holder).izdatel.setText(bookI.getIzdatel());
            ((StudentViewHolder) holder).articul.setText(bookI.getArtikul());
            ((StudentViewHolder) holder).sokr.setText(bookI.getSokr_name());
            ((StudentViewHolder) holder).zayavka.setText(bookI.getZayavka());
            ((StudentViewHolder) holder).zayavka.setFocusable(false);

            ((StudentViewHolder) holder).lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                activity.startInfoDescr(bookI.getK_id(), bookI.getZayavka());
                }
            });

            ((StudentViewHolder) holder).lin.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    activity.longClickItem(bookI, ((StudentViewHolder) holder).zayavka, position);
                    return false;
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
        public TextView fullname, classs, izdatel,articul, sokr;
        public TextView zayavka;
        LinearLayout lin;

        public StudentViewHolder(View v) {
            super(v);
            fullname = (TextView) v.findViewById(R.id.item_zayavki_booki_fullname);
            classs = (TextView) v.findViewById(R.id.item_zayavki_booki_class);
            izdatel = (TextView) v.findViewById(R.id.item_zayavki_booki_izdatel);
            articul = (TextView) v.findViewById(R.id.item_zayavki_booki_articul);
            sokr = (TextView) v.findViewById(R.id.item_zayavki_booki_sokr);
            zayavka = (TextView) v.findViewById(R.id.item_zayavki_booki_zayavka);
            lin = (LinearLayout) v.findViewById(R.id.lin);


        }
    }
}
