package by.minskkniga.minskkniga.activity.providers.adapter;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.providers.NewProviderZayavka;
import by.minskkniga.minskkniga.api.Class.providers.ProductForZayackaProvider;

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

            ((StudentViewHolder) holder).fullname.setText(bookI.getProducts().getName());
            ((StudentViewHolder) holder).classs.setText(bookI.getProducts().getClas());
            ((StudentViewHolder) holder).izdatel.setText(bookI.getProducts().getIzdatel());
            ((StudentViewHolder) holder).articul.setText(bookI.getProducts().getArtikul());
            ((StudentViewHolder) holder).sokr.setText(bookI.getProducts().getSokrName());
            ((StudentViewHolder) holder).zayavka.setText(bookI.getZayavka());
            ((StudentViewHolder) holder).lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogEditMony(((StudentViewHolder) holder).zayavka, position);
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

    public void showDialogEditMony(final TextView tv, final int position){

        final Dialog dialogEdit = new Dialog(activity);
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_edit_money);

        TextView tv_cancel = (TextView) dialogEdit.findViewById(R.id.editmoney_cancel);
        TextView tv_done = (TextView) dialogEdit.findViewById(R.id.editmoney_done);

        final EditText et = (EditText) dialogEdit.findViewById(R.id.editmoney_et);

        et.setText(tv.getText().toString());

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(et.getText().toString());
                dialogEdit.dismiss();
                activity.setList(position,
                        et.getText().toString());
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEdit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }
}