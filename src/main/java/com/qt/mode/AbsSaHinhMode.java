/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.common.YardConfig;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impCondition.timerCondition.TatalTimeOut;
import com.qt.controller.api.ApiService;
import com.qt.input.socket.YardModelHandle;
import com.qt.model.input.yard.YardRankModel;
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

    public AbsSaHinhMode(int speedLimit, int timeOut, MODEL_RANK_NAME modelRank, List<String> ranks) {
        super(new SaHinhView(), ConstKey.MODE_NAME.SA_HINH, ranks);
        this.speedLimit = speedLimit;
        this.conditionHandle.addConditon(new TatalTimeOut(timeOut, processModel));
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processlHandle));
        this.runnable = false;
        this.oldId = "";
        this.yardModelHandle = YardModelHandle.getInstance();
        YardConfig yardConfig = YardConfig.getInstance();
        switch (modelRank) {
            case RANK_C -> {
                this.yardRankConfig = yardConfig.getRankCConfig();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankC();
            }
            case RANK_D -> {
                this.yardRankConfig = yardConfig.getRankDConfig();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankD();
            }
            case RANK_E -> {
                this.yardRankConfig = yardConfig.getRankEConfig();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankE();
            }
            default -> {
                this.yardRankConfig = yardConfig.getRankBConfig();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankB();
            }
        }
    }

    @Override
    protected boolean loopCheckCanTest() {
        String id = this.processModel.getId();
        if (!runnable || !oldId.equals(id)) {
            oldId = id;
            switch (this.apiService.checkRunnable(id)) {
                case ApiService.START -> {
                    creadContestList();
                    runnable = true;
                }
                case ApiService.ID_INVALID -> {
                    soundPlayer.userIdHasTest();
                    runnable = false;
                    Util.delay(10000);
                }
                default -> {
                    runnable = false;
                    Util.delay(1000);
                }
            }
        }
        return runnable && (!contests.isEmpty());
    }

    protected abstract void creadContestList();

    @Override
    protected void endTest() {
        runnable = false;
        System.out.println(processlHandle.toProcessModelJson());
    }

    @Override
    protected void analysisResponce(String requestString) {
        switch (requestString) {
            case "update" -> {
                updateLog();
                upTestDataToServer();
            }
//            case "huyThi" -> {
//                if (getModeHandle() != null) {
//                    getModeHandle().stop();
//                }
//            }
        }
    }

    @Override
    public void modeInit() {
        this.yardModelHandle.start();
    }

    @Override
    public void modeEndInit() {
        this.yardModelHandle.stop();
    }
}
