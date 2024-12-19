/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.contest.impCondition.timerCondition.CheckTimeOut;
import com.nextone.contest.impContest.AbsSaHinhContest;

/**
 *
 * @author Admin
 */
public class XuatPhat extends AbsSaHinhContest {

    private final CheckTimeOut timeOut30s;
    private final CheckTimeOut timeOut20s;
    private boolean firstCheck = true;

    public XuatPhat(int speedLimit) {
        super(ConstKey.CONTEST_NAME.XUAT_PHAT, "begin", true, 60);
        this.timeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.OVER_30S_TO_START);
        this.timeOut20s = new CheckTimeOut(null, 20, ConstKey.ERR.OVER_20S_TO_START, false);
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected boolean loop() {
        if (this.carModel.getDistance() > 0.1 && this.firstCheck) {
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
        if (this.carModel.getDistance() >= 5) {
            if (this.carModel.isNt()) {
                this.addErrorCode(ConstKey.ERR.SIGNAL_KEPT_ON_OVER_5M);
            }
            this.mcuSerialHandler.sendLedOff();
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            firstCheck = true;
            this.carModel.setDistance(0);
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
