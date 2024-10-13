/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp;

import com.qt.controller.ProcessModelHandle;
import com.qt.controller.api.ApiService;
import com.qt.controller.settingElement.AbsElementSetting;
import com.qt.controller.settingElement.IElementSetting;
import com.qt.model.modelTest.process.ProcessModel;

/**
 *
 * @author Admin
 */
public class ChangeCarId extends AbsElementSetting implements IElementSetting {

    private final ApiService apiService;
    private final ProcessModel processModel;

    public ChangeCarId() {
        this.apiService = new ApiService();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
    }

    @Override
    public void run() {
        this.soundPlayer.inputCarId();
        String value = getInputValue();
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

    @Override
    public String getSettingName() {
        return "Số Xe";
    }


}
