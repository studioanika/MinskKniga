package by.minskkniga.minskkniga.activity.Spravoch_Couriers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.adapter.Zakazy.ObjectToReturn;

public class AdapterForReturn extends BaseAdapter{

    List<ObjectToReturn> list;
    private LayoutInflater lInflater;
    Context _context;

    public AdapterForReturn(List<ObjectToReturn> list, Context context) {
        this.list = list;
        this._context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _view = lInflater.inflate(R.layout.item_for_return_knigi, viewGroup, false);

        ObjectToReturn  object = list.get(i);

        TextView tv1 = _view.findViewById(R.id.name);
        TextView tv2 = _view.findViewById(R.id.cour);
        TextView tv3 = _view.findViewById(R.id.returns);

        tv1.setText(object.getName());
        tv2.setText(String.valueOf(object.getU_cur()));
        tv3.setText(String.valueOf(object.getReturn_kiniga()));

        return _view;
    }
}
