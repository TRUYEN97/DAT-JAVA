/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB1;

import com.qt.common.ConstKey;
import com.qt.contest.impContest.AbsSaHinhContest;

/**
 *
 * @author Admin
 */
public class TangTocDuongThangB1 extends AbsSaHinhContest {

    private final int speed;

    public TangTocDuongThangB1(int speed) {
        super(ConstKey.CONTEST_NAME.THAY_DOI_SO, ConstKey.CONTEST_NAME.THAY_DOI_SO,
                true, true, true, 120);
        this.speed = speed;
    }

    @Override
    protected void init() {
    }

    private int step;
    private boolean hasSpeedUp;

    @Override
    protected boolean loop() {
        if (step == 0) {
            if (this.carModel.getDistance() >= 25) {
                if (!hasSpeedUp) {
                    addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                    this.step = 1;
                } else {
                    this.step = 2;
                }
            } else {
                if (!hasSpeedUp && this.carModel.getSpeed() > this.speed) {
                    hasSpeedUp = true;
                }
            }
        }
        if (this.carModel.getDistance() >= 50) {
            if (this.step == 2) {
                if (this.carModel.getSpeed() > this.speed) {
                    addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            this.step = 0;
            this.hasSpeedUp = false;
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

}
