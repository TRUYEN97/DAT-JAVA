/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.API.Response;
import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impCondition.timerCondition.CheckSo3;
import com.qt.contest.impContest.GiamToc;
import com.qt.contest.impContest.KetThuc;
import com.qt.contest.impContest.TangToc;
import com.qt.contest.impContest.XuatPhat;
import com.qt.controller.api.ApiService;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.DuongTruongView;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B2_MODE extends AbsTestMode<DuongTruongView> {

    private boolean runnable;
    private String oldId;

    public DT_B2_MODE() {
        this(ConstKey.MODE_NAME.DUONG_TRUONG, List.of("B2", "C"));
    }

    public DT_B2_MODE(String name, List<String> ranks) {
        super(new DuongTruongView(), name, ranks);
        this.conditionHandle.addConditon(new CheckSo3());
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CT_NAME.KET_THUC, false, processlHandle));
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
        return runnable && (!contests.isEmpty() && contests.peek()
                .getName().equals(ConstKey.CT_NAME.XUAT_PHAT));
    }

    @Override
    protected void contestDone() {
    }

    @Override
    protected void endTest() {
        System.out.println(processlHandle.toProcessModelJson());
        this.hasXp = false;
        this.hasTs = false;
        this.hasGs = false;
        this.hasKt = false;
        this.runnable = false;
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> maps) {
        maps.put(ConstKey.KEY_BOARD.CONTEST.XP, (key) -> {
            if (hasXp || !runnable || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                return;
            }
            addContest(new XuatPhat(ConstKey.CT_NAME.XUAT_PHAT));
            hasXp = true;
        });
    }
    private boolean hasXp = false;
    private boolean hasKt = false;
    private boolean hasTs = false;
    private boolean hasGs = false;

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
        events.put(ConstKey.ERR.CL, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.HL, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.QT, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.RG, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.TN, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.TS, (key) -> {
            if (this.carModel.getGearBoxValue() >= 5) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangToc(ConstKey.CT_NAME.TANG_TOC));
            hasTs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.GS, (key) -> {
            if (this.carModel.getGearBoxValue() <= 1) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamToc(ConstKey.CT_NAME.GIAM_TOC));
            hasGs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.KT, (key) -> {
            String id = processModel.getId();
            int distance = id == null || id.equals("0") ? 200 : 2000;
            if (this.carModel.getDistance() < distance) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || !hasTs || !hasGs || hasKt) {
                return;
            }
            addContest(new KetThuc(ConstKey.CT_NAME.KET_THUC));
            hasKt = true;
        });
    }

    @Override
    protected void analysisResponce(Response responce) {
        if (responce == null) {
            return;
        }
        if (!responce.isSuccess() || responce.getData() == null) {
            return;
        }
        String requestString = responce.getData(JSONObject.class).getString("request");
        if (requestString == null) {
            return;
        }
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
