/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsCondition;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckCM;
import com.qt.contest.impCondition.OnOffImp.CheckRPM;
import com.qt.controller.ProcessModelHandle;
import com.qt.mode.AbsTestMode;
import com.qt.output.SoundPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ContestRunner implements Runnable {

    private final ProcessModelHandle processlHandle;
    private final SoundPlayer soundPlayer;
    private final List<AbsCondition> conditions;
    private AbsTestMode testMode;
    private boolean testDone = false;

    public ContestRunner() {
        this.processlHandle = ProcessModelHandle.getInstance();
        this.soundPlayer = SoundPlayer.getInstance();
        this.conditions = new ArrayList<>();
        this.conditions.add(new CheckCM());
        this.conditions.add(new CheckRPM());
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
            Util.delay(100);
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
                Util.delay(50);
            }
            currContest.end();
        }
    }

    private void end(AbsContest currContest) {
        if (this.testMode != null) {
            this.testMode.endContest();
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
        for (AbsCondition condition : conditions) {
            if (!condition.checkPassed() && condition.isImportant()) {
                return true;
            }
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

    public boolean isRunning() {
        return !testDone;
    }

}
