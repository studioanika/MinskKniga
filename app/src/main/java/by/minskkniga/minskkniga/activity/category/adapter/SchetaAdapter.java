package by.minskkniga.minskkniga.activity.category.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.activity.category.SchetaListActivity;
import by.minskkniga.minskkniga.api.Class.category.Schetum;

public class SchetaAdapter extends BaseAdapter{

    private Context _context;
    private List<Schetum> _objects;
    SchetaListActivity activity;

    public SchetaAdapter(Context context, List<Schetum> objects) {
        this._context = context;
        this._objects = new ArrayList<>();
        this._objects.addAll(objects);
        activity = (SchetaListActivity) _context;
    }

    @Override
    public int getCount() {
        return _objects.size();
    }

    @Override
    public Object getItem(int position) {
        return _objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(_context).inflate(R.layout.item_category, parent, false);

        Schetum category = _objects.get(position);

        RelativeLayout rel = view.findViewById(R.id.item_rel);
        TextView text = view.findViewById(R.id.item_text);
        TextView text1 = view.findViewById(R.id.item_comment);

        text.setText(category.getName());
        if (category.getType() != null) text1.setText(category.getType());
        else text1.setVisibility(View.GONE);

        if(category.isSelected()) rel.setBackgroundColor(_context.getResources().getColor(R.color.darkmy));

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.setSchet(position);
                activity.done();
            }
        });

        rel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                activity.setEditSchet(position);

                return false;
            }
        });

        return view;
    }

    public List<Schetum> getList(){
        return _objects;
    }

    public void setSelected(int position){

        for (Schetum podcat: _objects
                ) {
            podcat.setSelected(false);
        }

        _objects.get(position).setSelected(true);

        notifyDataSetChanged();
    }

}
