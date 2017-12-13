package io.github.jotacoutinho.appgerencia.mib;

import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;

import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Joao Pedro on 09/12/2017.
 */

public class MIBTree {

    public static final OID sysError = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 1});
    //public static final OID sysError = new OID(".1.3.6.1.4.1.1.1.1");
    public static final OID sysDeviceID = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 2, 1, 1});
    public static final OID sysDeviceID_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 2, 1, 1, 1});
    public static final OID sysDeviceIP = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 2, 1, 2});
    public static final OID sysDeviceIP_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 2, 1, 2, 1});
    //public static final OID sysDeviceBluetoothKey = new OID(new int[] {1,3,6,1,4,1,1,1,2,1,3});
    public static final OID sysNetworkStatus = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 3});
    public static final OID sysBluetoothStatus = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 4});
    public static final OID sensZone = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 1});
    public static final OID sensZone_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 1, 1});
    public static final OID sensZone_2 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 1, 2});
    public static final OID sensZone_3 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 1, 3});
    public static final OID sensZone_4 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 1, 4});
    public static final OID sensTemperature = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 2});
    public static final OID sensTemperature_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 2, 1});
    public static final OID sensTemperature_2 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 2, 2});
    public static final OID sensTemperature_3 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 2, 3});
    public static final OID sensTemperature_4 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 2, 4});
    public static final OID sensHumidity = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 3});
    public static final OID sensHumidity_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 3, 1});
    public static final OID sensHumidity_2 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 3, 2});
    public static final OID sensHumidity_3 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 3, 3});
    public static final OID sensHumidity_4 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 3, 4});
    public static final OID sensCamera = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 4});
    public static final OID sensCamera_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 4, 1});
    public static final OID sensCamera_2 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 4, 2});
    public static final OID sensCamera_3 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 4, 3});
    public static final OID sensCamera_4 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1, 1, 4, 4});

    //public static MIBTree mib = new MIBTree();
    public List<MIBNode> mibNodes = new ArrayList<MIBNode>();

    private MIBNode root;
    public MIBNode sysErrorNode;
    public MIBNode sysNetworkStatusNode;
    public MIBNode sysBluetoothStatusNode;
    public MIBNode sysDeviceIDNode_1;
    public MIBNode sysDeviceIPNode_1;
    public MIBNode sensZoneNode_1, sensZoneNode_2, sensZoneNode_3, sensZoneNode_4;
    public MIBNode sensTemperatureNode_1, sensTemperatureNode_2, sensTemperatureNode_3, sensTemperatureNode_4;
    public MIBNode sensHumidityNode_1, sensHumidityNode_2, sensHumidityNode_3, sensHumidityNode_4;
    public MIBNode sensCameraNode_1, sensCameraNode_2, sensCameraNode_3, sensCameraNode_4;


    private class MIBNode {
        public VariableBinding variableBinding;
        public MIBNode parent = null;
        public List<MIBNode> children = null;


        public MIBNode(VariableBinding vb, Integer32 data, MIBNode parent, List<MIBNode> children) {
            this.variableBinding = vb;
            this.variableBinding.setVariable(data);
            this.parent = parent;
            this.children = children;
        }
    }


    public MIBTree() {

        root = null;
        sysErrorNode = new MIBNode(new VariableBinding(sysError), new Integer32(), root, null);

        //table
        sysDeviceIDNode_1 = new MIBNode(new VariableBinding(sysDeviceID_1), new Integer32(), root, null);
        sysDeviceIPNode_1 = new MIBNode(new VariableBinding(sysDeviceIP_1), new Integer32(), root, null);
        //

        sysNetworkStatusNode = new MIBNode(new VariableBinding(sysNetworkStatus), new Integer32(), root, null);
        sysBluetoothStatusNode = new MIBNode(new VariableBinding(sysBluetoothStatus), new Integer32(), root, null);

        //table
        sensZoneNode_1 = new MIBNode(new VariableBinding(sensZone_1), new Integer32(), root, null);
        sensTemperatureNode_1 = new MIBNode(new VariableBinding(sensTemperature_1), new Integer32(), root, null);
        sensHumidityNode_1 = new MIBNode(new VariableBinding(sensHumidity_1), new Integer32(), root, null);
        sensCameraNode_1 = new MIBNode(new VariableBinding(sensCamera_1), new Integer32(), root, null);
        //

        //table
        sensZoneNode_2 = new MIBNode(new VariableBinding(sensZone_2), new Integer32(), root, null);
        sensTemperatureNode_2 = new MIBNode(new VariableBinding(sensTemperature_2), new Integer32(), root, null);
        sensHumidityNode_2 = new MIBNode(new VariableBinding(sensHumidity_2), new Integer32(), root, null);
        sensCameraNode_2 = new MIBNode(new VariableBinding(sensCamera_2), new Integer32(), root, null);
        //

        //table
        sensZoneNode_3 = new MIBNode(new VariableBinding(sensZone_3), new Integer32(), root, null);
        sensTemperatureNode_3 = new MIBNode(new VariableBinding(sensTemperature_3), new Integer32(), root, null);
        sensHumidityNode_3 = new MIBNode(new VariableBinding(sensHumidity_3), new Integer32(), root, null);
        sensCameraNode_3 = new MIBNode(new VariableBinding(sensCamera_3), new Integer32(), root, null);
        //

        //table
        sensZoneNode_4 = new MIBNode(new VariableBinding(sensZone_4), new Integer32(), root, null);
        sensTemperatureNode_4 = new MIBNode(new VariableBinding(sensTemperature_4), new Integer32(), root, null);
        sensHumidityNode_4 = new MIBNode(new VariableBinding(sensHumidity_4), new Integer32(), root, null);
        sensCameraNode_4 = new MIBNode(new VariableBinding(sensCamera_4), new Integer32(), root, null);
        //

        mibNodes.add(sysErrorNode);

        mibNodes.add(sysDeviceIDNode_1);
        mibNodes.add(sysDeviceIPNode_1);

        mibNodes.add(sysNetworkStatusNode);
        mibNodes.add(sysBluetoothStatusNode);

        mibNodes.add(sensZoneNode_1);
        mibNodes.add(sensTemperatureNode_1);
        mibNodes.add(sensHumidityNode_1);
        mibNodes.add(sensCameraNode_1);

        mibNodes.add(sensZoneNode_2);
        mibNodes.add(sensTemperatureNode_2);
        mibNodes.add(sensHumidityNode_2);
        mibNodes.add(sensCameraNode_2);

        mibNodes.add(sensZoneNode_3);
        mibNodes.add(sensTemperatureNode_3);
        mibNodes.add(sensHumidityNode_3);
        mibNodes.add(sensCameraNode_3);

        mibNodes.add(sensZoneNode_4);
        mibNodes.add(sensTemperatureNode_4);
        mibNodes.add(sensHumidityNode_4);
        mibNodes.add(sensCameraNode_4);

        //ListIterator<MIBNode> li = mibNodes.listIterator();
        //Log.i("AppGerencia", "mibNodes node: " + li.next().variableBinding);

    }

    public VariableBinding get(OID oid) {
        //Log.i("AppGerencia", "MIBTree get variableBinding: " + sysErrorNode.variableBinding);
        VariableBinding response = new VariableBinding();
        for (int i = 0; i < mibNodes.size(); i++) {
            if (mibNodes.get(i).variableBinding.getOid().equals(oid)) {
                response = mibNodes.get(i).variableBinding;
            }
        }
        return response;
        //return mibNodes.get(0).variableBinding;
    }

    public VariableBinding getnext(OID oid) {
        //Log.i("AppGerencia", "MIBTree get variableBinding: " + sysErrorNode.variableBinding);
        //ListIterator<MIBNode> listIterator = mibNodes.listIterator();
        VariableBinding response = new VariableBinding();
        for (int i = 0; i < mibNodes.size(); i++) {
            //listIterator.next();
            if (mibNodes.get(i).variableBinding.getOid().equals(oid)) {
                if (i == (mibNodes.size() - 1)) {
                    response = mibNodes.get(0).variableBinding;
                } else {
                    response = mibNodes.get(i + 1).variableBinding;
                }
            }
        }
        //Log.i("AppGerencia", "getnext response: " + response);
        return response;
        //return mibNodes.get(0).variableBinding;
    }

    public VariableBinding set(VariableBinding vb) {
        //Log.i("AppGerencia", "MIBTree get variableBinding: " + sysErrorNode.variableBinding);
        VariableBinding response = new VariableBinding();
        for (int i = 0; i < mibNodes.size(); i++) {
            if (mibNodes.get(i).variableBinding.getOid().equals(vb.getOid())) {
                mibNodes.get(i).variableBinding.setVariable(vb.getVariable());
                response = mibNodes.get(i).variableBinding;
            }
        }
        return response;
        //return mibNodes.get(0).variableBinding;
    }

    public VariableBinding getValue(OID oid) {
        for (int i = 0; i < mibNodes.size(); i++) {
            if (mibNodes.get(i).variableBinding.getOid().equals(oid)) {
                return mibNodes.get(i).variableBinding;
            }
        }
        return new VariableBinding();
    }
}
