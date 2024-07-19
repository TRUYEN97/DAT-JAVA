/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest;

import com.qt.controller.ContestModelHandle;
import com.qt.controller.ErrorCodeHandle;
import com.qt.interfaces.IgetTime;
import com.qt.controller.ProcessModelHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.input.CarModel;
import com.qt.output.SoundPlayer;
import javax.swing.Timer;
import lombok.Getter;

@Getter
public abstract class AbsContest implements IgetTime {

    private final ProcessModelHandle processlHandle;
    protected final CarModel carModel;
    protected final ContestDataModel contestModel;
    protected final SoundPlayer soundPlayer;
    protected final ContestModelHandle contestModelHandle;
    protected final ErrorCodeHandle errorcodeHandle;
    protected final boolean playSoundWhenInOut;
    protected boolean done;

    public AbsContest(String name, boolean soundInOut) {
        this.processlHandle = ProcessModelHandle.getInstance();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.errorcodeHandle = ErrorCodeHandle.getInstance();
        this.playSoundWhenInOut = soundInOut;
        this.contestModel = new ContestDataModel(name);
        this.contestModelHandle = new ContestModelHandle(contestModel);
        this.done = false;
    }

    public ContestDataModel getContestModel() {
        return contestModel;
    }

    public final String getName() {
        return this.contestModel.getName();
    }

    public final String getClassName() {
        return getClass().getSimpleName();
    }

    public boolean isDone() {
        return done;
    }

    public abstract boolean loop();

    protected abstract boolean isIntoContest();

    public boolean isStart() {
        if (isIntoContest()) {
            this.done = false;
            this.contestModelHandle.start();
            return true;
        }
        return false;
    }

    @Override
    public long getTestTime() {
        return this.contestModelHandle.getTestTime();
    }

    public void end() {
        this.done = true;
        this.contestModelHandle.end();
        this.processlHandle.addContestModel(contestModel);
    }
}
