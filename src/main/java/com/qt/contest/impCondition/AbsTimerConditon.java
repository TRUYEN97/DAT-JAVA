/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition;

import com.qt.common.timer.WaitTime.Class.TimeS;
import com.qt.contest.AbsCondition;

/**
 *
 * @author Admin
 */
public abstract class AbsTimerConditon extends AbsCondition {

    protected final TimeS timeS;
    protected boolean firstCheck;

    public AbsTimerConditon() {
        this.timeS = new TimeS();
        this.firstCheck = true;
    }

    @Override
    public boolean checkPassed() {
        if (firstCheck) {
            firstCheck = false;
            this.timeS.start(setTimeOut());
        }
        if (signal()) {
            if (!this.timeS.onTime()) {
                this.timeS.update();
                action();
                return false;
            }
        } else {
            this.timeS.update();
        }
        return true;
    }

    protected abstract int setTimeOut();

    protected abstract boolean signal();

    protected abstract void action();

}
