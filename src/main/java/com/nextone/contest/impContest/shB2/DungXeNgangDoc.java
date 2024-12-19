/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.contest.impCondition.timerCondition.CheckTimeOut;
import com.nextone.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class DungXeNgangDoc extends AbsConstestJustOneLine {

    private boolean hasStop = false;
    private boolean rollBack = false;
    private double distanceWhenStop = 0;
    private final CheckTimeOut checkTimeOut30s;

    public DungXeNgangDoc(ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.DUNG_XE_ND, 120, contestConfig);
        this.checkTimeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.OVER_30S_TO_START);
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected void init() {
    }

    @Override
    public void end() {
        this.checkTimeOut30s.stop();
        super.end();
    }

    @Override
    protected boolean loop() {
        double d = this.carModel.getDistance();
        if (!rollBack && hasStop && getDetaDistance(distanceWhenStop) < -0.5) {
            addErrorCode(ConstKey.ERR.ROLLED_BACK_OVER_50M);
            rollBack = true;
        }
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            distanceWhenStop = this.carModel.getDistance();
            if (d > contestConfig.getDistanceLine()) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES_2);
            } else if (d < contestConfig.getDistanceLine() - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.successSound();
            }
            this.checkTimeOut30s.start();
        } else {
            if (d > contestConfig.getDistanceLine() + 2 && hasStop) {
                this.checkTimeOut30s.setPass();
            }
            if (d > contestConfig.getDistanceOut()) {
                if (!hasStop) {
                    addErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED);
                }
                this.checkTimeOut30s.setPass();
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isAccept() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.hasStop = false;
            this.rollBack = false;
            return true;
        }
        return false;
    }

}
