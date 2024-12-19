/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import com.alibaba.fastjson2.JSONObject;
import com.nextone.common.ErrorLog;
import com.nextone.common.MyObjectMapper;
import com.nextone.controller.api.ApiService;
import com.nextone.input.camera.CameraRunner;
import com.nextone.main.Core;
import com.nextone.model.modelTest.Errorcode;
import com.nextone.model.modelTest.ErrorcodeWithContestNameModel;
import com.nextone.model.modelTest.contest.ContestDataModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.output.SoundPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ErrorcodeHandle {

    private static volatile ErrorcodeHandle instance;
    private final ProcessModelHandle processModelHandle;
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final Map<String, Errorcode> mapErrorcodes;
    private final List<ErrorcodeWithContestNameModel> ewcnms;
    private final ApiService apiService;

    private ErrorcodeHandle() {
        this.processModelHandle = ProcessModelHandle.getInstance();
        this.processModel = this.processModelHandle.getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.mapErrorcodes = new HashMap<>();
        this.ewcnms = new ArrayList<>();
        this.apiService = new ApiService();
    }

    public static ErrorcodeHandle getInstance() {
        ErrorcodeHandle ins = instance;
        if (ins == null) {
            synchronized (ErrorcodeHandle.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new ErrorcodeHandle();
                }
            }
        }
        return ins;
    }

    public void clear() {
        this.ewcnms.clear();
    }

    public List<ErrorcodeWithContestNameModel> getEwcnms() {
        return ewcnms;
    }

    public void putErrorCode(String key, Errorcode errorcode) {
        if (errorcode == null) {
            return;
        }
        this.mapErrorcodes.put(key, errorcode);
    }

    public void addBaseErrorCode(String key) {
        addBaseErrorCode(key, null);
    }

    public void addBaseErrorCode(String key, JSONObject jsono) {
        try {
            Errorcode errorcode = this.mapErrorcodes.get(key);
            if (errorcode == null || errorcode.getErrKey() == null) {
                return;
            }
            this.processModel.addErrorcode(errorcode);
            this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, ""));
            this.soundPlayer.sayErrorCode(errorcode.getErrKey());
            this.processModel.subtract(errorcode.getErrPoint());
            if (!Core.getInstance().getModeManagement().isOffLineMode()) {
                new Thread(() -> {
                    String id = this.processModel.getId();
                    if (id == null || id.isBlank() || id.equals("0")) {
                        return;
                    }
                    JSONObject testData = MyObjectMapper.convertValue(this.processModel, JSONObject.class);
                    if (jsono != null) {
                        testData.putAll(jsono);
                    }
                    this.apiService.sendData(CameraRunner.getInstance().getImage(),
                            testData);
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    public void addContestErrorCode(String key, ContestDataModel dataModel) {
        try {
            if (dataModel == null) {
                return;
            }
            Errorcode errorcode = this.mapErrorcodes.get(key);
            if (errorcode == null || errorcode.getErrKey() == null) {
                return;
            }
            this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, dataModel.getContestName()));
            this.soundPlayer.sayErrorCode(errorcode.getErrKey());
            dataModel.addErrorCode(errorcode);
            this.processModel.subtract(errorcode.getErrPoint());
            if (!Core.getInstance().getModeManagement().isOffLineMode()) {
                new Thread(() -> {
                    String id = this.processModel.getId();
                    if (id == null || id.isBlank() || id.equals("0")) {
                        return;
                    }
                    this.apiService.sendData(CameraRunner.getInstance().getImage(), 
                            this.processModelHandle.toProcessModelJson());
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }
}
