/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.communication.socket.Unicast.commons;

/**
 *
 * @author Administrator
 */
public class ServerLogger extends SocketLogger {

    private final String from;

    public ServerLogger(String path, String thisName, int port) {
        super(path);
        from = String.format("%s(%s)", thisName, port);
    }

    public synchronized void logSend(String clientName, String data, Object... params) {
        addlog(String.format("%s -> %s", from, clientName), data, params);
    }

    public synchronized void logReceived(String clientName, String data, Object... params) {
        addlog(String.format("%s -> %s", clientName, from), data, params);
    }

    public synchronized void logConnected(String clientName) {
        addlog(Keywords.SERVER, "--------------------- %s connected! -----------------", clientName);
    }

    public synchronized void logDisconnect(String clientName) {
        addlog(Keywords.SERVER, "--------------------- %s disconnected! -----------------", clientName);
    }

}
