/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement;

import com.qt.common.CarConfig;
import com.qt.output.SoundPlayer;
import com.qt.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public abstract class AbsElementSetting implements IElementSetting{
    protected final SoundPlayer soundPlayer;
    protected final KeyBoardFrame keyBoardFrame;
    protected final CarConfig carConfig;
    protected final VerifyPassword verifyPassword;
    
    protected AbsElementSetting() {
        this(false);
    }

    protected AbsElementSetting(boolean pwMode) {
        this.soundPlayer = SoundPlayer.getInstance();
        this.keyBoardFrame = new KeyBoardFrame(pwMode);
        this.carConfig = CarConfig.getInstance();
        this.verifyPassword = new VerifyPassword();
    }

    protected String getInputValue() {
        return this.keyBoardFrame.getValue(getSettingName());
    }
    
    protected String getInputValue(String name, String value) {
        return this.keyBoardFrame.getValue(name, value);
    }
    
    protected String getInputValue(String name) {
        return this.keyBoardFrame.getValue(name);
    }
    
    @Override
    public void close() {
        this.keyBoardFrame.cancel();
    }
}
