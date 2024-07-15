/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.model.modelTest.Errorcode;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.output.SoundPlayer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ErrorcodeHandle {

    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final Map<Integer, Errorcode> mapErrorcodes;

    public ErrorcodeHandle(ModeParam modeParam) {
        this.processModel = modeParam.getProcessModelHandle().getProcessModel();
        this.soundPlayer = modeParam.getSoundPlayer();
        this.mapErrorcodes = new HashMap<>();
    }

    public void putErrorCode(int key, Errorcode errorcode) {
        if (errorcode == null) {
            return;
        }
        this.mapErrorcodes.put(key, errorcode);
    }

    public void addBaseErrorCode(int key) {
        Errorcode errorcode = this.mapErrorcodes.get(key);
        if (errorcode == null) {
            return;
        }
        this.processModel.subtract(errorcode.getScore());
        this.soundPlayer.sayErrorCode(errorcode.getName());
        this.processModel.addErrorcode(errorcode.getName());
    }

    public void addContestErrorCode(int key, ContestDataModel dataModel) {
        if (dataModel == null) {
            return;
        }
        Errorcode errorcode = this.mapErrorcodes.get(key);
        if (errorcode == null) {
            return;
        }
        this.processModel.subtract(errorcode.getScore());
        this.soundPlayer.sayErrorCode(errorcode.getName());
        dataModel.addErrorCode(errorcode.getName());
    }
}
