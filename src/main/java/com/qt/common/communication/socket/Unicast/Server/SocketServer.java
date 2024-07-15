/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.communication.socket.Unicast.Server;

import com.qt.common.communication.socket.Unicast.commons.Keywords;
import com.qt.common.communication.socket.Unicast.commons.Interface.IFilter;
import com.qt.common.communication.socket.Unicast.commons.Interface.IObjectServerReceiver;
import com.qt.common.communication.socket.Unicast.commons.ServerLogger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class SocketServer extends Thread {

    private final ServerSocket serverSocket;
    private final HandleManagement handlerManager;
    private final IFilter filter;
    private final IObjectServerReceiver receiver;
    private final ExecutorService threadpool;
    private final ServerLogger logger;
    private int poolLimit;
    private boolean debug;

    public SocketServer(int port, IObjectServerReceiver receiver) throws Exception {
        this(port, null, receiver);
    }

    public SocketServer(int port, IFilter filter, IObjectServerReceiver receiver) throws Exception {
        this.serverSocket = new ServerSocket(port);
        this.handlerManager = new HandleManagement();
        this.filter = filter;
        this.receiver = receiver;
        this.threadpool = Executors.newCachedThreadPool();
        this.poolLimit = Integer.MAX_VALUE;
        this.logger = new ServerLogger("log/socket/server", Keywords.SERVER, port);
        this.logger.addlog(Keywords.SERVER, "Listen on port: %s", port);
        this.debug = false;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        this.handlerManager.setDebug(debug);
    }

    public void setPoolLimit(int poolLimit) {
        if (poolLimit <= 0) {
            return;
        }
        this.poolLimit = poolLimit;
    }

    public HandleManagement getHandlerManager() {
        return handlerManager;
    }

    public IFilter getFilter() {
        return filter;
    }

    public IObjectServerReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void run() {
        try (this.serverSocket) {
            ClientHandler handler;
            while (true) {
                handler = createHanhdler(this.serverSocket.accept());
                if (handler == null) {
                    continue;
                }
                if (this.poolLimit <= this.handlerManager.size()) {
                    handler.send("Overloading");
                    handler.disconnect();
                    continue;
                }
                handler.setDebug(debug);
                handler.setFilter(this.filter);
                handler.setObjectAnalysis(this.receiver);
                this.threadpool.execute(handler);
            }
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
                this.logger.addlog("ERROR", ex.getLocalizedMessage());
            }
        } finally {
            try (this.threadpool) {
                this.threadpool.shutdown();
            }
        }
    }

    private ClientHandler createHanhdler(Socket socket) {
        try {
            return new ClientHandler(socket, logger, handlerManager);
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
                this.logger.addlog("ERROR", e.getLocalizedMessage());
            }
            return null;
        }
    }

}
