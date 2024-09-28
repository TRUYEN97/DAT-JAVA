/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.FileService.FileService;
import com.qt.model.yardConfigMode.YardRankConfig;
import java.io.File;

/**
 *
 * @author Admin
 */
public class YardConfig {

    private static volatile YardConfig instance;
    private final JSONObject jsono;
    private final FileService fileService;
    private final String path;

    private YardConfig() {
        this.jsono = new JSONObject();
        this.fileService = new FileService();
        this.path = Setting.getInstance().getYardConfigPath();
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

    private void setDefaultConfig() {
        this.jsono.put(B, new YardRankConfig());
        this.jsono.put(C, new YardRankConfig());
        this.jsono.put(D, new YardRankConfig());
        this.jsono.put(E, new YardRankConfig());
        update();
    }
    public static final String E = "E";
    public static final String D = "D";
    public static final String C = "C";
    public static final String B = "B";

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

    public YardRankConfig getRankBConfig() {
        return getRankConfig(B);
    }

    public YardRankConfig getRankCConfig() {
        return getRankConfig(C);
    }

    public YardRankConfig getRankDConfig() {
        return getRankConfig(D);
    }

    public YardRankConfig getRankEConfig() {
        return getRankConfig(E);
    }

    public YardRankConfig getRankConfig(String rankName) {
        try {
            if (this.jsono.containsKey(rankName)) {
                JSONObject ob = jsono.getJSONObject(rankName);
                if (ob != null) {
                    YardRankConfig rankConfig = MyObjectMapper.map(ob, YardRankConfig.class);
                    if (rankConfig != null) {
                        return rankConfig;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            setDefaultConfig();
        }
        return new YardRankConfig();
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

//    public String getString(String key) {
//        return this.jsono.getString(key);
//    }
//
//    public String getString(String key, String defaultVal) {
//        String val = this.jsono.getString(key);
//        return val == null || val.isBlank() ? defaultVal : val;
//    }
}
