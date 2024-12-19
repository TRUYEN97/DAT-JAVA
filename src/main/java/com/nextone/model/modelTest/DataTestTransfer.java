/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest;

import com.alibaba.fastjson2.JSONObject;
import com.nextone.common.ErrorLog;

/**
 *
 * @author Admin
 */
public class DataTestTransfer {

    private final JSONObject jsono;

    public DataTestTransfer() {
        this.jsono = new JSONObject();
    }

    public void clear() {
        this.jsono.clear();
    }

    public void put(String key, Object data) {
        if (key == null) {
            return;
        }
        this.jsono.put(key, data);
    }

    public <T extends Object> T getData(String key) {
        return getData(key, null);
    }

    public <T extends Object> T getData(String key, T defaultValue) {
        if (key == null || !this.jsono.containsKey(key)) {
            return defaultValue;
        }
        Object ob = this.jsono.get(key);
        if (ob != null) {
            try {
                return (T) ob;
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
        }
        return defaultValue;
    }
}
