/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.timerCondition;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.AbsTimerConditon;

/**
 *
 * @author Admin
 */
public class TimeOutContest extends AbsTimerConditon {

    private final AbsContest contest;

    public TimeOutContest(AbsContest contest) {
        super(contest.getTimeout(), false);
        this.contest = contest;
        this.setContestDataModel(contest.getContestModel());
    }

    @Override
    protected boolean signal() {
        AbsContest a = contest;
        return a != null && a.getStatus() == AbsContest.RUNNING;
    }

    @Override
    protected void action() {
        setErrorcode(ConstKey.ERR.TIME_LIMIT_EXCEEDED);
    }

}
