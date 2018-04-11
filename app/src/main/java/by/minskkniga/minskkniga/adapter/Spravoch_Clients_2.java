package by.minskkniga.minskkniga.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.api.Class_Clients;
import by.minskkniga.minskkniga.R;


public class Spravoch_Clients_2 extends RecyclerView.Adapter<Spravoch_Clients_2.ViewHolder>{

    private List<Class_Clients> clie;

    public Spravoch_Clients_2(List<Class_Clients> clie) {
        this.clie = clie;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spravoch_client_2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Class_Clients cli = clie.get(position);


        holder.tv1.setText(cli.getName());
        if (cli.getObraz().equals("0")) {
            holder.ch.setChecked(false);
        } else {
            holder.ch.setChecked(true);
        }

        if (cli.getDolg()>=0) {
            holder.tv2.setTextColor(Color.BLACK);
        } else {
            holder.tv2.setTextColor(Color.RED);
        }
        holder.tv2.setText(cli.getDolg().toString());
    }

    @Override
    public int getItemCount() {
        if (clie == null)
            return 0;
        return clie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        CheckBox ch;
        TextView tv2;

        public ViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            ch = itemView.findViewById(R.id.ch1);
        }
    }
}