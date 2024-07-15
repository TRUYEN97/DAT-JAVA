/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.common.ConstKey;
import com.qt.controller.modeController.ModeManagement;
import com.qt.input.camera.CameraRunner;
import com.qt.mode.imp.B2_DT_Offline;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.model.input.CarModel;
import com.qt.model.input.YardModel;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.ViewMain;

/**
 *
 * @author Admin
 */
public class Core {

    private final ModeManagement modeManagement;
    private final ViewMain viewMain;
    private final KeyEventManagement eventManagement;
    private final CameraRunner cameraRunner;

    public Core(CarModel carModel, YardModel yardModel) {
        this.eventManagement = new KeyEventManagement(carModel.getRemoteValues());
        this.viewMain = new ViewMain();
        this.cameraRunner = new CameraRunner();
        this.modeManagement = new ModeManagement(createModeParam(carModel, yardModel));
        this.cameraRunner.setImageLabel(this.viewMain.getImageLabel());
        initView(carModel);
        addMode();
    }

    private void addMode() {
        this.modeManagement.putMode(ConstKey.RM_KEY.MODE.B2_DT_OFF,
                new B2_DT_Offline(this.modeManagement.getTestModeModel()));
    }

    private void initView(CarModel carModel) {
        this.viewMain.setCarModel(carModel);
        this.viewMain.setTestDataModel(this.modeManagement.getTestModeModel().
                getProcessModelHandle().
                getTestDataModel());
    }

    private ModeParam createModeParam(CarModel carModel, YardModel yardModel) {
        return ModeParam.builder()
                .carModel(carModel)
                .eventManagement(eventManagement)
                .processModelHandle(new ProcessModelHandle(new ProcessModel(), carModel))
                .soundPlayer(new SoundPlayer())
                .yardModel(yardModel)
                .cameraRunner(cameraRunner)
                .build();
    }

    public void start() {
        this.eventManagement.start();
        this.viewMain.display();
        this.cameraRunner.openCamera(0);
        this.cameraRunner.start();
        this.modeManagement.start();
    }

}
