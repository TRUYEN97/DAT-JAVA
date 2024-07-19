/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition;

import com.qt.common.ConstKey;

/**
 *
 * @author Admin
 */
public class CheckSo3 extends AbsTimerConditon {

    @Override
    protected int setTimeOut() {
        return 3;
    }

    @Override
    protected boolean signal() {
        return this.carModel.getGearBoxValue() >= 3 && this.carModel.getSpeed1() < 20;
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.SO3);
    }


    

}
