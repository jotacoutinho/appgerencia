package io.github.jotacoutinho.appgerencia.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.snmp4j.smi.VariableBinding;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.github.jotacoutinho.appgerencia.R;
import io.github.jotacoutinho.appgerencia.util.ManagerConsoleAdapter;

/**
 * Created by Joao Pedro on 12/12/2017.
 */

public class ManagerConsole extends AppCompatActivity {

    public List<String> response = new ArrayList<>();
    //public List<String> action = new ArrayList<>();
    public int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_console);

        //action = (List<String>) getIntent().getExtras().get("action");
        response = (List<String>) getIntent().getExtras().get("response");
        index = (int) getIntent().getExtras().get("index");
        //Toast.makeText(getApplicationContext(), "snmp" + action.get(0)+ " " + response.get(0).toString(), Toast.LENGTH_LONG).show();

        ListView responseList = (ListView) findViewById(R.id.response_list);

        ManagerConsoleAdapter adapter = new ManagerConsoleAdapter(response, this, index);
        responseList.setAdapter(adapter);

    }
}
