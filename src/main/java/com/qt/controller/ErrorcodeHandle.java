/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.model.modelTest.Errorcode;
import com.qt.model.modelTest.ErrorcodeWithContestNameModel;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.SoundPlayer;
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
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final Map<String, Errorcode> mapErrorcodes;
    private final List<ErrorcodeWithContestNameModel> ewcnms;

    private ErrorcodeHandle() {
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.mapErrorcodes = new HashMap<>();
        this.ewcnms = new ArrayList<>();
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

    public void putErrorCode(String key, Errorcode errorcode) {
        if (errorcode == null) {
            return;
        }
        this.mapErrorcodes.put(key, errorcode);
    }

    public void addBaseErrorCode(String key) {
        Errorcode errorcode = this.mapErrorcodes.get(key);
        if (errorcode == null || errorcode.getName() == null) {
            return;
        }
        this.processModel.addErrorcode(errorcode);
        this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, ""));
        this.soundPlayer.sayErrorCode(errorcode.getName());
        this.processModel.subtract(errorcode.getScore());
    }

    public void addContestErrorCode(String key, ContestDataModel dataModel) {
        if (dataModel == null) {
            return;
        }
        Errorcode errorcode = this.mapErrorcodes.get(key);
        if (errorcode == null || errorcode.getName() == null) {
            return;
        }
        this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, dataModel.getName()));
        this.soundPlayer.sayErrorCode(errorcode.getName());
        dataModel.addErrorCode(errorcode);
        this.processModel.subtract(errorcode.getScore());
    }
}
