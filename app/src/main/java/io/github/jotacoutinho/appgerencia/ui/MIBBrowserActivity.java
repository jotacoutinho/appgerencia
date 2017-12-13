package io.github.jotacoutinho.appgerencia.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import java.io.Serializable;

import io.github.jotacoutinho.appgerencia.MainActivity;
import io.github.jotacoutinho.appgerencia.R;
import io.github.jotacoutinho.appgerencia.agent.Agent;
import io.github.jotacoutinho.appgerencia.manager.Manager;
import io.github.jotacoutinho.appgerencia.manager.ManagerConsole;
import io.github.jotacoutinho.appgerencia.mib.MIBTree;
import io.github.jotacoutinho.appgerencia.util.SetHelper;
import io.github.jotacoutinho.appgerencia.util.TableDialog;
import io.github.jotacoutinho.appgerencia.util.TableHelper;

/**
 * Created by Joao Pedro on 07/12/2017.
 */

public class MIBBrowserActivity extends AppCompatActivity {

    private Manager manager;
    public MIBTree mib = MainActivity.mib;
    private boolean tableSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mib_tree_list);

        LinearLayout system = (LinearLayout) findViewById(R.id.system);
        final LinearLayout sysError = (LinearLayout) findViewById(R.id.sysError);
        final LinearLayout sysDevicesTable = (LinearLayout) findViewById(R.id.sysDevicesTable);
        final LinearLayout sysDevicesEntry = (LinearLayout) findViewById(R.id.sysDevicesEntry);
        final LinearLayout sysDevicesID = (LinearLayout) findViewById(R.id.sysDevicesID);
        final LinearLayout sysDevicesIP = (LinearLayout) findViewById(R.id.sysDevicesIP);
        final LinearLayout sysNetworkStatus = (LinearLayout) findViewById(R.id.sysNetworkStatus);
        final LinearLayout sysBluetoothStatus = (LinearLayout) findViewById(R.id.sysBluetoothStatus);
        LinearLayout sensor = (LinearLayout) findViewById(R.id.sensor);
        final LinearLayout sensZone = (LinearLayout) findViewById(R.id.sensZone);
        final LinearLayout sensZonesTable = (LinearLayout) findViewById(R.id.sensZonesTable);
        final LinearLayout sensZonesEntry = (LinearLayout) findViewById(R.id.sensZonesEntry);
        final LinearLayout sensTemperature = (LinearLayout) findViewById(R.id.sensTemperature);
        final LinearLayout sensHumidity = (LinearLayout) findViewById(R.id.sensHumidity);
        final LinearLayout sensCamera = (LinearLayout) findViewById(R.id.sensCamera);

        manager = new Manager("127.0.0.1", "12345", getApplicationContext());

        /** Views Setup **/
        sysError.setVisibility(View.GONE);
        sysDevicesTable.setVisibility(View.GONE);
        sysDevicesEntry.setVisibility(View.GONE);
        sysDevicesID.setVisibility(View.GONE);
        sysDevicesIP.setVisibility(View.GONE);
        sysNetworkStatus.setVisibility(View.GONE);
        sysBluetoothStatus.setVisibility(View.GONE);
        sensZone.setVisibility(View.GONE);
        sensZonesTable.setVisibility(View.GONE);
        sensZonesEntry.setVisibility(View.GONE);
        sensTemperature.setVisibility(View.GONE);
        sensHumidity.setVisibility(View.GONE);
        sensCamera.setVisibility(View.GONE);

        final Handler actionHandler = new Handler();
        //manager.sendGetRequest(mib.sensCamera);
        final int[] offset = new int[1];
        final NumberPicker numberPicker = new NumberPicker(MIBBrowserActivity.this);
        numberPicker.setMaxValue(4);
        numberPicker.setMinValue(1);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                offset[0] = numberPicker.getValue();
                Log.i("AppGerencia", "Click: " + offset[0]);
            }
        });
