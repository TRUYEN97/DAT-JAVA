/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.controller.api.ApiService;
import com.qt.controller.CheckConditionHandle;
import com.qt.controller.ErrorcodeHandle;
import com.qt.controller.logTest.FileTestService;
import com.qt.controller.ProcessModelHandle;
import com.qt.controller.api.PingAPI;
import com.qt.controller.modeController.ModeHandle;
import com.qt.input.camera.CameraRunner;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.Errorcode;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.IKeyEvent;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 * @param <V> view
 */
@Getter
@Setter
public abstract class AbsTestMode<V extends JPanel> {

    protected final V view;
    protected final String name;
    protected final int scoreSpec;
    protected final CarModel carModel;
    protected final ProcessModel processModel;
    protected final SoundPlayer soundPlayer;
    protected final ProcessModelHandle processlHandle;
    protected final Queue<AbsContest> contests;
    protected final KeyEventsPackage prepareEventsPackage;
    protected final KeyEventsPackage testEventsPackage;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final FileTestService fileTestService;
    protected final ApiService apiService;
    protected final CheckConditionHandle conditionHandle;
    protected final PingAPI pingAPI;
    private ModeHandle modeHandle;
    private boolean cancel;

    protected AbsTestMode(V view, String name) {
        this(view, name, 80);
    }

    protected AbsTestMode(V view, String name, int scoreSpec) {
        this.view = view;
        this.name = name;
        this.cancel = false;
        this.scoreSpec = scoreSpec;
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.processlHandle = ProcessModelHandle.getInstance();
        this.processModel = this.processlHandle.getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.contests = new LinkedList<>();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.prepareEventsPackage = initPrepareKeyEventPackage();
        this.testEventsPackage = initTestKeyEventPackage();
        this.fileTestService = FileTestService.getInstance();
        this.apiService = ApiService.getInstance();
        this.conditionHandle = new CheckConditionHandle();
        this.pingAPI = new PingAPI();
        initErrorcode();
    }

    protected abstract boolean loopCheckStartTest();

    protected abstract void contestDone();

    protected abstract void endTest();

    protected abstract void createPrepareKeyEvents(Map<String, IKeyEvent> events);

    protected abstract void createTestKeyEvents(Map<String, IKeyEvent> events);

    protected void addContest(AbsContest contest) {
        if (contest == null) {
            return;
        }
        this.contests.add(contest);
    }

    public String getClassName() {
        return getClass().getSimpleName();
    }

    public AbsContest peekContests() {
        return this.contests.peek();
    }

    public AbsContest pollContests() {
        return this.contests.poll();
    }

    public void begin() {
        this.cancel = false;
        this.processlHandle.setTesting(false);
        KeyEventManagement.getInstance().addKeyEventBackAge(prepareEventsPackage);
        while (!loopCheckStartTest()) {
            Util.delay(200);
        }
        this.processlHandle.setTesting(true);
        this.errorcodeHandle.clear();
        this.processModel.setStatus(ProcessModel.RUNNING);
        MCUSerialHandler.getInstance().sendLedYellowOn();
        MCUSerialHandler.getInstance().sendReset();
        KeyEventManagement.getInstance().addKeyEventBackAge(testEventsPackage);
        this.soundPlayer.begin();
        this.pingAPI.start();
        updateLog();
        upTestDataToServer();
    }

    public void endContest() {
        this.processlHandle.update();
        updateLog();
        upTestDataToServer();
        contestDone();
    }

    protected void updateLog() {
        this.fileTestService.saveLogJson(this.processModel.getId(),
                this.processlHandle.toProcessModelJson().toString());
        this.fileTestService.saveImg(this.processModel.getId(),
                CameraRunner.getInstance().getImage());
    }

    protected boolean upTestDataToServer() {
        if (cancel) {
            return true;
        }
        String id = processModel.getId();
        if (id == null || id.equals("0")) {
            return true;
        }
        File imgFile = this.fileTestService.getFileImagePath(id);
        if (imgFile == null) {
            ErrorLog.addError(this, "Không tìm thấy file png của id: " + id);
        }
        return this.apiService.sendData(processlHandle.toProcessModelJson().toString().getBytes(),
                imgFile);
    }

    public void end() {
        try {
            this.contests.clear();
            KeyEventManagement.getInstance().remove(prepareEventsPackage);
            KeyEventManagement.getInstance().remove(testEventsPackage);
            if (this.processlHandle.isPass()) {
                MCUSerialHandler.getInstance().sendLedGreenOn();
            } else {
                MCUSerialHandler.getInstance().sendLedRedOn();
            }
            this.pingAPI.stop();
            updateLog();
            if (!upTestDataToServer()) {
                String id = processModel.getId();
                this.soundPlayer.sendResultFailed();
                this.fileTestService.saveBackupLog(id, processlHandle.toProcessModelJson().toString(),
                        CameraRunner.getInstance().getImage());
            }
            endTest();
            this.processlHandle.setTesting(false);
            int score = this.processModel.getScore();
            this.processModel.setStatus(score >= scoreSpec ? ProcessModel.PASS : ProcessModel.FAIL);
            this.soundPlayer.sayResultTest(score, this.processlHandle.isPass());
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private void initErrorcode() {

        this.errorcodeHandle.putErrorCode(ConstKey.ERR.SO3, new Errorcode("SO3", 2, "TAY SO KO PHU HOP"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TIME_OUT, new Errorcode("timeout", 5, "QUA THOI GIAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.AT, new Errorcode("AT", 5, "DAY AN TOAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.NTP, new Errorcode("NTP", 5, "KHONG XI NHAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.PT, new Errorcode("PT", 5, "PHANH TAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TS, new Errorcode("TS", 5, "KO TANG DUOC SO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GS, new Errorcode("GS", 5, "KO GIAM DUOC SO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TT, new Errorcode("TT", 5, "KO TANG TOC DO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GT, new Errorcode("GT", 5, "KO GIAM TOC DO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.S20, new Errorcode("S20",5, "QUA 20 GIAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.RG, new Errorcode("RG", 5, "RUNG GIAT"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.CM, new Errorcode("CM", 5, "CHET MAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.RPM, new Errorcode("RPM", 5, "QUA VONG TUA"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.QT, new Errorcode("QT", 10, "QUY TAC GIAO THONG"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.S30, new Errorcode("S30", 25, "QUA 30 GIAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.HL, new Errorcode("HL", 25, "HIEU LENH"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TN, new Errorcode("TN", 25, "GAY TAI NAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.CL, new Errorcode("CL", 25, "CHOANG LAI"));
    }

    private KeyEventsPackage initPrepareKeyEventPackage() {
        Map<String, IKeyEvent> events = new HashMap<>();
        KeyEventsPackage epg = new KeyEventsPackage(name + "Prepare");
        createPrepareKeyEvents(events);
        epg.putEvents(events);
        return epg;
    }

    private KeyEventsPackage initTestKeyEventPackage() {
        Map<String, IKeyEvent> events = new HashMap<>();
        events.put(ConstKey.ERR.CL, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.HL, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.QT, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.RG, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.TN, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        KeyEventsPackage epg = new KeyEventsPackage(name + "Testing", true);
        createTestKeyEvents(events);
        epg.putEvents(events);
        return epg;
    }

    public boolean checkTestCondisions() {
        return this.conditionHandle.checkTestCondisions();
    }

    public void cancelTest() {
        this.cancel = true;
    }

}
