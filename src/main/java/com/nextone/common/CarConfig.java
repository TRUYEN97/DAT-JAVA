/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import com.alibaba.fastjson2.JSONObject;
import com.nextone.common.FileService.FileService;
import com.nextone.model.config.MCU_CONFIG_MODEL;
import java.io.File;

/**
 *
 * @author Admin
 */
public class CarConfig {

    private static final String MCU_KEY = "MCU";
    private static final String CAR_ID_KEY = "carId";
    private static final String TEST_STATUS_KEY = "testStatus";
    private static final String PASSWORD = "password";
    private static final String YARD_IP = "yardIp";
    private static final String EXAM_ID = "examId";
    private static final String YARD_PORT = "yardPort";
    private static final String MODE_INDEX = "modeIndex";
    private static final String YARDUSERNAME = "yardusername";
    private static final String YARDPASSWORD = "yardpassword";
    private static volatile CarConfig instance;
    private final JSONObject jsono;
    private final String path;
    private final FileService fileService;

    private CarConfig() {
        Setting setting = Setting.getInstance();
        this.jsono = new JSONObject();
        this.fileService = new FileService();
        this.path = setting.getConfigPath();
        try {
            File f = new File(this.path);
            if (!f.exists()) {
                setDefaultConfig();
                return;
            }
            String data = this.fileService.readFile(f);
            if (data != null && !data.isBlank()) {
                this.jsono.putAll(JSONObject.parseObject(data));
            } else {
                setDefaultConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError("load config", e);
            setDefaultConfig();
        }
    }

    public String getYardIp() {
        return getString(YARD_IP);
    }

    public int getYardPort() {
        return this.jsono.getIntValue(YARD_PORT, 68);
    }

    private void setDefaultConfig() {
        this.jsono.put(MCU_KEY, new MCU_CONFIG_MODEL());
        this.jsono.put(CAR_ID_KEY, "0");
        this.jsono.put(CENTER_NAME, "");
        this.jsono.put(EXAM_ID, "0");
        this.jsono.put(PASSWORD, "e10adc3949ba59abbe56e057f20f883e");
        this.jsono.put(YARD_IP, "192.168.1.168");
        this.jsono.put(YARD_PORT, 6868);
        this.jsono.put(MODE_INDEX, 0);
        this.jsono.put(YARDPASSWORD, "f3cea34ed1507b50f09c236045bb1067");
        this.jsono.put(YARDUSERNAME, "client");
        update();
    }

    public static CarConfig getInstance() {
        CarConfig ins = instance;
        if (ins == null) {
            synchronized (CarConfig.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new CarConfig();
                }
            }
        }
        return ins;
    }

    public String getCarId() {
        String val = this.jsono.getString(CAR_ID_KEY);
        return val == null || val.isBlank() ? "0" : val;
    }

    public String getExamId() {
        return getString(EXAM_ID, "0");
    }

    public String getTestStatusValue() {
        return this.jsono.getString(TEST_STATUS_KEY);
    }

    public void removeTestStatusValue() {
        this.jsono.put(TEST_STATUS_KEY, null);
        update();
    }

    public void setCurrentTestStatusValue(String id, String examId) {
        this.jsono.put(TEST_STATUS_KEY, String.format("%s,%s", id, examId));
        update();
    }

    public void setExamId(String examid) {
        this.jsono.put(EXAM_ID, examid == null ? "0" : examid);
        update();
    }

    public void setCarId(String carId) {
        this.jsono.put(CAR_ID_KEY, carId == null ? "0" : carId);
        update();
    }

    public synchronized final void update() {
        try {
            this.fileService.writeFile(this.path, this.jsono.toString(), false);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            setDefaultConfig();
        }
    }

    public void updateMcuConfig(MCU_CONFIG_MODEL mcu_config_model) {
        if (mcu_config_model == null) {
            return;
        }
        jsono.put(MCU_KEY, MyObjectMapper.convertValue(mcu_config_model, JSONObject.class));
        update();
    }

    public MCU_CONFIG_MODEL getMcuConfig() {
        try {
            if (jsono.containsKey(MCU_KEY)) {
                JSONObject ob = jsono.getJSONObject(MCU_KEY);
                if (ob != null) {
                    MCU_CONFIG_MODEL mcu_config_model = MyObjectMapper.map(ob, MCU_CONFIG_MODEL.class);
                    if (mcu_config_model != null) {
                        return mcu_config_model;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            setDefaultConfig();
        }
        return new MCU_CONFIG_MODEL();
    }

    public void setPassword(String pw) {
        if (pw == null || pw.isBlank()) {
            pw = "e10adc3949ba59abbe56e057f20f883e";
        }
        this.jsono.put(PASSWORD, pw);
        update();
    }

    public String getPassword() {
        return getString(PASSWORD, "e10adc3949ba59abbe56e057f20f883e");
    }

    public String getCenterName() {
        String centerName = "";
        if (jsono.containsKey(CENTER_NAME)) {
            centerName = jsono.getString(CENTER_NAME);
        }
        return centerName;
    }
    protected static final String CENTER_NAME = "centerName";

    public String getString(String key) {
        return this.jsono.getString(key);
    }

    public String getString(String key, String defaultVal) {
        String val = this.jsono.getString(key);
        return val == null || val.isBlank() ? defaultVal : val;
    }

    public void setModeIndex(int index) {
        this.jsono.put(MODE_INDEX, index < 0 ? 0 : index);
        update();
    }

    public int getIndexOfModel() {
        return this.jsono.getIntValue(MODE_INDEX, 0);
    }

    public String getYardPassword() {
        return this.getString(YARDPASSWORD, "f3cea34ed1507b50f09c236045bb1067");
    }

    public String getYardUser() {
        return this.getString(YARDUSERNAME, "client");
    }

}
