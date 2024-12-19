/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.view;

import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class AbsKeylistenerFrame extends JFrame {

    protected final KeyListener keyListener;
    
    public AbsKeylistenerFrame() {
        keyListener = new KeyListener() {
            private final CarModel carModel = MCUSerialHandler.getInstance().getModel();

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                String keyText = KeyEvent.getKeyText(code);
                if (keyText == null || keyText.isBlank()) {
                    return;
                }
                if (keyText.startsWith("NumPad-") || keyText.startsWith("NumPad ")) {
                    this.carModel.setRemoteValue(keyText.substring(7).trim());
                    return;
                }
                if (keyText.equalsIgnoreCase("clear")) {
                    this.carModel.setRemoteValue("begin");
                    return;
                }
                this.carModel.setRemoteValue(keyText);
            }
        };
        this.addKeyListener(keyListener);
    }

}
