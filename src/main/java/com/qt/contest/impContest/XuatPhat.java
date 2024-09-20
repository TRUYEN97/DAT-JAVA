/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut30s;

/**
 *
 * @author Admin
 */
public class XuatPhat extends AbsContest {

    private final CheckTimeOut30s timeOut30s;

    public XuatPhat() {
        this(ConstKey.CT_NAME.XUAT_PHAT);
    }

    public XuatPhat(String name) {
        super(name, name, false, true, 2000);
        this.timeOut30s = new CheckTimeOut30s();
        this.conditionHandle.addConditon(this.timeOut30s);
    }

    private boolean firstCheck = true;
    private double oldDistance = 0;
    private boolean hasSo1;
    private boolean hasSo2;
    private boolean hasSo3;

    @Override
    public boolean loop() {
        if (getDistance() > 0.1 && this.firstCheck) {
            firstCheck = false;
            if (!this.carModel.isAt()) {
                this.addErrorCode(ConstKey.ERR.AT);
            }
            if (!this.carModel.isNt()) {
                this.addErrorCode(ConstKey.ERR.NO_START_SIGNAL_LEFT);
            }
            if (this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.NPT);
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
                addErrorCode(ConstKey.ERR.TS);
            }
            return true;
        }
        return false;
    }

    private double getDistance() {
        return this.carModel.getDistance() - oldDistance;
    }

    @Override
    protected boolean isIntoContest() {
        hasSo1 = false;
        hasSo2 = false;
        hasSo3 = false;
        firstCheck = true;
        oldDistance = this.carModel.getDistance();
        this.timeOut30s.resetTimer();
        return true;
    }

}
