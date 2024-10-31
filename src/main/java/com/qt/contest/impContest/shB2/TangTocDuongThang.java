/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.impContest.AbsSaHinhContest;

/**
 *
 * @author Admin
 */
public class TangTocDuongThang extends AbsSaHinhContest {

    private final int beginGear;
    private final int speed;
    private boolean hasBeginGearInvaild = false;
    private boolean hasSpeedUp = false;
    private boolean hasGearUp = false;
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
                hasBeginGearInvaild = true;
            }
            firstTime = false;
        }
        if (!hasBeginGearInvaild) {
            check25M();
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

    private void check25M() {
        if (step != 0) {
            return;
        }
        if (this.carModel.getDistance() < 25) {
            if (!hasSpeedUp && this.carModel.getSpeed() > this.speed) {
                hasSpeedUp = true;
            } else if (!hasGearUp && !hasBeginGearInvaild
                    && this.carModel.getGearBoxValue() == beginGear + 1) {
                hasGearUp = true;
            }
        } else {
            if (!hasSpeedUp) {
                addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                step = 1;
            } else if (!hasBeginGearInvaild && !hasGearUp) {
                addErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT);
                step = 1;
            } else {
                step = 2;
            }
        }
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.hasBeginGearInvaild = false;
            this.hasSpeedUp = false;
            this.hasGearUp = false;
            this.firstTime = true;
            this.step = 0;
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

}
