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
public class CheckCM extends AbsOnOffCondition {

    @Override
    protected boolean signal() {
        return this.carModel.isCm();
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.ENGINE_STALLED);
    }

}
