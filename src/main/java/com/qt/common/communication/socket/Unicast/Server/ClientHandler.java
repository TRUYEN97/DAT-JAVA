/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.communication.socket.Unicast.Server;

import com.qt.common.communication.socket.Unicast.commons.Interface.IIsConnect;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import com.qt.common.communication.socket.Unicast.commons.Interface.Idisconnect;
import com.qt.common.communication.socket.Unicast.commons.Keywords;
import com.qt.common.communication.socket.Unicast.commons.Interface.IFilter;
import com.qt.common.communication.socket.Unicast.commons.Interface.IObjectServerReceiver;
import com.qt.common.communication.socket.Unicast.commons.ServerLogger;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author Administrator
 */
public class ClientHandler implements Runnable, Idisconnect, IIsConnect, Closeable {

    private final Socket socket;
    private final PrintWriter outputStream;
    private final BufferedReader inputStream;
    private final HandleManagement handlerManager;
    private final ServerLogger logger;
    private IObjectServerReceiver objectAnalysis;
    private IFilter filter;
    private boolean connect;
    private boolean debug;
    private static long number = 0;
    private String name;

    public ClientHandler(Socket socket, ServerLogger logger, HandleManagement handlerManager) throws IOException {
        this.socket = socket;
        this.logger = logger;
        this.outputStream = new PrintWriter(socket.getOutputStream(), true);
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.connect = true;
        this.handlerManager = handlerManager;
        this.name = String.format("Client-%s(%s)", ClientHandler.number++,
                socket.getInetAddress().getHostAddress());
        this.logger.logConnected(name);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getName() {
        return name;
    }

    public void setObjectAnalysis(IObjectServerReceiver objectAnalysis) {
        if (objectAnalysis == null) {
            return;
        }
        this.objectAnalysis = objectAnalysis;
    }

    public String getHostAddress() {
        InetAddress address;
        address = socket.getInetAddress();
        if (address == null) {
            return null;
        }
        return address.getHostAddress();
    }

    public int getPort() {
        return this.socket.getPort();
    }

    @Override
    public boolean isConnect() {
        return this.socket != null && this.socket.isConnected() && connect;
    }

    public void setFilter(IFilter filter) {
        this.filter = filter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            if (this.filter == null || (name = this.filter.filter(this)) != null) {
                this.handlerManager.add(name, this);
                String data;
                while ((data = readLine()) != null) {
                    if (data.trim().isBlank()) {
                        continue;
                    }
                    this.logger.logReceived(name, data);
                    if (this.objectAnalysis != null) {
                        this.objectAnalysis.receiver(this, data);
                    }
                }
            }
        } catch (IOException e) {
            if (debug) {
                e.printStackTrace();
            }
            this.logger.addLog("ERROR", e.getLocalizedMessage());
        } finally {
            disconnect();
        }
    }

    public String readLine() throws IOException {
        String line = this.inputStream.readLine();
        if (line != null) {
            return line.replaceAll("<newline>", "\r\n");
        }
        return null;
    }

    @Override
    public boolean disconnect() {
        try (socket; outputStream; inputStream) {
            this.handlerManager.disconnect(this);
            this.connect = false;
            this.logger.addLog(Keywords.SERVER, "%s disconnected! - ip: %s", this.name, socket.getLocalSocketAddress());
            return true;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
                this.logger.addLog("ERROR", e.getLocalizedMessage());
            }
            return false;
        }
    }

    public boolean send(String data) {
        try {
            if (!isConnect()) {
                return false;
            }
            data = data.replaceAll("\r\n", "<newline>");
            this.outputStream.println(data);
            this.logger.logSend(name, data);
            return true;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
                this.logger.addLog("ERROR", e.getLocalizedMessage());
            }
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

}
