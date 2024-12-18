/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common.communication.socket.Unicast.commons;

import com.nextone.common.mylogger.MyLogger;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class ClientLogger extends MyLogger {

    private final String from;
    private final String to;
    private boolean logEnable;

    public ClientLogger(String path, String host, String hostName, int port) {
        this.setSaveMemory(true);
        this.setDailyLog(true);
        this.setFolder(new File(path));
        from = String.format("%s(%s)", Keywords.CLIENT, Keywords.THIS);
        to = String.format("%s(%s:%s)", hostName, host, port);
        this.logEnable = true;
    }

    public void logSend(String data, Object... params) {
        if (!logEnable) {
            return;
        }
        addLog(String.format("%s -> %s", from, to), data, params);
    }

    public void logReceived(String data, Object... params) {
        if (!logEnable) {
            return;
        }
        addLog(String.format("%s -> %s", to, from), data, params);
    }

    public void logConnected() {
        if (!logEnable) {
            return;
        }
        addLog(from, "----------------- Connect to %s -----------------", to);
    }

    public void logDisconnect() {
        if (!logEnable) {
            return;
        }
        addLog(from, "----------------- Disconnect to %s --------------", to);
    }

    public void setLogEnable(boolean enable) {
        this.logEnable = enable;
    }
}
