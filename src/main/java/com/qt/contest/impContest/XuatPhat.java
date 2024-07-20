/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut20s;
import com.qt.contest.impCondition.timerCondition.CheckTimeOut30s;

/**
 *
 * @author Admin
 */
public class XuatPhat extends AbsContest {

    private final CheckTimeOut20s timeOut20s;
    private final CheckTimeOut30s timeOut30s;

    public XuatPhat() {
        this(ConstKey.CT_NAME.XUAT_PHAT);
    }

    public XuatPhat(String name) {
        super(name, true, 200);
        this.timeOut20s = new CheckTimeOut20s();
        this.timeOut30s = new CheckTimeOut30s();
    }

    private boolean firstCheck = true;
    private double oldDistance = 0;
    private boolean hasSo1;
    private boolean hasSo2;
    private boolean hasSo3;

    @Override
    public boolean loop() {
        this.timeOut20s.checkPassed();
        this.timeOut30s.checkPassed();
        if (firstCheck) {
            firstCheck = false;
            if (!this.carModel.isAt()) {
                this.addErrorCode(ConstKey.ERR.AT);
            }
            if (!this.carModel.isNt()) {
                this.addErrorCode(ConstKey.ERR.NTP);
            }
            if (this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.PT);
            }
        }
        if (this.carModel.getDistance() - oldDistance >= 15) {
            addErrorCode(ConstKey.ERR.TS);
            return true;
        } else if (this.carModel.getGearBoxValue() >= 3
                && hasSo1 && hasSo2 && hasSo3) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        hasSo1 = false;
        hasSo2 = false;
        hasSo3 = false;
        firstCheck = true;
        Util.delay(3000);
        oldDistance = this.carModel.getDistance();
        this.timeOut20s.resetTimer();
        this.timeOut30s.resetTimer();
        return true;
    }

}
