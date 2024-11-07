/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impContest.dtB1.GiamTocB1;
import com.qt.contest.impContest.dtB1.KetThucB1;
import com.qt.contest.impContest.dtB1.TangTocB1;
import com.qt.contest.impContest.dtB1.XuatPhatB1;
import com.qt.controller.api.ApiService;
import com.qt.controller.settingElement.imp.ChangeUserId;
import com.qt.mode.AbsDuongTruongMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.DuongTruongView;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B1_AUTO_MODE extends AbsDuongTruongMode {

    private boolean runnable;
    private String oldId;

    public DT_B1_AUTO_MODE(DuongTruongView truongView) {
        this(truongView, ConstKey.MODE_NAME.DUONG_TRUONG, List.of("B1"));
    }

    public DT_B1_AUTO_MODE(DuongTruongView truongView, String name, List<String> ranks) {
        super(truongView, name, ranks);
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
        if (id == null || id.isBlank()) {
            Util.delay(3000);
            return false;
        }
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
                .getName().equals(ConstKey.CONTEST_NAME.XUAT_PHAT));
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
        maps.put(ConstKey.KEY_BOARD.SBD, (key) -> {
            if (!hasXp) {
                ChangeUserId changeUserId = new ChangeUserId();
                changeUserId.run();
            }
        });
        maps.put(ConstKey.KEY_BOARD.CONTEST.XP, (key) -> {
            if (hasXp || !runnable || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                return;
            }
            addContest(new XuatPhatB1(ConstKey.CONTEST_NAME.XUAT_PHAT));
            hasXp = true;
        });
    }
    private boolean hasXp = false;
    private boolean hasKt = false;
    private boolean hasTs = false;
    private boolean hasGs = false;

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
        events.put(ConstKey.ERR.SWERVED_OUT_OF_LANE, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.IGNORED_INSTRUCTIONS, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.VIOLATION_TRAFFIC_RULES, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.HEAVY_SHAKING, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.ERR.CAUSED_AN_ACCIDENT, (key) -> {
            this.errorcodeHandle.addBaseErrorCode(key);
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.TS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangTocB1(ConstKey.CONTEST_NAME.TANG_TOC));
            hasTs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.GS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamTocB1(ConstKey.CONTEST_NAME.GIAM_TOC));
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
            addContest(new KetThucB1(ConstKey.CONTEST_NAME.KET_THUC));
            hasKt = true;
        });
    }
}
