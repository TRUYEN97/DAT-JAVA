/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp;

import com.qt.common.Util;
import com.qt.controller.settingElement.AbsElementSetting;

/**
 *
 * @author Admin
 */
public class ChangePassword extends AbsElementSetting{

    public ChangePassword() {
        super("Đôi mật khẩu", true);
        this.keyBoardFrame.setMaxNum(10);
        this.keyBoardFrame.setRemoveZero(false);
    }

    
    @Override
    public void run() {
        if (this.verifyPassword.check()) {
            this.soundPlayer.inputNewPassword();
            String newPw = this.getInputValue("Mật khẩu mới");
            if (newPw == null) {
                return;
            }
            this.carConfig.setPassword(Util.stringToMd5(newPw));
            this.soundPlayer.changeSucess();
        }
    }
    
}
