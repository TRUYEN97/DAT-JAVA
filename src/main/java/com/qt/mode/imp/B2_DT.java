/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.API.Response;
import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.contest.impCondition.timerCondition.CheckSo3;
import com.qt.contest.impContest.GiamToc;
import com.qt.contest.impContest.KetThuc;
import com.qt.contest.impContest.TangToc;
import com.qt.contest.impContest.XuatPhat;
import com.qt.controller.api.ApiService;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.DuongTruongView;
import com.qt.view.frame.ShowErrorcode;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class B2_DT extends AbsTestMode<DuongTruongView> {

    private final ShowErrorcode showErrorcode;
    private boolean runnable;
    private String oldId;

    public B2_DT() {
        this(ConstKey.MODE_NAME.B2_DUONG_TRUONG);
    }

    public B2_DT(String name) {
        super(new DuongTruongView(), name);
        this.showErrorcode = new ShowErrorcode();
        this.conditionHandle.addConditon(new CheckSo3());
        this.runnable = false;
        this.oldId = "";
        this.pingAPI.setPingAPIReceive((responce) -> {
            analysisRespmoce(responce);
        });
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
            if (hasXp || !runnable) {
                return;
            }
            addContest(new XuatPhat(ConstKey.CT_NAME.XUAT_PHAT));
            hasXp = true;
        });
        maps.put(ConstKey.KEY_BOARD.SHOW_ERROR, (key) -> {
            if (this.showErrorcode.isVisible()) {
                this.showErrorcode.dispose();
            } else {
                this.showErrorcode.display();
            }
        });
    }
    private boolean hasXp = false;
    private boolean hasKt = false;
    private boolean hasTs = false;
    private boolean hasGs = false;

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> maps) {
        maps.put(ConstKey.KEY_BOARD.SHOW_ERROR, (key) -> {
            if (this.showErrorcode.isVisible()) {
                this.showErrorcode.dispose();
            } else {
                this.showErrorcode.display();
            }
        });
        maps.put(ConstKey.KEY_BOARD.CONTEST.TS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangToc(ConstKey.CT_NAME.TANG_TOC));
            hasTs = true;
        });
        maps.put(ConstKey.KEY_BOARD.CONTEST.GS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamToc(ConstKey.CT_NAME.GIAM_TOC));
            hasGs = true;
        });
        maps.put(ConstKey.KEY_BOARD.CONTEST.KT, (key) -> {
            if (this.carModel.getDistance() < 200) {
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

    private void analysisRespmoce(Response responce) {
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
            case "capNhat" -> {
                updateLog();
                upTestDataToServer();
            }
            case "huyThi" -> {
                if (getModeHandle() != null) {
                    getModeHandle().stop();
                }
            }
        }
    }
}
