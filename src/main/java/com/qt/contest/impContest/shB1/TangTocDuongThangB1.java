/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB1;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class TangTocDuongThangB1 extends AbsContest {

    private final int speed;
    private double oldDistance;
    private boolean firstTime;

    public TangTocDuongThangB1(int speed) {
        super(ConstKey.CONTEST_NAME.THAY_DOI_SO, ConstKey.CONTEST_NAME.THAY_DOI_SO,
                true, true, true, 120);
        this.speed = speed;
    }

    @Override
    protected void init() {
    }

    private boolean firstPass;

    @Override
    protected boolean loop() {
        if (firstTime) {
            if (getDetaDistance(oldDistance) >= 25) {
                firstTime = false;
                if (this.carModel.getSpeed() <= this.speed) {
                    addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
                }else {
                    this.firstPass = true;
                }
            }
        }
        if (this.firstPass && getDetaDistance(oldDistance) >= 50) {
            if (this.carModel.getSpeed() > this.speed) {
                addErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED);
            } else{
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            this.firstPass = false;
            this.firstTime = true;
            this.oldDistance = this.carModel.getDistance();
            return true;
        }
        return false;
    }

}
