/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.pretreatment;

import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.input.serial.MCUSerialHandler;
import com.qt.interfaces.IStarter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Admin
 */
public class KeyEventManagement implements IStarter {

    private static volatile KeyEventManagement instance;
    private final Queue<String> keys;
    private final List<KeyEventsPackage> keyEvensPackages;
    private final ExecutorService thread;
    private boolean running = false;
    private boolean stop = false;
    private Future future;

    private KeyEventManagement() {
        this.keys = MCUSerialHandler.getInstance().getModel().getRemoteValues();
        this.keyEvensPackages = new ArrayList<>();
        this.thread = Executors.newSingleThreadExecutor();
    }

    public static KeyEventManagement getInstance() {
        KeyEventManagement ins = instance;
        if (ins == null) {
            synchronized (KeyEventManagement.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new KeyEventManagement();
                }
            }
        }
        return ins;
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        this.future = this.thread.submit(() -> {
            running = true;
            stop = false;
            IKeyEvent event;
            String key;
            KeyEventsPackage evensPackage;
            while (!stop) {
                try {
                    Util.delay(100);
                    if (keys.isEmpty()) {
                        continue;
                    }
                    key = keys.poll();
                    System.out.println(key);
                    for (int i = this.keyEvensPackages.size() - 1; i >= 0; i--) {
                        evensPackage = this.keyEvensPackages.get(i);
                        if (evensPackage == null) {
                            continue;
                        }
                        evensPackage.attack(key);
                        if (evensPackage.isJustMe()) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorLog.addError(this, e);
                }
            }
            running = false;
        });
    }

    @Override
    public void stop() {
        if (this.future == null || this.future.isDone()) {
            return;
        }
        while (isRunning()) {
            this.stop = true;
            this.future.cancel(true);
            Util.delay(200);
        }
    }

    public boolean isRunning() {
        return this.future != null && !this.future.isDone() && this.running;
    }

    public void addKeyEventBackAge(KeyEventsPackage evensPackage) {
        if (evensPackage == null) {
            return;
        }
        for (KeyEventsPackage keyEvensPackage : keyEvensPackages) {
            if (keyEvensPackage.equals(evensPackage) || keyEvensPackage.getName()
                    .equalsIgnoreCase(evensPackage.getName())) {
                return;
            }
        }
        this.keyEvensPackages.add(evensPackage);
    }

    public void remove(String name) {
        List<KeyEventsPackage> removes = new ArrayList<>();
        for (KeyEventsPackage keyEvensPackage : keyEvensPackages) {
            if (keyEvensPackage.isName(name)) {
                removes.add(keyEvensPackage);
            }
        }
        keyEvensPackages.removeAll(removes);
    }

    public void remove(KeyEventsPackage keyEvensPackage) {
        keyEvensPackages.remove(keyEvensPackage);
    }

    public void clearBaseKey() {
        this.keyEvensPackages.clear();
    }

    @Override
    public boolean isStarted() {
        return this.future != null && !this.future.isDone();
    }

}
