/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class KetThuc extends AbsContest {

    public KetThuc() {
        this(ConstKey.CT_NAME.KET_THUC);
    }

    public KetThuc(String name) {
        super(name, true, 200);
    }
    private boolean firstCheck = true;
    private long oldMill;
    private boolean isStop = false;
    private boolean kpt = true;
    private boolean so = true;

    @Override
    public boolean loop() {
        if (firstCheck) {
            firstCheck = false;
            if (!this.carModel.isNp()) {
                this.addErrorCode(ConstKey.ERR.NTP);
            }
        }
        if (this.carModel.getStatus() == ConstKey.CAR_ST.STOP || isStop) {
            this.isStop = true;
            long deta = System.currentTimeMillis() - oldMill;
            if (so && deta >= 3 && this.carModel.getGearBoxValue() != 0) {
                this.addErrorCode(ConstKey.ERR.VSO);
                so = false;
            }
            if (kpt && deta >= 5 && !this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.KPT);
                kpt = false;
                return true;
            }
        } else {
            oldMill = System.currentTimeMillis();
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        firstCheck = true;
        Util.delay(3000);
        oldMill = System.currentTimeMillis();
        return true;
    }

}
