/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.OnOffImp;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.AbsTimerConditon;

/**
 *
 * @author Admin
 */
public class CheckOverSpeedLimit extends AbsTimerConditon {

    private final int specSpeed;

    public CheckOverSpeedLimit(int specSpeed) {
        super(3, false);
        this.specSpeed = specSpeed;
    }

    @Override
    protected boolean signal() {
        return this.carModel.getSpeed() > specSpeed;
    }

    @Override
    protected void action() {
        setErrorcode(ConstKey.ERR.SPEED_LIMIT_EXCEEDED);
    }

}
