/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp;

import com.qt.controller.ProcessModelHandle;
import com.qt.controller.api.ApiService;
import com.qt.controller.modeController.ModeManagement;
import com.qt.controller.settingElement.AbsElementSetting;
import com.qt.main.Core;
import com.qt.model.input.UserModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.view.frame.KeyBoardFrame;
import com.qt.view.frame.UserInfoFrame;

/**
 *
 * @author Admin
 */
public class ChangeUserId extends AbsElementSetting {
    
    private final ApiService apiService;
    private final ProcessModel processModel;
    private final UserInfoFrame infoFrame;
    
    public ChangeUserId() {
        this.apiService = new ApiService();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
        this.infoFrame = new UserInfoFrame();
    }
    
    @Override
    public void run() {
        this.soundPlayer.inputId();
        String value = getInputValue();
        if (value == null) {
            return;
        }
        if (ProcessModelHandle.getInstance().isTesting()) {
            this.soundPlayer.canNotChange();
        }
        if (!value.isBlank() && !value.equals("0")) {
            this.soundPlayer.sayChecking();
            UserModel userModel = this.apiService.checkUserId(value, this.processModel.getCarId());
            String id;
            if (userModel != null && (id = userModel.getId()) != null) {
                String modeName = userModel.getModeName();
                String rank = userModel.getRank();
                ModeManagement modeManagement = Core.getInstance().getModeManagement();
                this.infoFrame.setUserModel(userModel);
                if (!this.infoFrame.isAccept()) {
                    return;
                }
                if (ProcessModelHandle.getInstance().isTesting()) {
                    this.soundPlayer.canNotChange();
                } else if (modeName != null && rank != null
                        && modeManagement.isMode(modeName, rank)) {
                    ProcessModelHandle.getInstance().setUserModel(userModel);
//                    this.soundPlayer.welcomeId(id);
                    this.soundPlayer.pleasePrepare();
                } else {
                    ProcessModelHandle.getInstance().setUserModel(new UserModel());
                    this.soundPlayer.modeInvalid();
                }
            } else {
                ProcessModelHandle.getInstance().setUserModel(new UserModel());
                this.soundPlayer.userIdInvalid();
            }
        } else {
            UserModel model = new UserModel();
            model.setId("0");
            ProcessModelHandle.getInstance().setUserModel(model);
            this.soundPlayer.practice();
        }
    }

    @Override
    public String getSettingName() {
        return KeyBoardFrame.SBD;
    }
    
}
