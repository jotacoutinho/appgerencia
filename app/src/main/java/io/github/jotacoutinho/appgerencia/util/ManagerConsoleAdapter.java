package io.github.jotacoutinho.appgerencia.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.github.jotacoutinho.appgerencia.R;
import io.github.jotacoutinho.appgerencia.manager.ManagerConsole;

/**
 * Created by Joao Pedro on 12/12/2017.
 */

public class ManagerConsoleAdapter extends BaseAdapter {

    private final List<String> response;
    //private final List<String> action;
    private final Context context;
    private final int index;

    public ManagerConsoleAdapter(List<String> response, Context context, int index) {
        this.response = response;
        //this.action = action;
        this.context = context;
        this.index = index;
    }


    @Override
    public int getCount() {
        return response.size();
    }

    @Override
    public Object getItem(int i) {
        return response.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.listitem_console, viewGroup, false);

        TextView responseText = (TextView) view.findViewById(R.id.response);
        responseText.setText("snmp"  + getItem(i));
        return view;
    }

}
