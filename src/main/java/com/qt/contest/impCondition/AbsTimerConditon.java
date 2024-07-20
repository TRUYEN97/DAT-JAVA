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

    public AbsTimerConditon() {
        this(0);
    }
    
    public AbsTimerConditon(int spec) {
        this.timeS = new TimeS(spec);
    }
    
    public void setSpec(int spec){
        this.timeS.setSpec(spec);
    }

    @Override
    public boolean checkPassed() {
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

    public void resetTimer() {
        this.timeS.update();
    }

    protected abstract boolean signal();

    protected abstract void action();

}
