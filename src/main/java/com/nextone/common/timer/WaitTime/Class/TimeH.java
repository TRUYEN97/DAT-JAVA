/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nextone.common.timer.WaitTime.Class;

import com.nextone.common.timer.WaitTime.AbsTime;

/**
 *
 * @author Administrator
 */
public class TimeH extends AbsTime {

    public TimeH() {
    }

    public TimeH(double time) {
        super(time);
    }

    @Override
    public double getTimeCurrent() {
        return (double) (System.currentTimeMillis() / (double) 36e6);
    }
}
