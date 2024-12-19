/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.settingElement;

import com.nextone.common.CarConfig;
import com.nextone.common.Util;
import com.nextone.output.SoundPlayer;
import com.nextone.view.frame.KeyBoardFrame;

/**
 *
 * @author Admin
 */
public class VerifyPassword {

    protected final SoundPlayer soundPlayer;
    protected final KeyBoardFrame keyBoardFrame;
    protected final CarConfig carConfig;

    public VerifyPassword() {
        this.soundPlayer = SoundPlayer.getInstance();
        this.keyBoardFrame = new KeyBoardFrame(true);
        this.carConfig = CarConfig.getInstance();
        this.keyBoardFrame.setMaxNum(10);
        this.keyBoardFrame.setHasPonit(false);
        this.keyBoardFrame.setRemoveZero(false);
    }

    public boolean check() {
        this.soundPlayer.inputPassword();
        String input = keyBoardFrame.getValue(KeyBoardFrame.PASSWORD);
        if (input == null) {
            return false;
        }
        String pw = carConfig.getPassword();
        String pwMd5 = Util.stringToMd5(input);
        if (pw != null && !pw.equals(pwMd5)) {
            this.soundPlayer.wrongPassword();
            return false;
        }
        return true;

    }

}
