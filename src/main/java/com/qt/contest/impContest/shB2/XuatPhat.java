/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut;
import com.qt.input.serial.MCUSerialHandler;

/**
 *
 * @author Admin
 */
public class XuatPhat extends AbsContest {

    private final CheckTimeOut timeOut30s;
    private final CheckTimeOut timeOut20s;
    private boolean firstCheck = true;
    private double oldDistance = 0;

    public XuatPhat(int speedLimit) {
        super(ConstKey.CONTEST_NAME.XUAT_PHAT, ConstKey.CONTEST_NAME.XUAT_PHAT,
                false,
                true, true, 60);
        this.timeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.OVER_30S_TO_START);
        this.timeOut20s = new CheckTimeOut(null, 20, ConstKey.ERR.OVER_20S_TO_START, false);
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionBeginHandle.addConditon(this.timeOut30s);
        this.conditionBeginHandle.addConditon(this.timeOut20s);
    }

    @Override
    protected boolean loop() {
        if (getDetaDistance(oldDistance) > 0.1 && this.firstCheck) {
            firstCheck = false;
            this.timeOut20s.setPass();
            this.timeOut30s.setPass();
            this.timeOut20s.stop();
            this.timeOut30s.stop();
            if (!this.carModel.isAt()) {
                this.addErrorCode(ConstKey.ERR.SEATBELT_NOT_FASTENED);
            }
            if (!this.carModel.isNt()) {
                this.addErrorCode(ConstKey.ERR.NO_START_SIGNAL_LEFT);
            }
            if (this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.PARKING_BRAKE_NOT_RELEASED);
            }
        }
        if (getDetaDistance(oldDistance) >= 5) {
            if (this.carModel.isNt()) {
                this.addErrorCode(ConstKey.ERR.SIGNAL_KEPT_ON_OVER_5M);
            }
            MCUSerialHandler.getInstance().sendLedOff();
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            firstCheck = true;
            oldDistance = this.carModel.getDistance();
            return true;
        }
        return false;
    }

    @Override
    protected void init() {
        firstCheck = true;
        this.timeOut30s.start();
        this.timeOut20s.start();
    }

}
