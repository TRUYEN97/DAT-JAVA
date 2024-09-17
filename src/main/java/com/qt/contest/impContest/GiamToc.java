/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class GiamToc extends AbsContest {

    private double oldDistance = 0;
    private int oldSo = 0;
    private double oldV = 0;
    private boolean hasChaged;

    public GiamToc() {
        this(ConstKey.CT_NAME.GIAM_TOC);
    }

    public GiamToc(String name) {
        super(name, name + "_B2", false, true, 2000);
    }

    @Override
    public boolean loop() {
        int detaG = oldSo - this.carModel.getGearBoxValue();
        double detaV = oldV - this.carModel.getSpeed();
        if (detaG == 1) {
            hasChaged = true;
        }
        if (this.carModel.getDistance() - oldDistance >= 100) {
            if (detaG < 1 || !hasChaged) {
                addErrorCode(ConstKey.ERR.GS);
            } else if (detaV < 5) {
                addErrorCode(ConstKey.ERR.GT);
            }
            return true;
        } else if (hasChaged && detaG >= 1 && detaV >= 5) {
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
