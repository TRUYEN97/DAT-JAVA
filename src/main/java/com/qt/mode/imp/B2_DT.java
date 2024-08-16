/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.contest.impCondition.timerCondition.CheckSo3;
import com.qt.contest.impContest.GiamToc;
import com.qt.contest.impContest.KetThuc;
import com.qt.contest.impContest.TangToc;
import com.qt.contest.impContest.XuatPhat;
import com.qt.controller.ApiService;
import com.qt.input.camera.CameraRunner;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.DuongTruongView;
import com.qt.view.frame.ShowErrorcode;
import java.io.File;
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
    }

    @Override
    protected boolean loopCheckStartTest() {
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
        String id = processModel.getId();
        if (id == null || id.equals("0")) {
            return;
        }
        File imgFile = this.fileTestService.getFileImagePath(id);
        if (imgFile == null) {
            ErrorLog.addError(this, "Không tìm thấy file png của id: " + id);
        }
        this.apiService.sendData(processlHandle.toProcessModelJson().toString().getBytes(),
                imgFile);
    }

    @Override
    protected void endTest() {
        try {
            String id = processModel.getId();
            if (id == null || id.equals("0")) {
                return;
            }
            File imgFile = this.fileTestService.getFileImagePath(id);
            if (imgFile == null) {
                ErrorLog.addError(this, "Không tìm thấy file png của id: " + id);
            }
            if (this.apiService.sendData(processlHandle.toProcessModelJson().toString().getBytes(),
                    imgFile)) {
                this.soundPlayer.sendResultFailed();
                this.fileTestService.saveBackupLog(id, oldId,
                        CameraRunner.getInstance().getImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        } finally {
            System.out.println(processlHandle.toProcessModelJson());
            this.hasXp = false;
            this.hasTs = false;
            this.hasGs = false;
            this.hasKt = false;
            this.runnable = false;
        }
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
}
