/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest;

import com.qt.controller.ErrorcodeHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.contest.ContestDataModel;

/**
 *
 * @author Admin
 */
public abstract class AbsCondition {

    protected final CarModel carModel;
    protected ContestDataModel contestDataModel;
    private final ErrorcodeHandle codeHandle;
    protected boolean important;

    public AbsCondition() {
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.codeHandle = ErrorcodeHandle.getInstance();
        this.important = false;
    }

    public void setContestDataModel(ContestDataModel contestDataModel) {
        this.contestDataModel = contestDataModel;
    }

    public void setImporttant(boolean st) {
        this.important = st;
    }

    public abstract boolean checkPassed();

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
}
