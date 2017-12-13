package io.github.jotacoutinho.appgerencia.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.github.jotacoutinho.appgerencia.MainActivity;
import io.github.jotacoutinho.appgerencia.agent.Agent;
import io.github.jotacoutinho.appgerencia.ui.MIBBrowserActivity;

/**
 * Created by Joao Pedro on 10/12/2017.
 */

public class Manager extends AppCompatActivity{

    private String ip;
    private String port;
    private CommunityTarget target;
    public Context context;
    public List<String> stringResponse = new ArrayList<>();
    public List<String> stringAction = new ArrayList<>();
    private int index = 0;

    public Manager(String ip, String port, Context context){

        target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setVersion(SnmpConstants.version1);
        target.setTimeout(1000);
        target.setRetries(1);
        target.setAddress(new UdpAddress(ip + "/" + port));

        this.context = context;

    }

    public Vector<Vector<? extends VariableBinding>> lastResponse = new Vector<>();

    public void sendGetRequest(final OID oid) {

            final PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));
            pdu.setType(PDU.GET);
            Log.i("AppGerencia", pdu.toString());

            new Thread(new Runnable(){
                @Override
                public void run() {

                    Snmp snmp = null;
                    try {
                        snmp = new Snmp(new DefaultUdpTransportMapping());
                        snmp.listen();

                        ResponseListener responseListener = new ResponseListener() {
                            @Override
                            public void onResponse(ResponseEvent responseEvent) {
                                ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                                PDU response = responseEvent.getResponse();
                                //Log.i("AppGerencia", "sendGetRequest onResponse response: " + response);
                                //PDU request = responseEvent.getRequest();
                                if(response != null){
                                    //Log.i("AppGerencia", "sendGetRequest onResponse not null");
                                    if(response.getErrorStatus() == PDU.noError){
                                        lastResponse.add(response.getVariableBindings());
                                        stringAction.add("get");
                                        showInfo();
                                    } else{
                                        Log.i("AppGerencia", "sendGetRequest onResponse PDU with error - response: " + response.getVariableBindings());
                                    }
                                }
                            }
                        };

                        snmp.send(pdu, target, null, responseListener);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    public void sendGetNextRequest(final OID oid) {

        final PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);
        Log.i("AppGerencia", pdu.toString());

        new Thread(new Runnable(){
            @Override
            public void run() {

                Snmp snmp = null;
                try {
                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    ResponseListener responseListener = new ResponseListener() {
                        @Override
                        public void onResponse(ResponseEvent responseEvent) {
                            ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                            PDU response = responseEvent.getResponse();
                            Log.i("AppGerencia", "sendGetRequest onResponse response: " + response);
                            //PDU request = responseEvent.getRequest();
                            if(response != null){
                                //Log.i("AppGerencia", "sendGetRequest onResponse not null");
                                if(response.getErrorStatus() == PDU.noError){
                                    lastResponse.add(response.getVariableBindings());
                                    stringAction.add("getnext");
                                    showInfo();
                                } else{
                                    Log.i("AppGerencia", "sendGetNextRequest onResponse PDU with error - response: " + response.getVariableBindings());
                                }
                            }
                        }
                    };

                    snmp.send(pdu, target, null, responseListener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        }

    public void sendSetRequest(final VariableBinding vb) {
        final PDU pdu = new PDU();
        pdu.add(vb);
        pdu.setType(PDU.SET);
        Log.i("AppGerencia", pdu.toString());

        new Thread(new Runnable(){
            @Override
            public void run() {

                Snmp snmp = null;
                try {
                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    ResponseListener responseListener = new ResponseListener() {
                        @Override
                        public void onResponse(ResponseEvent responseEvent) {
                            ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                            PDU response = responseEvent.getResponse();
                            Log.i("AppGerencia", "sendSetRequest onResponse response: " + response);
                            //PDU request = responseEvent.getRequest();
                            if(response != null){
                                //Log.i("AppGerencia", "sendGetRequest onResponse not null");
                                if(response.getErrorStatus() == PDU.noError){
                                    lastResponse.add(response.getVariableBindings());
                                    stringAction.add("set");
                                    showInfo();
                                } else{
                                    Log.i("AppGerencia", "sendSetRequest onResponse PDU with error - response: " + response.getVariableBindings());
                                }
                            }
                        }
                    };

                    snmp.send(pdu, target, null, responseListener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showInfo(){
        Log.i("AppGerencia", "sendGetRequest onResponse PDU noError - response: " + lastResponse);
        parseResponse();
        index = stringResponse.size();
        Intent intent =  new Intent(context, ManagerConsole.class);
        //intent.putExtra("action", (Serializable) stringAction);
        intent.putExtra("response", (Serializable) stringResponse);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }


    private void parseResponse() {
        for(int i = 0; i < lastResponse.size(); i++){
            String oid = lastResponse.get(i).get(0).getOid().toString();
            //Log.i("AppGerencia", "parseResponse OID: " + oid);
            String value = lastResponse.get(i).get(0).getVariable().toString();
            //Log.i("AppGerencia", "parseResponse Variable: " + value);
            stringResponse.add(stringAction.get(i) + " " + oid + " = " + value);
        }
    }
}
