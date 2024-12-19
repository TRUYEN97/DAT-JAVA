/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.modeController;

import com.nextone.common.Util;
import com.nextone.contest.AbsContest;
import com.nextone.mode.AbsTestMode;
import com.nextone.model.modelTest.DataTestTransfer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Admin
 */
public class ContestRunner implements Runnable {

    private final ExecutorService threadPool;
    private final DataTestTransfer dataTestTransfer;
    private AbsTestMode testMode;
    private boolean testDone = false;

    public ContestRunner() {
        this.threadPool = Executors.newSingleThreadExecutor();
        this.dataTestTransfer = new DataTestTransfer();
    }

    public boolean setTestMode(AbsTestMode testMode) {
        if (testMode == null) {
            return false;
        }
        this.testMode = testMode;
        return true;
    }

    @Override
    public void run() {
        this.testDone = false;
        AbsContest currContest;
        this.dataTestTransfer.clear();
        while (this.testMode != null && !this.testDone) {
            currContest = this.testMode.peekContests();
            if (this.testMode == null || testMode.isTestCondisionsFailed()) {
                stop();
                break;
            }
            if (currContest == null) {
                Util.delay(200);
                continue;
            }
            currContest.setDataTestTransfer(this.dataTestTransfer);
            runPart(currContest.begin(), currContest);
            if (!testDone) {
                runPart(currContest.test(), currContest);
            }
            currContest.end();
            if (this.testMode != null) {
                this.testMode.endContest();
                this.testMode.pollContests();
            }
        }
        this.dataTestTransfer.clear();
    }

    private void runPart(Runnable runnable, AbsContest currContest) {
        Future future = this.threadPool.submit(runnable);
        while (!future.isDone() && !this.testDone) {
            if (currContest.isTestCondisionsFailed()) {
                stop();
                break;
            }
            if (testMode == null || testMode.isTestCondisionsFailed()) {
                stop();
                break;
            }
        }
        while (!future.isDone()) {
            currContest.stop();
            future.cancel(true);
            if (currContest.isTestCondisionsFailed()) {
                stop();
                break;
            }
            if (testMode == null || testMode.isTestCondisionsFailed()) {
                stop();
                break;
            }
            Util.delay(100);
        }
    }

    public void stop() {
        this.testDone = true;
        if (testMode != null) {
            testMode.clearContest();
        }
    }

    public boolean isRunning() {
        return !testDone;
    }

}
