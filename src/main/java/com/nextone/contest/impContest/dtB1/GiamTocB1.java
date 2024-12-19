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
public class GiamTocB1 extends AbsContest {

    private double oldDistance = 0;
    private double oldV = 0;

    public GiamTocB1() {
        this(ConstKey.CONTEST_NAME.GIAM_TOC);
    }

    public GiamTocB1(String name) {
        super(name, name, true, false, true, 2000);
    }

    @Override
    public boolean loop() {
        double detaV = oldV - this.carModel.getSpeed();
        if (this.carModel.getDistance() - oldDistance >= 100) {
            if (detaV < 5) {
               addErrorCode(ConstKey.ERR.FAILED_SHIFTDOWN_GEAR_IN_100M);
            }
            return true;
        } else if (detaV >= 5) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        oldDistance = this.carModel.getDistance();
        oldV = this.carModel.getSpeed();
        return true;
    }

    @Override
    protected void init() {
    }

}
