package io.github.jotacoutinho.appgerencia.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.snmp4j.smi.OID;
import org.w3c.dom.Text;

import io.github.jotacoutinho.appgerencia.MainActivity;
import io.github.jotacoutinho.appgerencia.R;
import io.github.jotacoutinho.appgerencia.manager.Manager;
import io.github.jotacoutinho.appgerencia.mib.MIBTree;
import io.github.jotacoutinho.appgerencia.ui.MIBBrowserActivity;

/**
 * Created by Joao Pedro on 13/12/2017.
 */

public class TableHelper extends AppCompatActivity {

    public MIBTree mib = MainActivity.mib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_helper);

        final int action = (int) getIntent().getExtras().get("action");
        final OID entry = (OID) getIntent().getExtras().get("entry");
        final OID[] target = {new OID()};

        TextView instance1 = (TextView) findViewById(R.id.instance_1);
        TextView instance2 = (TextView) findViewById(R.id.instance_2);
        TextView instance3 = (TextView) findViewById(R.id.instance_3);
        TextView instance4 = (TextView) findViewById(R.id.instance_4);

        if(entry.equals(mib.sysDeviceID) || entry.equals(mib.sysDeviceIP)){
            instance2.setVisibility(View.GONE);
            instance3.setVisibility(View.GONE);
            instance4.setVisibility(View.GONE);
        }

        instance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.equals(mib.sysDeviceID)) {
                    target[0] = mib.sysDeviceID_1;
                } else if (entry.equals(mib.sysDeviceIP)) {
                    target[0] = mib.sysDeviceIP_1;
                } else if (entry.equals(mib.sensZone)) {
                    target[0] = mib.sensZone_1;
                } else if (entry.equals(mib.sensTemperature)) {
                    target[0] = mib.sensTemperature_1;
                } else if (entry.equals(mib.sensHumidity)) {
                    target[0] = mib.sensHumidity_1;
                } else if (entry.equals(mib.sensCamera)){
                    target[0] = mib.sensCamera_1;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("target", target[0]);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        instance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.equals(mib.sensZone)) {
                    target[0] = mib.sensZone_2;
                } else if (entry.equals(mib.sensTemperature)) {
                    target[0] = mib.sensTemperature_2;
                } else if (entry.equals(mib.sensHumidity)) {
                    target[0] = mib.sensHumidity_2;
                } else if (entry.equals(mib.sensCamera)){
                    target[0] = mib.sensCamera_2;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("target", target[0]);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        instance3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.equals(mib.sensZone)) {
                    target[0] = mib.sensZone_3;
                } else if (entry.equals(mib.sensTemperature)) {
                    target[0] = mib.sensTemperature_3;
                } else if (entry.equals(mib.sensHumidity)) {
                    target[0] = mib.sensHumidity_3;
                } else if (entry.equals(mib.sensCamera)){
                    target[0] = mib.sensCamera_3;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("target", target[0]);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        instance4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.equals(mib.sensZone)) {
                    target[0] = mib.sensZone_4;
                } else if (entry.equals(mib.sensTemperature)) {
                    target[0] = mib.sensTemperature_4;
                } else if (entry.equals(mib.sensHumidity)) {
                    target[0] = mib.sensHumidity_4;
                } else if (entry.equals(mib.sensCamera)){
                    target[0] = mib.sensCamera_4;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("target", target[0]);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });



    }
}
