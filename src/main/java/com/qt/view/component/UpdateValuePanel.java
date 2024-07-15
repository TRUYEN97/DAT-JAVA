/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.view.component;

import com.qt.model.input.CarModel;
import com.qt.model.modelTest.process.TestDataModelView;
import com.qt.view.DesignPanel;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class UpdateValuePanel extends DesignPanel {

    protected final Timer timer;
    protected CarModel carModel;
    protected TestDataModelView testDataModel;

    public UpdateValuePanel() {
        this(200);
    }

    public UpdateValuePanel(int updateTime) {
        this.carModel = new CarModel();
        this.testDataModel = new TestDataModelView(null, null);
        this.timer = new Timer(updateTime, (e) -> {
            updateValues();
        });
        this.timer.start();
    }

    protected void updateValues() {
        
    }

    public TestDataModelView getTestDataModel() {
        return testDataModel;
    }

    public void setTestDataModel(TestDataModelView testDataModel) {
        if (testDataModel == null) {
            return;
        }
        this.testDataModel = testDataModel;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        if (carModel == null) {
            return;
        }
        this.carModel = carModel;
    }
}
