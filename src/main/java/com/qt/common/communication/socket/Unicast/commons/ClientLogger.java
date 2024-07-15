/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.communication.socket.Unicast.commons;


/**
 *
 * @author Administrator
 */
public class ClientLogger extends SocketLogger{

    private final String from;
    private final String to;

    public ClientLogger(String path, String host, String hostName, int port) {
        super(path);
        from = String.format("%s(%s)", Keywords.CLIENT, Keywords.THIS);
        to = String.format("%s(%s:%s)", hostName, host, port);
    }

    public void logSend(String data, Object... params) {
        addlog(String.format("%s -> %s", from, to), data, params);
    }

    public void logReceived(String data, Object... params) {
        addlog(String.format("%s -> %s", to, from), data, params);
    }

    public void logConnected() {
        addlog(from, "----------------- Connect to %s -----------------", to);
    }

    public void logDisconnect() {
        addlog(from, "----------------- Disconnect to %s --------------", to);
    }
}
