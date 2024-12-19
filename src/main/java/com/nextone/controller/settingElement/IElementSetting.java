/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nextone.controller.settingElement;

import com.nextone.common.communication.Communicate.IgetName;

/**
 *
 * @author Admin
 */
public interface IElementSetting extends Runnable, IgetName{

    void close();
    
}
