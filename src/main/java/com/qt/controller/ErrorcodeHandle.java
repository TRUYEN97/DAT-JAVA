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
public class ErrorCodeHandle {

    private static volatile ErrorCodeHandle instance;
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final Map<Integer, Errorcode> mapErrorcodes;
    private final List<ErrorcodeWithContestNameModel> ewcnms;

    private ErrorCodeHandle() {
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.mapErrorcodes = new HashMap<>();
        this.ewcnms = new ArrayList<>();
    }

    public static ErrorCodeHandle getInstance() {
        ErrorCodeHandle ins = instance;
        if (ins == null) {
            synchronized (ErrorCodeHandle.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new ErrorCodeHandle();
                }
            }
        }
        return ins;
    }

    public void clear() {
        this.ewcnms.clear();
    }

    public void putErrorCode(int key, Errorcode errorcode) {
        if (errorcode == null) {
            return;
        }
        this.mapErrorcodes.put(key, errorcode);
    }

    public void addBaseErrorCode(int key) {
        Errorcode errorcode = this.mapErrorcodes.get(key);
        if (errorcode == null || errorcode.getName() == null) {
            return;
        }
        this.processModel.addErrorcode(errorcode);
        this.processModel.subtract(errorcode.getScore());
        this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, ""));
        this.soundPlayer.sayErrorCode(errorcode.getName());
    }

    public void addContestErrorCode(int key, ContestDataModel dataModel) {
        if (dataModel == null) {
            return;
        }
        Errorcode errorcode = this.mapErrorcodes.get(key);
        if (errorcode == null || errorcode.getName() == null) {
            return;
        }
        this.ewcnms.add(new ErrorcodeWithContestNameModel(errorcode, dataModel.getName()));
        this.processModel.subtract(errorcode.getScore());
        this.soundPlayer.sayErrorCode(errorcode.getName());
        dataModel.addErrorCode(errorcode);
    }
}
