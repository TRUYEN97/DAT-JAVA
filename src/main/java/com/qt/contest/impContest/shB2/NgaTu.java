/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut20s;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut30s;
import com.qt.model.input.yard.YardRankModel;

/**
 *
 * @author Admin
 */
public class NgaTu extends AbsContest {

    private final CheckTimeOut20s checkTimeOut20s;
    private final CheckTimeOut30s checkTimeOut30s;
    private final YardRankModel yardRankModel;
    private final double distanceOut;
    private final double distanceLine;
    private final int times;

    public NgaTu(int times, YardRankModel yardRankModel, double distanceOut, double distanceLine) {
        super(ConstKey.CONTEST_NAME.NGAT_TU, ConstKey.CONTEST_NAME.NGAT_TU, true, true, true, 120);
        this.times = times;
        this.checkTimeOut20s = new CheckTimeOut20s(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_20S);
        this.checkTimeOut30s = new CheckTimeOut30s(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_30S);
        this.yardRankModel = yardRankModel;
        this.distanceOut = distanceOut;
        this.distanceLine = distanceLine;
    }

    @Override
    protected void init() {
    }

    private boolean hasGreenLight = false;
    private boolean hasStop = false;
    private double oldDistance = 0;

    @Override
    public void end() {
        super.end();
        this.checkTimeOut20s.stop();
        this.checkTimeOut30s.stop();
    }

    @Override
    protected boolean loop() {
        if (!hasGreenLight) {
            if (times == 1 || times == 3) {
                if (this.yardRankModel.getTrafficLight() == YardRankModel.GREEN) {
                    this.checkTimeOut20s.setOldDisTance(this.carModel.getDistance());
                    this.checkTimeOut30s.setOldDisTance(this.carModel.getDistance());
                    this.checkTimeOut20s.start();
                    this.checkTimeOut30s.start();
                    hasGreenLight = true;
                }
            } else {
                if (this.yardRankModel.getTrafficLight1() == YardRankModel.GREEN) {
                    this.checkTimeOut20s.setOldDisTance(this.carModel.getDistance());
                    this.checkTimeOut30s.setOldDisTance(this.carModel.getDistance());
                    this.checkTimeOut20s.start();
                    this.checkTimeOut30s.start();
                    hasGreenLight = true;
                }
            }
        }
        double d = getDetaDistance(oldDistance);
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            if (d > distanceLine) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < distanceLine - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.dingDong();
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
        if (this.carModel.isT1()) {
            oldDistance = this.carModel.getDistance(); 
            this.dataTestTransfer.put(ConstKey.DATA_TRANSFER.OLD_DISTANCE, this.oldDistance);
            return true;
        }
        return false;
    }

}
