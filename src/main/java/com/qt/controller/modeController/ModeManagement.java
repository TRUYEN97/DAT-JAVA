/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.mode.AbsTestMode;
import com.qt.output.printer.Printer;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.IKeyEvent;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.frame.ChangeIdFrame;
import com.qt.view.ViewMain;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ModeManagement {

    public static String DEFAULT_MODE;
    private final ViewMain viewMain;
    private final KeyEventManagement eventManagement;
    private final Map<String, AbsTestMode> modes;
    private final IKeyEvent updateModeEvent;
    private final ModeHandle modeHandle;
    private final KeyEventsPackage eventsPackage;
    private final ChangeIdFrame changeIdFrame;
    private final Printer printer;

    public ModeManagement(ViewMain viewMain) {
        this.viewMain = viewMain;
        this.modes = new HashMap<>();
        this.eventManagement = KeyEventManagement.getInstance();
        this.modeHandle = new ModeHandle();
        this.changeIdFrame = new ChangeIdFrame();
        this.printer = new Printer();
        this.updateModeEvent = (String key) -> {
            this.updateMode(key);
        };
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName());
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SO_XE, (key) -> {
            this.changeIdFrame.display(ChangeIdFrame.SX);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SBD, (key) -> {
            this.changeIdFrame.display(ChangeIdFrame.SBD);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.IN, (key) -> {
            this.printer.printTestResult();
        });
    }
    public void updateMode(String key) {
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
            this.viewMain.setView(testMode.getView());
            return true;
        }
        return false;
    }

    public void putMode(String key, AbsTestMode absTestMode) {
        if (absTestMode == null) {
            return;
        }
        if (this.modes.isEmpty() && !this.modeHandle.isStarted()) {
            this.updateMode(absTestMode);
            DEFAULT_MODE = absTestMode.getName();
        }
        this.modes.put(key, absTestMode);
        this.eventsPackage.putEvent(key, updateModeEvent);
    }

    public void start() {
        this.eventManagement.start();
        SoundPlayer.getInstance().sayWelcome();
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        Util.delay(20000);
        this.eventsPackage.remove(ConstKey.KEY_BOARD.SO_XE);
    }

}
