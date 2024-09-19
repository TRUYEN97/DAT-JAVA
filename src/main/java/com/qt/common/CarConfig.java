/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.FileService.FileService;
import com.qt.model.config.MCU_CONFIG_MODEL;
import java.io.File;

/**
 *
 * @author Admin
 */
public class CarConfig {

    private static final String MCU_KEY = "MCU";
    private static final String CAR_ID_KEY = "carId";
    private static final String TEST_STATUS_KEY = "testStatus";
    private static volatile CarConfig instance;
    private final JSONObject jsono;
    private final Setting setting;
    private final FileService fileService;

    private CarConfig() {
        this.setting = Setting.getInstance();
        this.jsono = new JSONObject();
        this.fileService = new FileService();
        try {
            File f = new File(this.setting.getConfigPath());
            if (!f.exists()) {
                return;
            }
            String data = this.fileService.readFile(f);
            if (data != null && !data.isBlank()) {
                this.jsono.putAll(JSONObject.parseObject(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError("load config", e);
        }
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

    public void setCarId(String carId) {
        this.jsono.put(CAR_ID_KEY, carId == null ? "0" : carId);
        update();
    }

    public synchronized final void update() {
        this.fileService.writeFile(this.setting.getConfigPath(), this.jsono.toString(), false);
    }

    public void updateMcuConfig(MCU_CONFIG_MODEL mcu_config_model) {
        if (mcu_config_model == null) {
            return;
        }
        jsono.put(MCU_KEY, MyObjectMapper.convertValue(mcu_config_model, JSONObject.class));
        update();
    }

    public MCU_CONFIG_MODEL getMcuConfig() {
        if (jsono.containsKey(MCU_KEY)) {
            JSONObject ob = jsono.getJSONObject(MCU_KEY);
            if (ob != null) {
                MCU_CONFIG_MODEL mcu_config_model = MyObjectMapper.map(jsono, MCU_CONFIG_MODEL.class);
                if (mcu_config_model != null) {
                    return mcu_config_model;
                }
            }
        }
        return new MCU_CONFIG_MODEL();
    }

}
