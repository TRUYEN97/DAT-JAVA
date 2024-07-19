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
public class CheckPt extends AbsOnOffCondition{

    @Override
    protected boolean signal() {
        return !carModel.isPt();
    }

    @Override
    protected void action() {
        setErrorcode(ConstKey.ERR.KPT);
    }
    
}
