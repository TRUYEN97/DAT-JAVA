/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import com.nextone.interfaces.IgetTime;
import com.nextone.contest.AbsContest;
import com.alibaba.fastjson2.JSONObject;
import com.nextone.common.CarConfig;
import com.nextone.common.ErrorLog;
import com.nextone.common.MyObjectMapper;
import com.nextone.common.Util;
import com.nextone.common.timer.TimeBase;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.mode.AbsTestMode;
import com.nextone.model.input.CarModel;
import com.nextone.model.input.UserModel;
import com.nextone.model.modelTest.contest.ContestDataModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.model.modelTest.process.TestDataViewModel;
import java.util.List;

/**
 *
 * @author Admin
 */
public final class ProcessModelHandle implements IgetTime {

    private static volatile ProcessModelHandle instance;
    private final ProcessModel processModel;
    private final TestDataViewModel testDataModel;
    private final TimeBase timeBase;
    private final CarModel carModel;
    private long startMs = 0;
    private long endMs = 0;
    private long cysleTime = -1;
    private boolean testing;

    private ProcessModelHandle() {
        this.processModel = new ProcessModel();
        this.timeBase = new TimeBase();
        this.testDataModel = new TestDataViewModel(this, processModel);
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.testing = false;
        setCarID(CarConfig.getInstance().getCarId());
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

    public void setCarID(String carId) {
        if (testing) {
            return;
        }
        carId = carId == null || carId.isBlank() ? "0" : carId;
        this.processModel.setCarId(carId);
        CarConfig.getInstance().setCarId(carId);
    }

    public void setUserModel(UserModel userModel) {
        if (userModel == null || testing) {
            return;
        }
        CarConfig.getInstance().setExamId(userModel.getExamId());
        this.processModel.setId(userModel.getId());
        this.processModel.setName(userModel.getName());
        this.processModel.setSex(userModel.getSex());
        this.processModel.setModeName(userModel.getModeName());
        this.processModel.setDateOfBirth(userModel.getDateOfBirth());
        this.processModel.setPlaceOfOrigin(userModel.getPlaceOfOrigin());
        this.processModel.setExamId(userModel.getExamId());
        this.processModel.setExamStatus(userModel.getExamStatus());
        this.processModel.setRank(userModel.getRank());
    }

    public void resetModel() {
        this.startMs = 0;
        this.endMs = 0;
        this.cysleTime = -1;
        this.processModel.clear();
        this.testing = false;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public void startTest() {
        this.processModel.setContestsResult(ProcessModel.RUNNING);
        this.testing = true;
        this.startMs = System.currentTimeMillis();
        this.cysleTime = -1;
        this.processModel.setCycleTime(0);
        this.processModel.setStartTime(this.timeBase.getSimpleDateTime());
        this.processModel.setEndTime(null);
        this.processModel.getContests().clear();
        this.processModel.getErrorCodes().clear();
        this.processModel.setDistance(0);
        this.processModel.setScore(100);
        ///////////////////////////////////
    }

    public TestDataViewModel getTestDataModel() {
        return testDataModel;
    }

    public ProcessModel getProcessModel() {
        return processModel;
    }

    public JSONObject toProcessModelJson() {
        return MyObjectMapper.convertValue(processModel, JSONObject.class);
    }

    public JSONObject toTestDataJson() {
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

    public void update() {
        this.processModel.setCycleTime(getTestTime());
        this.processModel.setDistance(carModel.getDistance());
    }

    public void endTest() {
        update();
        this.endMs = System.currentTimeMillis();
        this.cysleTime = Util.getTestTime(startMs, endMs);
        this.processModel.setCycleTime(this.cysleTime);
        this.processModel.setEndTime(this.timeBase.getSimpleDateTime());
    }

    public boolean containContestClass(String name) {
        try {
            List<ContestDataModel> contestModels = processModel.getContests();
            if (name == null || contestModels == null || contestModels.isEmpty()) {
                return false;
            }
            String modelName;
            ContestDataModel contestModel;
            for (int i = 0; i < contestModels.size(); i++) {
                contestModel = contestModels.get(i);
                if (contestModel.getEndTime() == null || contestModel.getEndTime().isBlank()) {
                    continue;
                }
                modelName = contestModel.getContestName();
                if (modelName != null && modelName.equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public boolean containContestClass(String name, int index) {
        try {
            List<ContestDataModel> contestModels = processModel.getContests();
            if (name == null
                    || contestModels == null
                    || contestModels.isEmpty()
                    || index >= contestModels.size()
                    || index < 0) {
                return false;
            }
            String modelName = contestModels.get(index).getContestName();
            return modelName != null && modelName.equals(name);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public void setMode(AbsTestMode testMode) {
        if (testMode == null) {
            return;
        }
        this.processModel.setModeName(testMode.getName());
        this.testDataModel.setModeFullName(testMode.getFullName());
    }

    public boolean isPass() {
        return this.processModel.getContestsResult() == ProcessModel.PASS;
    }

    public boolean isTesting() {
        return this.testing;
    }

    public void setContest(AbsContest absContest) {
        this.testDataModel.setContest(absContest);
    }
}
