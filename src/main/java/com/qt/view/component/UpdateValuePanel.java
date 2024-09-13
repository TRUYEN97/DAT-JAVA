/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.view.component;

import com.qt.controller.ProcessModelHandle;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.process.TestDataViewModel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class UpdateValuePanel extends JPanel {

    protected final Timer timer;
    protected final CarModel carModel;
    protected final TestDataViewModel testDataModel;

    public UpdateValuePanel() {
        this(500);
    }

    public UpdateValuePanel(int updateTime) {
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.testDataModel = ProcessModelHandle.getInstance().getTestDataModel();
        this.timer = new Timer(updateTime, (e) -> {
            updateValues();
        });
        this.timer.start();
    }

    protected void updateValues() {
        
    }

    public TestDataViewModel getTestDataModel() {
        return testDataModel;
    }
}
