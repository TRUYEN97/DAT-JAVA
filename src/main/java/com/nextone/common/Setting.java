/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class Setting {

    private static volatile Setting instance;
    private final Properties filePath;
    private final Properties apiProperties;

    private Setting() {
        this.filePath = new Properties();
        this.apiProperties = new Properties();
        try {
            filePath.load(Setting.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
    }

    public void setSaHinhMode() {
        try {
            apiProperties.load(Setting.class.getResourceAsStream("/ShConfig.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
    }

    public void setDuongTruongMode() {
        try {
            apiProperties.load(Setting.class.getResourceAsStream("/DtConfig.properties"));
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
        return filePath.getProperty(ConstKey.PATH.DIR_BACKUP_LOG, "logBackup");
    }

    public String getLogDir() {
        return filePath.getProperty(ConstKey.PATH.DIR_LOG, "log");
    }

    public String getConfigPath() {
        return filePath.getProperty(ConstKey.PATH.CONFIG_PATH, "config/carConfig.json");
    }

    public String getYardConfigPath() {
        return filePath.getProperty(ConstKey.PATH.YARD_CONFIG_PATH, "config/yardConfig.json");
    }

    public String getServerPingIp() {
        return apiProperties.getProperty(ConstKey.URL.SERVER_PING_ADDR);
    }

    public String getSendDataUrl() {
        return apiProperties.getProperty(ConstKey.URL.SEND_DATA_URL);
    }

    public String getCheckCarIdUrl() {
        return apiProperties.getProperty(ConstKey.URL.CHECK_CAR_ID_URL);
    }

    public String getCheckUserIdUrl() {
        return apiProperties.getProperty(ConstKey.URL.CHECK_USER_ID_URL);
    }

    public String getCheckCommandUrl() {
        return apiProperties.getProperty(ConstKey.URL.CHECK_COMMAND_URL);
    }

    public String getCancelRequestUrl() {
        return apiProperties.getProperty(ConstKey.URL.CANCEL_REQUEST_URL);
    }

    public String getCheckRunnableUrl() {
        return apiProperties.getProperty(ConstKey.URL.RUNNABLE_URL);
    }

    public String getcheckCarPairUrl() {
        return apiProperties.getProperty(ConstKey.URL.CAR_PAIR_URL);
    }
}
