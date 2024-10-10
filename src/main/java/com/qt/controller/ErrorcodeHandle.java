/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.ErrorLog;
import com.qt.common.MyObjectMapper;
import com.qt.controller.api.ApiService;
import com.qt.input.camera.CameraRunner;
import com.qt.model.modelTest.ErrorCode;
import com.qt.model.modelTest.ErrorcodeWithContestNameModel;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.SoundPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Admin
 */
public class ErrorcodeHandle {

    private static volatile ErrorcodeHandle instance;
    private final ProcessModelHandle processModelHandle;
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final Map<String, ErrorCode> mapErrorcodes;
    private final List<ErrorcodeWithContestNameModel> ewcnms;
    private final ApiService apiService;
    private final ExecutorService threadPool;

    private ErrorcodeHandle() {
        this.processModelHandle = ProcessModelHandle.getInstance();
        this.processModel = this.processModelHandle.getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.mapErrorcodes = new HashMap<>();
        this.ewcnms = new ArrayList<>();
        this.apiService = ApiService.getInstance();
        this.threadPool = Executors.newSingleThreadExecutor();
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

    public void putErrorCode(String key, ErrorCode errorcode) {
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
            ErrorCode errorcode = this.mapErrorcodes.get(key);
            if (errorcode == null || errorcode.getErrKey() == null) {
                return;
            }
            this.processModel.addErrorcode(errorcode);
            this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, ""));
            this.soundPlayer.sayErrorCode(errorcode.getErrKey());
            this.processModel.subtract(errorcode.getErrPoint());
            this.threadPool.execute(() -> {
                JSONObject testData = MyObjectMapper.convertValue(this.processModel, JSONObject.class);
                if (jsono != null) {
                    testData.putAll(jsono);
                }
                this.apiService.sendData(CameraRunner.getInstance().getImage(), testData);
            });
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
            ErrorCode errorcode = this.mapErrorcodes.get(key);
            if (errorcode == null || errorcode.getErrKey() == null) {
                return;
            }
            this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, dataModel.getContestName()));
            this.soundPlayer.sayErrorCode(errorcode.getErrKey());
            dataModel.addErrorCode(errorcode);
            this.processModel.subtract(errorcode.getErrPoint());
            this.threadPool.execute(() -> {
                this.apiService.sendData(this.processModel, null);
            });
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }
}
