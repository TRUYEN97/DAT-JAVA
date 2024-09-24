/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp;

import com.qt.common.ErrorLog;
import com.qt.controller.settingElement.AbsElementSetting;
import com.qt.controller.settingElement.IElementSetting;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.config.MCU_CONFIG_MODEL;
import com.qt.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public class SettingSignalLinghtDelayTime extends AbsElementSetting implements IElementSetting {

    public SettingSignalLinghtDelayTime() {
        this.keyBoardFrame.setMaxNum(10);
        this.keyBoardFrame.setHasPonit(false);
        this.keyBoardFrame.setRemoveZero(true);
    }

    @Override
    public String getSettingName() {
        return KeyBoardFrame.DELAY_DIGNEL_LIGHT;
    }

    @Override
    public void run() {
        MCU_CONFIG_MODEL mcu_config_model = this.carConfig.getMcuConfig();
        if (mcu_config_model == null) {
            mcu_config_model = new MCU_CONFIG_MODEL();
        }
        String value = getInputValue(getSettingName(), String.valueOf(mcu_config_model.getNp_time()));
        if (value == null) {
            return;
        }
        try {
            Integer delay = Integer.valueOf(value);
            mcu_config_model.setNp_time(delay);
            mcu_config_model.setNt_time(delay);
            this.carConfig.updateMcuConfig(mcu_config_model);
            MCUSerialHandler.getInstance().sendConfig(mcu_config_model);
            this.soundPlayer.changeSucess();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

}
