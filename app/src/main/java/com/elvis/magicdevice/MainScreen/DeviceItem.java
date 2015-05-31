package com.elvis.magicdevice.MainScreen;

import com.elvis.magicdevice.BuildPropTool;

import java.io.Serializable;

public class DeviceItem implements Serializable{


    private String name;
    private String key;
    private String defaultvalue;
    private String targetValue = "NULL";


    public DeviceItem(String[] item) {
        this.name = item[0];
        this.key = item[1];
        this.defaultvalue = BuildPropTool.prop.getProperty(key,"NULL");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

}
