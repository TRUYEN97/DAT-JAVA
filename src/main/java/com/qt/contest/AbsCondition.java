/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest;

import com.qt.controller.ErrorCodeHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;

/**
 *
 * @author Admin
 */
public abstract class AbsCondition {

    protected final CarModel carModel;
    private final ErrorCodeHandle codeHandle;
    protected boolean important;

    public AbsCondition() {
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.codeHandle = ErrorCodeHandle.getInstance();
        this.important = false;
    }

    protected void setImporttant(boolean st) {
        this.important = st;
    }

    public abstract boolean checkPassed();

    protected void setErrorcode(int errorcode) {
        this.codeHandle.addBaseErrorCode(errorcode);
    }

    public boolean isImportant() {
        return important;
    }
}
