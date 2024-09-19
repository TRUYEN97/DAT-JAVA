/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.mode.AbsTestMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Admin
 */
public class ContestRunner implements Runnable {

    private final ExecutorService threadPool;
    private AbsTestMode testMode;
    private boolean testDone = false;

    public ContestRunner() {
        this.threadPool = Executors.newSingleThreadExecutor();
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
            Util.delay(200);
        }
    }

    public void stop() {
        this.testDone = true;
    }

    public boolean isRunning() {
        return !testDone;
    }

}
