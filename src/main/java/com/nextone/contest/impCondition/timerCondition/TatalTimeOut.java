/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.timerCondition;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.AbsTimerConditon;
import com.nextone.model.modelTest.process.ProcessModel;

/**
 *
 * @author Admin
 */
public class TatalTimeOut extends AbsTimerConditon {

    private final ProcessModel processModel;
    public TatalTimeOut(int time, ProcessModel processModel) {
        super(time, false);
        this.processModel = processModel;
    }

    @Override
    protected boolean signal() {
        return this.processModel.getContestsResult() == ProcessModel.RUNNING;
    }

    @Override
    protected void action() {
        setSpec(3);
        setErrorcode(ConstKey.ERR.OVERALL_TIME_EXCEEDED);
    }

}
