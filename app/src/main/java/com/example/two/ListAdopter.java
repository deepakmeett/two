package com.example.two;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdopter extends ArrayAdapter {
    List list = new ArrayList();

    public ListAdopter(Context context, int resource) {
        super( context, resource );
    }

    public void add(Listmodel listmodel) {
        super.add( listmodel );
        list.add( listmodel );
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get( position );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        Handler contactHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext
                    ().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = layoutInflater.inflate( R.layout.listitem, parent, false );
            contactHolder = new Handler();
            contactHolder.name = (TextView) row.findViewById( R.id.textView7 );
            contactHolder.email = (TextView) row.findViewById( R.id.textView10 );
            contactHolder.password = (TextView) row.findViewById( R.id.textView11 );
            row.setTag( contactHolder );
        } else {
            contactHolder = (Handler) row.getTag();
        }
        Listmodel listmodel = (Listmodel) this.getItem( position );
        contactHolder.name.setText( listmodel.getName() );
        contactHolder.email.setText( listmodel.getEmail() );
        contactHolder.password.setText( listmodel.getPassword() );
        return row;
    }

    public static class Handler {
        TextView name, email, password;
    }
}
