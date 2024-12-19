/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common.communication.socket.Unicast.Client;

import com.nextone.common.communication.socket.Unicast.commons.ClientLogger;
import com.nextone.common.communication.socket.Unicast.commons.Interface.IIsConnect;
import java.net.Socket;
import com.nextone.common.communication.socket.Unicast.commons.Interface.Idisconnect;
import com.nextone.common.communication.socket.Unicast.commons.Keywords;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Closeable;
import java.io.IOException;
import com.nextone.common.communication.socket.Unicast.commons.Interface.IReceiver;

/**
 *
 * @author Administrator
 */
public class SocketClient implements Runnable, Idisconnect, IIsConnect, Closeable {

    private final String host;
    private final String hostName;
    private final int port;
    private final IReceiver<SocketClient> clientReceiver;
    private final ClientLogger logger;
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private boolean connect;
    private boolean debug;

    public SocketClient(String host, int port, IReceiver objectAnalysis) {
        this(Keywords.SERVER, host, port, objectAnalysis);
    }

    public SocketClient(String hostName, String host, int port, IReceiver objectAnalysis) {
        this.host = host;
        this.hostName = hostName;
        this.port = port;
        this.logger = new ClientLogger("log/socket/client", host, hostName, port);
        this.clientReceiver = objectAnalysis;
        this.debug = false;
    }
    
    public void setLogEnable(boolean enable){
        this.logger.setLogEnable(enable);
    }

    public String getHostName() {
        return hostName;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getHost() {
        return host;
    }

    public String readLine() throws IOException {
        String line = this.inputStream.readLine();
        if (line != null) {
            return line;
        }
        return null;
    }

    public int getPort() {
        return port;
    }

    public boolean connect() {
        try {
            this.socket = new Socket(host, port);
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.logger.logConnected();
            this.connect = true;
            return true;
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
                this.logger.addLog(Keywords.ERROR, ex.getLocalizedMessage());
            }
            return false;
        }
    }

    public boolean send(String data) {
        try {
            if (!isConnect()) {
                return false;
            }
            this.outputStream.println(data);
            this.logger.logSend(data);
            return true;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
                this.logger.addLog(Keywords.ERROR, e.getLocalizedMessage());
            }
            return false;
        }
    }

    @Override
    public void run() {
        try {
            String data;
            while (isConnect() && (data = readLine()) != null) {
                if (data.trim().isBlank()) {
                    continue;
                }
                data = data.replaceAll("<newline>", "\r\n");
                this.logger.logReceived(data);
                if (this.clientReceiver != null) {
                    this.clientReceiver.receiver(this, data);
                }
            }
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
                this.logger.addLog("ERROR", ex.getLocalizedMessage());
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean isConnect() {
        return this.socket != null && this.socket.isConnected() && connect;
    }

    @Override
    public boolean disconnect() {
        if (!isConnect()) {
            return true;
        }
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            connect = false;
            this.logger.logDisconnect();
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
