/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.dtB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.contest.impCondition.timerCondition.CheckTimeOut;
import com.nextone.input.serial.MCUSerialHandler;

/**
 *
 * @author Admin
 */
public class XuatPhat extends AbsContest {

    private final CheckTimeOut timeOut30s;

    public XuatPhat() {
        this(ConstKey.CONTEST_NAME.XUAT_PHAT);
    }

    public XuatPhat(String name) {
        super(name, name, false, false, true, 2000);
        this.timeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.OVER_30S_TO_START);
        this.conditionIntoHandle.addConditon(timeOut30s);
    }

    private boolean firstCheck = true;
    private double oldDistance = 0;
    private boolean hasSo1;
    private boolean hasSo2;
    private boolean hasSo3;

    @Override
    protected void init() {
        hasSo1 = false;
        hasSo2 = false;
        hasSo3 = false;
    }

    @Override
    public boolean loop() {
        if (getDistance() > 0.5 && this.firstCheck) {
            this.timeOut30s.setPass();
            this.timeOut30s.stop();
            this.firstCheck = false;
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
        int so = this.carModel.getGearBoxValue();
        switch (so) {
            case 1 -> {
                hasSo1 = true;
            }
            case 2 -> {
                hasSo2 = true;
            }
            case 3 -> {
                hasSo3 = true;
            }
        }
        if (getDistance() >= 15) {
            if (so < 3 || !hasSo1 || !hasSo2 || !hasSo3) {
                addErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_15M);
            }
            MCUSerialHandler.getInstance().sendLedOff();
            this.timeOut30s.stop();
            return true;
        }
        return false;
    }

    private double getDistance() {
        return this.carModel.getDistance() - oldDistance;
    }

    @Override
    protected boolean isIntoContest() {
        firstCheck = true;
        oldDistance = this.carModel.getDistance();
        this.timeOut30s.start();
        return true;
    }

}
