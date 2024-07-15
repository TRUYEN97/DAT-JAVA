/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.interfaces.IStarter;
import com.qt.controller.ProcessModelHandle;
import com.qt.mode.AbsTestMode;
import com.qt.model.modelTest.process.ModeParam;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ModeHandle implements IStarter, Runnable {

    private final ProcessModelHandle processModelHandle;
    private final ContestRunner contestRunner;
    private final ExecutorService threadPool;
    private AbsTestMode testMode;
    private boolean running = false;
    private boolean stop = false;
    private Future testFuture;

    public ModeHandle(ModeParam modeParam) {
        this.processModelHandle = modeParam.getProcessModelHandle();
        this.contestRunner = new ContestRunner(modeParam);
        this.threadPool = Executors.newSingleThreadExecutor();
    }

    public boolean setTestMode(AbsTestMode testMode) {
        if (testMode == null) {
            return false;
        }
        this.testMode = testMode;
        this.processModelHandle.setMode(testMode);
        this.contestRunner.setTestMode(testMode);
        return true;
    }

    @Override
    public void run() {
        try {
            if (isRunning() || this.testMode == null) {
                return;
            }
            this.stop = false;
            while (!this.stop) {
                begin();
                test();
                end();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            this.running = false;
        }
    }

    @Override
    public boolean isStarted() {
        return this.testFuture != null && !this.testFuture.isDone();
    }

    public boolean isRunning() {
        return running;
    }

    private void begin() {
        this.processModelHandle.resetModel();
        this.testMode.begin();
        this.running = true;
        this.processModelHandle.startTest();
    }

    private void test() {
        this.contestRunner.run();
    }

    private void end() {
        if (!running) {
            return;
        }
        this.running = false;
        this.processModelHandle.endTest();
        if (this.testMode != null) {
            this.testMode.end();
        }
    }

    @Override
    public void stop() {
        if (this.running) {
            return;
        }
        this.stop = true;
        while (!isStarted()) {
            this.testFuture.cancel(true);
            Util.delay(200);
        }
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        this.testFuture = this.threadPool.submit(this);
    }
}
