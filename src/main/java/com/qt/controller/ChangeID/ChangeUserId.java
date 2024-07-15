/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.ChangeID;

import com.qt.common.ConstKey;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.model.config.ChangeIdModel;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.view.ChangeIdFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Admin
 */
public class ChangeUserId {

    private final ChangeIdFrame changeIdFrame;
    private final KeyEventManagement eventManagement;
    private final KeyEventsPackage eventsPackage;
    private final ChangeIdModel changeIdModel;
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;

    public ChangeUserId(ModeParam modeParam) {
        this.soundPlayer = modeParam.getSoundPlayer();
        this.changeIdFrame = new ChangeIdFrame();
        this.eventManagement = modeParam.getEventManagement();
        this.changeIdModel = new ChangeIdModel();
        this.changeIdModel.setName("SBD");
        this.processModel = modeParam.getProcessModelHandle().getProcessModel();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName(), true);
        this.changeIdFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ChangeUserId.this.eventManagement.remove(eventsPackage);
            }
        });
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.CANCEL, (key) -> {
            this.changeIdFrame.dispose();
        });
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.OK, (key) -> {
            this.processModel.setId(this.changeIdModel.getStringValue());
            this.changeIdFrame.dispose();
            this.soundPlayer.welcomeId(this.changeIdModel.getValue());
        });
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.DV, (key) -> {
            this.changeIdModel.dvUp();
            this.changeIdFrame.update();
        });
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.CH, (key) -> {
            this.changeIdModel.chUp();
            this.changeIdFrame.update();
        });
        this.eventsPackage.putEvent(ConstKey.RM_KEY.CONFIG.TR, (key) -> {
            this.changeIdModel.trUp();
            this.changeIdFrame.update();
        });
    }

    public void show() {
        this.soundPlayer.inputId();
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        String sbd = this.processModel.getId();
        int length = sbd.length();
        if (length >= 3) {
            this.changeIdModel.setTr(sbd.charAt(0) - 48);
            this.changeIdModel.setCh(sbd.charAt(1) - 48);
            this.changeIdModel.setDv(sbd.charAt(2) - 48);
        } else if (length == 2) {
            this.changeIdModel.setTr(0);
            this.changeIdModel.setCh(sbd.charAt(0) - 48);
            this.changeIdModel.setDv(sbd.charAt(1) - 48);
        } else if (length == 1) {
            this.changeIdModel.setTr(0);
            this.changeIdModel.setCh(0);
            this.changeIdModel.setDv(sbd.charAt(0) - 48);
        } else {
            this.changeIdModel.setTr(0);
            this.changeIdModel.setCh(0);
            this.changeIdModel.setDv(0);
        }
        this.changeIdFrame.setModel(changeIdModel);
        this.changeIdFrame.display();
    }

}
