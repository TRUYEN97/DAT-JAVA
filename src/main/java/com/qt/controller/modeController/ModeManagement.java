/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.controller.ChangeCarId;
import com.qt.controller.ChangeUserId;
import com.qt.controller.PrintWithKeyBoard;
import com.qt.input.camera.CameraRunner;
import com.qt.mode.AbsTestMode;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.ViewMain;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private final PrintWithKeyBoard printWithKeyBoard;
    private final CameraRunner cameraRunner;
    private final ChangeUserId changeUserId;
    private final ChangeCarId changeCarId;
    private final ExecutorService threadPool;
    private Timer timer;

    public ModeManagement(ViewMain viewMain) {
        this.viewMain = viewMain;
        this.modes = new ArrayList<>();
        this.eventManagement = KeyEventManagement.getInstance();
        this.modeHandle = new ModeHandle();
        this.printWithKeyBoard = new PrintWithKeyBoard();
        this.cameraRunner = CameraRunner.getInstance();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName());
        this.changeUserId = new ChangeUserId();
        this.changeCarId = new ChangeCarId();
        this.threadPool = Executors.newSingleThreadExecutor();
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SO_XE, (key) -> {
            this.threadPool.submit(this.changeCarId);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SBD, (key) -> {
            this.threadPool.submit(this.changeUserId);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.IN, (key) -> {
            this.threadPool.submit(this.printWithKeyBoard);
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
        AbsTestMode currMode = this.modeHandle.getTestMode();
        if (currMode != null && currMode.equals(testMode)) {
            System.out.println(currMode.getFullName());
            return true;
        }
        try {
            this.modeHandle.setWait(true);
            if (this.modeHandle.isRunning()) {
                return false;
            }
            if (this.modeHandle.isStarted()) {
                this.modeHandle.stop();
            }
            if (this.modeHandle.setTestMode(testMode)) {
                this.modeHandle.start();
                this.viewMain.setView(testMode.getView());
                this.cameraRunner.setTestModeView(testMode.getView());
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        } finally {
            this.modeHandle.setWait(false);
        }
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