//        AlertDialog.Builder builder = new AlertDialog.Builder(MIBBrowserActivity.this);
//        builder.setTitle("Select instance:");
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//                tableSelect = true;
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User cancelled the dialog
//                tableSelect = false;
//            }
//        });
//        builder.setView(numberPicker);
//        final AlertDialog dialog = builder.create();


        /** Menus **/
        sysError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sysError);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                manager.sendGetRequest(mib.sysError);
                                break;
                            case 2:
                                //Log.i("AppGerencia", "getnext triggered");
                                manager.sendGetNextRequest(mib.sysError);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        sysDevicesID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sysDevicesID);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                        switch (item.getItemId()){
                            case 1:
                                intent.putExtra("entry", mib.sysDeviceID);
                                intent.putExtra("action", 1);
                                startActivityForResult(intent, 1);
                                break;
                            case 2:
                                intent.putExtra("entry", mib.sysDeviceID);
                                intent.putExtra("action", 2);
                                startActivityForResult(intent, 2);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sysDevicesIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sysDevicesIP);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                        switch (item.getItemId()){
                            case 1:
                                intent.putExtra("entry", mib.sysDeviceIP);
                                intent.putExtra("action", 1);
                                startActivityForResult(intent, 1);
                                break;
                            case 2:
                                intent.putExtra("entry", mib.sysDeviceIP);
                                intent.putExtra("action", 2);
                                startActivityForResult(intent, 2);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sysNetworkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sysNetworkStatus);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                manager.sendGetRequest(mib.sysNetworkStatus);
                                break;
                            case 2:
                                manager.sendGetNextRequest(mib.sysNetworkStatus);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sysBluetoothStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sysBluetoothStatus);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                manager.sendGetRequest(mib.sysBluetoothStatus);
                                break;
                            case 2:
                                manager.sendGetNextRequest(mib.sysBluetoothStatus);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sensZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sensZone);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                        switch (item.getItemId()){
                            case 1:
                                intent.putExtra("entry", mib.sensZone);
                                intent.putExtra("action", 1);
                                startActivityForResult(intent, 1);
                                break;
                            case 2:
                                intent.putExtra("entry", mib.sensZone);
                                intent.putExtra("action", 2);
                                startActivityForResult(intent, 2);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sensTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sensTemperature);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.getMenu().add(1,3,1,"Set");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                        switch (item.getItemId()){
                            case 1:
                                intent.putExtra("entry", mib.sensTemperature);
                                intent.putExtra("action", 1);
                                startActivityForResult(intent, 1);
                                break;
                            case 2:
                                intent.putExtra("entry", mib.sensTemperature);
                                intent.putExtra("action", 2);
                                startActivityForResult(intent, 2);
                                break;
                            case 3:
                                intent.putExtra("entry", mib.sensTemperature);
                                intent.putExtra("action", 3);
                                startActivityForResult(intent, 3);
                                //manager.sendSetRequest(mib.sensTemperature);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sensHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sensHumidity);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.getMenu().add(1,3,1,"Set");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                        switch (item.getItemId()){
                            case 1:
                                intent.putExtra("entry", mib.sensHumidity);
                                intent.putExtra("action", 1);
                                startActivityForResult(intent, 1);
                                break;
                            case 2:
                                intent.putExtra("entry", mib.sensHumidity);
                                intent.putExtra("action", 2);
                                startActivityForResult(intent, 2);
                                break;
                            case 3:
                                intent.putExtra("entry", mib.sensHumidity);
                                intent.putExtra("action", 3);
                                startActivityForResult(intent, 3);
                                //manager.sendSetRequest(mib.sensHumidity);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        sensCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MIBBrowserActivity.this, sensCamera);
                popup.getMenu().add(1,1,1,"Get");
                popup.getMenu().add(1,2,1,"GetNext");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                        switch (item.getItemId()){
                            case 1:
                                //Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                                intent.putExtra("entry", mib.sensCamera);
                                intent.putExtra("action", 1);
                                startActivityForResult(intent, 1);
                                break;
                            case 2:
                                //Intent intent =  new Intent(MIBBrowserActivity.this, TableHelper.class);
                                intent.putExtra("entry", mib.sensCamera);
                                intent.putExtra("action", 2);
                                startActivityForResult(intent, 2);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        /** MIB Tree "Expandable List" Control **/

        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sysError.getVisibility() == View.GONE){
                    sysError.setVisibility(View.VISIBLE);
                    sysDevicesTable.setVisibility(View.VISIBLE);
                    sysNetworkStatus.setVisibility(View.VISIBLE);
                    sysBluetoothStatus.setVisibility(View.VISIBLE);
                } else{
                    sysError.setVisibility(View.GONE);
                    sysDevicesTable.setVisibility(View.GONE);
                    sysNetworkStatus.setVisibility(View.GONE);
                    sysBluetoothStatus.setVisibility(View.GONE);
                    sysDevicesEntry.setVisibility(View.GONE);
                    sysDevicesID.setVisibility(View.GONE);
                    sysDevicesIP.setVisibility(View.GONE);
                }

            }
        });

        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sensZonesTable.getVisibility() == View.GONE){
                    sensZonesTable.setVisibility(View.VISIBLE);
                } else{
                    sensZone.setVisibility(View.GONE);
                    sensZonesTable.setVisibility(View.GONE);
                    sensZonesEntry.setVisibility(View.GONE);
                    sensTemperature.setVisibility(View.GONE);
                    sensHumidity.setVisibility(View.GONE);
                    sensCamera.setVisibility(View.GONE);
                }
            }
        });

        sysDevicesTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sysDevicesEntry.getVisibility() == View.GONE){
                    sysDevicesEntry.setVisibility(View.VISIBLE);
                } else{
                    sysDevicesEntry.setVisibility(View.GONE);
                    sysDevicesID.setVisibility(View.GONE);
                    sysDevicesIP.setVisibility(View.GONE);
                }
            }
        });

        sysDevicesEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sysDevicesID.getVisibility() == View.GONE){
                    sysDevicesID.setVisibility(View.VISIBLE);
                    sysDevicesIP.setVisibility(View.VISIBLE);
                } else{
                    sysDevicesID.setVisibility(View.GONE);
                    sysDevicesIP.setVisibility(View.GONE);
                }
            }
        });

        sensZonesTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sensZonesEntry.getVisibility() == View.GONE){
                    sensZonesEntry.setVisibility(View.VISIBLE);
                } else{
                    sensZonesEntry.setVisibility(View.GONE);
                    sensZone.setVisibility(View.GONE);
                    sensTemperature.setVisibility(View.GONE);
                    sensHumidity.setVisibility(View.GONE);
                    sensCamera.setVisibility(View.GONE);
                }
            }
        });

        sensZonesEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sensZone.getVisibility() == View.GONE){
                    sensZone.setVisibility(View.VISIBLE);
                    sensTemperature.setVisibility(View.VISIBLE);
                    sensHumidity.setVisibility(View.VISIBLE);
                    sensCamera.setVisibility(View.VISIBLE);
                } else{
                    sensZone.setVisibility(View.GONE);
                    sensTemperature.setVisibility(View.GONE);
                    sensHumidity.setVisibility(View.GONE);
                    sensCamera.setVisibility(View.GONE);
                }
            }
        });

        /** Table Leaf Nodes Popup Menus **/
        // click listeners --> get instance or show table
        // handle here?


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                OID target = (OID) data.getExtras().get("target");
                manager.sendGetRequest(target);
            }
        } else if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                OID target = (OID) data.getExtras().get("target");
                manager.sendGetNextRequest(target);
            }
        } else if(requestCode == 3){
            if(resultCode == Activity.RESULT_OK){
                Intent setIntent = new Intent(MIBBrowserActivity.this, SetHelper.class);
                OID target = (OID) data.getExtras().get("target");
                setIntent.putExtra("entry", target);
                //startActivityForResult(setIntent, 1);
                startActivityForResult(setIntent, 4);
            }
        } else if(requestCode == 4){
            if(resultCode == Activity.RESULT_OK){
                OID target = (OID) data.getExtras().get("target");
                int value = (int) data.getExtras().get("value");
                //Log.i("AppGerencia", "check set_value = " + value);
                VariableBinding targetVB = new VariableBinding();
                targetVB.setOid(target);
                targetVB.setVariable(new Integer32(value));
                Log.i("AppGerencia", "check targetVB: " + targetVB);
                manager.sendSetRequest(targetVB);
            }
        }
    }
}
