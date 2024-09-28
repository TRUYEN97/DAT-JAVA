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
    private double oldDistance;
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

    private boolean firstPass;

    @Override
    protected boolean loop() {
        if (firstTime) {
            if (!hasBeginGear && this.carModel.getGearBoxValue() == this.beginGear) {
                hasBeginGear = true;
            }
            if (getDetaDistance(oldDistance) >= 25) {
                firstTime = false;
                if (this.carModel.getSpeed() <= this.speed) {
                    addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                } else if (!hasBeginGear || this.carModel.getGearBoxValue() != beginGear + 1) {
                    addErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT);
                } else {
                    this.firstPass = true;
                }
            }
        }
        if (this.firstPass && getDetaDistance(oldDistance) >= 50) {
            if (this.carModel.getSpeed() > this.speed) {
                addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
            } else if (this.carModel.getGearBoxValue() != beginGear) {
                addErrorCode(ConstKey.ERR.INCORRECT_GEAR_DOWNSHIFT);
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            this.hasBeginGear = false;
            this.firstPass = false;
            this.firstTime = true;
            this.oldDistance = this.carModel.getDistance();
            return true;
        }
        return false;
    }

}
