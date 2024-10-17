/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.common.ConstKey;
import com.qt.common.YardConfig;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impCondition.timerCondition.TatalTimeOut;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.input.socket.YardModelHandle;
import com.qt.model.input.UserModel;
import com.qt.model.input.yard.YardRankModel;
import com.qt.model.yardConfigMode.YardConfigModel;
import com.qt.model.yardConfigMode.YardRankConfig;
import com.qt.view.modeView.SaHinhView;
import java.util.List;

/**
 *
 * @author Admin
 */
public abstract class AbsSaHinhMode extends AbsTestMode<SaHinhView> {

    protected static enum MODEL_RANK_NAME {
        RANK_B, RANK_C, RANK_D, RANK_E
    };
    private boolean runnable;
    private String oldId;
    protected final YardRankModel yardRankModel;
    protected final int speedLimit;
    protected final YardModelHandle yardModelHandle;
    protected final YardRankConfig yardRankConfig;

    public AbsSaHinhMode(SaHinhView hinhView, int speedLimit, int timeOut, MODEL_RANK_NAME modelRank, List<String> ranks) {
        super(hinhView, ConstKey.MODE_NAME.SA_HINH, ranks);
        this.speedLimit = speedLimit;
        this.conditionHandle.addConditon(new TatalTimeOut(timeOut, processModel));
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processlHandle));
        this.runnable = false;
        this.oldId = "";
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

    @Override
    protected boolean loopCheckCanTest() {
        if (this.carModel.isNt() && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            UserModel userModel = new UserModel();
            userModel.setId("0");
            userModel.setExamId("0");
            this.processlHandle.setUserModel(userModel);
            creadContestList();
            return true;
        }
        return false;
    }

    protected abstract void creadContestList();

    @Override
    protected void endTest() {
        runnable = false;
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
        MCUSerialHandler.getInstance().sendReset();
    }

}
