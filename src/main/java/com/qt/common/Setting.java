/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class Setting {

    private static volatile Setting instance;
    private final Properties properties;

    private Setting() {
        this.properties = new Properties();
        try {
            properties.load(ErrorLog.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
    }

    public static Setting getInstance() {
        Setting ins = instance;
        if (ins == null) {
            synchronized (Setting.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new Setting();
                }
            }
        }
        return ins;
    }

    public String getBackUpLogDir() {
        return properties.getProperty(ConstKey.PATH.DIR_BACKUP_LOG, "logBackup");
    }

    public String getLogDir() {
        return properties.getProperty(ConstKey.PATH.DIR_LOG, "log");
    }

    public String getConfigPath() {
        return properties.getProperty(ConstKey.PATH.CONFIG_PATH, "config/carConfig.json");
    }
    
    public String getYardConfigPath() {
        return properties.getProperty(ConstKey.PATH.YARD_CONFIG_PATH, "config/yardConfig.json");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    public String getServerPingIp() {
        return properties.getProperty(ConstKey.URL.SERVER_PING_ADDR);
    }

    public String getSendDataUrl() {
        return properties.getProperty(ConstKey.URL.SEND_DATA_URL);
    }

    public String getCheckCarIdUrl() {
        return properties.getProperty(ConstKey.URL.CHECK_CAR_ID_URL);
    }

    public String getCheckUserIdUrl() {
        return properties.getProperty(ConstKey.URL.CHECK_USER_ID_URL);
    }

    public String getCheckCommandUrl() {
        return properties.getProperty(ConstKey.URL.CHECK_UPDATE_URL);
    }

    public String getCancelRequestUrl() {
        return properties.getProperty(ConstKey.URL.CANCEL_REQUEST_URL);
    }

    public String getCheckRunnableUrl() {
         return properties.getProperty(ConstKey.URL.RUNNABLE_URL);
    }
}
