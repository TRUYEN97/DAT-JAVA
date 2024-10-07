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
public class CheckTimeOut30s extends AbsTimerConditon {

    private String errName;
    private boolean pass = false;

    public CheckTimeOut30s() {
        this(ConstKey.ERR.OVER_30S_TO_START);
    }

    public CheckTimeOut30s(String errName) {
        super(30, true);
        setImporttant(true);
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
        this.setErrorcode(errName);
    }

}
