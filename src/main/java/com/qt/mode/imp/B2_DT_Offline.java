/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.common.ConstKey;
import com.qt.contest.imp.GiamToc;
import com.qt.contest.imp.KetThuc;
import com.qt.contest.imp.TangToc;
import com.qt.contest.imp.XuatPhat;
import com.qt.mode.AbsTestMode;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.pretreatment.IKeyEvent;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class B2_DT_Offline extends AbsTestMode {

    public B2_DT_Offline(ModeParam modeParam) {
        this(modeParam, ConstKey.MODE_NAME.OFF_B2_DUONG_TRUONG);
    }

    public B2_DT_Offline(ModeParam modeParam, String name) {
        super(modeParam, name);
    }

    @Override
    protected boolean loopCheckStartTest() {
        return (!contests.isEmpty() && contests.peek()
                .getName().equals(ConstKey.CT_NAME.XUAT_PHAT));
    }

    @Override
    public void contestDone() {
        System.out.println(": done ");
    }

    @Override
    protected void endTest() {
        System.out.println(processlHandle.processModeltoJson());
        this.hasXp = false;
        this.hasTs = false;
        this.hasGs = false;
        this.hasKt = false;
    }

    @Override
    protected void createPrepareKeyEvents(Map<Byte, IKeyEvent> maps) {
        maps.put(ConstKey.RM_KEY.CONTEST.XP, (key) -> {
            if (hasXp) {
                return;
            }
            addContest(new XuatPhat(contestParam, ConstKey.CT_NAME.XUAT_PHAT));
            hasXp = true;
        });
    }
    private boolean hasXp = false;
    private boolean hasKt = false;
    private boolean hasTs = false;
    private boolean hasGs = false;

    @Override
    protected void createTestKeyEvents(Map<Byte, IKeyEvent> maps) {
        maps.put(ConstKey.RM_KEY.CONTEST.TS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangToc(contestParam, ConstKey.CT_NAME.TANG_TOC));
            hasTs = true;
        });
        maps.put(ConstKey.RM_KEY.CONTEST.GS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamToc(contestParam, ConstKey.CT_NAME.GIAM_TOC));
            hasGs = true;
        });
        maps.put(ConstKey.RM_KEY.CONTEST.KT, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || !hasTs || !hasGs || hasKt) {
                return;
            }
            addContest(new KetThuc(contestParam, ConstKey.CT_NAME.KET_THUC));
            hasKt = true;
        });
    }
}
