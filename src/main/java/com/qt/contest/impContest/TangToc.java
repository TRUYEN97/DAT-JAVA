/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.timerCondition.CheckSo3;

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
        this(ConstKey.CT_NAME.TANG_TOC);
        this.conditionHandle.addConditon(new CheckSo3());
        this.hasChaged = false;
    }

    public TangToc(String name) {
        super(name, name + "_B2", false, true, 2000);
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
                addErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT);
            } else if (detaV < 5) {
                addErrorCode(ConstKey.ERR.TT);
            }
            return true;
        } else if (hasChaged && detaS >= 1 && detaV >= 5) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        Util.delay(2000);
        hasChaged = false;
        oldDistance = this.carModel.getDistance();
        oldSo = this.carModel.getGearBoxValue();
        oldV = this.carModel.getSpeed();
        return true;
    }

}
