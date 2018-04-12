package by.minskkniga.minskkniga.adapter.Spravoch_Providers;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Providers;

public class Main_2 extends RecyclerView.Adapter<Main_2.ViewHolder> {

        private List<Providers> prov;

        public Main_2(List<Providers> prov) {
            this.prov = prov;
        }

        @Override
        public Main_2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spravoch_provider_2, parent, false);
            return new Main_2.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(Main_2.ViewHolder holder, int position) {
            Providers cli = prov.get(position);

            holder.tv1.setText(cli.getName());

            if (cli.getCreditSize() >= 0) {
                holder.tv2.setTextColor(Color.BLACK);
            } else {
                holder.tv2.setTextColor(Color.RED);
            }
            holder.tv2.setText(cli.getCreditSize().toString());
            holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.ic_chevron_right));
        }

        @Override
        public int getItemCount() {
            if (prov == null)
                return 0;
            return prov.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv1;
            TextView tv2;
            ImageView iv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                iv = itemView.findViewById(R.id.iv);
            }
        }
    }