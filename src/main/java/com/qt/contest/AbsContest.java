/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest;

import com.qt.common.Util;
import com.qt.controller.CheckConditionHandle;
import com.qt.controller.ContestModelHandle;
import com.qt.controller.ErrorcodeHandle;
import com.qt.interfaces.IgetTime;
import com.qt.controller.ProcessModelHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.input.CarModel;
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
    protected final boolean playSoundWhenInOut;
    protected final CheckConditionHandle conditionHandle;
    protected int status;
    protected boolean stop;
    protected int timeOut;

    public AbsContest(String name, boolean soundInOut, int timeout) {
        this.processlHandle = ProcessModelHandle.getInstance();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.playSoundWhenInOut = soundInOut;
        this.contestModel = new ContestDataModel(name);
        this.contestModelHandle = new ContestModelHandle(contestModel);
        this.conditionHandle = new CheckConditionHandle();
        this.status = DONE;
        this.stop = false;
        this.timeOut = timeout < 0 ? 0 : timeout;
    }

    protected void addErrorCode(int errorKey) {
        this.errorcodeHandle.addContestErrorCode(errorKey, contestModel);
    }

    public boolean checkTestCondisions() {
        return this.conditionHandle.checkTestCondisions();
    }

    public ContestDataModel getContestModel() {
        return contestModel;
    }

    public final String getName() {
        return this.contestModel.getName();
    }

    public int getStatus() {
        return status;
    }

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
            this.status = WAIT;
            this.stop = false;
            this.processlHandle.setContest(this);
            this.soundPlayer.contestName(getName());
            while (!isIntoContest() && !stop) {
                Util.delay(50);
            }
            if (this.playSoundWhenInOut) {
                this.soundPlayer.startContest();
            }
            this.contestModelHandle.start();
            this.status = RUNNING;
        };
    }

    public Runnable test() {
        return () -> {
            status = RUNNING;
            while (!stop && !loop()) {
                Util.delay(50);
            }
            status = DONE;
        };
    }

    public void end() {
        status = DONE;
        this.contestModelHandle.end();
        this.processlHandle.addContestModel(contestModel);
        if (this.playSoundWhenInOut) {
            this.soundPlayer.endContest();
        }
        this.processlHandle.setContest(null);
    }

    public int getTimeout() {
        return timeOut;
    }

}
