package by.minskkniga.minskkniga.adapter.Dialog;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;
import by.minskkniga.minskkniga.api.Class.Dialog_clients;

public class Main extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<Dialog_clients> _objects;

    public Main(Context context, ArrayList<Dialog_clients> objects) {
        this._context = context;
        this._objects = objects;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_dialog_client, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        ImageView iv1 = view.findViewById(R.id.iv1);

        tv1.setText(_objects.get(position).getName());

        if (_objects.get(position).getPodar().equals("1")) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Glide.with(_context).load(R.drawable.ic_gift).into(iv1);
                } else {
                    Glide.with(_context).load(R.drawable.ic_gift).into(iv1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
