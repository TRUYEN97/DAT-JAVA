/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.settingElement.imp;

import com.nextone.controller.ProcessModelHandle;
import com.nextone.controller.api.ApiService;
import com.nextone.controller.modeController.ModeManagement;
import com.nextone.controller.settingElement.AbsElementSetting;
import com.nextone.main.Core;
import com.nextone.model.input.UserModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.view.frame.KeyBoardFrame;
import com.nextone.view.frame.UserInfoFrame;

/**
 *
 * @author Admin
 */
public class ChangeUserId extends AbsElementSetting {
    
    private final ApiService apiService;
    private final ProcessModel processModel;
    private final UserInfoFrame infoFrame;
    
    public ChangeUserId() {
        super(KeyBoardFrame.SBD);
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

}
