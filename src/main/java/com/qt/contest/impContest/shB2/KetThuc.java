/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;

/**
 *
 * @author Admin
 */
public class KetThuc extends AbsContest {

    public KetThuc(int speedLimit) {
        super(ConstKey.CONTEST_NAME.KET_THUC, ConstKey.CONTEST_NAME.KET_THUC, true, true, true, 2000);
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean loop() {
        if (this.carModel.isT1()) {
            if (!this.carModel.isNp()) {
                addErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        return true;
    }

}
