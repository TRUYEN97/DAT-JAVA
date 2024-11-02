/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut;
import com.qt.contest.impContest.AbsSaHinhContest;
import com.qt.model.input.yard.TrafficLightModel;
import com.qt.model.input.yard.YardModel;
import com.qt.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class NgaTu extends AbsSaHinhContest {

    private final CheckTimeOut checkTimeOut20s;
    private final CheckTimeOut checkTimeOut30s;
    private final TrafficLightModel trafficLightModel;
    private final double distanceOut;
    private final double distanceLine;
    private final int times;

    public NgaTu(int times, YardModel yardModel, ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.NGAT_TU, ConstKey.CONTEST_NAME.NGAT_TU, true, true, true, 120);
        this.times = times;
        this.checkTimeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_30S);
        this.checkTimeOut20s = new CheckTimeOut(null, 20, ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_20S, false);
        if (times == 1 || times == 3) {
            this.trafficLightModel = yardModel.getTrafficLightModel1();
        } else {
            this.trafficLightModel = yardModel.getTrafficLightModel();
        }
        this.distanceOut = contestConfig.getDistanceOut();
        this.distanceLine = contestConfig.getDistanceLine();
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    private boolean hasStop = false;

    @Override
    public void end() {
        super.end();
        this.checkTimeOut20s.stop();
        this.checkTimeOut30s.stop();
    }

    private boolean ranRedLight;
    private boolean dontTurnOnNt;
    private boolean dontTurnOnNp;
    private boolean firstTime;
    private int lightStatus;

    @Override
    protected boolean loop() {
        double d = this.carModel.getDistance();
        if (d > distanceLine) {
            if (firstTime) {
                firstTime = false;
                checkCondition();
            }
            if (checkEndTest()) {
                this.checkTimeOut20s.setPass();
                this.checkTimeOut30s.setPass();
                return true;
            }
        }
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            d = this.carModel.getDistance();
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
        double d = this.carModel.getDistance();
        if (times == 2 || times == 3) {
            if (this.carModel.isT1() || this.carModel.isT2()) {
                return true;
            }
            if (d >= distanceOut) {
                addErrorCode(ConstKey.ERR.WRONG_WAY);
                return true;
            }
        } else {
            if (this.carModel.isT1() || this.carModel.isT2()) {
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
        this.checkTimeOut20s.start();
        this.checkTimeOut30s.start();
        if (lightStatus != 1) {
            if (this.trafficLightModel.getTrafficLight() == TrafficLightModel.GREEN) {
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
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.dontTurnOnNt = false;
            this.dontTurnOnNp = false;
            this.ranRedLight = false;
            this.firstTime = true;
            this.lightStatus = -1;
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

    @Override
    protected void init() {
    }

}
