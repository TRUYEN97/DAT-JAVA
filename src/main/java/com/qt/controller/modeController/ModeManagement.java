/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.mode.AbsTestMode;
import com.qt.output.printer.Printer;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.frame.ChangeIdFrame;
import com.qt.view.ViewMain;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class ModeManagement {

    public static String DEFAULT_MODE;
    private final ViewMain viewMain;
    private final KeyEventManagement eventManagement;
    private final List<AbsTestMode> modes;
    private final ModeHandle modeHandle;
    private final KeyEventsPackage eventsPackage;
    private final ChangeIdFrame changeIdFrame;
    private final Printer printer;
    private Timer timer;

    public ModeManagement(ViewMain viewMain) {
        this.viewMain = viewMain;
        this.modes = new ArrayList<>();
        this.eventManagement = KeyEventManagement.getInstance();
        this.modeHandle = new ModeHandle();
        this.changeIdFrame = new ChangeIdFrame();
        this.printer = new Printer();
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
        this.timer = new Timer(20000, (e) -> {
            moveChangeCarIdEvent();
        });
        this.eventManagement.start();
    }

    public List<AbsTestMode> getModes() {
        return modes;
    }

    private void moveChangeCarIdEvent() {
        this.eventsPackage.remove(ConstKey.KEY_BOARD.SO_XE);
        this.timer.stop();
        this.timer = null;
    }

    public boolean updateMode(String modeName, String rank) {
        for (AbsTestMode mode : modes) {
            if (mode != null && mode.isMode(modeName, rank)) {
                return updateMode(mode);
            }
        }
        return false;
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

    public AbsTestMode getCurrentMode() {
        return this.modeHandle.getTestMode();
    }

    public void addMode(AbsTestMode absTestMode) {
        if (absTestMode == null) {
            return;
        }
        if (this.modes.isEmpty() && !this.modeHandle.isStarted()) {
            DEFAULT_MODE = absTestMode.getName();
        }
        this.modes.add(absTestMode);
    }

    public void start() {
        SoundPlayer.getInstance().sayWelcome();
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        this.timer.start();
    }

}
