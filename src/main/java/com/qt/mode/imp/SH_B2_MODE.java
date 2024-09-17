/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.common.API.Response;
import com.qt.common.ConstKey;
import com.qt.controller.ErrorcodeHandle;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.SaHinhView;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class SH_B2_MODE extends AbsTestMode<SaHinhView> {

    public SH_B2_MODE() {
        super(new SaHinhView(), ConstKey.MODE_NAME.SA_HINH, List.of("B2", "C"));
    }

    @Override
    protected boolean loopCheckCanTest() {
        return false;
    }

    @Override
    protected void contestDone() {
    }

    @Override
    protected void endTest() {
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> events) {
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
    }

    @Override
    protected void analysisResponce(Response responce) {
    }

}
