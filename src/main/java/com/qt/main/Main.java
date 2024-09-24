/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.main;

import com.qt.common.ErrorLog;
import com.qt.input.serial.KeyBoardSerialHandler;
import com.qt.input.serial.MCUSerialHandler;

/**
 *
 * @author Admin
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            KeyBoardSerialHandler.getInstance().start();
            MCUSerialHandler.getInstance().start();
            Core.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError("main", e);
            System.exit(1);
        }
    }
}
