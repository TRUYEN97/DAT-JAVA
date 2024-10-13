/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition;

import com.qt.common.ConstKey;
import com.qt.contest.AbsCondition;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class CheckWheelCrossedLine extends AbsCondition {

    protected final Timer timer;
    private boolean hasFailed;
    private final ConditionActionListener actionListener;

    public CheckWheelCrossedLine(int spec, ConditionActionListener actionListener) {
        this.actionListener = actionListener;
        this.hasFailed = false;
        this.timer = new Timer(spec * 1000, (e) -> {
            setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
        });
    }

    @Override
    public void start() {
        this.timer.start();
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
            if (!hasFailed) {
                hasFailed = true;
                this.timer.restart();
                setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
            }
            return false;
        } else {
            hasFailed = false;
            this.timer.restart();
        }
        return true;
    }

}
