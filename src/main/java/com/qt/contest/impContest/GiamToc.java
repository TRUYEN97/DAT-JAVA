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
public class GiamToc extends AbsContest {

    private double oldDistance = 0;
    private double oldSo = 0;
    private double oldV = 0;

    public GiamToc() {
        this(ConstKey.CT_NAME.GIAM_TOC);
    }

    public GiamToc(String name) {
        super(name, true, 200);
    }

    @Override
    public boolean loop() {
        if (this.carModel.getDistance() - oldDistance >= 100) {
            if ( oldSo - this.carModel.getGearBoxValue() == 1) {
                addErrorCode(ConstKey.ERR.TS);
            } else if ( oldV - this.carModel.getSpeed1() >= 5) {
                addErrorCode(ConstKey.ERR.TT);
            }
            return true;
        } else if (oldSo - this.carModel.getGearBoxValue() == 1
                && oldV - this.carModel.getSpeed1() >= 5) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        Util.delay(2000);
        oldDistance = this.carModel.getDistance();
        oldSo = this.carModel.getGearBoxValue();
        oldV = this.carModel.getSpeed1();
        return true;
    }

}
