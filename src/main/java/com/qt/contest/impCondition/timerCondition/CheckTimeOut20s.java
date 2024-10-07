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

    private boolean pass = false;
    private String errName;

    public CheckTimeOut20s() {
        this(ConstKey.ERR.OVER_20S_TO_START);
    }

    public CheckTimeOut20s(String errName) {
        super(20, true);
        this.errName = errName;
    }

    
    public void setPass() {
        this.pass = true;
    }

    @Override
    protected boolean signal() {
        return !pass;
    }
    @Override
    protected void action() {
        this.setErrorcode(this.errName);
    }

}
