/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.process;

import com.qt.contest.AbsContest;
import com.qt.interfaces.IgetTime;

/**
 *
 * @author Admin
 */
public class TestDataViewModel implements IgetTime {

    private final IgetTime igetTime;
    private final ProcessModel processModel;
    private String modeFullName;
    private AbsContest contest;

    public TestDataViewModel(IgetTime igetTime, ProcessModel processModel) {
            this.igetTime = igetTime;
            this.processModel = processModel;
    }

    public String getModeFullName() {
        return modeFullName;
    }

    public void setModeFullName(String modeFullName) {
        this.modeFullName = modeFullName;
    }

    public void setContest(AbsContest contest) {
        this.contest = contest;
    }

    public String getId() {
        if (processModel == null) {
            return null;
        }
        return processModel.getId();
    }

    public String getCarId() {
        if (processModel == null) {
            return null;
        }
        return processModel.getCarId();
    }

    public String getModeName() {
        if (processModel == null) {
            return null;
        }
        return processModel.getModeName();
    }

    public AbsContest getContest() {
        return contest;
    }

    public int getScore() {
        if (processModel == null) {
            return 0;
        }
        return this.processModel.getScore();
    }

    @Override
    public long getTestTime() {
        if (igetTime == null) {
            return 0;
        }
        return igetTime.getTestTime();
    }

}
