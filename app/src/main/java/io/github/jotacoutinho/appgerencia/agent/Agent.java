package io.github.jotacoutinho.appgerencia.agent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ListIterator;

import io.github.jotacoutinho.appgerencia.MainActivity;
import io.github.jotacoutinho.appgerencia.mib.MIBTree;

/**
 * Created by Joao Pedro on 09/12/2017.
 */

public class Agent extends Service implements CommandResponder {

    private String SNMP_PORT = "12345";
    private Snmp snmp;
    //public MIBTree mib = new MIBTree();
    public MIBTree mib = MainActivity.mib;

    @Override
    public void onCreate(){

        new AgentListener().start();
        //MIBTree jmib = MIBTree.mib;

    }


    @Override
    public synchronized void processPdu(CommandResponderEvent commandResponderEvent) {
        PDU command = (PDU) commandResponderEvent.getPDU().clone();
        if (command != null) {
            if (command.getType() == PDU.GET){
                //Log.i("AppGerencia", "handleGetRequest");
                 handleGetRequest(command);
            } else if(command.getType() == PDU.GETNEXT){
                //Log.i("AppGerencia", "handleGetNextRequest");
                handleGetNextRequest(command);
            } else if (command.getType() == PDU.SET) {
                handleSetRequest(command);
                Log.i("AppGerencia", "handleSetRequest");
            }
            Address address = commandResponderEvent.getPeerAddress();
            Log.i("AppGerencia", "processPdu command: " + command);
            sendResponse(address, command);
        }
    }

    private void sendResponse(Address address, PDU command) {
        command.setType(PDU.RESPONSE);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(address);
        target.setRetries(0);
        target.setTimeout(1000);
        target.setVersion(SnmpConstants.version1);

        try{
            snmp.send(command, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetRequest(PDU command) {
        VariableBinding varBind;
        //Log.i("AppGerencia", "handleGetRequest - command(" + command.size() + "): " + command);
        for(int i = 0; i < command.size(); i++){
            varBind = command.get(i);
            varBind.setVariable(mib.get(varBind.getOid()).getVariable());
            //Log.i("AppGerencia", "handleGetRequest - command(varBind): " + varBind.toString());
            //command.get(i).setVariable((Variable) varBind);
        }

    }

    private void handleGetNextRequest(PDU command) {
        VariableBinding varBind;
        for(int i = 0; i < command.size(); i++){
            varBind = command.get(i);
            VariableBinding responseVB = mib.getnext(varBind.getOid());
            //varBind.setVariable(mib.getnext(varBind.getOid()).getVariable());
            varBind.setOid(responseVB.getOid());
            varBind.setVariable(responseVB.getVariable());
        }
    }

    private void handleSetRequest(PDU command) {
        VariableBinding varBind;
        for(int i = 0; i < command.size(); i++){
            varBind = command.get(i);
            mib.set(varBind);
            //varBind.setVariable(mib.set(varBind.getOid()).getVariable());
        }
    }
    
    private class AgentListener extends Thread{
        public void run(){
            try {

                TransportMapping transport = new DefaultUdpTransportMapping(new UdpAddress("127.0.0.1/" + SNMP_PORT));

                snmp = new Snmp(transport);
                snmp.addCommandResponder(Agent.this);
                snmp.listen();
                Log.i("AppGerencia", "agent listening");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
