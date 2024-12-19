/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import com.alibaba.fastjson2.JSONObject;
import com.nextone.common.FileService.FileService;
import com.nextone.model.yardConfigMode.YardConfigModel;
import java.io.File;

/**
 *
 * @author Admin
 */
public class YardConfig {

    private static volatile YardConfig instance;
    private final FileService fileService;
    private final String path;
    private final YardConfigModel yardConfigModel;

    private YardConfig() {
        this.fileService = new FileService();
        this.yardConfigModel = new YardConfigModel();
        this.path = Setting.getInstance().getYardConfigPath();
        try {
            File f = new File(this.path);
            if (!f.exists()) {
                update();
                return;
            }
            String data = this.fileService.readFile(f);
            if (data != null && !data.isBlank()) {
                MyObjectMapper.copy(
                        MyObjectMapper.convertValue(
                                JSONObject.parseObject(data),
                                YardConfigModel.class),
                                this.yardConfigModel
                );
            } else {
                update();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError("load config", e);
            update();
        }
    }

    public YardConfigModel getYardConfigModel() {
        return yardConfigModel;
    }

    public static YardConfig getInstance() {
        YardConfig ins = instance;
        if (ins == null) {
            synchronized (YardConfig.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new YardConfig();
                }
            }
        }
        return ins;
    }

    public synchronized final void update() {
        try {
            this.fileService.writeFile(this.path, MyObjectMapper.writeValueAsString(this.yardConfigModel), false);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }
}
