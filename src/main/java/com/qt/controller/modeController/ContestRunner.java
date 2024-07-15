/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.interfaces.IStarter;
import com.qt.controller.ProcessModelHandle;
import com.qt.mode.AbsTestMode;
import com.qt.model.modelTest.process.ModeParam;
import com.qt.output.SoundPlayer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Admin
 */
public class ContestRunner implements IStarter, Runnable {

    private final ProcessModelHandle processlHandle;
    private final ExecutorService threadPool;
    private final SoundPlayer soundPlayer;
    private AbsTestMode testMode;
    private boolean testDone = false;
    private Future testFuture;

    public ContestRunner(ModeParam modeParam) {
        this.processlHandle = modeParam.getProcessModelHandle();
        this.threadPool = Executors.newSingleThreadExecutor();
        this.soundPlayer = modeParam.getSoundPlayer();
    }

    public boolean setTestMode(AbsTestMode testMode) {
        if (testMode == null) {
            return false;
        }
        this.testMode = testMode;
        return true;
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        this.testFuture = this.threadPool.submit(this);
    }

    @Override
    public boolean isStarted() {
        return this.testFuture != null && !this.testFuture.isDone();
    }

    @Override
    public void stop() {
        testDone = true;
        while (!isRunning()) {
            this.testFuture.cancel(true);
            Util.delay(200);
        }
    }

    @Override
    public void run() {
        this.testDone = false;
        AbsContest currContest;
        while (this.testMode != null && !this.testDone) {
            currContest = this.testMode.peekContests();
            if (this.testMode == null || checkStopTestCondisions()) {
                this.testDone = true;
                break;
            }
            if (currContest == null) {
                Util.delay(200);
                continue;
            }
            begin(currContest);
            test(currContest);
            end(currContest);
            this.testMode.pollContests();
        }
    }

    private void begin(AbsContest currContest) {
        if (this.testMode == null) {
            return;
        }
        this.processlHandle.setContest(currContest);
        this.soundPlayer.contestName(currContest.getName());
        while (!currContest.isStart()) {
            if (this.testMode == null || checkStopTestCondisions()) {
                this.testDone = true;
                break;
            }
            Util.delay(200);
        }
        if (currContest.isPlaySoundWhenInOut()) {
            this.soundPlayer.startContest();
        }
    }

    private void test(AbsContest currContest) {
        if (this.testMode != null && !this.testDone) {
            while (!this.testDone && !currContest.loop()) {
                if (checkStopTestCondisions()) {
                    this.testDone = true;
                    break;
                }
                Util.delay(200);
            }
            currContest.end();
        }
    }

    private void end(AbsContest currContest) {
        if (this.testMode != null) {
            this.testMode.contestDone();
        }
        if (this.testMode == null || checkStopTestCondisions()) {
            this.testDone = true;
        }
        if (currContest.isPlaySoundWhenInOut()) {
            this.soundPlayer.endContest();
        }
        this.processlHandle.setContest(null);
    }

    private boolean checkStopTestCondisions() {
        if (processlHandle.containContestClass(ConstKey.CT_NAME.KET_THUC)) {
            return true;
        }
        return this.processlHandle.getProcessModel().getScore() < 80;
    }

    public boolean isRunning() {
        return !testDone;
    }

}
