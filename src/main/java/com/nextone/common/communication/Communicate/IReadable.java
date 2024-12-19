/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nextone.common.communication.Communicate;

import com.nextone.common.timer.WaitTime.AbsTime;

/**
 *
 * @author Administrator
 */
public interface IReadable extends IgetName{
    
    void stopRead();
    
    StringBuffer getStringResult();
    
    String readLine();
    
    String readLine(AbsTime tiker);

    String readAll();

    String readAll(AbsTime tiker);

    String readUntil(AbsTime tiker, String... keywords);

    String readUntil(String... keywords);
}
