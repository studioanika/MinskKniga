package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.XXX;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuAdapterViewHolder>{
    public static final String TAG=MenuAdapter.class.getSimpleName();

    Context context;
    List<XXX> list;

    public MenuAdapter(List<XXX> _list, Context context){ // 0 -active; 1- no active
        this.list=_list;
        this.context=context;
    }

    @Override
    public MenuAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nomenklatura_new_zakaz,parent,false);
        return new MenuAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuAdapterViewHolder holder,final int position){

        final XXX menu=list.get(position);


        holder.name.setText(menu.getName());
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(position).setColvo(s.toString());
            }
        });

        String dsd = "";

    }

    public List<XXX> getNewsList(){
        return this.list;
    }

    @Override
    public int getItemCount(){
        return list==null?0:list.size();
    }

    static class MenuAdapterViewHolder extends RecyclerView.ViewHolder {


        TextView name, tv_action;
        EditText editText;

        public MenuAdapterViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.tv);
            this.editText = (EditText) view.findViewById(R.id.ed);

        }
    }
}
