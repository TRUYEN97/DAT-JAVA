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

    private final JSONObject jsono;
    private final Setting setting;
    private final FileService fileService;

    public CarConfig() {
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

    public final void update() {
        this.fileService.writeFile(this.setting.getConfigPath(), this.jsono.toString(), false);
    }

    public MCU_CONFIG_MODEL getMcuConfig() {
        MCU_CONFIG_MODEL mcu_config_model = new MCU_CONFIG_MODEL();
//        if (this.jsono.containsKey(ConstKey.CAR_CONFIG.MCU_CONFIG)) {
//            cf.putAll(this.jsono.getJSONObject(ConstKey.CAR_CONFIG.MCU_CONFIG));
//        }
        return mcu_config_model;
    }

}
