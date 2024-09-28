/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.input.serial.MCUSerialHandler;

/**
 *
 * @author Admin
 */
public class KhanCap extends AbsContest {

    private long oldTime;
    private boolean hasStop;
    private boolean firstDone;
    private final double distanceBegin;
    private double oldDistance;
    private final MCUSerialHandler uSerialHandler;

    public KhanCap(double distanceBegin) {
        super(ConstKey.CONTEST_NAME.KHAN_CAP,
                ConstKey.CONTEST_NAME.KHAN_CAP, false, false, false, 1200);
        this.distanceBegin = distanceBegin;
        this.uSerialHandler = MCUSerialHandler.getInstance();
    }

    @Override
    protected void init() {
        this.oldDistance = this.carModel.getDistance();
    }

    @Override
    protected boolean loop() {
        if (getDetaTime() < 5000) {
            this.uSerialHandler.sendLedRedOn();
            this.soundPlayer.alarm();
            this.uSerialHandler.sendLedOff();
            if (!hasStop && this.carModel.isNp() && this.carModel.isNt()) {
                if (this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                    addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
                    return true;
                } else {
                    this.hasStop = true;
                }
            }
            if (getDetaTime() > 3000 && !hasStop) {
                addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
                return true;
            }
        } else if (this.hasStop && getDetaTime() < 3000) {
            this.uSerialHandler.sendLedOff();
            if (!this.firstDone) {
                Util.delay(200);
                this.firstDone = true;
                this.soundPlayer.successSound();
            }
            if (!this.carModel.isNp() && !this.carModel.isNt()) {
                if (this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                    addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
                }
                return true;
            }
        } else {
            addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
            return true;
        }
        return false;
    }

    private long getDetaTime() {
        return System.currentTimeMillis() - oldTime;
    }

    @Override
    protected boolean isIntoContest() {
        if (getDetaDistance(oldDistance) >= distanceBegin) {
            hasStop = false;
            firstDone = false;
            oldTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
