/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut20s;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut30s;
import com.qt.model.input.yard.TrafficLightModel;
import com.qt.model.input.yard.YardModel;
import com.qt.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class NgaTu extends AbsContest {

    private final CheckTimeOut20s checkTimeOut20s;
    private final CheckTimeOut30s checkTimeOut30s;
    private final TrafficLightModel trafficLightModel;
    private final double distanceOut;
    private final double distanceLine;
    private final int times;

    public NgaTu(int times, YardModel yardModel, ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.NGAT_TU, ConstKey.CONTEST_NAME.NGAT_TU, true, true, true, 120);
        this.times = times;
        this.checkTimeOut20s = new CheckTimeOut20s(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_20S);
        this.checkTimeOut30s = new CheckTimeOut30s(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_30S);
        if (times == 1 || times == 3) {
            this.trafficLightModel = yardModel.getTrafficLightModel1();
        } else {
            this.trafficLightModel = yardModel.getTrafficLightModel();
        }
        this.distanceOut = contestConfig.getDistanceOut();
        this.distanceLine = contestConfig.getDistanceLine();
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected void init() {
    }
    private boolean hasStop = false;
    private double oldDistance = 0;

    @Override
    public void end() {
        super.end();
        this.checkTimeOut20s.stop();
        this.checkTimeOut30s.stop();
    }

    private boolean ranRedLight;
    private boolean dontTurnOnNt;
    private boolean dontTurnOnNp;
    private int lightStatus;

    @Override
    protected boolean loop() {
        double d = getDetaDistance(oldDistance);
        if (d >= distanceLine) {
            checkCondition();
            if (checkEndTest()) {
                this.checkTimeOut20s.setPass();
                this.checkTimeOut30s.setPass();
                return true;
            }
        }
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            d = getDetaDistance(oldDistance);
            if (d >= distanceLine) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < distanceLine - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.successSound();
            }
        }
        return false;
    }

    private boolean checkEndTest() {
        double d = getDetaDistance(oldDistance);
        if (times == 0 || times == 3) {
            if (this.carModel.isT1() && this.carModel.getStatus() == ConstKey.CAR_ST.BACKWARD) {
                return true;
            }
            if (d >= distanceOut) {
                addErrorCode(ConstKey.ERR.WRONG_WAY);
                return true;
            }
        } else {
            if (this.carModel.isT1()) {
                addErrorCode(ConstKey.ERR.WRONG_WAY);
                return true;
            }
            if (d >= distanceOut) {
                return true;
            }
        }
        return false;
    }

    private void checkCondition() {
        if (lightStatus != 1) {
            if (this.trafficLightModel.getTrafficLight() == TrafficLightModel.GREEN) {
                this.checkTimeOut20s.start();
                this.checkTimeOut30s.start();
                lightStatus = 1;
            } else {
                lightStatus = 0;
            }
        }
        if (!ranRedLight && lightStatus == 0) {
            addErrorCode(ConstKey.ERR.RAN_A_RED_LIGHT);
            ranRedLight = true;
        }
        if (!this.dontTurnOnNt && times == 3 && !this.carModel.isNt()) {
            addErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_LEFT);
            this.dontTurnOnNt = true;
        }
        if (!this.dontTurnOnNp && times == 4 && !this.carModel.isNp()) {
            addErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_RIGHT);
            this.dontTurnOnNp = true;
        }
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            this.dontTurnOnNt = false;
            this.dontTurnOnNp = false;
            this.ranRedLight = false;
            this.lightStatus = -1;
            this.oldDistance = this.carModel.getDistance();
            this.dataTestTransfer.put(ConstKey.DATA_TRANSFER.OLD_DISTANCE, this.oldDistance);
            return true;
        }
        return false;
    }

}
