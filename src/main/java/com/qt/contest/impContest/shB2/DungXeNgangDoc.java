/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut;
import com.qt.contest.impContest.AbsSaHinhContest;
import com.qt.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class DungXeNgangDoc extends AbsSaHinhContest {

    private boolean hasStop = false;
    private boolean rollBack = false;
    private double distanceWhenStop = 0;
    private final CheckTimeOut checkTimeOut30s;
    private final double distanceOut;
    private final double distanceLine;

    public DungXeNgangDoc(ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.DUNG_XE_ND, ConstKey.CONTEST_NAME.DUNG_XE_ND,
                true, true, true, 120);
        this.checkTimeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.OVER_30S_TO_START);
        this.distanceOut = contestConfig.getDistanceOut();
        this.distanceLine = contestConfig.getDistanceLine();
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
            if (d > distanceLine) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < distanceLine - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.successSound();
            }
            this.checkTimeOut30s.start();
        } else if (d > distanceOut) {
            if (!hasStop) {
                addErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED);
            }
            this.checkTimeOut30s.setPass();
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.hasStop = false;
            this.rollBack = false;
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

}
