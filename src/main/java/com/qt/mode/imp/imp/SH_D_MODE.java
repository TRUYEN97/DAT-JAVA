/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp.imp;

import com.qt.mode.imp.AbsCDE_Mode;
import com.qt.pretreatment.IKeyEvent;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class SH_D_MODE extends AbsCDE_Mode {

    public SH_D_MODE() {
        super(24, 900, MODEL_RANK_NAME.RANK_D, List.of("D"));
    }

    @Override
    protected void contestDone() {
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> events) {
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
    }

}
