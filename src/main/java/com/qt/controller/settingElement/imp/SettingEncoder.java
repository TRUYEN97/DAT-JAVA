/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp;

import com.qt.common.ErrorLog;
import com.qt.controller.settingElement.AbsElementSetting;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.config.MCU_CONFIG_MODEL;
import com.qt.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public class SettingEncoder extends AbsElementSetting {

    public SettingEncoder() {
        this.keyBoardFrame.setMaxNum(10);
        this.keyBoardFrame.setHasPonit(true);
        this.keyBoardFrame.setRemoveZero(true);
    }

    @Override
    public String getSettingName() {
        return KeyBoardFrame.SO_ENCODER;
    }

    @Override
    public void run() {
        MCU_CONFIG_MODEL mcu_config_model = this.carConfig.getMcuConfig();
        if (mcu_config_model == null) {
            mcu_config_model = new MCU_CONFIG_MODEL();
        }
        String value = getInputValue(getSettingName(), String.valueOf(mcu_config_model.getEncoder()));
        if (value == null) {
            return;
        }
        try {
            Double encoderScale = Double.valueOf(value);
            mcu_config_model.setEncoder(encoderScale);
            this.carConfig.updateMcuConfig(mcu_config_model);
            MCUSerialHandler.getInstance().sendConfig(mcu_config_model);
            this.soundPlayer.changeSucess();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

}
