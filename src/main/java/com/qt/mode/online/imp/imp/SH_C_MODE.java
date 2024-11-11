/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.online.imp.imp;

import com.qt.mode.online.imp.AbsCDE_Mode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.SaHinhView;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class SH_C_MODE extends AbsCDE_Mode {

    public SH_C_MODE(SaHinhView hinhView, boolean isOnline) {
        super(hinhView, 20, 1200, MODEL_RANK_NAME.RANK_C, List.of("C"), isOnline);
    }


    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> events) {
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
    }

}
