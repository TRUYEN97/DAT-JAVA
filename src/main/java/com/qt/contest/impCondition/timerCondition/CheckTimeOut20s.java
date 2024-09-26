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
    private String errName;

    public CheckTimeOut20s() {
        this(ConstKey.ERR.OVER_20S_TO_START);
    }

    public CheckTimeOut20s(String errName) {
        super(20, true);
        this.errName = errName;
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
        this.setErrorcode(this.errName);
    }

}
