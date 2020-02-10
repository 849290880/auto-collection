package com.hai.autocollection.monitor.health;

import com.hai.autocollection.monitor.exec.FileMonitorExec;

import java.util.HashMap;
import java.util.Map;

/**
 * @author created by hai on 2020/1/20
 */
public class FileMonitorAdmin {

    public final static String HEAL_STATUS = "HEAL_STATUS";

    private Map<String,String> map = new HashMap<>();

    private static FileMonitorAdmin fileMonitorExec = new FileMonitorAdmin();

    private FileMonitorAdmin(){}

    public static FileMonitorAdmin get(){
        return fileMonitorExec;
    }

    public void putHealMsg(String msg){
        map.put(HEAL_STATUS,msg);
    }

    public Map<String,String> getMap(){
        return map;
    }

}
