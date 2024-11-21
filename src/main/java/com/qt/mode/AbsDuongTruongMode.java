/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.TestStatusLogger;
import com.qt.common.Util;
import com.qt.contest.impCondition.ContainContestChecker;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impContest.dtB1.XuatPhatB1;
import com.qt.controller.api.ApiService;
import com.qt.controller.settingElement.imp.ChangeUserId;
import com.qt.input.camera.CameraRunner;
import com.qt.mode.AbsTestMode;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.printer.Printer;
import com.qt.pretreatment.IKeyEvent;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.modeView.AbsModeView;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public abstract class AbsDuongTruongMode extends AbsTestMode<AbsModeView> {

    protected final Printer printer;
    protected final Timer timer;
    protected boolean runnable;
    protected boolean hasXp = false;
    protected boolean hasKt = false;
    protected boolean hasTs = false;
    protected boolean hasGs = false;
    protected String oldId;

    public AbsDuongTruongMode(AbsModeView view, String name, List<String> ranks, boolean isOnline) {
        super(view, name, ranks, isOnline);
        this.printer = new Printer();
        this.runnable = false;
        this.oldId = "";
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processlHandle));
        this.timer = new Timer(20000, (e) -> {
            new Thread(() -> {
                upTestDataToServer();
            }).start();
        });
    }

    @Override
    protected void contestDone() {
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
    protected boolean loopCheckCanTest() {
        String id = this.processModel.getId();
        if (id == null || id.isBlank()) {
            Util.delay(3000);
            return false;
        }
        if (isOnline) {
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
        } else {
            runnable = true;
        }
        return runnable && (!contests.isEmpty() && contests.peek()
                .getName().equals(ConstKey.CONTEST_NAME.XUAT_PHAT));
    }

    @Override
    public void begin() {
        this.timer.stop();
        super.begin(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.soundPlayer.begin();
        if (isOnline) {
            this.timer.start();
        }
    }

    @Override
    protected int upTestDataToServer() {
        if (isOnline) {
            this.timer.restart();
            return super.upTestDataToServer(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
        return ApiService.PASS;
    }

    @Override
    public void end() {
        try {
            this.timer.stop();
            this.conditionHandle.stop();
            this.contests.clear();
            KeyEventManagement.getInstance().remove(prepareEventsPackage);
            KeyEventManagement.getInstance().remove(testEventsPackage);
            Util.delay(2000);
            int score = this.processModel.getScore();
            this.processModel.setContestsResult(score >= scoreSpec ? ProcessModel.PASS : ProcessModel.FAIL);
            updateLog();
            this.printer.printTestResult(this.processModel.getId());
            this.soundPlayer.sayResultTest(score, this.processlHandle.isPass());
            TestStatusLogger.getInstance().remove();
            if (isOnline) {
                int rs = ApiService.FAIL;
                for (int i = 0; i < 3; i++) {
                    rs = upTestDataToServer();
                    if (rs == ApiService.PASS) {
                        break;
                    }
                }
                if (rs == ApiService.DISCONNECT) {
                    String id = processModel.getId();
                    this.soundPlayer.sendlostConnect();
                    this.fileTestService.saveBackupLog(id, processlHandle.toProcessModelJson().toString(),
                            CameraRunner.getInstance().getImage());
                } else if (rs == ApiService.FAIL) {
                    this.soundPlayer.sendResultFailed();
                }
            }
            endTest();
            this.processModel.setId("");
            this.processlHandle.setTesting(false);
            this.soundPlayer.nextId();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

}
