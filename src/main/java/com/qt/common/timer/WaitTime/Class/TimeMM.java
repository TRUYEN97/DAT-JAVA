/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qt.common.timer.WaitTime.Class;

import com.qt.common.timer.WaitTime.AbsTime;


/**
 *
 * @author Administrator
 */
public class TimeMM extends AbsTime {

    public TimeMM() {
    }
    

    public TimeMM(double time) {
        super(time);
    }

    @Override
    public double getTimeCurrent() {
        return (double)(System.currentTimeMillis() /(double) 6e4);
    }
}