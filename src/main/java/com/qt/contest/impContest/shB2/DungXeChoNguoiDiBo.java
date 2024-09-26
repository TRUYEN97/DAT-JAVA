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
public class DungXeChoNguoiDiBo extends AbsContest {

    private double oldDistance = 0;
    private final double distanceOut;
    private final double distanceLine;

    public DungXeChoNguoiDiBo(double distanceOut, double distanceLine) {
        super(ConstKey.CONTEST_NAME.GIAM_TOC, ConstKey.CONTEST_NAME.GIAM_TOC,
                true, true, true, 200);
        this.distanceOut = distanceOut;
        this.distanceLine = distanceLine;
    }

    @Override
    protected void init() {

    }

    private boolean hasStop = false;

    @Override
    protected boolean loop() {
        double d = getDetaDistance(oldDistance);
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            if (d > distanceLine) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < distanceLine - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.dingDong();
            }
        } else if (d > distanceOut) {
            if (!hasStop) {
                addErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED);
            }
            this.dataTestTransfer.put(ConstKey.CAR_CONFIG.MCU_CONFIG, this.carModel.getDistance());
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        hasStop = false;
        oldDistance = this.carModel.getDistance();
        return this.carModel.isT1();
    }

}