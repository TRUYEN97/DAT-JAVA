/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import com.nextone.output.SoundPlayer;
import com.nextone.output.printer.Printer;
import com.nextone.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public class PrintWithKeyBoard implements Runnable{
    private final KeyBoardFrame keyBoardFrame;
    private final Printer printer;
    private final SoundPlayer soundPlayer;

    public PrintWithKeyBoard() {
        this.keyBoardFrame = new KeyBoardFrame();
        this.printer = new Printer();
        this.soundPlayer = SoundPlayer.getInstance();
    }

    @Override
    public void run() {
        this.soundPlayer.inputId();
        String val = this.keyBoardFrame.getValue(KeyBoardFrame.SBD);
        if (val == null) {
            return;
        }
        this.printer.printTestResult(val);
    }
    
}
