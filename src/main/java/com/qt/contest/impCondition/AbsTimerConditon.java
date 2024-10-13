/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition;

import com.qt.contest.AbsCondition;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public abstract class AbsTimerConditon extends AbsCondition {

    protected final Timer timer;
    private boolean timeout;

    public AbsTimerConditon(int spec, boolean justOneTime) {
        this.timeout = false;
        this.timer = new Timer(spec * 1000, (e) -> {
            if (signal()) {
                timeout = true;
                if (important) {
                    hasFail = true;
                }
                if (justOneTime) {
                    stop();
                }
                action();
            } else {
                timeout = false;
                resetTimer();
            }
        });
    }

    public void setSpec(int spec) {
        this.timer.setDelay(spec * 1000);
    }

    @Override
    public void start() {
        super.start();
        this.timeout = false;
        this.timer.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.timer.stop();
    }

    @Override
    protected boolean checkCondition() {
        return stop || !timeout;
    }

    public void resetTimer() {
        if (this.timer.isRunning()) {
            this.timer.restart();
        }
    }

    protected abstract boolean signal();

    protected abstract void action();

}
