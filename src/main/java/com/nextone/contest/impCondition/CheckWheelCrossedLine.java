/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsCondition;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class CheckWheelCrossedLine extends AbsCondition {

    protected final Timer timer;
    private final ConditionActionListener actionListener;

    public CheckWheelCrossedLine(int spec, ConditionActionListener actionListener) {
        this.actionListener = actionListener;
        this.timer = new Timer(spec * 1000, (e) -> {
            if (stop) {
                stop();
                return;
            }
            setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
        });
    }

    @Override
    public void start() {
        super.start(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void stop() {
        this.timer.stop();
        super.stop(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected boolean checkCondition() {
        if (actionListener.activate()) {
            if (!stop && !this.timer.isRunning()) {
                this.timer.start();
                setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
            }
            return false;
        } else {
            this.timer.stop();
        }
        return true;
    }

}
