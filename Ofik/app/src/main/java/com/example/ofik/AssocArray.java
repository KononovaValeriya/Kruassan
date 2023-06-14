package com.example.ofik;

import android.util.Log;

import java.io.Serializable;

public class AssocArray implements Serializable {
    private String[][] arData;

    public AssocArray() {
        arData = new String[1][2];
        for (int i = 0; i < 1; i++) {
            arData[i][0] = "";
            arData[i][1] = "";
        }
    }

    public void set(String strKey, String strVal) {
        int intFindKey = -1;
        for (int i = 0; i < arData.length; i++)
            if (arData[i][0].equals("") || arData[i][0].equals(strKey)) {
                intFindKey = i;
                break;
            }

        if (intFindKey == -1) {
            recreate(arData.length + 1);
            intFindKey = arData.length - 1;
        }

        arData[intFindKey][0] = strKey;
        arData[intFindKey][1] = strVal;
    }

    public String get(String strKey) {
        String strVal = "";
        for (int i = 0; i < arData.length; i++)
            if (arData[i][0].equals(strKey)) {
                strVal = arData[i][1];
                break;
            }

        return strVal;
    }

    public String[] getKeys() {
        String[] arKeys = new String[arData.length];
        for (int i = 0; i < arData.length; i++) arKeys[i] = arData[i][0];

        return arKeys;
    }

    public void debug() {
        Log.d("DEB", "Data schema");

        for (int i = 0; i < arData.length; i++) {
            Log.d("DEB", arData[i][0]+"="+arData[i][1]);
        }
    }

    public String[][] getSchema() {
        return this.arData;
    }

    private void recreate(int intI) {
        String[][] arNewData = new String[intI][2];
        for (int i = 0; i < intI; i++) {
            arNewData[i][0] = "";
            arNewData[i][1] = "";
        }

        int intNewICounter = 0;
        for (int i = 0; i < arData.length; i++) {
            if (arData[i][0].equals("")) continue;
            arNewData[intNewICounter][0] = arData[i][0];
            arNewData[intNewICounter][1] = arData[i][1];
            intNewICounter++;
        }

        arData = arNewData;
    }
}
