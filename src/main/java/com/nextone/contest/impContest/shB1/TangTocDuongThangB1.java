/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB1;

import com.nextone.common.ConstKey;
import com.nextone.contest.impContest.shB2.AbsConstestJustOneLine;
import com.nextone.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class TangTocDuongThangB1 extends AbsConstestJustOneLine {

    private final int speed;

    public TangTocDuongThangB1(int speed, ContestConfig contestConfig) {
        super(ConstKey.CONTEST_NAME.THAY_DOI_SO, 120, contestConfig);
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
    protected boolean isAccept() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.step = 0;
            this.hasSpeedUp = false;
            return true;
        }
        return false;
    }

}
