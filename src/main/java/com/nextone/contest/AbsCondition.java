/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest;

import com.nextone.controller.ErrorcodeHandle;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelTest.contest.ContestDataModel;

/**
 *
 * @author Admin
 */
public abstract class AbsCondition {

    protected final CarModel carModel;
    protected ContestDataModel contestDataModel;
    private final ErrorcodeHandle codeHandle;
    protected boolean important;
    protected boolean stop;
    protected boolean hasFail;

    public AbsCondition() {
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.codeHandle = ErrorcodeHandle.getInstance();
        this.important = false;
        this.stop = true;
        this.hasFail = false;
    }

    public void setContestDataModel(ContestDataModel contestDataModel) {
        this.contestDataModel = contestDataModel;
    }

    public void setImporttant(boolean st) {
        this.important = st;
    }

    public void start() {
        this.stop = false;
        this.hasFail = false;
    }

    public void stop() {
        this.stop = true;
    }

    public boolean checkPassed() {
        if (hasFail) {
            return false;
        }
        if (stop) {
            return true;
        }
        if (!checkCondition()) {
            if (important) {
                hasFail = true;
            }
            return false;
        }
        return true;
    }

    protected abstract boolean checkCondition();

    protected void setErrorcode(String errorcode) {
        if (contestDataModel == null) {
            this.codeHandle.addBaseErrorCode(errorcode);
        } else {
            this.codeHandle.addContestErrorCode(errorcode, contestDataModel);
        }
    }

    public boolean isImportant() {
        return important;
    }

    public boolean isTestCondisionsFailed() {
        return !checkPassed() && important;
    }
}
