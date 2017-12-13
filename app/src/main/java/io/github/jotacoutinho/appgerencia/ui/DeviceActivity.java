package io.github.jotacoutinho.appgerencia.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.github.jotacoutinho.appgerencia.MainActivity;
import io.github.jotacoutinho.appgerencia.R;
import io.github.jotacoutinho.appgerencia.mib.MIBTree;

/**
 * Created by Joao Pedro on 05/12/2017.
 */

public class DeviceActivity extends AppCompatActivity {

    public MIBTree mib = MainActivity.mib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Button zone1 = (Button) this.findViewById(R.id.zone1);
        Button zone2 = (Button) this.findViewById(R.id.zone2);
        Button zone3 = (Button) this.findViewById(R.id.zone3);
        Button zone4= (Button) this.findViewById(R.id.zone4);

        final Toast toast1 = Toast.makeText(getApplicationContext(), "Temperature: " + mib.getValue(mib.sensTemperature_1).getVariable() + "\nHumidity: " + mib.getValue(mib.sensHumidity_1).getVariable(), Toast.LENGTH_LONG);
        final Toast toast2 = Toast.makeText(getApplicationContext(), "Temperature: " + mib.getValue(mib.sensTemperature_2).getVariable() + "\nHumidity: " + mib.getValue(mib.sensHumidity_2).getVariable(), Toast.LENGTH_LONG);
        final Toast toast3 = Toast.makeText(getApplicationContext(), "Temperature: " + mib.getValue(mib.sensTemperature_3).getVariable() + "\nHumidity: " + mib.getValue(mib.sensHumidity_3).getVariable(), Toast.LENGTH_LONG);
        final Toast toast4 = Toast.makeText(getApplicationContext(), "Temperature: " + mib.getValue(mib.sensTemperature_4).getVariable() + "\nHumidity: " + mib.getValue(mib.sensHumidity_4).getVariable(), Toast.LENGTH_LONG);

        zone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast1.show();
            }
        });

        zone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast2.show();
            }
        });

        zone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast3.show();
            }
        });

        zone4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast4.show();
            }
        });
    }
}
