/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.contest.impCondition.timerCondition.TimeOutContest;
import com.qt.controller.CheckConditionHandle;
import com.qt.controller.ProcessModelHandle;
import com.qt.mode.AbsTestMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Admin
 */
public class ContestRunner implements Runnable {

    private final ProcessModelHandle processlHandle;
    private final CheckConditionHandle conditionHandle;
    private final TimeOutContest timeOutContest;
    private final ExecutorService threadPool;
    private AbsTestMode testMode;
    private boolean testDone = false;

    public ContestRunner() {
        this.processlHandle = ProcessModelHandle.getInstance();
        this.timeOutContest = new TimeOutContest();
        this.threadPool = Executors.newSingleThreadExecutor();
        this.conditionHandle = new CheckConditionHandle();
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(this.timeOutContest);
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
            if (this.testMode == null || checkStopTestCondisions()) {
                stop();
                break;
            }
            if (currContest == null) {
                Util.delay(200);
                continue;
            }
            runPart(currContest.begin(), currContest);
            if (!testDone) {
                this.timeOutContest.setContest(currContest);
                runPart(currContest.test(), currContest);
                this.timeOutContest.setContest(null);
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
            if (!currContest.checkTestCondisions()) {
                stop();
                break;
            }
            if (checkStopTestCondisions()) {
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

    private boolean checkStopTestCondisions() {
        if (!this.conditionHandle.checkTestCondisions()) {
            return true;
        }
        if (processlHandle.containContestClass(ConstKey.CT_NAME.KET_THUC)) {
            return true;
        }
        if (testMode != null) {
            if (!testMode.checkTestCondisions()) {
                return true;
            }
            if (this.processlHandle.getProcessModel().getScore() < testMode.getScoreSpec()) {
                return true;
            }
        }
        return false;
    }

    public void stop() {
        this.testDone = true;
    }

    public boolean isRunning() {
        return !testDone;
    }

}
