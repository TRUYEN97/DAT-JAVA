/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.controller.api.ApiService;
import com.qt.controller.modeController.ModeManagement;
import com.qt.main.Core;
import com.qt.model.input.UserModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.SoundPlayer;
import com.qt.view.frame.KeyBoardFrame;
import com.qt.view.frame.UserInfoFrame;

/**
 *
 * @author Admin
 */
public class ChangeUserId implements Runnable {

    private final SoundPlayer soundPlayer;
    private final ApiService apiService;
    private final ProcessModel processModel;
    private final KeyBoardFrame keyBoardFrame;
    private final UserInfoFrame infoFrame;

    public ChangeUserId() {
        this.soundPlayer = SoundPlayer.getInstance();
        this.apiService = ApiService.getInstance();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
        this.keyBoardFrame = new KeyBoardFrame();
        this.infoFrame = new UserInfoFrame();
    }

    @Override
    public void run() {
        this.soundPlayer.inputId();
        String value = this.keyBoardFrame.getValue(KeyBoardFrame.SBD);
        if (value == null) {
            return;
        }
        this.soundPlayer.sayChecking();
        UserModel userModel = this.apiService.checkUserId(value, this.processModel.getCarId());
        String id;
        if (ProcessModelHandle.getInstance().isTesting()) {
            this.soundPlayer.canNotChange();
        }
        if (userModel != null && (id = userModel.getId()) != null) {
            String modeName = userModel.getModeName();
            String rank = userModel.getRank();
            ModeManagement modeManagement = Core.getInstance().getModeManagement();
            if (id.equals("0")) {
                ProcessModelHandle.getInstance().setUserModel(userModel);
                this.soundPlayer.practice();
            } else {
                this.infoFrame.setUserModel(userModel);
                if (!this.infoFrame.isAccept()) {
                    return;
                }
                if (ProcessModelHandle.getInstance().isTesting()) {
                    this.soundPlayer.canNotChange();
                } else if (modeName != null && rank != null
                        && modeManagement.updateMode(modeName, rank)) {
                    ProcessModelHandle.getInstance().setUserModel(userModel);
                    this.soundPlayer.welcomeId(id);
                    this.soundPlayer.pleasePrepare();
                } else {
                    ProcessModelHandle.getInstance().setUserModel(new UserModel());
                    this.soundPlayer.modeInvalid();
                }
            }
        } else {
            ProcessModelHandle.getInstance().setUserModel(new UserModel());
            this.soundPlayer.userIdInvalid();
        }
    }

}
