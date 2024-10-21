/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.timerCondition;

import com.qt.contest.impCondition.AbsTimerConditon;
import com.qt.contest.impCondition.ImportantError;

/**
 *
 * @author Admin
 */
public class CheckTimeOut extends AbsTimerConditon {

    private final String errName;
    private boolean pass = false;

    public CheckTimeOut(ImportantError importantError, int time, String errName) {
        this(importantError, time, errName, true);
    }

    public CheckTimeOut(ImportantError importantError, int time, String errName, boolean isImporttant) {
        super(importantError, time, true);
        setImporttant(isImporttant);
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
        if (errName == null) {
            return;
        }
        this.setErrorcode(errName);
    }

}
