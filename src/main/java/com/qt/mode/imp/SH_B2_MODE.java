/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.common.CarConfig;
import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impCondition.timerCondition.TatalTimeOut;
import com.qt.contest.impContest.shB2.DungXeChoNguoiDiBo;
import com.qt.contest.impContest.shB2.XuatPhat;
import com.qt.controller.api.ApiService;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.SaHinhView;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class SH_B2_MODE extends AbsTestMode<SaHinhView> {

    private boolean runnable;
    private String oldId;

    public SH_B2_MODE() {
        super(new SaHinhView(), ConstKey.MODE_NAME.SA_HINH, List.of("B2"));
        this.conditionHandle.addConditon(new CheckOverSpeedLimit(24));
        this.conditionHandle.addConditon(new TatalTimeOut(1080, processModel));
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processlHandle));
        this.runnable = false;
        this.oldId = "";
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
    private void creadContestList(){
        contests.clear();
        int yardRank = CarConfig.getInstance().getYardRank();
        int rd = new Random().nextInt(5 - yardRank);
        System.out.println(rd);
        contests.add(new XuatPhat());
        contests.add(new DungXeChoNguoiDiBo());
        
    }

    @Override
    protected void contestDone() {
    }

    @Override
    protected void endTest() {
        runnable = false;
        System.out.println(processlHandle.toProcessModelJson());
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> events) {
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
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

}
