/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.dtB1;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class KetThucB1 extends AbsContest {

    public KetThucB1() {
        this(ConstKey.CONTEST_NAME.KET_THUC);
    }

    public KetThucB1(String name) {
        super(name, name, true, false, true, 2000);
    }
    private long oldMill;
    private boolean isStop = false;
    private boolean kpt = true;
    private boolean so = true;

    @Override
    protected void init() {
    }

    @Override
    public boolean loop() {
        if (this.carModel.getStatus() == ConstKey.CAR_ST.STOP || isStop) {
            this.isStop = true;
            if (!this.carModel.isNp()) {
                this.addErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END);
            }
            long deta = System.currentTimeMillis() - oldMill;
            if (so && deta >= 3000 && this.carModel.getGearBoxValue() != 0) {
                this.addErrorCode(ConstKey.ERR.FAILED_SHIFTTO_NEUTRAL);
                so = false;
            }
            if (kpt && deta >= 5000 && !this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.FAILED_APPLY_PARKING_BRAKE);
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
        oldMill = System.currentTimeMillis();
        return true;
    }

}
