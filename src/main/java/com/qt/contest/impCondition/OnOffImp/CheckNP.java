/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.OnOffImp;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.AbsOnOffCondition;


/**
 *
 * @author Admin
 */
public class CheckNP extends AbsOnOffCondition{

    @Override
    protected boolean signal() {
        return !this.carModel.isNp();
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.NO_SIGNAL_TURN_RIGHT);
    }
    
}
