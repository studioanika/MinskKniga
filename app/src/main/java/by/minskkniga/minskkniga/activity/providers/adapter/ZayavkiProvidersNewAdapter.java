package by.minskkniga.minskkniga.activity.providers.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.DescriptionZayavkaActivity;
import by.minskkniga.minskkniga.activity.providers.NewProviderZayavka;
import by.minskkniga.minskkniga.api.Class.providers.ProductForZayackaProvider;
import by.minskkniga.minskkniga.api.Class.providers.ZavInfoTovar;

public class ZayavkiProvidersNewAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ProductForZayackaProvider> list;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public NewProviderZayavka activity;



    public ZayavkiProvidersNewAdapter(List<ProductForZayackaProvider> _list, RecyclerView recyclerView, NewProviderZayavka activity) {
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

            final ProductForZayackaProvider bookI = (ProductForZayackaProvider) list.get(position);
            //final BookI bookI = (BookI) item.getLists().get(0);



            ((StudentViewHolder) holder).fullname.setText(bookI.getProducts().getName());
            ((StudentViewHolder) holder).classs.setText(bookI.getProducts().getClas());
            ((StudentViewHolder) holder).izdatel.setText(bookI.getProducts().getIzdatel());
            ((StudentViewHolder) holder).articul.setText(bookI.getProducts().getArtikul());
            ((StudentViewHolder) holder).sokr.setText(bookI.getProducts().getSokrName());
            ((StudentViewHolder) holder).zayavka.setText(bookI.getZayavka());
            ((StudentViewHolder) holder).zayavka.setFocusable(true);

            ((StudentViewHolder) holder).zayavka.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    activity.setList(position, editable.toString());
                }
            });

            ((StudentViewHolder) holder).lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
        public EditText zayavka;
        LinearLayout lin;

        public StudentViewHolder(View v) {
            super(v);
            fullname = (TextView) v.findViewById(R.id.item_zayavki_booki_fullname);
            classs = (TextView) v.findViewById(R.id.item_zayavki_booki_class);
            izdatel = (TextView) v.findViewById(R.id.item_zayavki_booki_izdatel);
            articul = (TextView) v.findViewById(R.id.item_zayavki_booki_articul);
            sokr = (TextView) v.findViewById(R.id.item_zayavki_booki_sokr);
            zayavka = (EditText) v.findViewById(R.id.item_zayavki_booki_zayavka);
            lin = (LinearLayout) v.findViewById(R.id.lin);


        }
    }
}