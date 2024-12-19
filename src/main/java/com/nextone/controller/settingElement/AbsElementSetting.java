/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.settingElement;

import com.nextone.common.CarConfig;
import com.nextone.output.SoundPlayer;
import com.nextone.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public abstract class AbsElementSetting implements IElementSetting{
    protected final SoundPlayer soundPlayer;
    protected final KeyBoardFrame keyBoardFrame;
    protected final CarConfig carConfig;
    protected final VerifyPassword verifyPassword;
    protected String name;
    
    protected AbsElementSetting(String name) {
        this(name, false);
    }

    protected AbsElementSetting(String name, boolean pwMode) {
        this.soundPlayer = SoundPlayer.getInstance();
        this.keyBoardFrame = new KeyBoardFrame(pwMode);
        this.carConfig = CarConfig.getInstance();
        this.verifyPassword = new VerifyPassword();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    

    protected String getInputValue() {
        return this.keyBoardFrame.getValue(getName());
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
