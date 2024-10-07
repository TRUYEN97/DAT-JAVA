/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.interfaces.IStarter;
import com.qt.controller.ProcessModelHandle;
import com.qt.controller.api.PingAPI;
import com.qt.mode.AbsTestMode;
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
    private final PingAPI pingAPI;
    private AbsTestMode testMode;
    private boolean running = false;
    private boolean stop = false;
    private Future testFuture;
    private boolean wait;

    public ModeHandle() {
        this.processModelHandle = ProcessModelHandle.getInstance();
        this.contestRunner = new ContestRunner();
        this.threadPool = Executors.newSingleThreadExecutor();
        this.pingAPI = new PingAPI();
        this.pingAPI.start();
    }

    public boolean setTestMode(AbsTestMode testMode) {
        try {
            if (testMode == null) {
                return false;
            }
            this.pingAPI.setPingAPIReceive((responce) -> {
                try {
                    if (responce == null) {
                        return;
                    }
                    if (!responce.isSuccess() || responce.getData() == null) {
                        return;
                    }
                    testMode.analysisResponce(responce.getData(JSONObject.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorLog.addError(this, e);
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                }
            });
            this.testMode = testMode;
            this.testMode.setModeHandle(this);
            this.processModelHandle.setMode(testMode);
            this.contestRunner.setTestMode(testMode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            return false;
        }
    }

    public AbsTestMode getTestMode() {
        return testMode;
    }

    @Override
    public void run() {
        try {
            if (isRunning() || this.testMode == null) {
                return;
            }
            wait = false;
            while (true) {
                while (wait) {
                    Util.delay(1000);
                }
                begin();
                if (!stop) {
                    test();
                    end();
                }
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
        this.testMode.begin();
        this.running = true;
        this.stop = false;
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
        this.stop = true;
        this.contestRunner.stop();
        if (this.testMode != null) {
            this.testMode.cancelTest();
        }
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        if (this.testMode != null) {
            this.testMode.modeInit();
        }
        this.testFuture = this.threadPool.submit(this);
    }

    void setWait(boolean st) {
        this.wait = st;
    }
}
