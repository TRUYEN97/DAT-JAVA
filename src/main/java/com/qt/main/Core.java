/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.main;

import com.qt.common.ConstKey;
import com.qt.controller.modeController.ModeManagement;
import com.qt.input.camera.CameraRunner;
import com.qt.input.serial.KeyBoardSerialHandler;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.mode.imp.B2_DT;
import com.qt.view.ViewMain;

/**
 *
 * @author Admin
 */
public class Core {

    private final ModeManagement modeManagement;
    private final ViewMain viewMain;
    private final CameraRunner cameraRunner;

    public Core() {
        this.viewMain = new ViewMain();
        this.cameraRunner = CameraRunner.getInstance();
        this.cameraRunner.setCamera(0);
        this.modeManagement = new ModeManagement(viewMain);
        addMode();
    }

    private void addMode() {
        this.modeManagement.putMode(ConstKey.KEY_BOARD.MODE.B2_DT_OFF,
                new B2_DT());
    }

    public void start() {
        KeyBoardSerialHandler.getInstance().start();
        MCUSerialHandler.getInstance().start();
        this.cameraRunner.start();
        this.viewMain.display();
        this.modeManagement.start();
    }

}
