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
    protected final boolean playSoundWhenIn;
    protected final boolean playSoundWhenOut;
    protected final CheckConditionHandle conditionHandle;
    protected final String nameSound;
    protected int status;
    protected boolean stop;
    protected int timeOut;

    public AbsContest(String name, String nameSound, boolean soundIn, boolean soundOut, int timeout) {
        this.nameSound = name;
        this.processlHandle = ProcessModelHandle.getInstance();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.playSoundWhenIn = soundIn;
        this.playSoundWhenOut = soundOut;
        this.contestModel = new ContestDataModel(name);
        this.contestModelHandle = new ContestModelHandle(contestModel);
        this.conditionHandle = new CheckConditionHandle();
        this.status = DONE;
        this.stop = false;
        this.timeOut = timeout < 0 ? 0 : timeout;
    }

    public String getNameSound() {
        return nameSound;
    }
    
    protected void addErrorCode(String errorKey) {
        this.errorcodeHandle.addContestErrorCode(errorKey, contestModel);
    }

    public boolean checkTestCondisions() {
        return this.conditionHandle.checkTestCondisions();
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
            while (!isIntoContest() && !stop) {
                Util.delay(50);
            }
            this.contestModelHandle.start();
            this.soundPlayer.contestName(nameSound);
            if (this.playSoundWhenIn) {
                this.soundPlayer.startContest();
            }
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
        if (this.playSoundWhenOut) {
            this.soundPlayer.endContest();
        }
        this.processlHandle.setContest(null);
    }

    public int getTimeout() {
        return timeOut;
    }

}
