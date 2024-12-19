/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nextone.common.communication.socket.Unicast.commons.Interface;



/**
 *
 * @author Administrator
 */
public interface IReceiver<T> {
    
    void receiver(T commnunication, String data);
}
