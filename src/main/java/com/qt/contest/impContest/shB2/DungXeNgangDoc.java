/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut30s;
import com.qt.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class DungXeNgangDoc extends AbsContest {

    private boolean hasStop = false;
    private double oldDistance = 0;
    private double distanceWhenStop = 0;
    private final CheckTimeOut30s checkTimeOut30s;
    private final double distanceOut;
    private final double distanceLine;

    public DungXeNgangDoc(ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.DUNG_XE_ND, ConstKey.CONTEST_NAME.DUNG_XE_ND,
                true, true, true, 120);
        this.checkTimeOut30s = new CheckTimeOut30s();
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
        double d = getDetaDistance(oldDistance);
        if (hasStop && getDetaDistance(distanceWhenStop) < -0.5) {
            addErrorCode(ConstKey.ERR.ROLLED_BACK_OVER_50M);
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
            this.checkTimeOut30s.setOldDisTance(distanceWhenStop);
            this.checkTimeOut30s.start();
        } else if (d > distanceOut) {
            if (!hasStop) {
                addErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            this.hasStop = false;
            this.oldDistance = this.carModel.getDistance();
            this.dataTestTransfer.put(ConstKey.DATA_TRANSFER.OLD_DISTANCE, this.oldDistance);
            return true;
        }
        return false;
    }

}
