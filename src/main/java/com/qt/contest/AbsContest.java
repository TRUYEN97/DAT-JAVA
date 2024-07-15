/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest;

import com.qt.controller.ContestModelHandle;
import com.qt.controller.ErrorcodeHandle;
import com.qt.interfaces.IgetTime;
import com.qt.controller.ProcessModelHandle;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.modelTest.contest.ContestParam;
import com.qt.model.input.CarModel;
import com.qt.model.input.YardModel;
import com.qt.output.SoundPlayer;
import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public abstract class AbsContest implements IgetTime {

    private final ProcessModelHandle processlHandle;
    protected final CarModel mcuModel;
    protected final YardModel yardModel;
    protected final ContestDataModel contestModel;
    protected final SoundPlayer soundPlayer;
    protected final ContestModelHandle contestModelHandle;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final boolean playSoundWhenInOut;
    protected boolean done;

    public AbsContest(ContestParam contestParam, String name, boolean soundInOut) {
        this.processlHandle = contestParam.getProcessModelHandle();
        this.mcuModel = contestParam.getCarModel();
        this.yardModel = contestParam.getYardModel();
        this.soundPlayer = contestParam.getSoundPlayer();
        this.errorcodeHandle = contestParam.getErrorcodeHandle();
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
