/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nextone.common.communication.Communicate;

import java.io.Closeable;

/**
 *
 * @author Administrator
 */
public interface ISender extends Closeable, IgetName{
    
    boolean sendCtrl_C();
    
    boolean sendCommand(String command, Object... params);
    
    boolean insertCommand(String command, Object... params);
}
