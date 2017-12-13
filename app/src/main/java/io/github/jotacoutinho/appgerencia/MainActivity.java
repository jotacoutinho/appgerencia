package io.github.jotacoutinho.appgerencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.jotacoutinho.appgerencia.agent.Agent;
import io.github.jotacoutinho.appgerencia.manager.Manager;
import io.github.jotacoutinho.appgerencia.manager.ManagerConsole;
import io.github.jotacoutinho.appgerencia.mib.MIBTree;
import io.github.jotacoutinho.appgerencia.ui.DeviceActivity;
import io.github.jotacoutinho.appgerencia.ui.MIBBrowserActivity;

public class MainActivity extends AppCompatActivity {

    public static final int KEY_START_DEVICE_ACTIVITY = 2;
    public Manager manager;
    public static MIBTree mib = new MIBTree();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, Agent.class));
    }

    public void startDeviceActivity(View view) {
        Intent device = new Intent(this, DeviceActivity.class);
        startActivity(device);
    }

    public void startMIBBrowserActivity(View view) {
        Intent browser = new Intent(this, MIBBrowserActivity.class);
        startActivity(browser);
    }

    public void startConsoleActivity(View view) {
        //Intent console = new Intent(this, ManagerConsole.class);
        //startActivity(console);
    }
}
