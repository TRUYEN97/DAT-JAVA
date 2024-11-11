/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.online.imp;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.timerCondition.CheckSo3;
import com.qt.contest.impContest.dtB2.GiamToc;
import com.qt.contest.impContest.dtB2.KetThuc;
import com.qt.contest.impContest.dtB2.TangToc;
import com.qt.mode.online.AbsDuongTruongMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.DuongTruongView;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B_MODE extends AbsDuongTruongMode {

    public DT_B_MODE(DuongTruongView truongView, boolean isOnline) {
        this(truongView, ConstKey.MODE_NAME.DUONG_TRUONG, List.of("B1", "B2", "C", "D", "E"), isOnline);
    }

    public DT_B_MODE(DuongTruongView truongView, String name, List<String> ranks, boolean isOnline) {
        super(truongView, name, ranks, isOnline);
        this.conditionHandle.addConditon(new CheckSo3());
    }

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
            if (this.carModel.getGearBoxValue() >= 5) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangToc(ConstKey.CONTEST_NAME.TANG_TOC));
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
            addContest(new GiamToc(ConstKey.CONTEST_NAME.GIAM_TOC));
            hasGs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.KT, (key) -> {
            String id = processModel.getId();
            int distance = id == null || id.equals("0") || !isOnline ? 200 : 2000;
            if (this.carModel.getDistance() < distance) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || !hasTs || !hasGs || hasKt) {
                return;
            }
            addContest(new KetThuc(ConstKey.CONTEST_NAME.KET_THUC));
            hasKt = true;
        });
    }
}
