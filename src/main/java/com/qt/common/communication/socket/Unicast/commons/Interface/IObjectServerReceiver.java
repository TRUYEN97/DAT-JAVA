/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qt.common.communication.socket.Unicast.commons.Interface;

import com.qt.common.communication.socket.Unicast.Server.ClientHandler;

/**
 *
 * @author Administrator
 */
public interface IObjectServerReceiver {

    void receiver(ClientHandler handler, String data);
}
