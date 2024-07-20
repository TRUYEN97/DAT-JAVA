/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.contest.AbsCondition;
import com.qt.contest.AbsContest;
import com.qt.controller.ApiService;
import com.qt.controller.CheckConditionHandle;
import com.qt.controller.ErrorcodeHandle;
import com.qt.controller.FileTestService;
import com.qt.controller.ProcessModelHandle;
import com.qt.input.camera.CameraRunner;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.Errorcode;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.IKeyEvent;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
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

    protected AbsTestMode(V view, String name) {
        this(view, name, 80);
    }

    protected AbsTestMode(V view, String name, int scoreSpec) {
        this.view = view;
        this.name = name;
        this.scoreSpec = scoreSpec;
        MCUSerialHandler.getInstance().start();
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
        initErrorcode();
    }

    protected abstract boolean loopCheckStartTest();

    protected abstract void contestDone();

    protected abstract void endTest();

    protected abstract void createPrepareKeyEvents(Map<Integer, IKeyEvent> events);

    protected abstract void createTestKeyEvents(Map<Integer, IKeyEvent> events);

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
        this.processlHandle.setTesting(false);
        MCUSerialHandler.getInstance().sendReset();
        MCUSerialHandler.getInstance().sendLedOff();
        this.processModel.setStatus(ProcessModel.RUNNING);
        this.errorcodeHandle.clear();
        KeyEventManagement.getInstance().addKeyEventBackAge(prepareEventsPackage);
        while (!loopCheckStartTest()) {
            Util.delay(200);
        }
        this.processlHandle.setTesting(true);
        MCUSerialHandler.getInstance().sendLedYellowOn();
        MCUSerialHandler.getInstance().sendReset();
        KeyEventManagement.getInstance().addKeyEventBackAge(testEventsPackage);
        this.soundPlayer.begin();
    }

    public void endContest() {
        this.processlHandle.update();
        this.fileTestService.saveLogJson(this.processModel.getId(),
                this.processlHandle.processModeltoJson().toString());
        this.fileTestService.saveImg(this.processModel.getId(),
                CameraRunner.getInstance().getImage());
        contestDone();
    }

    public void end() {
        try {
            this.contests.clear();
            KeyEventManagement.getInstance().remove(prepareEventsPackage);
            KeyEventManagement.getInstance().remove(testEventsPackage);
            int score = this.processModel.getScore();
            this.processModel.setStatus(score >= scoreSpec ? ProcessModel.PASS : ProcessModel.FAIL);
            this.soundPlayer.sayResultTest(score, this.processlHandle.isPass());
            this.fileTestService.saveLogJson(this.processModel.getId(),
                    this.processlHandle.processModeltoJson().toString());
            if (this.processlHandle.isPass()) {
                MCUSerialHandler.getInstance().sendLedGreenOn();
            } else {
                MCUSerialHandler.getInstance().sendLedRedOn();
            }
            endTest();
            this.processlHandle.setTesting(false);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private void initErrorcode() {

        this.errorcodeHandle.putErrorCode(ConstKey.ERR.SO3, new Errorcode("SO3", 2));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TIME_OUT, new Errorcode("timeout", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.AT, new Errorcode("AT", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.NTP, new Errorcode("NTP", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.PT, new Errorcode("PT", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TS, new Errorcode("TS", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GS, new Errorcode("GS", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TT, new Errorcode("TT", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GT, new Errorcode("GT", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.S20, new Errorcode("S20", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.RG, new Errorcode("RG", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.CM, new Errorcode("CM", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.RPM, new Errorcode("RPM", 5));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.QT, new Errorcode("QT", 10));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.S30, new Errorcode("S30", 25));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.HL, new Errorcode("HL", 25));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TN, new Errorcode("TN", 25));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.CL, new Errorcode("CL", 25));
    }

    private KeyEventsPackage initPrepareKeyEventPackage() {
        Map<Integer, IKeyEvent> events = new HashMap<>();
        KeyEventsPackage epg = new KeyEventsPackage(name + "Prepare");
        createPrepareKeyEvents(events);
        epg.putEvents(events);
        return epg;
    }

    private KeyEventsPackage initTestKeyEventPackage() {
        Map<Integer, IKeyEvent> events = new HashMap<>();
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

}
