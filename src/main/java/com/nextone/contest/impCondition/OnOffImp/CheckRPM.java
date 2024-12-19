/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.OnOffImp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.AbsOnOffCondition;

/**
 *
 * @author Admin
 */
public class CheckRPM extends AbsOnOffCondition {

    @Override
    protected boolean signal() {
        return this.carModel.getRpm() > 4000;
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.ENGINE_SPEED_EXCEEDED_4000_RPM);
    }

}
