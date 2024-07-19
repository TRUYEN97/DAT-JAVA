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
public class XuatPhat extends AbsContest {

    public XuatPhat() {
        this(ConstKey.CT_NAME.XUAT_PHAT);
    }

    public XuatPhat(String name) {
        super(name, true);
    }

    private boolean firstCheck = true;

    @Override
    public boolean loop() {
        if (firstCheck) {
            firstCheck();
        }
        return true;
    }

    private void firstCheck() {
        firstCheck = false;
        if (!this.carModel.isAt()) {
            this.errorcodeHandle.addBaseErrorCode(ConstKey.ERR.AT);
        }
        if (!this.carModel.isNt()) {
            this.errorcodeHandle.addBaseErrorCode(ConstKey.ERR.NTP);
        }
        if (this.carModel.isPt()) {
            this.errorcodeHandle.addBaseErrorCode(ConstKey.ERR.PT);
        }
    }

    @Override
    protected boolean isIntoContest() {
        Util.delay(1000);
        return true;
    }

}
