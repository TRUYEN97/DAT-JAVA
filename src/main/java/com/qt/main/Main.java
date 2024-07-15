/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.main;

import com.qt.input.serial.MCUSerialHandler;
import com.qt.controller.Core;
import com.qt.model.input.YardModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Main {

    public static void main(String[] args) {
        try {
            MCUSerialHandler.getInstance().start();
            new Core(MCUSerialHandler.getInstance().getModel(), new YardModel()).start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            System.exit(1);
        }
    }
}
