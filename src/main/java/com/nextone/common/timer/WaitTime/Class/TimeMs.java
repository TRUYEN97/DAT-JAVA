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
public class TimeMs extends AbsTime {

    public TimeMs() {
    }
    

    public TimeMs(double time) {
        super(time);
    }

    @Override
    public double getTimeCurrent() {
        return System.currentTimeMillis();
    }
}
