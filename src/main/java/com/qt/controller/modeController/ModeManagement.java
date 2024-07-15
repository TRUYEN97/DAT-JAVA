/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.controller.ChangeID.ChangeCarId;
import com.qt.controller.ChangeID.ChangeUserId;
import com.qt.mode.AbsTestMode;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.pretreatment.IKeyEvent;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.pretreatment.KeyEventManagement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class ModeManagement {

    private final KeyEventManagement eventManagement;
    private final Map<Byte, AbsTestMode> modes;
    private final ModeParam modeParam;
    private final IKeyEvent updateModeEvent;
    private final ModeHandle modeHandle;
    private final KeyEventsPackage eventsPackage;
    private final ChangeUserId changeUserId;
    private final ChangeCarId changeCarId;

    public ModeManagement(ModeParam modeParam) {
        this.modes = new HashMap<>();
        this.eventManagement = modeParam.getEventManagement();
        this.modeParam = modeParam;
        this.modeHandle = new ModeHandle(modeParam);
        this.updateModeEvent = (byte key) -> {
            this.updateMode(key);
        };
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName());
        this.changeUserId = new ChangeUserId(modeParam);
        this.changeCarId = new ChangeCarId(modeParam);
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.SO_XE, (key) -> {
            this.changeCarId.show();
        });
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.SBD, (key) -> {
            this.changeUserId.show();
        });
    }

    public void updateMode(byte key) {
        if (this.modes.containsKey(key)) {
            this.updateMode(this.modes.get(key));
        }
    }

    public boolean updateMode(AbsTestMode testMode) {
        if (this.modeHandle.isRunning()) {
            return false;
        }
        if (this.modeHandle.isStarted()) {
            this.modeHandle.stop();
        }
        if (this.modeHandle.setTestMode(testMode)) {
            this.modeHandle.start();
            return true;
        }
        return false;
    }

    public void putMode(byte key, AbsTestMode absTestMode) {
        if (absTestMode == null) {
            return;
        }
        if (this.modes.isEmpty() && !this.modeHandle.isStarted()) {
            this.updateMode(absTestMode);
        }
        this.modes.put(key, absTestMode);
        this.eventsPackage.putEvent(key, updateModeEvent);
    }

    public ModeParam getTestModeModel() {
        return modeParam;
    }

    public void start() {
        this.modeParam.getSoundPlayer().sayWelcome();
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        Util.delay(20000);
        this.eventsPackage.remove(ConstKey.RM_KEY.CONFIG.SO_XE);
    }

}
