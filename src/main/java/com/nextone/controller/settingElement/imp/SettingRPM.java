/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.settingElement.imp;

import com.nextone.common.ErrorLog;
import com.nextone.controller.settingElement.AbsElementSetting;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.config.MCU_CONFIG_MODEL;
import com.nextone.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public class SettingRPM extends AbsElementSetting {

    public SettingRPM() {
        super(KeyBoardFrame.SO_RPM);
        this.keyBoardFrame.setMaxNum(10);
        this.keyBoardFrame.setHasPonit(true);
        this.keyBoardFrame.setRemoveZero(true);
    }

    @Override
    public void run() {
        MCU_CONFIG_MODEL mcu_config_model = this.carConfig.getMcuConfig();
        if (mcu_config_model == null) {
            mcu_config_model = new MCU_CONFIG_MODEL();
        }
        String value = getInputValue(getName(), String.valueOf(mcu_config_model.getRpm()));
        if (value == null) {
            return;
        }
        try {
            Double rpmScale = Double.valueOf(value);
            mcu_config_model.setRpm(rpmScale);
            this.carConfig.updateMcuConfig(mcu_config_model);
            if (MCUSerialHandler.getInstance().sendConfig(mcu_config_model)) {
                this.soundPlayer.changeSucess();
            }else{
                this.soundPlayer.canNotChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

}
