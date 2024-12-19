/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impContest.AbsSaHinhContest;
import com.nextone.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public abstract class AbsConstestJustOneLine extends AbsSaHinhContest {

    protected final ContestConfig contestConfig;
    private final double intoDis;

    public AbsConstestJustOneLine(String name, int timeout, ContestConfig contestConfig) {
        super(name, true, timeout);
        this.contestConfig = contestConfig;
        this.intoDis = 3;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.contestConfig != null
                && this.carModel.getDistance() > this.contestConfig.getDistanceUpperLimit()) {
            addErrorCode(ConstKey.ERR.WRONG_WAY);
            this.importantError.setIsImportantError();
            this.stop();
            return false;
        }
        if (this.carModel.getDistance() >= this.intoDis && isAccept()
                && checkDistanceIntoContest()) {
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

    protected abstract boolean isAccept();

    private boolean checkDistanceIntoContest() {
        double d = this.carModel.getDistance();
        if (d < this.contestConfig.getDistanceLowerLimit()
                || d > this.contestConfig.getDistanceUpperLimit()) {
            addErrorCode(ConstKey.ERR.WRONG_LANE);
            stop();
            return false;
        }
        return true;
    }

}
