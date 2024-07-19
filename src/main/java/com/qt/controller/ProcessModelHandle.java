/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.interfaces.IgetTime;
import com.qt.contest.AbsContest;
import com.alibaba.fastjson2.JSONObject;
import com.qt.common.MyObjectMapper;
import com.qt.common.Util;
import com.qt.common.timer.TimeBase;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.mode.AbsTestMode;
import com.qt.model.modelTest.process.TestInfo;
import com.qt.model.input.CarModel;
import com.qt.model.input.UserModel;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.modelTest.process.TestDataModelView;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProcessModelHandle implements IgetTime {

    private static volatile ProcessModelHandle instance;
    private final ProcessModel processModel;
    private final TestDataModelView testDataModel;
    private final TimeBase timeBase;
    private final CarModel carModel;
    private final FileTestService logTestService;
    private long startMs = 0;
    private long endMs = 0;
    private long cysleTime = -1;

    private ProcessModelHandle() {
        this.processModel = new ProcessModel();
        this.timeBase = new TimeBase();
        this.testDataModel = new TestDataModelView(this, processModel);
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.logTestService = FileTestService.getInstance();
        String carId = this.logTestService.getCarId();
        this.processModel.setCarId(carId == null || carId.isBlank() ? "0" : carId);
    }

    public static ProcessModelHandle getInstance() {
        ProcessModelHandle ins = instance;
        if (ins == null) {
            synchronized (ProcessModelHandle.class) {
                if (ins == null) {
                    instance = ins = new ProcessModelHandle();
                }
            }
        }
        return ins;
    }

    public void resetModel() {
        this.startMs = 0;
        this.endMs = 0;
        this.cysleTime = -1;
        this.processModel.clear();
    }

    public void setUserId(UserModel userModel) {
        if (userModel == null) {
            return;
        }
        this.processModel.setId(userModel.getName());
        this.processModel.setName(userModel.getName());
        this.processModel.setSex(userModel.getSex());
        this.processModel.setModeName(userModel.getModeName());
        this.processModel.setDate_of_birth(userModel.getDate_of_birth());
        this.processModel.setPlace_of_origin(userModel.getPlace_of_origin());
    }

    public void startTest() {
        this.logTestService.updateDate();
        this.startMs = System.currentTimeMillis();
        this.cysleTime = -1;
        this.processModel.setCycleTime(0);
        this.processModel.setStartTime(this.timeBase.getSimpleDateTime());
        this.processModel.setEndTime("");
        this.processModel.getContests().clear();
        this.processModel.getErrorcodes().clear();
        this.processModel.setDistance(0);
        this.processModel.setScore(100);
        ///////////////////////////////////
    }

    public TestDataModelView getTestDataModel() {
        return testDataModel;
    }

    public ProcessModel getProcessModel() {
        return processModel;
    }

    public JSONObject processModeltoJson() {
        return MyObjectMapper.convertValue(processModel, JSONObject.class);
    }

    public JSONObject testDataModeltoJson() {
        return MyObjectMapper.convertValue(testDataModel, JSONObject.class);
    }

    @Override
    public long getTestTime() {
        if (cysleTime >= 0) {
            return cysleTime;
        }
        return Util.getTestTime(startMs, endMs);
    }

    public void addContestModel(ContestDataModel contestModel) {
        if (contestModel == null) {
            return;
        }
        this.processModel.addContestModel(contestModel);
    }

    public void setContest(AbsContest absContest) {
        this.testDataModel.setContest(absContest);
    }
    public void update(){
        this.processModel.setDistance(carModel.getDistance());
    }

    public void endTest() {
        update();
        this.endMs = System.currentTimeMillis();
        this.cysleTime = endMs - startMs;
        this.processModel.setCycleTime((endMs - startMs) / 1000);
        this.processModel.setEndTime(this.timeBase.getSimpleDateTime());
    }

    public boolean containContestClass(String name) {
        List<ContestDataModel> contestModels = processModel.getContests();
        if (name == null || contestModels == null || contestModels.isEmpty()) {
            return false;
        }
        String modelName;
        for (ContestDataModel contestModel : contestModels) {
            modelName = contestModel.getName();
            if (modelName != null && modelName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean containContestClass(String name, int index) {
        List<ContestDataModel> contestModels = processModel.getContests();
        if (name == null
                || contestModels == null
                || contestModels.isEmpty()
                || index >= contestModels.size()
                || index < 0) {
            return false;
        }
        String modelName = contestModels.get(index).getName();
        return modelName != null && modelName.equals(name);
    }

    public void setMode(AbsTestMode testMode) {
        if (testMode == null) {
            return;
        }
        this.processModel.setModeName(testMode.getName());
    }

    public boolean isPass() {
        return this.processModel.getStatus() == ProcessModel.PASS;
    }

    public void setCarID(String modelVal) {
        this.processModel.setCarId(modelVal);
        this.logTestService.writeCarId(modelVal);
    }
}
