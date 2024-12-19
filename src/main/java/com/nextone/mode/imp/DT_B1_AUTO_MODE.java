/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode.imp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impContest.dtB1.GiamTocB1;
import com.nextone.contest.impContest.dtB1.KetThucB1;
import com.nextone.contest.impContest.dtB1.TangTocB1;
import com.nextone.mode.AbsDuongTruongMode;
import com.nextone.pretreatment.IKeyEvent;
import com.nextone.view.modeView.DuongTruongView;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B1_AUTO_MODE extends AbsDuongTruongMode {

    public DT_B1_AUTO_MODE(DuongTruongView truongView, boolean isOnline) {
        this(truongView, ConstKey.MODE_NAME.DUONG_TRUONG, List.of("B1"), isOnline);
    }

    public DT_B1_AUTO_MODE(DuongTruongView truongView, String name, List<String> ranks, boolean isOnline) {
        super(truongView, name, ranks, isOnline);
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
            addContest(new KetThucB1(ConstKey.CONTEST_NAME.KET_THUC));
            hasKt = true;
        });
    }
}
