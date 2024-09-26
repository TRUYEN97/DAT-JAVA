/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.socket;

import com.qt.common.communication.socket.Unicast.Client.SocketClient;
import com.qt.common.communication.socket.Unicast.commons.Interface.IReceiver;

/**
 *
 * @author Admin
 */
public class SocketHandle extends SocketClient{
    
    public SocketHandle(String host, int port, IReceiver objectAnalysis) {
        super(host, port, objectAnalysis);
    }
    
}
