/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.communication.socket.Unicast.commons;

/**
 *
 * @author Administrator
 */
public class ClientLogger extends SocketLogger {

    private final String from;
    private final String to;
    private boolean logEnable;

    public ClientLogger(String path, String host, String hostName, int port) {
        super(path);
        from = String.format("%s(%s)", Keywords.CLIENT, Keywords.THIS);
        to = String.format("%s(%s:%s)", hostName, host, port);
        this.logEnable = true;
    }

    public void logSend(String data, Object... params) {
        if (!logEnable) {
            return;
        }
        addlog(String.format("%s -> %s", from, to), data, params);
    }

    public void logReceived(String data, Object... params) {
        if (!logEnable) {
            return;
        }
        addlog(String.format("%s -> %s", to, from), data, params);
    }

    public void logConnected() {
        if (!logEnable) {
            return;
        }
        addlog(from, "----------------- Connect to %s -----------------", to);
    }

    public void logDisconnect() {
        if (!logEnable) {
            return;
        }
        addlog(from, "----------------- Disconnect to %s --------------", to);
    }

    public void setLogEnable(boolean enable) {
        this.logEnable = enable;
    }
}
