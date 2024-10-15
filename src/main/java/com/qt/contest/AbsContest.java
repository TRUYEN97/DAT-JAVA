/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest;

import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.contest.impCondition.ImportantError;
import com.qt.contest.impCondition.timerCondition.TimeOutContest;
import com.qt.controller.CheckConditionHandle;
import com.qt.controller.ContestModelHandle;
import com.qt.controller.ErrorcodeHandle;
import com.qt.interfaces.IgetTime;
import com.qt.controller.ProcessModelHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.DataTestTransfer;
import com.qt.output.SoundPlayer;

public abstract class AbsContest implements IgetTime {

    public static final int WAIT = 0;
    public static final int RUNNING = 1;
    public static final int DONE = 2;
    private final ProcessModelHandle processlHandle;
    protected final CarModel carModel;
    protected final ContestDataModel contestModel;
    protected final SoundPlayer soundPlayer;
    protected final ContestModelHandle contestModelHandle;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final boolean playSoundWhenIn;
    protected final boolean playSoundWhenOut;
    protected final CheckConditionHandle conditionBeginHandle;
    protected final CheckConditionHandle conditionIntoHandle;
    private final TimeOutContest timeOutContest;
    protected final String nameSound;
    protected final ImportantError importantError;
    protected int status;
    protected boolean stop;
    protected int timeOut;
    private final boolean sayContestName;
    protected DataTestTransfer dataTestTransfer;

    public AbsContest(String name, String nameSound,
            boolean sayContestName, boolean soundIn,
            boolean soundOut, int timeout) {
        this.timeOut = timeout < 0 ? 0 : timeout;
        this.sayContestName = sayContestName;
        this.nameSound = nameSound;
        this.processlHandle = ProcessModelHandle.getInstance();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.playSoundWhenIn = soundIn;
        this.playSoundWhenOut = soundOut;
        this.contestModel = new ContestDataModel(name);
        this.contestModelHandle = new ContestModelHandle(contestModel);
        this.conditionBeginHandle = new CheckConditionHandle(this.contestModel);
        this.conditionIntoHandle = new CheckConditionHandle(this.contestModel);
        this.timeOutContest = new TimeOutContest(this);
        this.importantError = new ImportantError();
        this.conditionBeginHandle.addConditon(importantError);
        this.conditionIntoHandle.addConditon(importantError);
        this.conditionIntoHandle.addConditon(timeOutContest);
        this.status = DONE;
        this.stop = false;
    }

    protected double getDetaDistance(double oldDistance) {
        return this.carModel.getDistance() - oldDistance;
    }

    public String getNameSound() {
        return nameSound;
    }

    protected void addErrorCode(String errorKey) {
        this.errorcodeHandle.addContestErrorCode(errorKey, contestModel);
    }

    public boolean isTestCondisionsFailed() {
        boolean st = false;
        if (this.conditionBeginHandle.isTestCondisionsFailed()) {
            st = true;
        }
        if (this.conditionIntoHandle.isTestCondisionsFailed()) {
            st = true;
        }
        return st;
    }

    public ContestDataModel getContestModel() {
        return contestModel;
    }

    public final String getName() {
        return this.contestModel.getContestName();
    }

    public int getStatus() {
        return status;
    }

    protected abstract void init();

    protected abstract boolean loop();

    protected abstract boolean isIntoContest();

    public void stop() {
        this.stop = true;
    }

    @Override
    public long getTestTime() {
        return this.contestModelHandle.getTestTime();
    }

    public Runnable begin() {
        return () -> {
            try {
                this.contestModelHandle.reset();
                this.status = WAIT;
                this.stop = false;
                this.processlHandle.setContest(this);
                this.processlHandle.addContestModel(contestModel);
                init();
                this.conditionBeginHandle.start();
                if (this.sayContestName) {
                    this.soundPlayer.contestName(nameSound);
                }
                while (!isIntoContest() && !stop) {
                    Util.delay(10);
                }
                this.conditionIntoHandle.start();
                if (this.playSoundWhenIn) {
                    this.soundPlayer.startContest();
                }
                this.contestModelHandle.start();
                this.status = RUNNING;
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(AbsContest.this, e);
            }
        };
    }

    public Runnable test() {
        return () -> {
            try {
                status = RUNNING;
                while (!stop && !loop()) {
                    Util.delay(10);
                }
                status = DONE;
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(AbsContest.this, e);
            }
        };
    }

    public void end() {
        try {
            this.conditionBeginHandle.stop();
            this.conditionIntoHandle.stop();
            status = DONE;
            this.contestModelHandle.end();
            if (this.playSoundWhenOut) {
                this.soundPlayer.endContest();
            }
            this.processlHandle.setContest(null);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    public int getTimeout() {
        return timeOut;
    }

    public void setDataTestTransfer(DataTestTransfer dataTestTransfer) {
        this.dataTestTransfer = dataTestTransfer;
    }

}
