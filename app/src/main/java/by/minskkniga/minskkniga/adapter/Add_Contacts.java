package by.minskkniga.minskkniga.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.minskkniga.minskkniga.R;


public class Add_Contacts extends BaseAdapter {

    private Context _context;
    private LayoutInflater lInflater;
    private ArrayList<String> type;
    private ArrayList<String> text;

    public Add_Contacts(Context context, ArrayList<String> type, ArrayList<String> text) {
        this._context = context;
        this.type = type;
        this.text = text;
    }


    @Override
    public int getCount() {
        return type.size();
    }

    @Override
    public Object getItem(int position) {
        return type.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view

        lInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.adapter_add_contacts, parent, false);

        TextView tv1 = view.findViewById(R.id.tv1);
        ImageButton b1 = view.findViewById(R.id.b1);


        switch (type.get(position)) {
            case "tel":
                b1.setImageResource(R.drawable.ic_tel);
                break;
            case "mail":
                b1.setImageResource(R.drawable.ic_email);
                break;
            case "adress":
                b1.setVisibility(View.INVISIBLE);
                break;
            case "site":
                b1.setImageResource(R.drawable.ic_site);
                break;
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type.get(position)) {
                    case "tel":
                        try {
                            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", text.get(position), null));
                            _context.startActivity(tel);
                        } catch (Exception e) {
                            Toast.makeText(_context, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "mail":
                        try {
                            Intent mail = new Intent(Intent.ACTION_SEND);
                            String[] TO = {text.get(position)};
                            mail.setData(Uri.parse("mailto:"));
                            mail.setType("text/plain");
                            mail.putExtra(Intent.EXTRA_EMAIL, TO);
                            mail.putExtra(Intent.EXTRA_SUBJECT, _context.getString(R.string.app_name));
                            _context.startActivity(Intent.createChooser(mail, "Send Email"));
                        } catch (Exception e) {
                            Toast.makeText(_context, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "site":
                        try {
                            Intent site = new Intent(Intent.ACTION_VIEW);
                            site.setData(Uri.parse(text.get(position)));
                            _context.startActivity(site);
                        } catch (Exception e) {
                            Toast.makeText(_context, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        if (type.get(position).equals("site")) {
            if (text.get(position).length() > 6) {
                if (!text.get(position).substring(0, 7).equals("http://")) {
                    text.add(position, "http://" + text.get(position));
                    tv1.setText(text.get(position));
                }
            } else {
                text.add(position, "http://" + text.get(position));
                tv1.setText(text.get(position));
            }
        } else {
            tv1.setText(text.get(position));
        }


        return view;
    }


}