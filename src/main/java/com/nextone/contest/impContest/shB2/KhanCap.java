/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.common.Util;
import com.nextone.contest.AbsContest;
import com.nextone.input.serial.MCUSerialHandler;

/**
 *
 * @author Admin
 */
public class KhanCap extends AbsContest {

    private long oldTime;
    private boolean firstDone;
    private final double distanceBegin;
    private final WarningSoundPlayer warningSoundPlayer;
    private double oldDistance;
    private final MCUSerialHandler uSerialHandler;

    public KhanCap(double distanceBegin) {
        super(ConstKey.CONTEST_NAME.KHAN_CAP,
                ConstKey.CONTEST_NAME.KHAN_CAP, false, false, false, 1200);
        this.distanceBegin = distanceBegin;
        this.uSerialHandler = MCUSerialHandler.getInstance();
        this.warningSoundPlayer = new WarningSoundPlayer();
    }

    @Override
    protected void init() {
        this.oldDistance = this.carModel.getDistance();
    }

    private boolean success = false;

    @Override
    protected boolean loop() {
        if (getDetaTime() < 5000) {
            if (this.carModel.isNp() && this.carModel.isNt()) {
                if (this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                    this.warningSoundPlayer.stop();
                    addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
                    return true;
                } else {
                    success = true;
                }
            } else {
                success = false;
            }
            if (!success && getDetaTime() > 3000) {
                this.warningSoundPlayer.stop();
                addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
                return true;
            }
        } else {
            this.warningSoundPlayer.stop();
            if (!this.firstDone) {
                Util.delay(200);
                this.firstDone = true;
                this.soundPlayer.successSound();
            }
            if (getDetaTime() > 9000 || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                addErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL);
                return true;
            }
            if (!this.carModel.isNp() && !this.carModel.isNt()) {
                return true;
            }
        }
        return false;
    }

    private long getDetaTime() {
        return System.currentTimeMillis() - oldTime;
    }

    @Override
    public void end() {
        this.warningSoundPlayer.stop();
        super.end(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected boolean isIntoContest() {
        if (getDetaDistance(oldDistance) >= distanceBegin) {
            firstDone = false;
            success = false;
            this.warningSoundPlayer.start();
            oldTime = System.currentTimeMillis();
            this.oldDistance = this.carModel.getDistance();
            return true;
        }
        return false;
    }

    class WarningSoundPlayer {

        Thread thread;
        boolean stop = false;

        void start() {
            if (thread == null || !thread.isAlive()) {
                thread = new Thread(() -> {
                    stop = false;
                    while (!stop) {
                        uSerialHandler.sendLedRedOn();
                        soundPlayer.alarm();
                        uSerialHandler.sendLedRedOff();
                        Util.delay(50);
                    }
                });
                thread.start();
            }
        }

        void stop() {
            stop = true;
            if (this.thread != null && this.thread.isAlive()) {
                this.thread.stop();
            }
        }
    }

}
