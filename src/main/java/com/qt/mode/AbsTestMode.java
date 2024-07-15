/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.FileService.FileService;
import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.MyObjectMapper;
import com.qt.common.Util;
import com.qt.common.timer.TimeBase;
import com.qt.contest.AbsContest;
import com.qt.controller.ErrorcodeHandle;
import com.qt.controller.ProcessModelHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.model.input.YardModel;
import com.qt.model.modelTest.Errorcode;
import com.qt.model.modelTest.contest.ContestParam;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.IKeyEvent;
import com.qt.pretreatment.KeyEventsPackage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public abstract class AbsTestMode {

    private static final String LOG_PATH= "log/test";
    protected final ModeParam modeParam;
    protected final String name;
    protected final int scoreSpec;
    protected final CarModel carModel;
    protected final YardModel yardModel;
    protected final ProcessModel processModel;
    protected final SoundPlayer soundPlayer;
    protected final ProcessModelHandle processlHandle;
    protected final Queue<AbsContest> contests;
    protected final KeyEventsPackage prepareEventsPackage;
    protected final KeyEventsPackage testEventsPackage;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final ContestParam contestParam;
    protected final FileService fileService;
    protected final TimeBase timeBase;

    protected AbsTestMode(ModeParam modeParam, String name) {
        this(modeParam, name, 80);
    }

    protected AbsTestMode(ModeParam modeParam, String name, int scoreSpec) {
        this.modeParam = modeParam;
        this.name = name;
        this.scoreSpec = scoreSpec;
        this.carModel = modeParam.getCarModel();
        this.yardModel = modeParam.getYardModel();
        this.processlHandle = modeParam.getProcessModelHandle();
        this.processModel = this.processlHandle.getProcessModel();
        this.soundPlayer = modeParam.getSoundPlayer();
        this.contests = new LinkedList<>();
        this.errorcodeHandle = new ErrorcodeHandle(modeParam);
        this.contestParam = new ContestParam(modeParam, errorcodeHandle);
        this.fileService = new FileService();
        this.timeBase = new TimeBase();
        this.prepareEventsPackage = initPrepareKeyEventPackage();
        this.testEventsPackage = initTestKeyEventPackage();
        initErrorcode();
    }

    protected abstract boolean loopCheckStartTest();

    public abstract void contestDone();

    protected abstract void endTest();

    protected abstract void createPrepareKeyEvents(Map<Byte, IKeyEvent> events);

    protected abstract void createTestKeyEvents(Map<Byte, IKeyEvent> events);

    private KeyEventsPackage initPrepareKeyEventPackage() {
        Map<Byte, IKeyEvent> events = new HashMap<>();
        KeyEventsPackage epg = new KeyEventsPackage(name + "Prepare");
        createPrepareKeyEvents(events);
        epg.putEvents(events);
        return epg;
    }

    private KeyEventsPackage initTestKeyEventPackage() {
        Map<Byte, IKeyEvent> events = new HashMap<>();
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
        events.put((byte) 74, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put((byte) 12, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        KeyEventsPackage epg = new KeyEventsPackage(name + "Testing", true);
        createTestKeyEvents(events);
        epg.putEvents(events);
        return epg;
    }

    public String getClassName() {
        return getClass().getSimpleName();
    }

    protected void addContest(AbsContest contest) {
        if (contest == null) {
            return;
        }
        this.contests.add(contest);
    }

    public AbsContest peekContests() {
        return this.contests.peek();
    }

    public AbsContest pollContests() {
        return this.contests.poll();
    }

    public void begin() {
        MCUSerialHandler.getInstance().sendReset();
        MCUSerialHandler.getInstance().sendLedOff();
        this.modeParam.getEventManagement().addKeyEventBackAge(prepareEventsPackage);
        while (!loopCheckStartTest()) {
            Util.delay(200);
        }
        MCUSerialHandler.getInstance().sendLedYellowOn();
        MCUSerialHandler.getInstance().sendReset();
        this.modeParam.getEventManagement().addKeyEventBackAge(testEventsPackage);
        this.soundPlayer.begin();
    }

    public void end() {
        try {
            this.contests.clear();
            this.modeParam.getEventManagement().remove(prepareEventsPackage);
            this.modeParam.getEventManagement().remove(testEventsPackage);
            int score = this.processModel.getScore();
            boolean pass = score >= scoreSpec;
            this.processModel.setPass(pass);
            this.soundPlayer.sayResultTest(score, pass);
            saveLogJson();
            if (pass) {
                MCUSerialHandler.getInstance().sendLedGreenOn();
            } else {
                MCUSerialHandler.getInstance().sendLedRedOn();
            }
            endTest();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private void saveLogJson() {
        String filePathString = String.format("%s/%s/%s/jsonLog.json",
                LOG_PATH,
                this.timeBase.getDate(),
                this.processModel.getId());
        this.fileService.writeFile(filePathString,
                this.processlHandle.processModeltoJson().toString(),
                false);
    }

    private void initErrorcode() {

        this.errorcodeHandle.putErrorCode(ConstKey.ERR.SO3, new Errorcode("SO3", 2));
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

}
