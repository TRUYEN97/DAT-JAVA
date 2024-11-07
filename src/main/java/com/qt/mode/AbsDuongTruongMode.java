/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode;

import com.qt.common.ErrorLog;
import com.qt.common.TestStatusLogger;
import com.qt.common.Util;
import com.qt.controller.api.ApiService;
import com.qt.input.camera.CameraRunner;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.printer.Printer;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.view.modeView.AbsModeView;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public abstract class AbsDuongTruongMode extends AbsTestMode<AbsModeView> {

    protected final Printer printer;
    protected final Timer timer;

    public AbsDuongTruongMode(AbsModeView view, String name, List<String> ranks) {
        super(view, name, ranks);
        this.printer = new Printer();
        this.timer = new Timer(20000, (e) -> {
            new Thread(() -> {
                upTestDataToServer();
            }).start();
        });
    }

    @Override
    public void begin() {
        this.timer.stop();
        super.begin(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.soundPlayer.begin();
        this.timer.start();
    }

    @Override
    protected int upTestDataToServer() {
        this.timer.restart();
        return super.upTestDataToServer(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
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
