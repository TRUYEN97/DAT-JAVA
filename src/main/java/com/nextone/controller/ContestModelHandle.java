/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import com.nextone.interfaces.IgetTime;
import com.nextone.common.Util;
import com.nextone.common.timer.TimeBase;
import com.nextone.model.modelTest.contest.ContestDataModel;

/**
 *
 * @author Admin
 */
public class ContestModelHandle implements IgetTime {

    private final ContestDataModel contestModel;
    private final TimeBase timeBase;
    private long startMs;
    private long endMs;
    private long cysleTime;

    public ContestModelHandle(ContestDataModel contestModel) {
        this.contestModel = contestModel;
        this.timeBase = new TimeBase();
        this.startMs = 0;
        this.endMs = 0;
        this.cysleTime = -1;
    }

    public String getName() {
        return this.contestModel.getContestName();
    }

    public ContestDataModel getContestModel() {
        return contestModel;
    }

    public void reset() {
        this.contestModel.setStartTime("");
        this.contestModel.setEndTime("");
        this.contestModel.clear();
        this.startMs = 0;
        this.endMs = 0;
        this.cysleTime = -1;
    }

    public void start() {
        this.contestModel.setStartTime(timeBase.getSimpleDateTime());
        this.startMs = System.currentTimeMillis();
    }

    @Override
    public long getTestTime() {
        if (cysleTime >= 0) {
            return cysleTime;
        }
        return Util.getTestTime(startMs, endMs);
    }

    public void end() {
        this.endMs = System.currentTimeMillis();
        this.cysleTime = Util.getTestTime(startMs, endMs);
        this.contestModel.setEndTime(timeBase.getSimpleDateTime());
        this.contestModel.setCycleTime(this.cysleTime);
    }
}
