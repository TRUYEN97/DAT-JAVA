/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.online;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.TestStatusLogger;
import com.qt.common.Util;
import com.qt.common.YardConfig;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impCondition.timerCondition.TatalTimeOut;
import com.qt.controller.api.ApiService;
import com.qt.input.camera.CameraRunner;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.input.socket.YardModelHandle;
import com.qt.mode.AbsTestMode;
import com.qt.model.input.UserModel;
import com.qt.model.input.yard.YardRankModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.model.yardConfigMode.YardConfigModel;
import com.qt.model.yardConfigMode.YardRankConfig;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.modeView.AbsModeView;
import java.util.List;

/**
 *
 * @author Admin
 */
public abstract class AbsSaHinhMode extends AbsTestMode<AbsModeView> {

    protected static enum MODEL_RANK_NAME {
        RANK_B, RANK_C, RANK_D, RANK_E
    };
    protected final YardRankModel yardRankModel;
    protected final int speedLimit;
    protected final YardModelHandle yardModelHandle;
    protected final YardRankConfig yardRankConfig;

    public AbsSaHinhMode(AbsModeView hinhView, int speedLimit, int timeOut,
            MODEL_RANK_NAME modelRank, List<String> ranks, boolean isOnline) {
        super(hinhView, ConstKey.MODE_NAME.SA_HINH, ranks, isOnline);
        this.speedLimit = speedLimit;
        this.conditionHandle.addConditon(new TatalTimeOut(timeOut, processModel));
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processlHandle));
        this.yardModelHandle = YardModelHandle.getInstance();
        YardConfigModel yardConfig = YardConfig.getInstance().getYardConfigModel();
        switch (modelRank) {
            case RANK_C -> {
                this.yardRankConfig = yardConfig.getC();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankC();
            }
            case RANK_D -> {
                this.yardRankConfig = yardConfig.getD();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankD();
            }
            case RANK_E -> {
                this.yardRankConfig = yardConfig.getE();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankE();
            }
            default -> {
                this.yardRankConfig = yardConfig.getB();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankB();
            }
        }
    }

    private boolean st = false;

    @Override
    protected boolean loopCheckCanTest() {
        if (this.carModel.isNt() && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            if (!st) {
                st = true;
                this.mCUSerialHandler.sendLedRedOn();
            }
            if (isOnline) {
                UserModel userModel = this.apiService.checkCarPair(this.processModel.getCarId());
                if (userModel == null || userModel.getId() == null || userModel.getId().isBlank()) {
                    Util.delay(1000);
                    return false;
                }
                this.processlHandle.setUserModel(userModel);
                switch (this.apiService.checkRunnable(userModel.getId())) {
                    case ApiService.START -> {
                        this.mCUSerialHandler.sendLedRedOff();
                        creadContestList();
                        return true;
                    }
                    case ApiService.WAIT -> {
                        Util.delay(2000);
                    }
                    case ApiService.ID_INVALID -> {
                        soundPlayer.userIdInvalid();
                        Util.delay(2000);
                    }
                }
            } else {
                UserModel userModel = new UserModel();
                userModel.setId("0");
                userModel.setExamId("0");
                this.processlHandle.setUserModel(userModel);
                return true;
            }
        } else if (st) {
            st = false;
            this.mCUSerialHandler.sendLedOff();
        }
        return false;
    }

    protected abstract void creadContestList();

    @Override
    public void end() {
        try {
            this.conditionHandle.stop();
            this.contests.clear();
            KeyEventManagement.getInstance().remove(prepareEventsPackage);
            KeyEventManagement.getInstance().remove(testEventsPackage);
            Util.delay(2000);
            int score = this.processModel.getScore();
            this.processModel.setContestsResult(score >= scoreSpec ? ProcessModel.PASS : ProcessModel.FAIL);
            updateLog();
            this.soundPlayer.sayResultTest(score, this.processlHandle.isPass());
            if (this.processlHandle.isPass()) {
                MCUSerialHandler.getInstance().sendLedGreenOn();
            } else {
                MCUSerialHandler.getInstance().sendLedRedOn();
            }
            TestStatusLogger.getInstance().remove();
            if (isOnline) {
                int rs = ApiService.FAIL;
                for (int i = 0; i < 3; i++) {
                    rs = upTestDataToServer();
                    if (rs == ApiService.PASS) {
                        break;
                    }
                }
                if (rs == ApiService.DISCONNECT) {
                    String id = processModel.getId();
                    this.soundPlayer.sendlostConnect();
                    this.fileTestService.saveBackupLog(id, processlHandle.toProcessModelJson().toString(),
                            CameraRunner.getInstance().getImage());
                } else if (rs == ApiService.FAIL) {
                    this.soundPlayer.sendResultFailed();
                }
            }
            endTest();
            this.processlHandle.setTesting(false);
            this.processModel.setId("");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    @Override
    protected void endTest() {
    }

    @Override
    public void modeInit() {
        super.modeInit();
        this.yardModelHandle.start();
    }

    @Override
    public void modeEndInit() {
        super.modeEndInit();
        this.yardModelHandle.stop();
    }

    @Override
    protected void contestDone() {
    }

}
