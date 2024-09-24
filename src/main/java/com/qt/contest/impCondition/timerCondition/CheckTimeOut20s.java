/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.timerCondition;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.AbsTimerConditon;

/**
 *
 * @author Admin
 */
public class CheckTimeOut20s extends AbsTimerConditon {

    private double oldDisTance = 0;
    public CheckTimeOut20s() {
        super(20, true);
    }

    public void setOldDisTance(double oldDisTance) {
        this.oldDisTance = oldDisTance;
    }

    @Override
    protected boolean signal() {
        return this.carModel.getDistance() - oldDisTance < 1;
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.OVER_20S_TO_START);
    }


    

}
