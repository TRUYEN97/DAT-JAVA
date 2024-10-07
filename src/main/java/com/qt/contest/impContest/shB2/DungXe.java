/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class DungXe extends AbsContest {

    private double oldDistance = 0;
    private final double distanceOut;
    private final double distanceLine;

    public DungXe(ContestConfig contestConfig, int speedLimit) {
        this(ConstKey.CONTEST_NAME.DUNG_XE_CNDB, contestConfig, speedLimit);
    }

    public DungXe(String name, ContestConfig contestConfig, int speedLimit) {
        super(name, name, true, true, true, 200);
        this.distanceOut = contestConfig.getDistanceOut();
        this.distanceLine = contestConfig.getDistanceLine();
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected void init() {

    }

    private boolean hasStop = false;

    @Override
    protected boolean loop() {
        double d = getDetaDistance(oldDistance);
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            if (d > distanceLine) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < distanceLine - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.successSound();
            }
        } else if (d > distanceOut) {
            if (!hasStop) {
                addErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED);
            }
            this.dataTestTransfer.put(ConstKey.CAR_CONFIG.MCU_CONFIG, this.carModel.getDistance());
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1() && this.carModel.getStatus() == ConstKey.CAR_ST.FORWARD) {
            hasStop = false;
            oldDistance = this.carModel.getDistance();
            return true;
        }
        return false;
    }

}
