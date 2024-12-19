/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.dtB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class TangToc extends AbsContest {

    private double oldDistance = 0;
    private int oldSo = 0;
    private double oldV = 0;
    private boolean hasChaged;

    public TangToc() {
        this(ConstKey.CONTEST_NAME.TANG_TOC);
        this.hasChaged = false;
    }

    public TangToc(String name) {
        super(name, name + "_B2", true, false, true, 2000);
    }

    @Override
    protected void init() {
    }

    @Override
    public boolean loop() {
        int detaS = this.carModel.getGearBoxValue() - oldSo;
        double detaV = this.carModel.getSpeed() - oldV;
        if (detaS == 1) {
            hasChaged = true;
        }
        if (this.carModel.getDistance() - oldDistance >= 100) {
            if (detaS < 1 || !hasChaged) {
                addErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_100M);
            } else if (detaV < 5) {
                addErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_100M);
            }
            return true;
        } else if (hasChaged && detaS >= 1 && detaV >= 5) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        hasChaged = false;
        oldDistance = this.carModel.getDistance();
        oldSo = this.carModel.getGearBoxValue();
        oldV = this.carModel.getSpeed();
        return true;
    }

}
