/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.view.component;

import com.nextone.controller.ProcessModelHandle;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.interfaces.IStarter;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelTest.process.TestDataViewModel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class UpdateValuePanel extends JPanel implements IStarter {

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
    }

    protected void updateValues() {

    }

    public TestDataViewModel getTestDataModel() {
        return testDataModel;
    }

    @Override
    public void start() {
        this.timer.start();
    }

    @Override
    public void stop() {
        this.timer.stop();
    }

    @Override
    public boolean isStarted() {
        return this.timer.isRunning();
    }
}
