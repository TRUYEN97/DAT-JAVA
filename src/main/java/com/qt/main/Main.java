/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.main;

import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Main {

    public static void main(String[] args) {
        try {
            new Core().start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            System.exit(1);
        }
    }
}
