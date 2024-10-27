/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class TangTocDuongThang extends AbsContest {

    private final int beginGear;
    private final int speed;
    private boolean hasBeginGear;
    private boolean firstTime;

    public TangTocDuongThang(int beginGear, int speed) {
        super(ConstKey.CONTEST_NAME.THAY_DOI_SO, ConstKey.CONTEST_NAME.THAY_DOI_SO,
                true, true, true, 120);
        this.beginGear = beginGear;
        this.speed = speed;
    }

    @Override
    protected void init() {
    }

    private int step = 0;

    @Override
    protected boolean loop() {
        if (firstTime) {
            if (this.carModel.getGearBoxValue() != this.beginGear) {
                addErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT);
                hasBeginGear = true;
            }
            firstTime = false;
        }
        if (this.carModel.getDistance() >= 25 && step == 0) {
            if (this.carModel.getSpeed() <= this.speed) {
                addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                step = 1;
            } else if (!hasBeginGear && this.carModel.getGearBoxValue() != beginGear + 1) {
                addErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT);
                step = 1;
            } else {
                step = 2;
            }
        }
        if (this.carModel.getDistance() >= 50) {
            if (step == 2) {
                if (this.carModel.getSpeed() > this.speed) {
                    addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                } else if (this.carModel.getGearBoxValue() != beginGear) {
                    addErrorCode(ConstKey.ERR.INCORRECT_GEAR_DOWNSHIFT);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.hasBeginGear = false;
            this.firstTime = true;
            this.step = 0;
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

}
