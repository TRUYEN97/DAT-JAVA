/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.contest.impContest.AbsSaHinhContest;

/**
 *
 * @author Admin
 */
public class KetThuc extends AbsSaHinhContest {

    public KetThuc(int speedLimit) {
        super(ConstKey.CONTEST_NAME.KET_THUC, false, 2000);
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean loop() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
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
