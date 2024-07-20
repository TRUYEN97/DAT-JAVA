/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.timerCondition.CheckSo3;
import com.qt.contest.impContest.GiamToc;
import com.qt.contest.impContest.KetThuc;
import com.qt.contest.impContest.TangToc;
import com.qt.contest.impContest.XuatPhat;
import com.qt.controller.ApiService;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.DuongTruongView;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class B2_DT extends AbsTestMode<DuongTruongView> {

    private boolean runnable;
    public B2_DT() {
        this(ConstKey.MODE_NAME.B2_DUONG_TRUONG);
        this.conditionHandle.addConditon(new CheckSo3());
        this.runnable = false;
    }

    public B2_DT(String name) {
        super(new DuongTruongView(), name);
    }

    @Override
    protected boolean loopCheckStartTest() {
        if (!runnable) {
            int st = this.apiService.checkRunnable(this.processModel.getId(), 
                    this.processModel.getCarId());
            runnable = st == ApiService.START;
        }
        return runnable && (!contests.isEmpty() && contests.peek()
                .getName().equals(ConstKey.CT_NAME.XUAT_PHAT));
    }

    @Override
    protected void contestDone() {
        this.apiService.sendData(processModel.getId());
    }

    @Override
    protected void endTest() {
        this.apiService.sendData(processModel.getId());
        System.out.println(processlHandle.processModeltoJson());
        this.hasXp = false;
        this.hasTs = false;
        this.hasGs = false;
        this.hasKt = false;
        this.runnable = false;
    }

    @Override
    protected void createPrepareKeyEvents(Map<Integer, IKeyEvent> maps) {
        maps.put(ConstKey.RM_KEY.CONTEST.XP, (key) -> {
            if (hasXp && !runnable) {
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
    protected void createTestKeyEvents(Map<Integer, IKeyEvent> maps) {
        maps.put(ConstKey.RM_KEY.CONTEST.TS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangToc(ConstKey.CT_NAME.TANG_TOC));
            hasTs = true;
        });
        maps.put(ConstKey.RM_KEY.CONTEST.GS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamToc(ConstKey.CT_NAME.GIAM_TOC));
            hasGs = true;
        });
        maps.put(ConstKey.RM_KEY.CONTEST.KT, (key) -> {
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
}
