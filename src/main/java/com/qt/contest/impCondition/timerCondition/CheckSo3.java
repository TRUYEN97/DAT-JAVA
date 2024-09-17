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
public class CheckSo3 extends AbsTimerConditon {

    public CheckSo3() {
        super(3);
    }

    @Override
    protected boolean signal() {
        if (this.carModel.getGearBoxValue() >= 3) {
            return this.carModel.getSpeed() < 20;
        }
        return false;
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.SO3);
    }

}
