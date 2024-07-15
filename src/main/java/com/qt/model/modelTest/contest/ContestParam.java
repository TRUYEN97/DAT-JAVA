/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.contest;

import com.qt.model.modelTest.process.ModeParam;
import com.qt.controller.ErrorcodeHandle;
import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class ContestParam extends ModeParam{
    private final ErrorcodeHandle errorcodeHandle;

    public ContestParam(ModeParam modeParam, ErrorcodeHandle errorcodeHandle) {
        super(modeParam.getCarModel(), 
                modeParam.getYardModel(), 
                modeParam.getSoundPlayer(), 
                modeParam.getEventManagement(), 
                modeParam.getProcessModelHandle(),
                modeParam.getCameraRunner());
        this.errorcodeHandle = errorcodeHandle;
    }
    
}
