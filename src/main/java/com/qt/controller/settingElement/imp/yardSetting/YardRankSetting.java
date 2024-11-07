/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp.yardSetting;

import com.qt.common.ConstKey;
import com.qt.controller.settingElement.IElementSetting;
import com.qt.model.yardConfigMode.YardRankConfig;
import com.qt.view.frame.MenuShowRoom;

/**
 *
 * @author Admin
 */
public class YardRankSetting implements IElementSetting {

    private final YardRankConfig yardRankCf;
    private final String name;
    private MenuShowRoom<IElementSetting> menuShowRoom;

    public YardRankSetting(String name, YardRankConfig yardRankCf) {
        this.name = name;
        this.yardRankCf = yardRankCf;
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
                }, null, 4, 4);
        this.menuShowRoom.setShowRoomName(name);
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.DUNG_XE_CNDB
                , yardRankCf.getDungXeChoNg()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.DUNG_XE_ND
                , yardRankCf.getDungXeNgangDoc()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.VET_BANH_XE
                , yardRankCf.getVetBanhXe()));
        this.menuShowRoom.addElement(new YardContestConfigSetting("DV_GOC", yardRankCf.getDuongVuongGoc()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.NGAT_TU+" 1"
                , yardRankCf.getNgaTu1()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.CHU_S
                , yardRankCf.getDuongS()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.NGAT_TU+" 2"
                , yardRankCf.getNgaTu2()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.GHEP_XE_DOC
                , yardRankCf.getDoXeDoc()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.NGAT_TU+" 3"
                , yardRankCf.getNgaTu3()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.DUONG_TAU
                , yardRankCf.getDuongTau()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.THAY_DOI_SO
                , yardRankCf.getTangToc()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.GHEP_XE_NGANG
                , yardRankCf.getDoXeNgang()));
        this.menuShowRoom.addElement(new YardContestConfigSetting(ConstKey.CONTEST_NAME.NGAT_TU+" 4"
                , yardRankCf.getNgaTu4()));
        this.menuShowRoom.run();
    }

    @Override
    public String getName() {
        return this.name;
    }

}
