/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.settingElement.imp.yardSetting;

import com.nextone.common.YardConfig;
import com.nextone.controller.settingElement.IElementSetting;
import com.nextone.model.yardConfigMode.YardConfigModel;
import com.nextone.view.frame.MenuShowRoom;

/**
 *
 * @author Admin
 */
public class YardConfigSetting implements IElementSetting {

    private final YardConfigModel yardConfigModel;
    private MenuShowRoom<IElementSetting> menuShowRoom;

    public YardConfigSetting() {
        this.yardConfigModel = YardConfig.getInstance().getYardConfigModel();
    }

    @Override
    public void close() {
        if (menuShowRoom != null) {
            menuShowRoom.close();
            menuShowRoom = null;
        }
    }

    @Override
    public void run() {
        this.menuShowRoom = new MenuShowRoom<>(IElementSetting.class,
                (t) -> {
                    if (t == null) {
                        return false;
                    }
                    t.run();
                    return true;
                },
                (t) -> {
                    if (t == null) {
                        return false;
                    }
                    t.close();
                    return true;
                }, 2, 2);
        this.menuShowRoom.setShowRoomName(getName());
        this.menuShowRoom.removeAllElement();
        this.menuShowRoom.addElement(new YardRankSetting("Rank B", this.yardConfigModel.getB()));
        this.menuShowRoom.addElement(new YardRankSetting("Rank C", this.yardConfigModel.getC()));
        this.menuShowRoom.addElement(new YardRankSetting("Rank D", this.yardConfigModel.getD()));
        this.menuShowRoom.addElement(new YardRankSetting("Rank E", this.yardConfigModel.getE()));
        this.menuShowRoom.run();
    }

    @Override
    public String getName() {
        return "Yard config";
    }

}
