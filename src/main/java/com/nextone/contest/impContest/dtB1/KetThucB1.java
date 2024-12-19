/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.dtB1;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;

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
    private boolean checkNp = false;
    private boolean kpt = true;
    private boolean so = true;
    private long oldTime = 0;

    @Override
    protected void init() {
    }

    @Override
    public boolean loop() {
        if (!checkNp && System.currentTimeMillis() - oldTime >= 5000
                && !this.carModel.isNp()) {
            this.addErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END);
            checkNp = true;
        }
        if (this.carModel.getStatus() == ConstKey.CAR_ST.STOP || isStop) {
            this.isStop = true;
            if (!checkNp && !this.carModel.isNp()) {
                this.addErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END);
                checkNp = true;
            }
            if (this.carModel.getGearBoxValue() == 0 && this.carModel.isPt()) {
                return true;
            }
            long deta = System.currentTimeMillis() - oldMill;
            if (so && deta >= 3000 && this.carModel.getGearBoxValue() != 0) {
                this.addErrorCode(ConstKey.ERR.FAILED_SHIFTTO_NEUTRAL);
                so = false;
            }
            if (kpt && deta >= 5000 && !this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.FAILED_APPLY_PARKING_BRAKE);
                kpt = false;
            }
        } else {
            oldMill = System.currentTimeMillis();
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        oldMill = System.currentTimeMillis();
        oldTime = System.currentTimeMillis();
        kpt = true;
        so = true;
        checkNp = false;
        return true;
    }

}
