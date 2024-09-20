/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.controller.api.ApiService;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.SoundPlayer;
import com.qt.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public class ChangeCarId implements Runnable {

    private final SoundPlayer soundPlayer;
    private final ApiService apiService;
    private final ProcessModel processModel;
    private final KeyBoardFrame keyBoardFrame;

    public ChangeCarId() {
        this.soundPlayer = SoundPlayer.getInstance();
        this.apiService = ApiService.getInstance();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
        this.keyBoardFrame = new KeyBoardFrame();
    }

    @Override
    public void run() {
        this.soundPlayer.inputCarId();
        String value = this.keyBoardFrame.getValue(KeyBoardFrame.SX);
        if (value == null) {
            return;
        }
        this.soundPlayer.sayChecking();
        if (this.apiService.checkCarId(value)) {
            if (ProcessModelHandle.getInstance().isTesting()) {
                this.soundPlayer.canNotChange();
            } else {
                ProcessModelHandle.getInstance().setCarID(value);
                this.soundPlayer.welcomeCarId(this.processModel.getCarId());
            }
        } else {
            this.soundPlayer.carIdInvalid();
        }
    }

}
